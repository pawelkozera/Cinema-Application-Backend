package isi.cinema.controller;

import isi.cinema.DTO.UserMovieHistoryDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.Ticket;
import isi.cinema.DTO.TicketDTO;
import isi.cinema.service.ScreeningScheduleService;
import isi.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TicketController {
    private final TicketService ticketService;
    private final ScreeningScheduleService screeningScheduleService;

    @Autowired
    public TicketController(TicketService ticketService, ScreeningScheduleService screeningScheduleService) {
        this.screeningScheduleService = screeningScheduleService;
        this.ticketService = ticketService;
    }

    @PostMapping("/ticket/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket, Authentication authentication) {
        Ticket bookedTicket;

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            bookedTicket = ticketService.bookTicket(ticket, username);
        } else {
            bookedTicket = ticketService.bookTicket(ticket, null);
        }

        long screeningScheduleId = bookedTicket.getScreeningSchedule().getId();
        screeningScheduleService.addTakenSeats(bookedTicket.getSeats(), screeningScheduleId);

        return new ResponseEntity<>(bookedTicket, HttpStatus.CREATED);
    }

    @GetMapping("/ticket/{uuid}")
    public ResponseEntity<TicketDTO> getTicketByUUID(@PathVariable UUID uuid) {
        TicketDTO ticketDTO = ticketService.getTicketByUUID(uuid);
        if (ticketDTO != null) {
            return ResponseEntity.ok(ticketDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ticket/history")
    public ResponseEntity<List<UserMovieHistoryDTO>> getTicketsHistory(Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        if (!userAuthentication.checkAuthentication(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        List<UserMovieHistoryDTO> userMovieHistoryDTO = ticketService.getTicketsHistory(username);

        return ResponseEntity.ok(userMovieHistoryDTO);
    }
}
