package isi.cinema.service;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.Movie;
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