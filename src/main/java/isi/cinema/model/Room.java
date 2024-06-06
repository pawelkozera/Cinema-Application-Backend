package isi.cinema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private List<String> seats;

    @ManyToOne
    @JoinColumn(name="cinemaId")
    private Cinema cinema;

    @OneToMany(mappedBy = "room")
    private List<ScreeningSchedule> screeningSchedules;

    protected Room() {}

    public Room(String name, List<String> seats) {
        this.name = name;
        this.seats = seats;
    }
}
