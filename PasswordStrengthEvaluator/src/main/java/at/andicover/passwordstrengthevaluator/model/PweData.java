package at.andicover.passwordstrengthevaluator.model;

import java.util.Objects;

public class PweData {

    private String password;
    private PasswordLength passwordLength;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public PasswordLength getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(PasswordLength passwordLength) {
        this.passwordLength = passwordLength;
    }

    public double getEntropy() {
        return entropy;
    }

    public void setEntropy(double entropy) {
        this.entropy = entropy;
    }

    public long getUppercaseLetters() {
        return uppercaseLetters;
    }

    public void setUppercaseLetters(long uppercaseLetters) {
        this.uppercaseLetters = uppercaseLetters;
    }

    public long getLowercaseLetters() {
        return lowercaseLetters;
    }

    public void setLowercaseLetters(long lowercaseLetters) {
        this.lowercaseLetters = lowercaseLetters;
    }

    public long getNumbers() {
        return numbers;
    }

    public void setNumbers(long numbers) {
        this.numbers = numbers;
    }

    public long getSymbols() {
        return symbols;
    }

    public void setSymbols(long symbols) {
        this.symbols = symbols;
    }

    public boolean isOnWeakPasswordList() {
        return isOnWeakPasswordList;
    }

    public void setOnWeakPasswordList(boolean onWeakPasswordList) {
        isOnWeakPasswordList = onWeakPasswordList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PweData pweData = (PweData) o;
        return Double.compare(pweData.entropy, entropy) == 0 &&
                uppercaseLetters == pweData.uppercaseLetters &&
                lowercaseLetters == pweData.lowercaseLetters &&
                numbers == pweData.numbers &&
                symbols == pweData.symbols &&
                isOnWeakPasswordList == pweData.isOnWeakPasswordList &&
                score == pweData.score &&
                Objects.equals(password, pweData.password) &&
                passwordLength == pweData.passwordLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, passwordLength, entropy, uppercaseLetters, lowercaseLetters, numbers, symbols, isOnWeakPasswordList, score);
    }
}