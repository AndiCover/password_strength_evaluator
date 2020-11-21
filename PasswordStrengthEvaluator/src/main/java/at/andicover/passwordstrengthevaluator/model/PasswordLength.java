package at.andicover.passwordstrengthevaluator.model;

/**
 * Enum for the password length type.
 * Passwords with length <= 7 are of type {@link PasswordLength#SHORT}
 * Passwords with length between 8 and 11 are of type {@link PasswordLength#MEDIUM}
 * Passwords with length >= 12 are of type {@link PasswordLength#LONG}.
 */
public enum PasswordLength {
    SHORT(Integer.MIN_VALUE, 7),
    MEDIUM(8, 11),
    LONG(12, Integer.MAX_VALUE);

    private final int minLength;
    private final int maxLength;

    PasswordLength(final int minLength, final int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    /**
     * @return the minimum password length of this type.
     */
    public int getMinLength() {
        return this.minLength;
    }

    /**
     * @return the maximum password length of this type.
     */
    public int getMaxLength() {
        return this.maxLength;
    }
}