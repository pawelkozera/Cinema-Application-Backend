package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String name;
    private List<String> seats;

    public RoomDTO() {
    }

    public RoomDTO (List<String> seats) {
        this.seats = seats;
    }
}
