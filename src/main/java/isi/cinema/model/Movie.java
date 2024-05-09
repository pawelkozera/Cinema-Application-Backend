package isi.cinema.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
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

    protected Movie() {}

    public Movie(String title, String ageRating, String description, int length, String countryProduction, String yearProduction, String category, String type) {
        this.title = title;
        this.ageRating = ageRating;
        this.description = description;
        this.length = length;
        this.countryProduction = countryProduction;
        this.yearProduction = yearProduction;
        this.category = category;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getDescription() {
        return description;
    }

    public int getLength() {
        return length;
    }

    public String getCountryProduction() {
        return countryProduction;
    }

    public String getYearProduction() {
        return yearProduction;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<ScreeningSchedule> getScreeningSchedules() {
        return screeningSchedules;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setCountryProduction(String countryProduction) {
        this.countryProduction = countryProduction;
    }

    public void setYearProduction(String yearProduction) {
        this.yearProduction = yearProduction;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setScreeningSchedules(List<ScreeningSchedule> screeningSchedules) {
        this.screeningSchedules = screeningSchedules;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }
}
