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

    @PostMapping("/add")
    public ResponseEntity<ScreeningSchedule> addScreeningSchedule(@RequestBody ScreeningSchedule screeningSchedule, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<ScreeningSchedule> authorizationResponse = (ResponseEntity<ScreeningSchedule>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        ScreeningSchedule addedScreeningSchedule = screeningScheduleService.addScreeningSchedule(screeningSchedule);

        return new ResponseEntity<>(addedScreeningSchedule, HttpStatus.CREATED);
    }

    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<ScreeningSchedule> deleteScreeningSchedule(@PathVariable Long id, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<ScreeningSchedule> authorizationResponse = (ResponseEntity<ScreeningSchedule>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        screeningScheduleService.deleteScreeningScheduleById(id);
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

    @GetMapping("/getTakenSeats/{scheduleId}")
    public ResponseEntity<ScreeningScheduleDTO> getTakenSeats(@PathVariable Long scheduleId) {
        ScreeningScheduleDTO screeningSchedulesDTO = screeningScheduleService.getTakenSeats(scheduleId);
        return ResponseEntity.ok(screeningSchedulesDTO);
    }
}
