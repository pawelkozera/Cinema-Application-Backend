package isi.cinema.controller;

import isi.cinema.DTO.CinemaDTO;
import isi.cinema.DTO.OrderDTO;
import isi.cinema.DTO.UserMovieHistoryDTO;
import isi.cinema.model.Movie;
import isi.cinema.model.ScreeningSchedule;
import isi.cinema.service.CinemaService;
import isi.cinema.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<OrderDTO>> getTransactions(Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<List<OrderDTO>> authorizationResponse = (ResponseEntity<List<OrderDTO>>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        List<OrderDTO> orderDTOS = orderService.getTransactions();

        return ResponseEntity.ok(orderDTOS);
    }

    @PutMapping("/changePaid/{id}")
    public ResponseEntity<String> changePaid(@PathVariable Long id, Authentication authentication) {
        UserAuthentication userAuthentication = new UserAuthentication();
        ResponseEntity<String> authorizationResponse = (ResponseEntity<String>) userAuthentication.checkAdminAuthorization(authentication);
        if (authorizationResponse != null) {
            return authorizationResponse;
        }

        String updateOrder = orderService.changePaid(id);
        if(updateOrder != null) {
            return ResponseEntity.ok(updateOrder);
        }
        return ResponseEntity.notFound().build();
    }
}
