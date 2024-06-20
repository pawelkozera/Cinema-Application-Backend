package isi.cinema.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String paymentId;
    private String payerId;
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private boolean isPaid;

    @OneToOne
    @JoinColumn(name="ticketId")
    private Ticket ticket;

    public Order() {

    }

    public Order(double price, String currency, String method, String intent, String description, boolean isPaid, String paymentId, String payerId, Ticket ticket) {
        this.price = price;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.isPaid = isPaid;
        this.paymentId = paymentId;
        this.payerId = payerId;
        this.ticket = ticket;
    }
}