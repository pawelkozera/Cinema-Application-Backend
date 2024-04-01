package isi.cinema.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String email;
    private String login;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    protected User() {}

    public User(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, email='%s', password='%s']",
                id, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}