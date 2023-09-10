package fr.fortytwo.cinema.repositories;

import fr.fortytwo.cinema.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsersRepository {
    public UsersRepository() {

    }

    public Optional<User> findById(long id) {
        return Optional.of(new User(id, "amine", "bounya", "0666666666", "pass123"));
    }
}
