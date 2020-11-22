package at.andicover.passwordstrengthevaluator.pse;

import at.andicover.passwordstrengthevaluator.db.CassandraConnector;
import at.andicover.passwordstrengthevaluator.model.WeakPassword;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Password service that handles inserting and querying weak passwords.
 */
public final class PasswordService {

    private static final CassandraConnector CASSANDRA_CONNECTOR;
    private static final String STATEMENT_GET_PASSWORD = "SELECT password FROM weak_passwords WHERE password = ?;";
    private static final String STATEMENT_GET_ALL = "SELECT password FROM weak_passwords;";
    private static final String STATEMENT_INSERT_WEAK_PASSWORD =
            "INSERT INTO weak_passwords (password) VALUES (?);";

    static {
        CASSANDRA_CONNECTOR = new CassandraConnector();
    }

    private PasswordService() {
        super();
    }

    public static boolean insertWeakPassword(@NonNull final List<String> passwords) {
        int fetched = 0;
        final BatchStatement batch = new BatchStatement();
        final Session session = CASSANDRA_CONNECTOR.getSession();
        final PreparedStatement preparedStatement = session.prepare(STATEMENT_INSERT_WEAK_PASSWORD);

        while (fetched < passwords.size()) {
            final List<String> passwordSublist = passwords.subList(fetched, Math.min(65536, passwords.size()));
            fetched += passwordSublist.size();

            for (String weakPassword : passwordSublist) {
                batch.add(preparedStatement.bind(weakPassword));
            }
            if (!session.execute(batch).wasApplied()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOnWeakPasswordList(@NonNull final String password) {
        final Session session = CASSANDRA_CONNECTOR.getSession();
        BoundStatement boundStatement = session.prepare(STATEMENT_GET_PASSWORD).bind(password);
        ResultSet resultSet = session.execute(boundStatement);
        return resultSet.one() != null;
    }

    public static List<WeakPassword> getWeakPasswords() {
        final Session session = CASSANDRA_CONNECTOR.getSession();
        ResultSet resultSet = session.execute(STATEMENT_GET_ALL);
        return resultSet.all().stream().map(WeakPassword::parse).collect(Collectors.toList());
    }
}