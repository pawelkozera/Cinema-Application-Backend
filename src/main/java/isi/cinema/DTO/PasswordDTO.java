package isi.cinema.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {
    String oldPassword;
    String newPassword;

    public PasswordDTO() {
    }

    public PasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
