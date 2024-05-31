package isi.cinema.DTO;

import isi.cinema.model.Movie;
import jakarta.persistence.ManyToMany;

import java.util.List;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
