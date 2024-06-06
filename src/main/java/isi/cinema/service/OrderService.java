package isi.cinema.service;

import isi.cinema.DTO.CinemaDTO;
import isi.cinema.DTO.OrderDTO;
import isi.cinema.model.Cinema;
import isi.cinema.model.Order;
import isi.cinema.repository.CinemaRepository;
import isi.cinema.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> getTransactions() {
        Iterable<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = mapToDTO(order);
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }

    private OrderDTO mapToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPayerId(order.getPayerId());
        orderDTO.setPaymentId(order.getPaymentId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setCurrency(order.getCurrency());
        orderDTO.setMethod(order.getMethod());
        orderDTO.setDescription(order.getDescription());
        orderDTO.setPaid(order.isPaid());

        return orderDTO;
    }

    public String changePaid(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setPaid(!order.isPaid());
            orderRepository.save(order);

            return "Order paid status changed successfully";
        }

        return null;
    }
}
