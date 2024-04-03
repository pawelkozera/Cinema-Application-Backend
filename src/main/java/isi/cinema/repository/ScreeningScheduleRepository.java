package isi.cinema.repository;

import isi.cinema.model.ScreeningSchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningScheduleRepository extends CrudRepository<ScreeningSchedule, Long> {
}
