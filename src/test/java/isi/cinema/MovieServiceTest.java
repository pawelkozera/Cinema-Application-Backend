package isi.cinema;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.Cinema;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.model.Ticket;
import isi.cinema.repository.MovieRepository;
import isi.cinema.repository.CinemaRepository;
import isi.cinema.repository.ScreeningScheduleRepository;
import isi.cinema.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private ScreeningScheduleRepository screeningScheduleRepository;

    @InjectMocks
    private MovieService movieService;

    private static Movie createMovie() {
        return new Movie();
    }

    private static MovieDTO createMovieDTO() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Test Movie");
        movieDTO.setAgeRating("PG-13");
        movieDTO.setDescription("Test Description");
        movieDTO.setLength(120);
        movieDTO.setCountryProduction("USA");
        movieDTO.setYearProduction("2021");
        movieDTO.setCategory("Action");
        movieDTO.setType("Feature");
        movieDTO.setImageUrl("http://example.com/image.jpg");
        movieDTO.setScreeningScheduleIds(Arrays.asList(1L, 2L));
        movieDTO.setCinemaIds(Arrays.asList(1L, 2L));
        return movieDTO;
    }

    @Test
    public void testAddMovie() {
        MovieDTO movieDTO = createMovieDTO();

        ScreeningSchedule schedule1 = new ScreeningSchedule();
        ScreeningSchedule schedule2 = new ScreeningSchedule();
        when(screeningScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule1));
        when(screeningScheduleRepository.findById(2L)).thenReturn(Optional.of(schedule2));

        Cinema cinema1 = new Cinema();
        Cinema cinema2 = new Cinema();
        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema1));
        when(cinemaRepository.findById(2L)).thenReturn(Optional.of(cinema2));

        Movie movie = new Movie();

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.addMovie(movieDTO);

        assertNotNull(result);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    public void testUpdateMovie() {
        Long movieId = 1L;
        MovieDTO updatedMovieDTO = createMovieDTO();

        Movie existingMovie = createMovie();
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));

        ScreeningSchedule schedule1 = new ScreeningSchedule();
        ScreeningSchedule schedule2 = new ScreeningSchedule();
        when(screeningScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule1));
        when(screeningScheduleRepository.findById(2L)).thenReturn(Optional.of(schedule2));

        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie updatedMovie = movieService.updateMovie(movieId, updatedMovieDTO);

        assertNotNull(updatedMovie);
        assertEquals(updatedMovieDTO.getTitle(), updatedMovie.getTitle());
        verify(movieRepository, times(1)).save(existingMovie);
    }

    @Test
    public void testFindAllMoviesWithScreeningSchedulesByCinemaName() {
        String cinemaName = "Test Cinema";

        Movie movie = createMovie();
        when(movieRepository.findAllMoviesByCinemaName(cinemaName)).thenReturn(Collections.singletonList(movie));

        List<MovieDTO> result = movieService.findAllMoviesWithScreeningSchedulesByCinemaName(cinemaName);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieRepository, times(1)).findAllMoviesByCinemaName(cinemaName);
    }

    @Test
    public void testFindMovieById() {
        Long movieId = 1L;

        Movie movie = createMovie();
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        List<MovieDTO> result = movieService.findMovieById(movieId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    public void testDeleteMovieById_NoTickets() {
        Long id = 1L;
        Movie movieToDelete = new Movie();
        when(movieRepository.findById(id)).thenReturn(Optional.of(movieToDelete));

        String result = movieService.deleteMovieById(id);

        assertEquals("Movie deleted successfully", result);
        verify(movieRepository, times(1)).delete(movieToDelete);
    }

    @Test
    public void testDeleteMovieById_WithTickets() {
        Long id = 1L;
        Movie movieToDelete = new Movie();
        Ticket ticket = new Ticket();
        ticket.setMovie(movieToDelete);
        List<Ticket> ticketList = List.of(ticket);
        movieToDelete.setTickets(ticketList);
        when(movieRepository.findById(id)).thenReturn(Optional.of(movieToDelete));

        String result = movieService.deleteMovieById(id);

        assertEquals("Cannot delete movie with tickets", result);
        verify(movieRepository, never()).delete(movieToDelete);
    }

    @Test
    public void testDeleteMovieById_MovieNotFound() {
        Long id = 1L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        String result = movieService.deleteMovieById(id);

        assertEquals("Cannot delete movie", result);
        verify(movieRepository, never()).delete(any());
    }
}
