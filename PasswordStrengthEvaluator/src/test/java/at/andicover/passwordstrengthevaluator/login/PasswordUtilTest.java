package at.andicover.passwordstrengthevaluator.login;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class PasswordUtilTest {

    private static final String PASSWORD = "sup3rStrong_P4ssword!";

    @Test
    public void testRandomSalting() throws NoSuchAlgorithmException {
        Map.Entry<String, byte[]> firstPasswordData = PasswordUtil.saltPassword(PASSWORD);
        Map.Entry<String, byte[]> secondPasswordData = PasswordUtil.saltPassword(PASSWORD);
        assertThat(firstPasswordData.getKey(), not(equalTo(secondPasswordData.getKey())));
        assertThat(firstPasswordData.getValue(), not(equalTo(secondPasswordData.getValue())));
    }

    @Test
    public void testSaltAndVerify() throws NoSuchAlgorithmException {
        Map.Entry<String, byte[]> passwordData = PasswordUtil.saltPassword(PASSWORD);
        assertThat(passwordData.getKey(), equalTo(PasswordUtil.saltPassword(PASSWORD, passwordData.getValue()).getKey()));
    }
}