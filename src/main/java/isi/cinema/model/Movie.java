package isi.cinema.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "movie")
    private List<Ticket> tickets;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movieScreeningSchedule",
            joinColumns = @JoinColumn(name = "screeningScheduleId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movieId",
                    referencedColumnName = "id"))
    private List<ScreeningSchedule> screeningSchedules;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movieCinema",
            joinColumns = @JoinColumn(name = "cinemaId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movieId",
                    referencedColumnName = "id"))
    private List<Cinema> cinemas;

    protected Movie() {}

    public Movie(String title, String ageRating, String description, int length, String countryProduction, String yearProduction) {
        this.title = title;
        this.ageRating = ageRating;
        this.description = description;
        this.length = length;
        this.countryProduction = countryProduction;
        this.yearProduction = yearProduction;
    }
}
