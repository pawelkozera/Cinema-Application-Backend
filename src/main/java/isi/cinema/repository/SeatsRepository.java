package isi.cinema.repository;

import isi.cinema.model.Seats;
import org.springframework.data.repository.CrudRepository;

public interface SeatsRepository extends CrudRepository<Seats, Long> {
}
