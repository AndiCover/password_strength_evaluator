package at.andicover.passwordstrengthevaluator.util;

import at.andicover.passwordstrengthevaluator.model.PasswordLength;
import at.andicover.passwordstrengthevaluator.model.PweData;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class PasswordStrengthEvaluatorUtilTest {

    private final static int MAX_PASSWORD_LENGTH = 50;
    private static final String PASSWORD_EMPTY = "";
    private static final String PASSWORD_ONLY_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String PASSWORD_ONLY_LOWERCASE_UMLAUTS = "abcdefghijklmnopqrstuvwxyzäöü";
    private static final String PASSWORD_ONLY_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String PASSWORD_ONLY_UPPERCASE_UMLAUTS = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ";
    private static final String PASSWORD_ONLY_NUMBERS = "0123456789";
    private static final String PASSWORD_LOWERCASE_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜabcdefghijklmnopqrstuvwxyzäöü";
    private static final String PASSWORD_ONLY_SYMBOLS = ",;.:-_+*~#'?=)(/&%$§\"!";
    private static final String PASSWORD_ALL = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜabcdefghijklmnopqrstuvwxyzäöü0123456789,;.:-_+*~#'?=)(/&%$§\"!";

    private static final String WEAK_PASSWORD = "12345";
    private static final String BAD_PASSWORD = "kal12";
    private static final String MEDIUM_PASSWORD = "t4h8HJ(=";
    private static final String GOOD_PASSWORD = "JIfjds=fk30ßPÜ_j*?A";

    @Test
    public void testPasswordLength() {
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i <= MAX_PASSWORD_LENGTH; i++) {
            passwordBuilder.append('a');
            PasswordLength passwordLength;
            if (passwordBuilder.length() <= PasswordLength.SHORT.getMaxLength()) {
                passwordLength = PasswordLength.SHORT;
            } else if (passwordBuilder.length() <= PasswordLength.MEDIUM.getMaxLength()) {
                passwordLength = PasswordLength.MEDIUM;
            } else {
                passwordLength = PasswordLength.LONG;
            }

            assertThat(PasswordStrengthEvaluatorUtil.evaluatePasswordLength(passwordBuilder.toString()), equalTo(passwordLength));
        }
    }

    @Test
    public void testPasswordLowercaseCount() {
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_EMPTY), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_ONLY_LOWERCASE), equalTo(26L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_ONLY_UPPERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_ONLY_LOWERCASE_UMLAUTS), equalTo(29L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_ONLY_UPPERCASE_UMLAUTS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_ONLY_NUMBERS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_LOWERCASE_UPPERCASE), equalTo(29L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_ONLY_SYMBOLS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countLowercaseLetters(PASSWORD_ALL), equalTo(29L));
    }

    @Test
    public void testPasswordUppercaseCount() {
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_EMPTY), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_ONLY_LOWERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_ONLY_UPPERCASE), equalTo(26L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_ONLY_LOWERCASE_UMLAUTS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_ONLY_UPPERCASE_UMLAUTS), equalTo(29L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_ONLY_NUMBERS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_LOWERCASE_UPPERCASE), equalTo(29L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_ONLY_SYMBOLS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countUppercaseLetters(PASSWORD_ALL), equalTo(29L));
    }

    @Test
    public void testPasswordNumberCount() {
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_EMPTY), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_ONLY_LOWERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_ONLY_UPPERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_ONLY_LOWERCASE_UMLAUTS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_ONLY_UPPERCASE_UMLAUTS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_ONLY_NUMBERS), equalTo(10L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_LOWERCASE_UPPERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_ONLY_SYMBOLS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countNumbers(PASSWORD_ALL), equalTo(10L));
    }

    @Test
    public void testPasswordSymbolCount() {
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_EMPTY), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_ONLY_LOWERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_ONLY_UPPERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_ONLY_LOWERCASE_UMLAUTS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_ONLY_UPPERCASE_UMLAUTS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_ONLY_NUMBERS), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_LOWERCASE_UPPERCASE), equalTo(0L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_ONLY_SYMBOLS), equalTo(22L));
        assertThat(PasswordStrengthEvaluatorUtil.countSymbols(PASSWORD_ALL), equalTo(22L));
    }

    @Test
    public void testCalculateEntropy() {
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_EMPTY), equalTo(0.0));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_ONLY_LOWERCASE), equalTo(4.7));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_ONLY_UPPERCASE), equalTo(4.7));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_ONLY_LOWERCASE_UMLAUTS), equalTo(4.8));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_ONLY_UPPERCASE_UMLAUTS), equalTo(4.8));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_ONLY_NUMBERS), equalTo(3.3));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_LOWERCASE_UPPERCASE), equalTo(5.8));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_ONLY_SYMBOLS), equalTo(4.4));
        assertThat(PasswordStrengthEvaluatorUtil.calculateEntropy(PASSWORD_ALL), equalTo(6.4));
    }

    @Test
    public void testIsOnWeakPasswordList() {
        assertThat(PasswordStrengthEvaluatorUtil.isOnWeakPasswordList(WEAK_PASSWORD), equalTo(true));
        assertThat(PasswordStrengthEvaluatorUtil.isOnWeakPasswordList(BAD_PASSWORD), equalTo(false));
        assertThat(PasswordStrengthEvaluatorUtil.isOnWeakPasswordList(MEDIUM_PASSWORD), equalTo(false));
        assertThat(PasswordStrengthEvaluatorUtil.isOnWeakPasswordList(GOOD_PASSWORD), equalTo(false));
    }

    @Test
    public void testEvaluatePassword() {
        PweData pweData = new PweData();
        pweData.setPassword(WEAK_PASSWORD);
        assertThat(PasswordStrengthEvaluatorUtil.evaluate(pweData).getScore(), equalTo(0));

        pweData.setPassword(BAD_PASSWORD);
        assertThat(PasswordStrengthEvaluatorUtil.evaluate(pweData).getScore(), equalTo(9));

        pweData.setPassword(MEDIUM_PASSWORD);
        assertThat(PasswordStrengthEvaluatorUtil.evaluate(pweData).getScore(), equalTo(48));

        pweData.setPassword(GOOD_PASSWORD);
        assertThat(PasswordStrengthEvaluatorUtil.evaluate(pweData).getScore(), equalTo(100));
    }
}