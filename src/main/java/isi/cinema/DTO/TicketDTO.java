package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TicketDTO {
    private UUID uuid;
    private String price;
    private List<String> seats;
    private int amount;
    private String movieTitle;
    private Date screeningDate;
    private String screeningFormat;
    private boolean isPaid;
    private String imageUrl;
}