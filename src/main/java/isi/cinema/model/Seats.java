package isi.cinema.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Seats {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private List<String> seats;

    protected Seats() {}

    public Seats(List<String> seats) {
        this.seats = seats;
    }
}
