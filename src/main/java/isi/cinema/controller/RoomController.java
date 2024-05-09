package isi.cinema.controller;

import isi.cinema.model.Room;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.service.RoomService;
import isi.cinema.service.ScreeningScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
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
}
