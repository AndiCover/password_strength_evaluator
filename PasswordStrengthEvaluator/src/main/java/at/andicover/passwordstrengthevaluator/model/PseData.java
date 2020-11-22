package at.andicover.passwordstrengthevaluator.model;

import java.util.Objects;

/**
 * Password Strength Evaluation model. Holds the provided password and the evaluation information.
 */
public final class PseData {

    private String password;
    private PasswordLength passwordLength = PasswordLength.SHORT;
    private double entropy;
    private long uppercaseLetters;
    private long lowercaseLetters;
    private long numbers;
    private long symbols;
    private boolean isOnWeakPasswordList;
    private int score;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public PasswordLength getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(final PasswordLength passwordLength) {
        this.passwordLength = passwordLength;
    }

    public double getEntropy() {
        return entropy;
    }

    public void setEntropy(final double entropy) {
        this.entropy = entropy;
    }

    public long getUppercaseLetters() {
        return uppercaseLetters;
    }

    public void setUppercaseLetters(final long uppercaseLetters) {
        this.uppercaseLetters = uppercaseLetters;
    }

    public long getLowercaseLetters() {
        return lowercaseLetters;
    }

    public void setLowercaseLetters(final long lowercaseLetters) {
        this.lowercaseLetters = lowercaseLetters;
    }

    public long getNumbers() {
        return numbers;
    }

    public void setNumbers(final long numbers) {
        this.numbers = numbers;
    }

    public long getSymbols() {
        return symbols;
    }

    public void setSymbols(final long symbols) {
        this.symbols = symbols;
    }

    public boolean isOnWeakPasswordList() {
        return isOnWeakPasswordList;
    }

    public void setOnWeakPasswordList(final boolean onWeakPasswordList) {
        isOnWeakPasswordList = onWeakPasswordList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PseData pseData = (PseData) o;
        return Double.compare(pseData.entropy, entropy) == 0
                && uppercaseLetters == pseData.uppercaseLetters
                && lowercaseLetters == pseData.lowercaseLetters
                && numbers == pseData.numbers
                && symbols == pseData.symbols
                && isOnWeakPasswordList == pseData.isOnWeakPasswordList
                && score == pseData.score
                && Objects.equals(password, pseData.password)
                && passwordLength == pseData.passwordLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, passwordLength, entropy, uppercaseLetters, lowercaseLetters, numbers, symbols,
                isOnWeakPasswordList, score);
    }
}