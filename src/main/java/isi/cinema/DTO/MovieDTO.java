package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDTO {
    private Long id;
    private String title;
    private String category;
    private String type;
    private String ageRating;
    private String description;
    private int length;
    private String countryProduction;
    private String yearProduction;
    private String imageUrl;

    private List<Long> screeningScheduleIds;
    private List<Long> cinemaIds;

    private List<ScreeningScheduleDTO> screeningDates;
}
