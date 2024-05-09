package isi.cinema.service;

import isi.cinema.model.Room;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.repository.RoomRepository;
import isi.cinema.repository.ScreeningScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }

    public Room updateRom(Long id, Room updateRoom) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setName(updateRoom.getName());
            room.setSeats(updateRoom.getSeats());

            return roomRepository.save(room);
        }
        return  null;
    }
}