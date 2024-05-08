package isi.cinema.DTO;

import java.util.Date;

public class ScreeningScheduleDTO {
    private Long id;
    private Date date;
    private String format;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getFormat() {
        return format;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
