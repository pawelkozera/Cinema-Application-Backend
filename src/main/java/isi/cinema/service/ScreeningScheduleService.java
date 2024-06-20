package isi.cinema.service;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.*;
import isi.cinema.repository.MovieRepository;
import isi.cinema.repository.RoomRepository;
import isi.cinema.repository.ScreeningScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public String deleteScreeningScheduleById(Long id) {
        ScreeningSchedule screeningScheduleToDelete = screeningScheduleRepository.findById(id).orElse(null);

        if (screeningScheduleToDelete != null) {
            for (Ticket ticket : screeningScheduleToDelete.getTickets()) {
                if (ticket.getScreeningSchedule() != null) {
                    return "Cannot delete screening schedule with tickets";
                }
            }

            for (Movie movie : screeningScheduleToDelete.getMovies()) {
                if (movie.getScreeningSchedules() != null) {
                    return "Cannot delete screening schedule with movies";
                }
            }

            screeningScheduleRepository.deleteById(id);
            return "Screening schedule deleted successfully";
        }

        return "Cannot delete screening schedule with id";
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


    public List<ScreeningScheduleDTO> getUpcomingScreeningSchedules() {
        Date currentDate = new Date();
        List<ScreeningSchedule> screeningSchedules = screeningScheduleRepository.findUpcoming(currentDate);
        return screeningSchedules.stream()
                .map(schedule -> new ScreeningScheduleDTO(schedule.getId(), schedule.getDate(), schedule.getFormat()))
                .collect(Collectors.toList());
    }

    public ScreeningScheduleDTO getTakenSeats(Long screeningScheduleId) {
        Optional<ScreeningSchedule> screeningSchedule = screeningScheduleRepository.findById(screeningScheduleId);

        return screeningSchedule.map(schedule -> new ScreeningScheduleDTO(schedule.getTakenSeats())).orElse(null);
    }

    public void addTakenSeats(List<String> takenSeats, long screeningScheduleId) {
        Optional<ScreeningSchedule> screeningScheduleOptional = screeningScheduleRepository.findById(screeningScheduleId);
        if(screeningScheduleOptional.isPresent()) {
            ScreeningSchedule screeningSchedule = screeningScheduleOptional.get();
            List<String> oldTakenSeats = new ArrayList<>(List.of());

            if (screeningSchedule.getTakenSeats() != null) {
                oldTakenSeats.addAll(screeningSchedule.getTakenSeats());
            }

            oldTakenSeats.addAll(takenSeats);
            screeningSchedule.setTakenSeats(oldTakenSeats);
            screeningScheduleRepository.save(screeningSchedule);
        }
    }
}