package isi.cinema.controller;

import isi.cinema.DTO.MovieDTO;
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

    @GetMapping("{cinemaName}/movies")
    public ResponseEntity<List<MovieDTO>> getAllMoviesWithScreeningSchedules(@PathVariable String cinemaName) {
        List<MovieDTO> moviesWithScreeningSchedules = movieService.findAllMoviesWithScreeningSchedulesByCinemaName(cinemaName);
        return ResponseEntity.ok(moviesWithScreeningSchedules);
    }

    @GetMapping("{cinemaName}/movies/{movieId}")
    public ResponseEntity<List<MovieDTO>> getMovieById(@PathVariable Long movieId) {
        List<MovieDTO> movie = movieService.findMovieById(movieId);
        return ResponseEntity.ok(movie);
    }
}
