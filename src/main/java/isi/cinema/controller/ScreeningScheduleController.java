package isi.cinema.controller;

import isi.cinema.DTO.MovieDTO;
import isi.cinema.DTO.ScreeningScheduleDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.Role;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.service.MovieService;
import isi.cinema.service.ScreeningScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/screeningSchedule")
public class ScreeningScheduleController {
    private final ScreeningScheduleService screeningScheduleService;

    @Autowired
    public ScreeningScheduleController(ScreeningScheduleService screeningScheduleService) {
        this.screeningScheduleService = screeningScheduleService;
    }

    @PostMapping("/add/{roomId}")
    public ResponseEntity<ScreeningSchedule> addScreeningSchedule(@PathVariable Long roomId, @RequestBody ScreeningSchedule screeningScheduleRequest, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<ScreeningSchedule> authorizationResponse = (ResponseEntity<ScreeningSchedule>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        ScreeningSchedule screeningSchedule = new ScreeningSchedule(screeningScheduleRequest.getDate(), screeningScheduleRequest.getFormat());
        ScreeningSchedule addedScreeningSchedule = screeningScheduleService.addScreeningSchedule(screeningSchedule, roomId);

        return new ResponseEntity<>(addedScreeningSchedule, HttpStatus.CREATED);
    }

    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<String> deleteScreeningSchedule(@PathVariable Long id, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<ScreeningSchedule> authorizationResponse = (ResponseEntity<ScreeningSchedule>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            ResponseEntity.noContent().build();
        }

        String response = screeningScheduleService.deleteScreeningScheduleById(id);
        if ("Cannot delete screening schedule with tickets".equals(response)) {
            return new ResponseEntity<>("Cannot delete screening schedule with tickets", HttpStatus.BAD_REQUEST);
        } else if ("Cannot delete screening schedule with movies".equals(response)) {
            return new ResponseEntity<>("Cannot delete screening schedule with movies", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScreeningSchedule> updateScreeningSchedule(@PathVariable Long id, @RequestBody ScreeningSchedule screeningSchedule, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<ScreeningSchedule> authorizationResponse = (ResponseEntity<ScreeningSchedule>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        ScreeningSchedule updatedScreeningSchedule = screeningScheduleService.updateScreeningSchedule(id, screeningSchedule);
        if(updatedScreeningSchedule != null) {
            return ResponseEntity.ok(updatedScreeningSchedule);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getSchedules")
    public ResponseEntity<List<ScreeningScheduleDTO>> getScreeningSchedule(Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        if (!userAuthentication.checkAuthentication(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!userAuthentication.checkRole(authentication, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ScreeningScheduleDTO> screeningSchedulesDTO = screeningScheduleService.getAllScreeningSchedules();
        return ResponseEntity.ok(screeningSchedulesDTO);
    }

    @GetMapping("/getUpcomingSchedules")
    public ResponseEntity<List<ScreeningScheduleDTO>> getUpcomingScreeningSchedule(Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        if (!userAuthentication.checkAuthentication(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!userAuthentication.checkRole(authentication, Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ScreeningScheduleDTO> screeningSchedulesDTO = screeningScheduleService.getUpcomingScreeningSchedules();
        return ResponseEntity.ok(screeningSchedulesDTO);
    }

    @GetMapping("/getTakenSeats/{scheduleId}")
    public ResponseEntity<ScreeningScheduleDTO> getTakenSeats(@PathVariable Long scheduleId) {
        ScreeningScheduleDTO screeningSchedulesDTO = screeningScheduleService.getTakenSeats(scheduleId);
        return ResponseEntity.ok(screeningSchedulesDTO);
    }
}
