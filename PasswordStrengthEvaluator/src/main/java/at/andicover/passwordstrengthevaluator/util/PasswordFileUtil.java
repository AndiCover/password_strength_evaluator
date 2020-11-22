package at.andicover.passwordstrengthevaluator.util;

import at.andicover.passwordstrengthevaluator.pse.PasswordService;
import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public final class PasswordFileUtil {

    private PasswordFileUtil() {
        super();
    }

    public static void uploadWeakPasswords(final InputStream inputStream) {
        if (inputStream != null) {
            final List<String> weakPasswords =
                    new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList());
            PasswordService.insertWeakPassword(weakPasswords);
        }
    }
}