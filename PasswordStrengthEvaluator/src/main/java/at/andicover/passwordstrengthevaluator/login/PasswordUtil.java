package at.andicover.passwordstrengthevaluator.login;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.springframework.lang.NonNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Utility class for password salting.
 */
public final class PasswordUtil {

    public static final Charset ENCODING = StandardCharsets.ISO_8859_1;

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
        super();
    }

    private static byte[] getSalt() {
        final byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public static Map.Entry<String, byte[]> saltPassword(@NonNull final String password, @NonNull final byte[] salt)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.SHA3_512);
        md.update(salt);
        return new AbstractMap.SimpleEntry<>(new String(md.digest(password.getBytes(ENCODING)), ENCODING), salt);
    }

    public static Map.Entry<String, byte[]> saltPassword(@NonNull final String password)
            throws NoSuchAlgorithmException {
        final byte[] salt = getSalt();
        return saltPassword(password, salt);
    }
}