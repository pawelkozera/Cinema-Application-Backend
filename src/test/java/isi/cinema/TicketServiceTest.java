package isi.cinema;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import isi.cinema.DTO.TicketDTO;
import isi.cinema.DTO.UserMovieHistoryDTO;
import isi.cinema.model.*;
import isi.cinema.repository.TicketRepository;
import isi.cinema.repository.UserRepository;
import isi.cinema.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void testBookTicket_Success() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Ticket ticket = new Ticket();
        ticket.setPrice("10.0");

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        Ticket bookedTicket = ticketService.bookTicket(ticket, username);

        assertNotNull(bookedTicket);
        assertEquals(user, bookedTicket.getUser());
        verify(ticketRepository, times(1)).save(ticket);
    }


    @Test
    public void testBookTicket_UserNotFound() {
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Ticket ticket = new Ticket();
        ticket.setPrice("10.0");

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        Ticket bookedTicket = ticketService.bookTicket(ticket, username);

        assertNull(bookedTicket.getUser());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void testGetTicketByUUID_Success() {
        UUID uuid = UUID.randomUUID();

        Movie movie = new Movie();
        ScreeningSchedule screeningSchedule = new ScreeningSchedule();

        Ticket ticket = new Ticket();
        ticket.setUuid(uuid);
        ticket.setPrice("10.0");
        ticket.setMovie(movie);
        ticket.setScreeningSchedule(screeningSchedule);

        when(ticketRepository.findByUuid(uuid)).thenReturn(Optional.of(ticket));

        TicketDTO ticketDTO = ticketService.getTicketByUUID(uuid);

        assertNotNull(ticketDTO);
        assertEquals(uuid, ticketDTO.getUuid());
        assertEquals(ticket.getPrice(), ticketDTO.getPrice());
        verify(ticketRepository, times(1)).findByUuid(uuid);
    }

    @Test
    public void testGetTicketByUUID_TicketNotFound() {
        UUID uuid = UUID.randomUUID();

        when(ticketRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        TicketDTO ticketDTO = ticketService.getTicketByUUID(uuid);

        assertNull(ticketDTO);
        verify(ticketRepository, times(1)).findByUuid(uuid);
    }

    @Test
    public void testGetTicketsHistory_Success() {
        String username = "testuser";

        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        ScreeningSchedule screeningSchedule1 = new ScreeningSchedule();
        screeningSchedule1.setFormat("Napisy");

        ScreeningSchedule screeningSchedule2 = new ScreeningSchedule();
        screeningSchedule2.setFormat("Dubbing");

        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket();
        ticket1.setUuid(UUID.randomUUID());
        ticket1.setMovie(new Movie("Movie 1", "18", "description", 120, "country", "year", "category", "type", "image1.jpg", null, null));
        ticket1.setScreeningSchedule(screeningSchedule1);

        Ticket ticket2 = new Ticket();
        ticket2.setUuid(UUID.randomUUID());
        ticket2.setMovie(new Movie("Movie 2", "18", "description", 120, "country", "year", "category", "type", "image1.jpg", null, null));
        ticket2.setScreeningSchedule(screeningSchedule2);

        tickets.add(ticket1);
        tickets.add(ticket2);

        when(ticketRepository.findByUserId(user)).thenReturn(tickets);

        List<UserMovieHistoryDTO> userMovieHistoryDTO = ticketService.getTicketsHistory(username);

        assertNotNull(userMovieHistoryDTO);
        assertEquals(2, userMovieHistoryDTO.size());

        UserMovieHistoryDTO historyDTO1 = userMovieHistoryDTO.get(0);
        assertEquals(ticket1.getUuid(), historyDTO1.getTicketUUID());
        assertEquals(ticket1.getMovie().getTitle(), historyDTO1.getTitle());
        assertEquals(ticket1.getMovie().getImageUrl(), historyDTO1.getImageUrl());
        assertEquals(screeningSchedule1.getFormat(), historyDTO1.getType());

        UserMovieHistoryDTO historyDTO2 = userMovieHistoryDTO.get(1);
        assertEquals(ticket2.getUuid(), historyDTO2.getTicketUUID());
        assertEquals(ticket2.getMovie().getTitle(), historyDTO2.getTitle());
        assertEquals(ticket2.getMovie().getImageUrl(), historyDTO2.getImageUrl());
        assertEquals(screeningSchedule2.getFormat(), historyDTO2.getType());
    }

    @Test
    public void testGetTicketsHistory_UserNotFound() {
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        List<UserMovieHistoryDTO> userMovieHistoryDTO = ticketService.getTicketsHistory(username);

        assertNotNull(userMovieHistoryDTO);
        assertTrue(userMovieHistoryDTO.isEmpty());
    }
}

