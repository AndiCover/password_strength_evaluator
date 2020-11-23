package at.andicover.passwordstrengthevaluator.db;

import java.io.File;
import java.io.IOException;

public class CassandraRunner {

    public static void main(final String[] args) {
        startCassandra();
    }

    public static void startCassandra() {
        System.out.println("Starting cassandra...");

        final ProcessBuilder processBuilder =
                new ProcessBuilder("cmd.exe",
                        "start",
                        "/C",
                        "start",
                        "cassandra\\apache-cassandra-3.11.8\\bin\\cassandra.bat",
                        "-f");
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        try {
            processBuilder.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}