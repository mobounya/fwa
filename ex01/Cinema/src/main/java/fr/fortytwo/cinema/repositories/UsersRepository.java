package fr.fortytwo.cinema.repositories;

import fr.fortytwo.cinema.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepository {
    static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNumber(rs.getString("phone_number"));
            return user;
        }
    }
    private DataSource dataSource;

    @Autowired
    public UsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(User entity) {
        String query = "INSERT INTO users (email, first_name, last_name, phone_number, password) " +
                        "VALUES (:email, :first_name, :last_name, :phone_number, :password);";
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.dataSource);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", entity.getEmail());
        parameters.addValue("first_name", entity.getFirstName());
        parameters.addValue("last_name", entity.getLastName());
        parameters.addValue("password", entity.getPassword());
        parameters.addValue("phone_number", entity.getPhoneNumber());
        template.update(query, parameters);
    }

    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = :email;";
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", email);
        List<User> users = template.query(query, parameters, new UserMapper());
        if (users.isEmpty())
            return Optional.empty();
        else
            return Optional.of(users.get(0));
    }
}
