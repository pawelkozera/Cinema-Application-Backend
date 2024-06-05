package isi.cinema.repository;

import isi.cinema.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.cinema.name = :cinemaName")
    List<Room> findByCinemaName(@Param("cinemaName") String cinemaName);
}
