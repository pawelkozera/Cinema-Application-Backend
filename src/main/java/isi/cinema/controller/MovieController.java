package isi.cinema.controller;

import isi.cinema.model.Movie;
import isi.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Map<String, Object>>> getAllMoviesWithScreeningSchedules() {
        List<Map<String, Object>> moviesWithScreeningSchedules = movieService.findAllMoviesWithScreeningSchedulesByCinemaName();
        return ResponseEntity.ok(moviesWithScreeningSchedules);
    }
}
