package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class ScreeningScheduleDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String format;
    @Getter
    @Setter
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
