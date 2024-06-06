package isi.cinema.service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
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
    private final PaypalService paypalService;

    @Autowired
    public OrderService(OrderRepository orderRepository, PaypalService paypalService) {
        this.paypalService = paypalService;
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

    public void createOrderFromPayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = paypalService.executePayment(paymentId, payerId);

        Transaction transaction = payment.getTransactions().get(0);
        Amount amount = transaction.getAmount();
        String currency = amount.getCurrency();
        Double price = Double.valueOf(amount.getTotal());
        String method = payment.getPayer().getPaymentMethod();
        String intent = payment.getIntent();
        String description = transaction.getDescription();

        createOrder(price, currency, method, intent, description, paymentId, payerId);
    }

    private void createOrder(Double price, String currency, String method, String intent, String description, String paymentId, String payerId) {
        Order order = new Order(price, currency, method, intent, description, true, paymentId, payerId);
        orderRepository.save(order);
    }
}
