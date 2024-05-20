package isi.cinema.service;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.Cinema;
import isi.cinema.model.Movie;
import isi.cinema.model.Ticket;
import isi.cinema.model.User;
import isi.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovieById(Long id) {
        Movie movieToDelete = movieRepository.findById(id).orElse(null);

        if (movieToDelete != null) {
            for (Cinema cinema : movieToDelete.getCinemas()) {
                cinema.getMovies().remove(movieToDelete);
            }

            for (Ticket ticket : movieToDelete.getTickets()) {
                if (ticket.getMovie() != null) {
                    return;
                }
            }

            movieToDelete.getCinemas().clear();
            movieToDelete.getScreeningSchedules().clear();
            movieToDelete.getTickets().clear();

            movieRepository.delete(movieToDelete);
        }
    }


    public Movie updateMovie(Long id, Movie updatedMovie) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if(movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            movie.setTitle(updatedMovie.getTitle());
            movie.setScreeningSchedules(updatedMovie.getScreeningSchedules());
            movie.setAgeRating(updatedMovie.getAgeRating());
            movie.setDescription(updatedMovie.getDescription());
            movie.setLength(updatedMovie.getLength());
            movie.setCountryProduction(updatedMovie.getCountryProduction());
            movie.setYearProduction(updatedMovie.getYearProduction());

            return movieRepository.save(movie);
        }
        return  null;
    }

    public List<MovieDTO> findAllMoviesWithScreeningSchedulesByCinemaName(String cinemaName) {
        List<Movie> movies = movieRepository.findAllMoviesByCinemaName(cinemaName);

        return movies.stream().map(movie -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setCategory(movie.getCategory());
            movieDTO.setType(movie.getType());

            List<ScreeningScheduleDTO> screeningDates = movie.getScreeningSchedules().stream()
                    .map(screeningSchedule -> {
                        ScreeningScheduleDTO scheduleDTO = new ScreeningScheduleDTO();
                        scheduleDTO.setId(screeningSchedule.getId());
                        scheduleDTO.setDate(screeningSchedule.getDate());
                        scheduleDTO.setFormat(screeningSchedule.getFormat());
                        return scheduleDTO;
                    })
                    .collect(Collectors.toList());

            movieDTO.setScreeningDates(screeningDates);

            return movieDTO;
        }).collect(Collectors.toList());
    }

    public List<MovieDTO> findMovieById(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            return Collections.emptyList();
        }

        Movie movie = movieOptional.get();
        List<MovieDTO> movieDTOs = new ArrayList<>();

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setCategory(movie.getCategory());
        movieDTO.setType(movie.getType());
        movieDTO.setAgeRating(movie.getAgeRating());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setLength(movie.getLength());
        movieDTO.setCountryProduction(movie.getCountryProduction());
        movieDTO.setYearProduction(movie.getYearProduction());

        List<ScreeningScheduleDTO> screeningScheduleDTOs = movie.getScreeningSchedules().stream()
                .map(screeningSchedule -> {
                    ScreeningScheduleDTO scheduleDTO = new ScreeningScheduleDTO();
                    scheduleDTO.setId(screeningSchedule.getId());
                    scheduleDTO.setDate(screeningSchedule.getDate());
                    scheduleDTO.setFormat(screeningSchedule.getFormat());
                    return scheduleDTO;
                })
                .collect(Collectors.toList());

        movieDTO.setScreeningDates(screeningScheduleDTOs);
        movieDTOs.add(movieDTO);

        return movieDTOs;
    }
}