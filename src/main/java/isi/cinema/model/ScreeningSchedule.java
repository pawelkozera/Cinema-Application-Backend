package isi.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@Getter
@Setter
public class ScreeningSchedule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    private String format;
    private List<String> takenSeats;

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
}
