package isi.cinema.repository;

import isi.cinema.model.Room;
import isi.cinema.model.Ticket;
import isi.cinema.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Optional<Ticket> findByUuid(UUID uuid);

    @Query("SELECT t FROM Ticket t WHERE t.user = :user")
    List<Ticket> findByUserId(@Param("user") User user);
}
