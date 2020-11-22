package at.andicover.passwordstrengthevaluator.model;

import com.datastax.driver.core.Row;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * WeakPassword model. Contains same fields as DB table.
 */
public final class WeakPassword {

    private final String password;

    public WeakPassword(@NonNull final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Parse a DB result row to a WeakPassword instance.
     *
     * @param row DB result row.
     * @return WeakPassword instance or null.
     */
    public static WeakPassword parse(Row row) {
        if (row == null) {
            return null;
        }

        final String password = row.getString("password");

        if (password != null) {
            return new WeakPassword(password);
        }
        return null;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeakPassword that = (WeakPassword) o;
        return Objects.equals(password, that.password);
    }

    @Override public int hashCode() {
        return Objects.hash(password);
    }

    @Override public String toString() {
        return "WeakPassword{" +
                "password='" + password + '\'' +
                '}';
    }
}