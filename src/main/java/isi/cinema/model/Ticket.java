package isi.cinema.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
   // @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;
    private String price;
    private List<String> seats;
    private int amount;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="scheduleId")
    private ScreeningSchedule screeningSchedule;

    protected Ticket() {}

    public Ticket(String price, List<String> seats, int amount, User user, Movie movie, ScreeningSchedule screeningSchedule) {
        this.price = price;
        this.seats = seats;
        this.amount = amount;
        this.user = user;
        this.movie = movie;
        this.screeningSchedule = screeningSchedule;
    }

    public ScreeningSchedule getScreeningSchedule() {
        return screeningSchedule;
    }

    public void setScreeningSchedule(ScreeningSchedule screeningSchedule) {
        this.screeningSchedule = screeningSchedule;
    }


    public String getPrice() {
        return price;
    }

    public List<String> getSeats() {
        return seats;
    }

    public int getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "uuid=" + uuid +
                ", price='" + price + '\'' +
                ", seats=" + seats +
                ", amount=" + amount +
                ", user=" + user +
                ", movie=" + movie +
                ", screeningSchedule=" + screeningSchedule +
                '}';
    }
}
