package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserMovieHistoryDTO {
    private String title;
    private UUID ticketUUID;
    private String type;
    private String imageUrl;

    public UserMovieHistoryDTO() {};

    public UserMovieHistoryDTO(String title, UUID ticketUUID, String type, String imageUrl) {
        this.title = title;
        this.ticketUUID = ticketUUID;
        this.type = type;
        this.imageUrl = imageUrl;
    }
}
