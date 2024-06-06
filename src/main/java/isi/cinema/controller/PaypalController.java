package isi.cinema.controller;

import isi.cinema.model.Order;
import isi.cinema.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "/pay/success";
    public static final String CANCEL_URL = "/pay/cancel";

    @PostMapping("/pay")
    public String payment(@RequestBody Order order, @RequestParam("cinemaName") String cinemaName) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:5173/" + cinemaName + CANCEL_URL,
                    "http://localhost:5173/" + cinemaName + SUCCESS_URL);

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("/execute")
    public String executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                service.createOrderFromPayment(paymentId, payerId);
                return "completed";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "failed";
    }
}