package isi.cinema.service;

import isi.cinema.model.Cinema;
import isi.cinema.model.User;
import isi.cinema.repository.CinemaRepository;
import isi.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public List<Cinema> getAllCinemas() {
        return (List<Cinema>) cinemaRepository.findAll();
    }
    public List<String> getAllCinemaNames() {
        return cinemaRepository.findAllCinemaNames();
    }
}
