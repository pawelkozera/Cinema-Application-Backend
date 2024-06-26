package isi.cinema.controller;

import isi.cinema.DTO.RoomDTO;
import isi.cinema.model.Room;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.service.RoomService;
import isi.cinema.service.ScreeningScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/getAvailableSeats/{scheduleId}")
    public ResponseEntity<RoomDTO> getAvailableSeats(@PathVariable Long scheduleId) {
        RoomDTO roomDTO = roomService.getAvailableSeats(scheduleId);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<Room> authorizationResponse = (ResponseEntity<Room>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        Room addedRoom = roomService.addRoom(room);

        return new ResponseEntity<>(addedRoom, HttpStatus.CREATED);
    }

    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<Room> authorizationResponse = (ResponseEntity<Room>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        roomService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<Room> authorizationResponse = (ResponseEntity<Room>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        Room updatedRoom = roomService.updateRom(id, room);
        if(updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byCinemaName/{cinemaName}")
    public ResponseEntity<List<Room>> getRoomsByCinemaName(@PathVariable String cinemaName, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<List<Room>> authorizationResponse = (ResponseEntity<List<Room>>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        List<Room> rooms = roomService.getRoomsByCinemaName(cinemaName);
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(rooms);
    }
}
