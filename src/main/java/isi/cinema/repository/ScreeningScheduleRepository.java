package isi.cinema.repository;

import isi.cinema.model.ScreeningSchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Date;

@Repository
public interface ScreeningScheduleRepository extends CrudRepository<ScreeningSchedule, Long> {
    @Query("SELECT s FROM ScreeningSchedule s WHERE s.date > :currentDate")
    List<ScreeningSchedule> findUpcoming(@Param("currentDate") Date currentDate);
}
