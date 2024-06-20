package isi.cinema.controller;

import isi.cinema.DTO.PaymentResponseDTO;
import isi.cinema.model.Order;
import isi.cinema.model.Ticket;
import isi.cinema.repository.OrderRepository;
import isi.cinema.repository.TicketRepository;
import isi.cinema.service.OrderService;
import isi.cinema.service.PaypalService;
import isi.cinema.service.ScreeningScheduleService;
import isi.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaypalController {
    private final PaypalService service;
    private final OrderService orderService;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final ScreeningScheduleService screeningScheduleService;

    @Autowired
    public PaypalController(PaypalService service, OrderService orderService, TicketService ticketService, TicketRepository ticketRepository, ScreeningScheduleService screeningScheduleService) {
        this.orderService = orderService;
        this.service = service;
        this.ticketService = ticketService;
        this.screeningScheduleService = screeningScheduleService;
        this.ticketRepository = ticketRepository;
    }

    public static final String SUCCESS_URL = "/pay/success";
    public static final String CANCEL_URL = "/movies";

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponseDTO> payment(@RequestBody Order order, @RequestParam("cinemaName") String cinemaName, Authentication authentication) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:5173/" + cinemaName + CANCEL_URL,
                    "http://localhost:5173/" + cinemaName + SUCCESS_URL);

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    System.out.println("Approval URL: " + link.getHref());

                    Ticket ticket = null;
                    if (authentication != null && authentication.isAuthenticated()) {
                        String username = authentication.getName();
                        ticket = ticketService.bookTicket(order.getTicket(), username);
                    } else {
                        ticket = ticketService.bookTicket(order.getTicket(), null);
                    }
                    order.setTicket(ticket);
                    orderService.createOrderFromPayment(payment.getId(), order);

                    PaymentResponseDTO response = new PaymentResponseDTO();
                    response.setApprovalUrl(link.getHref());
                    response.setTicketId(ticket != null ? ticket.getUuid().toString() : null);

                    return ResponseEntity.ok(response);
                }
            }
            System.out.println("No approval URL found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PayPal error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/execute")
    public ResponseEntity<Ticket> executePayment(@RequestBody Ticket ticket, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                Ticket bookedTicket = null;

                Optional<Ticket> existingTicketOptional = ticketRepository.findByUuid(ticket.getUuid());
                if (existingTicketOptional.isPresent()) {
                    bookedTicket = existingTicketOptional.get();

                    long screeningScheduleId = bookedTicket.getScreeningSchedule().getId();
                    screeningScheduleService.addTakenSeats(bookedTicket.getSeats(), screeningScheduleId);
                }

                orderService.updateOrder(paymentId, payerId);

                return new ResponseEntity<>(bookedTicket, HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}