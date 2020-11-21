package at.andicover.passwordstrengthevaluator.login;

import at.andicover.passwordstrengthevaluator.db.CassandraConnector;
import org.springframework.lang.NonNull;

import java.util.List;

public final class PasswordService {

    private static final CassandraConnector CASSANDRA_CONNECTOR;

    static {
        CASSANDRA_CONNECTOR = new CassandraConnector();
    }

    private PasswordService() {
        super();
    }

    public static boolean insertWeakPassword(@NonNull final List<String> passwords) {
        // TODO
        return false;
    }

    public static boolean isOnWeakPasswordList(@NonNull final String password) {
        // TODO
        return false;
    }
}