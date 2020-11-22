package at.andicover.passwordstrengthevaluator;

import at.andicover.passwordstrengthevaluator.pse.PasswordService;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Main entry point of the application.
 */
@SpringBootApplication
public class PasswordStrengthEvaluatorWebApplication {

    private static final String DEFAULT_WEAK_PASSWORD_FILE = "weak_passwords.txt";

    public static void main(final String[] args) {
        SpringApplication.run(PasswordStrengthEvaluatorWebApplication.class, args);
        additionalStartUpSteps();
    }

    private static void additionalStartUpSteps() {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(DEFAULT_WEAK_PASSWORD_FILE);
        if (inputStream != null) {
            final List<String> weakPasswords =
                    new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList());
            PasswordService.insertWeakPassword(weakPasswords);
        }
    }
}