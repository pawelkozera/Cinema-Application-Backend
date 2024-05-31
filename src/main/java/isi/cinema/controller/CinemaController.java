package isi.cinema.controller;

import isi.cinema.DTO.CinemaDTO;
import isi.cinema.model.Cinema;
import isi.cinema.model.User;
import isi.cinema.service.CinemaService;
import isi.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CinemaController {
    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/cinemas")
    public ResponseEntity<List<String>> getAllCinemas() {
        List<String> cinemas = cinemaService.getAllCinemaNames();

        return ResponseEntity.ok(cinemas);
    }

    @GetMapping("/getCinemaByName/{movieName}")
    public ResponseEntity<CinemaDTO> getAllInfoCinemas(@PathVariable String movieName) {
        CinemaDTO cinema = cinemaService.getCinemaByName(movieName);

        return ResponseEntity.ok(cinema);
    }
}
