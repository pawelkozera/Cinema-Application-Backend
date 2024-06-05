package isi.cinema.service;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.Room;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.repository.MovieRepository;
import isi.cinema.repository.RoomRepository;
import isi.cinema.repository.ScreeningScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScreeningScheduleService {
    private final ScreeningScheduleRepository screeningScheduleRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ScreeningScheduleService(ScreeningScheduleRepository screeningScheduleRepository, RoomRepository roomRepository) {
        this.screeningScheduleRepository = screeningScheduleRepository;
        this.roomRepository = roomRepository;
    }

    public ScreeningSchedule addScreeningSchedule(ScreeningSchedule screeningSchedule, Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        screeningSchedule.setRoom(room);

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

    public List<ScreeningScheduleDTO> getAllScreeningSchedules() {
        Iterable<ScreeningSchedule> screeningSchedules = screeningScheduleRepository.findAll();
        return StreamSupport.stream(screeningSchedules.spliterator(), false)
                .map(schedule -> new ScreeningScheduleDTO(schedule.getId(), schedule.getDate(), schedule.getFormat()))
                .collect(Collectors.toList());
    }

    public ScreeningScheduleDTO getTakenSeats(Long screeningScheduleId) {
        Optional<ScreeningSchedule> screeningSchedule = screeningScheduleRepository.findById(screeningScheduleId);

        return screeningSchedule.map(schedule -> new ScreeningScheduleDTO(schedule.getTakenSeats())).orElse(null);
    }
}