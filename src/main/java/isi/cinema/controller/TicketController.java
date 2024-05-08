package isi.cinema.controller;

import isi.cinema.model.Ticket;
import isi.cinema.DTO.TicketDTO;
import isi.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/ticket/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        Ticket bookedTicket = ticketService.bookTicket(ticket);

        return new ResponseEntity<>(bookedTicket, HttpStatus.CREATED);
    }

    @GetMapping("/ticket/{uuid}")
    public ResponseEntity<TicketDTO> getTicketByUUID(@PathVariable UUID uuid) {
        Ticket ticket = ticketService.getTicketByUUID(uuid);
        if (ticket != null) {
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setUuid(ticket.getUuid());
            ticketDTO.setPrice(ticket.getPrice());
            ticketDTO.setSeats(ticket.getSeats());
            ticketDTO.setAmount(ticket.getAmount());
            ticketDTO.setMovieTitle(ticket.getMovie().getTitle());
            ticketDTO.setScreeningDate(ticket.getScreeningSchedule().getDate());
            ticketDTO.setScreeningFormat(ticket.getScreeningSchedule().getFormat());

            return ResponseEntity.ok(ticketDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
