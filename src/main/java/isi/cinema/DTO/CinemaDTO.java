package isi.cinema.DTO;

import isi.cinema.model.Movie;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CinemaDTO {
    private Long id;
    private String name;
    private List<Movie> movies;

    public CinemaDTO() {
    }

    public CinemaDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
