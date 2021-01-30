package at.andicover.passwordstrengthevaluator.login;

import at.andicover.passwordstrengthevaluator.db.CassandraConnector;
import at.andicover.passwordstrengthevaluator.model.LoginData;
import at.andicover.passwordstrengthevaluator.model.User;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import static at.andicover.passwordstrengthevaluator.login.PasswordUtil.ENCODING;
import static at.andicover.passwordstrengthevaluator.login.PasswordUtil.saltPassword;

/**
 * User service that handles register and login of user. Executes DB statements.
 */
public final class UserService {

    private static final CassandraConnector CASSANDRA_CONNECTOR;

    private static final String STATEMENT_GET_USER =
            "SELECT id, name, username, password, salt FROM user WHERE username = ?;";
    private static final String STATEMENT_INSERT_USER =
            "INSERT INTO user (id, name, username, password, salt) VALUES (?, ?, ?, ?, ?);";
    private static final String STATEMENT_DELETE_USER = "DELETE FROM user WHERE id = ?;";

    private static PreparedStatement preparedStatementGetUser;
    private static PreparedStatement preparedStatementInsertUser;
    private static PreparedStatement preparedStatementDeleteUser;

    static {
        CASSANDRA_CONNECTOR = new CassandraConnector();
    }

    private UserService() {
        super();
    }

    /**
     * Login the user. Fetches the user from the DB by username and veriefies the provided password.
     *
     * @param loginData Provided login data of user.
     * @return Logged in user. Null if login failed.
     */
    @Nullable
    public static User login(@NonNull final LoginData loginData) {
        User user = getUser(loginData.getUsername());
        if (user == null) {
            return null;
        }

        try {
            final Map.Entry<String, byte[]> passwordData =
                    saltPassword(loginData.getPassword(), user.getSalt().getBytes(ENCODING));

            if (passwordData.getKey().equals(user.getPassword())) {
                return user;
            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Registers a new user.
     *
     * @param loginData Provided login data of user.
     * @return Login successful.
     */
    public static boolean register(@NonNull final LoginData loginData) {
        User user = getUser(loginData.getUsername());
        if (user != null) {
            return false; //User already exists
        }
        final Session session = CASSANDRA_CONNECTOR.getSession();

        synchronized (UserService.class) {
            if (preparedStatementInsertUser == null) {
                preparedStatementInsertUser = session.prepare(STATEMENT_INSERT_USER);
            }
        }

        try {
            final Map.Entry<String, byte[]> passwordData = saltPassword(loginData.getPassword());
            BoundStatement boundStatement = preparedStatementInsertUser
                    .bind(UUID.randomUUID(), loginData.getName(), loginData.getUsername(), passwordData.getKey(),
                            new String(passwordData.getValue(), ENCODING));
            return session.execute(boundStatement).wasApplied();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean delete(@NonNull final LoginData loginData) {
        final User userToDelete = getUser(loginData.getUsername());
        final Session session = CASSANDRA_CONNECTOR.getSession();

        synchronized (UserService.class) {
            if (preparedStatementDeleteUser == null) {
                preparedStatementDeleteUser = session.prepare(STATEMENT_DELETE_USER);
            }
        }

        BoundStatement boundStatement = preparedStatementDeleteUser.bind(userToDelete.getId());
        return session.execute(boundStatement).wasApplied();
    }

    private static User getUser(@NonNull final String username) {
        final Session session = CASSANDRA_CONNECTOR.getSession();

        synchronized (UserService.class) {
            if (preparedStatementGetUser == null) {
                preparedStatementGetUser = session.prepare(STATEMENT_GET_USER);
            }
        }

        BoundStatement boundStatement = preparedStatementGetUser.bind(username);
        ResultSet resultSet = session.execute(boundStatement);
        return User.parse(resultSet.one());
    }
}