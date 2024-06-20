package isi.cinema.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@Getter
@Setter
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

    @OneToOne(mappedBy = "ticket")
    private Order order;

    protected Ticket() {}

    public Ticket(String price, List<String> seats, int amount, User user, Movie movie, ScreeningSchedule screeningSchedule) {
        this.price = price;
        this.seats = seats;
        this.amount = amount;
        this.user = user;
        this.movie = movie;
        this.screeningSchedule = screeningSchedule;
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
