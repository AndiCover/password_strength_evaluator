package at.andicover.passwordstrengthevaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of the application.
 */
@SpringBootApplication
public final class PasswordStrengthEvaluatorWebApplication {

    private PasswordStrengthEvaluatorWebApplication() {
        super();
    }

    public static void main(final String[] args) {
        SpringApplication.run(PasswordStrengthEvaluatorWebApplication.class, args);
    }
}