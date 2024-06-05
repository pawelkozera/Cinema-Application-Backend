package isi.cinema.service;

import isi.cinema.DTO.TicketDTO;
import isi.cinema.model.Ticket;
import isi.cinema.model.User;
import isi.cinema.repository.CinemaRepository;
import isi.cinema.repository.TicketRepository;
import isi.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket bookTicket(Ticket ticket, String username) {
        if (username != null) {
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setUsername(username);
                ticket.setUser(user);
            }
        }

        return ticketRepository.save(ticket);
    }

    public TicketDTO getTicketByUUID(UUID uuid) {
        Optional<Ticket> ticketOptional = ticketRepository.findByUuid(uuid);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setUuid(ticket.getUuid());
            ticketDTO.setPrice(ticket.getPrice());
            ticketDTO.setSeats(ticket.getSeats());
            ticketDTO.setAmount(ticket.getAmount());
            ticketDTO.setMovieTitle(ticket.getMovie().getTitle());
            ticketDTO.setScreeningDate(ticket.getScreeningSchedule().getDate());
            ticketDTO.setScreeningFormat(ticket.getScreeningSchedule().getFormat());

            return ticketDTO;
        } else {
            return null;
        }
    }
}
