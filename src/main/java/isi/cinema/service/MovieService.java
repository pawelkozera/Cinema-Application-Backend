package isi.cinema.service;

import isi.cinema.model.Movie;
import isi.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Map<String, Object>> findAllMoviesWithScreeningSchedulesByCinemaName(String cinemaName) {
        List<Movie> movies = movieRepository.findAllMoviesByCinemaName(cinemaName);

        return movies.stream().map(movie -> {
            Map<String, Object> movieInfo = new HashMap<>();
            movieInfo.put("id", movie.getId());
            movieInfo.put("title", movie.getTitle());
            movieInfo.put("category", movie.getCategory());
            movieInfo.put("type", movie.getType());

            List<Map<String, String>> screeningDates = movie.getScreeningSchedules().stream()
                    .map(screeningSchedule -> {
                        Map<String, String> scheduleInfo = new HashMap<>();
                        scheduleInfo.put("date", screeningSchedule.getDate().toString());
                        scheduleInfo.put("format", screeningSchedule.getFormat());
                        return scheduleInfo;
                    })
                    .collect(Collectors.toList());
            movieInfo.put("screeningDates", screeningDates);

            return movieInfo;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> findMovieById(Long movieId) {
        Optional<Movie> movies = movieRepository.findById(movieId);

        return movies.stream().map(movie -> {
            Map<String, Object> movieInfo = new HashMap<>();
            movieInfo.put("id", movie.getId());
            movieInfo.put("title", movie.getTitle());
            movieInfo.put("category", movie.getCategory());
            movieInfo.put("type", movie.getType());
            movieInfo.put("ageRating", movie.getAgeRating());
            movieInfo.put("description", movie.getDescription());
            movieInfo.put("length", movie.getLength());
            movieInfo.put("countryProduction", movie.getCountryProduction());
            movieInfo.put("yearProduction", movie.getYearProduction());

            List<Map<String, String>> screeningDates = movie.getScreeningSchedules().stream()
                    .map(screeningSchedule -> {
                        Map<String, String> scheduleInfo = new HashMap<>();
                        scheduleInfo.put("date", screeningSchedule.getDate().toString());
                        scheduleInfo.put("format", screeningSchedule.getFormat());
                        return scheduleInfo;
                    })
                    .collect(Collectors.toList());
            movieInfo.put("screeningDates", screeningDates);

            return movieInfo;
        }).collect(Collectors.toList());
    }
}