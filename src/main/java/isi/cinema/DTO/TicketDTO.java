package isi.cinema.DTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TicketDTO {
    private UUID uuid;
    private String price;
    private List<String> seats;
    private int amount;
    private String movieTitle;
    private Date screeningDate;
    private String screeningFormat;

    public UUID getUuid() {
        return uuid;
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

    public String getMovieTitle() {
        return movieTitle;
    }

    public Date getScreeningDate() {
        return screeningDate;
    }

    public String getScreeningFormat() {
        return screeningFormat;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setScreeningDate(Date screeningDate) {
        this.screeningDate = screeningDate;
    }

    public void setScreeningFormat(String screeningFormat) {
        this.screeningFormat = screeningFormat;
    }
}