package isi.cinema.repository;

import isi.cinema.model.Cinema;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Long> {
    @Query("SELECT c.name FROM Cinema c")
    List<String> findAllCinemaNames();

    @Query("SELECT c FROM Cinema c WHERE c.name = :cinemaName")
    Cinema findCinemaByName(String cinemaName);
}
