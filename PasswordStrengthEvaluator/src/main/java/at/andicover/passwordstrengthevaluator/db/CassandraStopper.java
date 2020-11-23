package at.andicover.passwordstrengthevaluator.db;

import java.io.File;
import java.io.IOException;

/**
 * Stops a running cassandra database.
 */
public class CassandraStopper {

    public static void main(final String[] args) {
        startCassandra();
    }

    public static void startCassandra() {
        System.out.println("Stopping cassandra...");
        try {
            final ProcessBuilder processBuilder =
                    new ProcessBuilder("cmd.exe",
                            "start",
                            "/C",
                            "cassandra\\apache-cassandra-3.11.8\\bin\\stop-server.bat",
                            "cassandra\\apache-cassandra-3.11.8\\pid.txt");
            processBuilder.directory(new File(System.getProperty("user.dir")));
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}