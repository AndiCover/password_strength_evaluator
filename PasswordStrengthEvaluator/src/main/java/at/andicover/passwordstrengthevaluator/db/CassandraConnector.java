package at.andicover.passwordstrengthevaluator.db;

import at.andicover.passwordstrengthevaluator.login.UserService;
import at.andicover.passwordstrengthevaluator.model.LoginData;
import at.andicover.passwordstrengthevaluator.util.PasswordFileUtil;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;
import com.google.common.collect.ImmutableMap;

import java.io.InputStream;

/**
 * Connection class for Cassandra DB.
 */
public final class CassandraConnector {

    private static final String NODE = System.getProperty("cassandra_node", "127.0.0.1");
    private static final String DEFAULT_WEAK_PASSWORD_FILE = "weak_passwords.txt";
    private static final int PORT = 9042;
    private static final String KEYSPACE = "test";
    private static final int REPLICATION_FACTOR = 3;

    private Cluster cluster;
    private Session session;

    /**
     * Opens the DB connection and initializes the DB (creating keyspace, tables, ...).
     */
    private void connect() {
        Cluster.Builder builder = Cluster.builder()
                .addContactPoint(NODE)
                .withPort(PORT);
        cluster = builder
                .withoutJMXReporting()
                .build();
        session = cluster.connect();
        initDB();
    }

    /**
     * Opens the DB connection or returns an existing session.
     *
     * @return The DB session.
     */
    public Session getSession() {
        if (this.session == null) {
            synchronized (CassandraConnector.class) {
                if (this.session == null) {
                    connect();
                }
            }
        }
        return this.session;
    }

    /**
     * Closes the DB connection.
     */
    public void close() {
        session.close();
        cluster.close();
    }

    private void initDB() {
        createKeyspace();
        createTables();
        insertDefaultUser();
        insertWeakPasswords();
    }

    private void createKeyspace() {
        final SchemaStatement statement = SchemaBuilder.createKeyspace(KEYSPACE)
                .ifNotExists()
                .with()
                .replication(ImmutableMap.of("class", "SimpleStrategy", "replication_factor", REPLICATION_FACTOR));
        getSession().execute(statement);
        session.execute(String.format("USE %s;", KEYSPACE));
    }

    private void createTables() {
        String createTableStatement =
                "CREATE TABLE IF NOT EXISTS user (id uuid, username text, name text, password text, salt text, PRIMARY KEY (id));";
        session.execute(createTableStatement);
        final String createIndexStatement = "CREATE INDEX IF NOT EXISTS user_username ON user (username);";
        session.execute(createIndexStatement);

        createTableStatement = "CREATE TABLE IF NOT EXISTS weak_passwords (password text, PRIMARY KEY (password));";
        session.execute(createTableStatement);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void dropTables() {
        String statement = "DROP TABLE user;";
        session.execute(statement);

        statement = "DROP TABLE weak_passwords";
        session.execute(statement);
    }

    private static void insertWeakPasswords() {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(DEFAULT_WEAK_PASSWORD_FILE);
        PasswordFileUtil.uploadWeakPasswords(inputStream);
    }

    private static void insertDefaultUser() {
        UserService.register(new LoginData("test", "test", "Admin"));
    }
}