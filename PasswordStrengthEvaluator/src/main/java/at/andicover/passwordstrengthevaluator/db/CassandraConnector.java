package at.andicover.passwordstrengthevaluator.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;
import com.google.common.collect.ImmutableMap;

/**
 * Connection class for Cassandra DB.
 */
public final class CassandraConnector {

    private static final String NODE = System.getProperty("cassandra_node");
    private static final int PORT = 9042;
    private static final String KEYSPACE = "test";
    private static final int REPLICATION_FACTOR = 3;

    private Cluster cluster;
    private Session session;

    /**
     * Opens the DB connection and initializes the DB (creating keyspace, tables, ...).
     */
    public void connect() {
        Cluster.Builder builder = Cluster.builder()
                .addContactPoint(NODE)
                .withPort(PORT);
        cluster = builder
                .withoutJMXReporting()
                .build();
        session = cluster.connect();
        initDB();
    }

    public Session getSession() {
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
        final String createTableStatement = "CREATE TABLE IF NOT EXISTS user (id uuid, username text, name text, password text, salt text, PRIMARY KEY (id));";
        session.execute(createTableStatement);
        final String createIndexStatement = "CREATE INDEX IF NOT EXISTS user_username ON user (username);";
        session.execute(createIndexStatement);
    }

    private void dropTables() {
        final String statement = "DROP TABLE user;";
        session.execute(statement);
    }
}