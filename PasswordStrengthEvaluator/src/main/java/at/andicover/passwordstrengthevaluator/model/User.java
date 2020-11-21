package at.andicover.passwordstrengthevaluator.model;

import com.datastax.driver.core.Row;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * User model. Contains same fields as DB table.
 */
public final class User {

    public static final String SESSION_ATTRIBUTE = "user";

    private final UUID id;
    private final String username;
    private final String password;
    private final String salt;
    private final String name;

    public User(@NonNull final UUID id,
                @NonNull final String username,
                @NonNull final String password,
                @NonNull final String salt,
                @NonNull final String name) {
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.salt = requireNonNull(salt);
        this.name = requireNonNull(name);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getName() {
        return name;
    }

    /**
     * Parse a DB result row to a User instance.
     *
     * @param row DB result row.
     * @return User instance or null.
     */
    public static User parse(final Row row) {
        if (row == null) {
            return null;
        }

        final UUID id = row.getUUID("id");
        final String username = row.getString("username");
        final String password = row.getString("password");
        final String salt = row.getString("salt");
        final String name = row.getString("name");

        if (username != null && password != null && salt != null && name != null) {
            return new User(id, username, password, salt, name);
        }
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(salt, user.salt)
                && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, salt, name);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", salt='" + salt + '\''
                + ", name='" + name + '\''
                + '}';
    }
}