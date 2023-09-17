package fr.fortytwo.cinema.services;

import fr.fortytwo.cinema.models.User;
import fr.fortytwo.cinema.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
    }

    private String encryptPassword(String password) {
        return encoder.encode(password);
    }

    private boolean comparePasswords(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    public void registerUser(User user) {
        String encodedPassword = encryptPassword(user.getPassword());
        user.setPassword(encodedPassword);
        usersRepository.save(user);
    }

    public Optional<User> signIn(String email, String password) {
        Optional<User> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            return Optional.empty();
        User fetchedUser = optionalUser.get();
        return Optional.of(fetchedUser);
    }
}
