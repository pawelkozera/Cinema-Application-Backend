package isi.cinema.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    private String ageRating;
    private String description;
    private int length;
    private String countryProduction;
    private String yearProduction;
    private String category;
    private String type;
    private String imageUrl;

    @OneToMany(mappedBy = "movie")
    private List<Ticket> tickets;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movieScreeningSchedule",
            joinColumns = @JoinColumn(name = "screeningScheduleId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movieId",
                    referencedColumnName = "id"))
    private List<ScreeningSchedule> screeningSchedules;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "movieCinema",
            joinColumns = @JoinColumn(name = "cinemaId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movieId",
                    referencedColumnName = "id"))
    private List<Cinema> cinemas;

    public Movie() {}

    public Movie(String title, String ageRating, String description, int length, String countryProduction, String yearProduction, String category, String type, String imageUrl, List<ScreeningSchedule> screeningSchedules, List<Cinema> cinemas) {
        this.title = title;
        this.ageRating = ageRating;
        this.description = description;
        this.length = length;
        this.countryProduction = countryProduction;
        this.yearProduction = yearProduction;
        this.category = category;
        this.type = type;
        this.imageUrl = imageUrl;
        this.screeningSchedules = screeningSchedules;
        this.cinemas = cinemas;
    }
}
