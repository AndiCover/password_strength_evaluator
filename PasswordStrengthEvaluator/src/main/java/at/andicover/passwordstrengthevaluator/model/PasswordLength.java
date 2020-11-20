package at.andicover.passwordstrengthevaluator.model;

public enum PasswordLength {
    SHORT(Integer.MIN_VALUE, 7),
    MEDIUM(8, 11),
    LONG(12, Integer.MAX_VALUE);

    private int minLength;
    private int maxLength;

    PasswordLength(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }
}