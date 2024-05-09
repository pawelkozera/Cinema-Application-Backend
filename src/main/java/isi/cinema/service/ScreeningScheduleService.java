package isi.cinema.service;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.repository.MovieRepository;
import isi.cinema.repository.ScreeningScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningScheduleService {
    private final ScreeningScheduleRepository screeningScheduleRepository;

    @Autowired
    public ScreeningScheduleService(ScreeningScheduleRepository screeningScheduleRepository) {
        this.screeningScheduleRepository = screeningScheduleRepository;
    }

    public ScreeningSchedule addScreeningSchedule(ScreeningSchedule screeningSchedule) {
        return screeningScheduleRepository.save(screeningSchedule);
    }

    public void deleteScreeningScheduleById(Long id) {
        screeningScheduleRepository.deleteById(id);
    }

    public ScreeningSchedule updateScreeningSchedule(Long id, ScreeningSchedule updateScreeningSchedule) {
        Optional<ScreeningSchedule> screeningScheduleOptional = screeningScheduleRepository.findById(id);
        if(screeningScheduleOptional.isPresent()) {
            ScreeningSchedule screeningSchedule = screeningScheduleOptional.get();
            screeningSchedule.setDate(updateScreeningSchedule.getDate());
            screeningSchedule.setFormat(updateScreeningSchedule.getFormat());

            return screeningScheduleRepository.save(screeningSchedule);
        }
        return  null;
    }
}