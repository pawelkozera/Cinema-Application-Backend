package isi.cinema.controller;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.User;
import isi.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/movie")
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

    @PostMapping("/addMovie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie addedMovie = movieService.addMovie(movie);

        return new ResponseEntity<>(addedMovie, HttpStatus.CREATED);
    }

    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }
}
