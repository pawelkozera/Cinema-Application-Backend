package isi.cinema.service;

import com.paypal.base.rest.PayPalRESTException;
import isi.cinema.DTO.OrderDTO;
import isi.cinema.model.*;
import isi.cinema.repository.OrderRepository;
import isi.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
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

    public void createOrderFromPayment(String paymentId, Order order) {
        String currency = order.getCurrency();
        String payerId = order.getPayerId();
        Double price = Double.valueOf(order.getPrice());
        String method = order.getMethod();
        String intent = order.getIntent();
        String description = order.getDescription();
        Ticket ticket = order.getTicket();

        Order newOrder = new Order(price, currency, method, intent, description, false, paymentId, payerId, ticket);
        orderRepository.save(newOrder);
    }

    public void updateOrder(String paymentId, String payerId) {
        Optional<Order> orderOptional = orderRepository.findByPaymentId(paymentId);

        if(orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setPayerId(payerId);
            order.setPaid(true);
            orderRepository.save(order);
        }
    }
}
