package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String paymentId;
    private String payerId;
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private boolean isPaid;

    public OrderDTO() {
    }

    public OrderDTO(Long id, double price, String currency, String method, String intent, String description, boolean isPaid) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.isPaid = isPaid;
    }
}
