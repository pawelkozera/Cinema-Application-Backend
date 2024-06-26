package isi.cinema.controller;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.Role;
import isi.cinema.model.User;
import isi.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDTO, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<Movie> authorizationResponse = (ResponseEntity<Movie>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        Movie addedMovie = movieService.addMovie(movieDTO);

        return new ResponseEntity<>(addedMovie, HttpStatus.CREATED);
    }

    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<String> deleteMovie(@PathVariable Long id, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<String> authorizationResponse = (ResponseEntity<String>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        String response = movieService.deleteMovieById(id);
        if ("Cannot delete movie with tickets".equals(response)) {
            return new ResponseEntity<>("Cannot delete movie with tickets", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movie, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<Movie> authorizationResponse = (ResponseEntity<Movie>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        Movie updatedMovie = movieService.updateMovie(id, movie);
        if(updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        }
        return ResponseEntity.notFound().build();
    }
}
