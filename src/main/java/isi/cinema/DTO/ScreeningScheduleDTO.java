package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ScreeningScheduleDTO {
    private Long id;
    private Date date;
    private String format;
    private List<String> takenSeats;

    public ScreeningScheduleDTO() {
    }

    public ScreeningScheduleDTO(Long id, Date date, String format) {
        this.id = id;
        this.date = date;
        this.format = format;
    }

    public ScreeningScheduleDTO(List<String> takenSeats) {
        this.takenSeats = takenSeats;
    }
}
