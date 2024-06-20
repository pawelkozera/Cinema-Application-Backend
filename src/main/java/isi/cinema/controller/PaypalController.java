package isi.cinema.controller;

import isi.cinema.model.Order;
import isi.cinema.repository.OrderRepository;
import isi.cinema.service.OrderService;
import isi.cinema.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaypalController {
    private final PaypalService service;
    private final OrderService orderService;

    @Autowired
    public PaypalController(PaypalService service, OrderService orderService) {
        this.orderService = orderService;
        this.service = service;
    }

    public static final String SUCCESS_URL = "/pay/success";
    public static final String CANCEL_URL = "/movies";

    @PostMapping("/pay")
    public ResponseEntity<String> payment(@RequestBody Order order, @RequestParam("cinemaName") String cinemaName) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:5173/" + cinemaName + CANCEL_URL,
                    "http://localhost:5173/" + cinemaName + SUCCESS_URL);

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    System.out.println("Approval URL: " + link.getHref());
                    orderService.createOrderFromPayment(payment.getId(), order);

                    return ResponseEntity.ok(link.getHref());
                }
            }
            System.out.println("No approval URL found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No approval_url found");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PayPal error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment creation failed: " + e.getMessage());
        }
    }

    @PostMapping("/execute")
    public String executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                orderService.updateOrder(paymentId, payerId);

                return "completed";
            } else {
                return "failed";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "failed";
        }
    }

}