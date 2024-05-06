package isi.cinema.model;

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
    private Date date;
    private String format;

    @ManyToMany(mappedBy = "screeningSchedules", fetch = FetchType.LAZY)
    private List<Movie> movies;

    protected ScreeningSchedule() {}

    public ScreeningSchedule(Date date, String format) {
        this.date = date;
        this.format = format;
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
}
