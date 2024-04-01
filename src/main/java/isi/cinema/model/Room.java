package isi.cinema.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private List<String> seats;

    protected Room() {}

    public Room(String name, List<String> seats) {
        this.name = name;
        this.seats = seats;
    }
}
