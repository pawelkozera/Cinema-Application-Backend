package isi.cinema.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class ScreeningSchedule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date date;
    private String format;

    @ManyToMany(mappedBy = "screeningSchedules")
    private List<Movie> movies;

    protected ScreeningSchedule() {}

    public ScreeningSchedule(Date date, String format) {
        this.date = date;
        this.format = format;
    }
}
