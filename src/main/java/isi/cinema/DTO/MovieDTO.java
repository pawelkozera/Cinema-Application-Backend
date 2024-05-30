package isi.cinema.DTO;

import java.util.List;

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

    private List<Long> screeningScheduleIds;
    private List<Long> cinemaIds;

    private List<ScreeningScheduleDTO> screeningDates;

    public List<Long> getScreeningScheduleIds() {
        return screeningScheduleIds;
    }

    public List<Long> getCinemaIds() {
        return cinemaIds;
    }

    public void setScreeningScheduleIds(List<Long> screeningScheduleIds) {
        this.screeningScheduleIds = screeningScheduleIds;
    }

    public void setCinemaIds(List<Long> cinemaIds) {
        this.cinemaIds = cinemaIds;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public List<ScreeningScheduleDTO> getScreeningDates() {
        return screeningDates;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public void setScreeningDates(List<ScreeningScheduleDTO> screeningDates) {
        this.screeningDates = screeningDates;
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
}
