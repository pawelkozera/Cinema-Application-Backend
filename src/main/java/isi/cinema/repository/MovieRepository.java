package isi.cinema.repository;

import isi.cinema.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m JOIN m.cinemas c WHERE c.name = :cinemaName")
    List<Movie> findAllMoviesByCinemaName(@Param("cinemaName") String cinemaName);
    Optional<Movie> findById(Long movieId);
}
