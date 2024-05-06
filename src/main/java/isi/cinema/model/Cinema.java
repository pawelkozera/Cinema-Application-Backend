package isi.cinema.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cinema {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "cinemas")
    private List<Movie> movies;

    protected Cinema() {}

    public Cinema(String name) {
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
}
