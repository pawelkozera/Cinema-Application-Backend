package isi.cinema.service;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.*;
import isi.cinema.repository.CinemaRepository;
import isi.cinema.repository.MovieRepository;
import isi.cinema.repository.ScreeningScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final ScreeningScheduleRepository screeningScheduleRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, CinemaRepository cinemaRepository, ScreeningScheduleRepository screeningScheduleRepository) {
        this.movieRepository = movieRepository;
        this.cinemaRepository = cinemaRepository;
        this.screeningScheduleRepository = screeningScheduleRepository;
    }

    public Movie addMovie(MovieDTO movieDTO) {
        List<ScreeningSchedule> screeningSchedules = movieDTO.getScreeningScheduleIds().stream()
                .map(screeningScheduleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<Cinema> cinemas = movieDTO.getCinemaIds().stream()
                .map(cinemaRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        Movie movie = new Movie(
                movieDTO.getTitle(),
                movieDTO.getAgeRating(),
                movieDTO.getDescription(),
                movieDTO.getLength(),
                movieDTO.getCountryProduction(),
                movieDTO.getYearProduction(),
                movieDTO.getCategory(),
                movieDTO.getType(),
                movieDTO.getImageUrl(),
                screeningSchedules,
                cinemas
        );
        return movieRepository.save(movie);
    }

    public String deleteMovieById(Long id) {
        Movie movieToDelete = movieRepository.findById(id).orElse(null);

        if (movieToDelete != null) {
            if (movieToDelete.getScreeningSchedules() != null) {
                for (Cinema cinema : movieToDelete.getCinemas()) {
                    cinema.getMovies().remove(movieToDelete);
                }
            }

            if (movieToDelete.getTickets() != null) {
                for (Ticket ticket : movieToDelete.getTickets()) {
                    if (ticket.getMovie() != null) {
                        return "Cannot delete movie with tickets";
                    }
                }
            }

            movieRepository.delete(movieToDelete);

            return "Movie deleted successfully";
        }

        return "Cannot delete movie";
    }


    public Movie updateMovie(Long id, MovieDTO updatedMovie) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            movie.setTitle(updatedMovie.getTitle());
            movie.setAgeRating(updatedMovie.getAgeRating());
            movie.setDescription(updatedMovie.getDescription());
            movie.setLength(updatedMovie.getLength());
            movie.setCountryProduction(updatedMovie.getCountryProduction());
            movie.setYearProduction(updatedMovie.getYearProduction());
            movie.setCategory(updatedMovie.getCategory());
            movie.setType(updatedMovie.getType());
            movie.setImageUrl(updatedMovie.getImageUrl());

            List<ScreeningSchedule> screeningSchedules = updatedMovie.getScreeningScheduleIds().stream()
                    .map(screeningScheduleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            movie.setScreeningSchedules(screeningSchedules);

            return movieRepository.save(movie);
        }
        return null;
    }

    public List<MovieDTO> findAllMoviesWithScreeningSchedulesByCinemaName(String cinemaName) {
        List<Movie> movies = movieRepository.findAllMoviesByCinemaName(cinemaName);

        return movies.stream().map(movie -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setCategory(movie.getCategory());
            movieDTO.setType(movie.getType());
            movieDTO.setImageUrl(movie.getImageUrl());
            movieDTO.setAgeRating(movie.getAgeRating());
            movieDTO.setDescription(movie.getDescription());
            movieDTO.setLength(movie.getLength());
            movieDTO.setCountryProduction(movie.getCountryProduction());
            movieDTO.setYearProduction(movie.getYearProduction());

            List<ScreeningScheduleDTO> screeningDates = new ArrayList<>();
            if (movie.getScreeningSchedules() != null) {
                screeningDates = movie.getScreeningSchedules().stream()
                        .map(screeningSchedule -> {
                            ScreeningScheduleDTO scheduleDTO = new ScreeningScheduleDTO();
                            scheduleDTO.setId(screeningSchedule.getId());
                            scheduleDTO.setDate(screeningSchedule.getDate());
                            scheduleDTO.setFormat(screeningSchedule.getFormat());
                            return scheduleDTO;
                        })
                        .collect(Collectors.toList());
            }

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
        movieDTO.setImageUrl(movie.getImageUrl());
        movieDTO.setAgeRating(movie.getAgeRating());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setLength(movie.getLength());
        movieDTO.setCountryProduction(movie.getCountryProduction());
        movieDTO.setYearProduction(movie.getYearProduction());

        List<ScreeningScheduleDTO> screeningScheduleDTOs = new ArrayList<>();
        if (movie.getScreeningSchedules() != null) {
            screeningScheduleDTOs = movie.getScreeningSchedules().stream()
                    .map(screeningSchedule -> {
                        ScreeningScheduleDTO scheduleDTO = new ScreeningScheduleDTO();
                        scheduleDTO.setId(screeningSchedule.getId());
                        scheduleDTO.setDate(screeningSchedule.getDate());
                        scheduleDTO.setFormat(screeningSchedule.getFormat());
                        return scheduleDTO;
                    })
                    .collect(Collectors.toList());
        }

        movieDTO.setScreeningDates(screeningScheduleDTOs);
        movieDTOs.add(movieDTO);

        return movieDTOs;
    }
}