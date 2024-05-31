package isi.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
public class ScreeningSchedule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    private String format;

    @ManyToMany(mappedBy = "screeningSchedules", fetch = FetchType.LAZY)
    private List<Movie> movies;

    @OneToMany(mappedBy = "screeningSchedule", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name="roomId")
    private Room room;

    protected ScreeningSchedule() {}

    public ScreeningSchedule(Date date, String format) {
        this.date = date;
        this.format = format;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }


    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getFormat() {
        return format;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
