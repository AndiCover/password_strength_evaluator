package at.andicover.passwordstrengthevaluator.util;

import at.andicover.passwordstrengthevaluator.model.PasswordLength;
import at.andicover.passwordstrengthevaluator.model.PweData;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PasswordStrengthEvaluatorUtil {

    private static final double LOW_ENTROPY = 2.0;
    private static final double HIGH_ENTROPY = 3.0;

    private PasswordStrengthEvaluatorUtil() {
        super();
    }

    public static PweData evaluate(@NonNull PweData pweData) {
        pweData.setPasswordLength(evaluatePasswordLength(pweData.getPassword()));
        pweData.setEntropy(calculateEntropy(pweData.getPassword()));
        pweData.setLowercaseLetters(countLowercaseLetters(pweData.getPassword()));
        pweData.setUppercaseLetters(countUppercaseLetters(pweData.getPassword()));
        pweData.setNumbers(countNumbers(pweData.getPassword()));
        pweData.setSymbols(countSymbols(pweData.getPassword()));
        pweData.setOnWeakPasswordList(isOnWeakPasswordList(pweData.getPassword()));

        pweData.setScore(evaluateScore(pweData));
        return pweData;
    }

    static PasswordLength evaluatePasswordLength(@NonNull final String password) {
        for (PasswordLength passwordLength : PasswordLength.values()) {
            if (password.length() >= passwordLength.getMinLength() && password.length() <= passwordLength.getMaxLength()) {
                return passwordLength;
            }
        }
        return PasswordLength.SHORT;
    }

    static double calculateEntropy(@NonNull final String password) {
        Map<Character, Double> probabilityTable = new HashMap<>();
        double entropy = 0;

        for (Character character : password.chars().mapToObj(ch -> (char) ch).collect(Collectors.toList())) {
            double probability = 1d / password.length();

            if (probabilityTable.containsKey(character))
                probabilityTable.put(character, probabilityTable.get(character) + probability);
            else
                probabilityTable.put(character, probability);
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
        return password.chars().mapToObj(ch -> (char) ch).filter(c -> !Character.isAlphabetic(c) && !Character.isDigit(c)).count();
    }

    static boolean isOnWeakPasswordList(@NonNull final String password) {
        try (Stream<String> stream = Files.lines(Paths.get("E:\\Programming\\git\\password_strength_evaluator\\WeakPasswords\\weak_passwords.txt"))) {  //TODO, maybe use DB?
            return stream.anyMatch(x -> x.equals(password));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static int evaluateScore(@NonNull final PweData pweData) {
        int score = 0;
        if (pweData.isOnWeakPasswordList()) {
            return score;
        }

        score = switch (pweData.getPasswordLength()) {
            case LONG -> 100;
            case MEDIUM -> 60;
            default -> 20;
        };

        if (pweData.getNumbers() == 0) {
            score *= 0.5;
        }
        if (pweData.getLowercaseLetters() == 0) {
            score *= 0.5;
        }
        if (pweData.getUppercaseLetters() == 0) {
            score *= 0.5;
        }
        if (pweData.getSymbols() == 0) {
            score *= 0.5;
        }
        if (pweData.getEntropy() <= LOW_ENTROPY) {
            score *= 0.4;
        } else if (pweData.getEntropy() <= HIGH_ENTROPY) {
            score *= 0.8;
        }
        return score;
    }
}