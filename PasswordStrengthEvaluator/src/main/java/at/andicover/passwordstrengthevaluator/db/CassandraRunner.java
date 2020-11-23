package at.andicover.passwordstrengthevaluator.db;

import java.io.IOException;

/**
 * Starts the cassandra database in the background.
 */
public class CassandraRunner {

    public static void main(final String[] args) {
        startCassandra();
    }

    public static void startCassandra() {
        System.out.println("Starting cassandra...");
        try {
            final Process process = Runtime.getRuntime()
                    .exec("cmd.exe start /C cassandra\\apache-cassandra-3.11.8\\bin\\cassandra.bat");
            Thread.sleep(5_000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}