package isi.cinema;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.Role;
import isi.cinema.model.User;
import isi.cinema.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    private MovieDTO movieDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        movieDTO = new MovieDTO();
        movieDTO.setTitle("Test Movie");
        movieDTO.setAgeRating("PG-13");
        movieDTO.setDescription("Test Description");
        movieDTO.setLength(120);
        movieDTO.setCountryProduction("USA");
        movieDTO.setYearProduction("2021");
        movieDTO.setCategory("Action");
        movieDTO.setType("Type");
        movieDTO.setImageUrl("http://asd.com/asd.jpg");
        movieDTO.setScreeningScheduleIds(Arrays.asList(1L, 2L));
        movieDTO.setCinemaIds(Arrays.asList(1L, 2L));

        UserDetails userDetails = User.builder()
                .username("admin")
                .password("password")
                .role(Role.ADMIN)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetAllMoviesWithScreeningSchedules() throws Exception {
        when(movieService.findAllMoviesWithScreeningSchedulesByCinemaName(anyString()))
                .thenReturn(Collections.singletonList(movieDTO));

        mockMvc.perform(get("/api/movie/Test Cinema/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    public void testGetMovieById() throws Exception {
        when(movieService.findMovieById(anyLong()))
                .thenReturn(Collections.singletonList(movieDTO));

        mockMvc.perform(get("/api/movie/Test Cinema/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    public void testAddMovie() throws Exception {
        mockMvc.perform(post("/api/movie/addMovie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Movie\", \"ageRating\": \"PG-13\", \"description\": \"Test Description\", \"length\": 120, \"countryProduction\": \"USA\", \"yearProduction\": \"2021\", \"category\": \"Action\", \"type\": \"Feature\", \"imageUrl\": \"http://example.com/image.jpg\", \"screeningScheduleIds\": [1, 2], \"cinemaIds\": [1, 2]}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteMovie() throws Exception {
        when(movieService.deleteMovieById(anyLong())).thenReturn("Movie deleted successfully");

        mockMvc.perform(delete("/api/movie/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateMovie() throws Exception {
        Movie updatedMovie = new Movie();
        when(movieService.updateMovie(anyLong(), any(MovieDTO.class))).thenReturn(updatedMovie);

        mockMvc.perform(put("/api/movie/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Movie\", \"ageRating\": \"PG-13\", \"description\": \"Updated Description\", \"length\": 120, \"countryProduction\": \"USA\", \"yearProduction\": 2021, \"category\": \"Action\", \"type\": \"Feature\", \"imageUrl\": \"http://example.com/image.jpg\", \"screeningScheduleIds\": [1, 2], \"cinemaIds\": [1, 2]}"))
                .andExpect(status().isOk());
    }
}
