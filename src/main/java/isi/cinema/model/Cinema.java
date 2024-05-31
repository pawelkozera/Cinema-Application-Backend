package isi.cinema.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
public class Cinema {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "cinemas")
    private List<Movie> movies;

    @OneToMany(mappedBy = "cinema")
    private List<Room> rooms;

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
