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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }
}
