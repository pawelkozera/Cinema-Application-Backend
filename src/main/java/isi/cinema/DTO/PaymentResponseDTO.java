package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDTO {
    private String approvalUrl;
    private String ticketId;
}
