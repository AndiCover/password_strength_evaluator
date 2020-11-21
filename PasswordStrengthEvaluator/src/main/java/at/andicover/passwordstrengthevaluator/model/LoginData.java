package at.andicover.passwordstrengthevaluator.model;

import org.springframework.lang.NonNull;

import static java.util.Objects.requireNonNull;

/**
 * Model for the login view.
 */
public final class LoginData {
    private String username;
    private String password;
    private String name;

    public LoginData() {
        this("", "");
    }

    public LoginData(@NonNull final String username, @NonNull final String password) {
        this(username, password, "");
    }

    public LoginData(@NonNull final String username, @NonNull final String password, @NonNull final String name) {
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.name = requireNonNull(name);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull final String username) {
        this.username = requireNonNull(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull final String password) {
        this.password = requireNonNull(password);
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull final String name) {
        this.name = name;
    }
}