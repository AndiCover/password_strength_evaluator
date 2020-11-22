package at.andicover.passwordstrengthevaluator.pse;

import at.andicover.passwordstrengthevaluator.model.PasswordLength;
import at.andicover.passwordstrengthevaluator.model.PseData;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for evaluating a password provided as {@link PseData}.
 * Sets the evaluation results on the provided instance of {@link PseData} and returns it.
 */
public final class PasswordStrengthEvaluatorUtil {

    private static final double LOW_ENTROPY = 2.0;
    private static final double HIGH_ENTROPY = 3.0;

    private PasswordStrengthEvaluatorUtil() {
        super();
    }

    /**
     * Evaluates the provided password.
     *
     * @param pseData {@link PseData} containing the password.
     * @return {@link PseData} containing the password and the password evaluation information.
     */
    public static PseData evaluate(@NonNull final PseData pseData) {
        pseData.setPasswordLength(evaluatePasswordLength(pseData.getPassword()));
        pseData.setEntropy(calculateEntropy(pseData.getPassword()));
        pseData.setLowercaseLetters(countLowercaseLetters(pseData.getPassword()));
        pseData.setUppercaseLetters(countUppercaseLetters(pseData.getPassword()));
        pseData.setNumbers(countNumbers(pseData.getPassword()));
        pseData.setSymbols(countSymbols(pseData.getPassword()));
        pseData.setOnWeakPasswordList(isOnWeakPasswordList(pseData.getPassword()));

        pseData.setScore(evaluateScore(pseData));
        return pseData;
    }

    static PasswordLength evaluatePasswordLength(@NonNull final String password) {
        for (PasswordLength passwordLength : PasswordLength.values()) {
            if (password.length() >= passwordLength.getMinLength()
                    && password.length() <= passwordLength.getMaxLength()) {
                return passwordLength;
            }
        }
        return PasswordLength.SHORT;
    }

    /**
     * Calculates the Shannon Entropy of the password.
     *
     * @param password the provided password.
     * @return Shannon Entropy.
     */
    static double calculateEntropy(@NonNull final String password) {
        Map<Character, Double> probabilityTable = new HashMap<>();
        double entropy = 0;

        for (Character character : password.chars().mapToObj(ch -> (char) ch).collect(Collectors.toList())) {
            double probability = 1d / password.length();

            if (probabilityTable.containsKey(character)) {
                probabilityTable.put(character, probabilityTable.get(character) + probability);
            } else {
                probabilityTable.put(character, probability);
            }
        }

        for (double prob : probabilityTable.values()) {
            entropy = entropy + prob * (Math.log(prob) / Math.log(2));
        }

        return ((int) (-entropy * 10)) / 10.0;
    }

    static long countLowercaseLetters(@NonNull final String password) {
        return password.chars().mapToObj(ch -> (char) ch).filter(Character::isLowerCase).count();
    }

    static long countUppercaseLetters(@NonNull final String password) {
        return password.chars().mapToObj(ch -> (char) ch).filter(Character::isUpperCase).count();
    }

    static long countNumbers(@NonNull final String password) {
        return password.chars().mapToObj(ch -> (char) ch).filter(Character::isDigit).count();
    }

    static long countSymbols(@NonNull final String password) {
        return password.chars().mapToObj(ch -> (char) ch)
                .filter(c -> !Character.isAlphabetic(c) && !Character.isDigit(c)).count();
    }

    static boolean isOnWeakPasswordList(@NonNull final String password) {
        return PasswordService.isOnWeakPasswordList(password);
    }

    private static int evaluateScore(@NonNull final PseData pseData) {
        int score = 0;
        if (pseData.isOnWeakPasswordList()) {
            return score;
        }

        switch (pseData.getPasswordLength()) {
            case LONG:
                score = 100;
                break;
            case MEDIUM:
                score = 60;
                break;
            case SHORT:
            default:
                score = 20;
                break;
        }

        if (pseData.getNumbers() == 0) {
            score *= 0.8;
        }
        if (pseData.getLowercaseLetters() == 0) {
            score *= 0.8;
        }
        if (pseData.getUppercaseLetters() == 0) {
            score *= 0.8;
        }
        if (pseData.getSymbols() == 0) {
            score *= 0.8;
        }
        if (pseData.getEntropy() <= LOW_ENTROPY) {
            score *= 0.4;
        } else if (pseData.getEntropy() <= HIGH_ENTROPY) {
            score *= 0.8;
        }
        return score;
    }
}