package isi.cinema.service;

import isi.cinema.model.Ticket;
import isi.cinema.model.User;
import isi.cinema.repository.CinemaRepository;
import isi.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket bookTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
