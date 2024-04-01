package isi.cinema.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String price;
    private List<String> seats;
    private int amount;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="movieId")
    private Movie movie;

    protected Ticket() {}

    public Ticket(String price, List<String> seats, int amount, User user) {
        this.price = price;
        this.seats = seats;
        this.amount = amount;
        this.user = user;
    }
}
