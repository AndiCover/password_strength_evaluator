package at.andicover.passwordstrengthevaluator.db;

import at.andicover.passwordstrengthevaluator.login.UserService;
import at.andicover.passwordstrengthevaluator.model.LoginData;
import at.andicover.passwordstrengthevaluator.util.PasswordFileUtil;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Connection class for Cassandra DB.
 */
public final class CassandraConnector {

    private static final String DEFAULT_WEAK_PASSWORD_FILE = "weak_passwords.txt";
    private static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    private static final int REPLICATION_FACTOR = 3;

    private Cluster cluster;
    private Session session;
    private Properties properties;

    /**
     * Opens the DB connection and initializes the DB (creating keyspace, tables, ...).
     */
    private void connect() {
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(APPLICATION_PROPERTIES_FILE)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Cluster.Builder builder = Cluster.builder()
                .addContactPoint(properties.getProperty("spring.data.cassandra.contact-points", "127.0.0.1"))
                .withPort(Integer.parseInt(properties.getProperty("spring.data.cassandra.port", "9042")));
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
        final String keyspace = properties.getProperty("spring.data.cassandra.keyspace-name", "test");
        final SchemaStatement statement = SchemaBuilder.createKeyspace(keyspace)
                .ifNotExists()
                .with()
                .replication(ImmutableMap.of("class", "SimpleStrategy", "replication_factor", REPLICATION_FACTOR));
        getSession().execute(statement);
        session.execute(String.format("USE %s;", keyspace));
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
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(DEFAULT_WEAK_PASSWORD_FILE)) {
            PasswordFileUtil.uploadWeakPasswords(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertDefaultUser() {
        UserService.register(new LoginData("test", "test", "Admin"));
    }
}