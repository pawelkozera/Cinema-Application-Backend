package isi.cinema.repository;

import isi.cinema.model.Cinema;
import org.springframework.data.repository.CrudRepository;

public interface CinemaRepository extends CrudRepository<Cinema, Long> {
}
