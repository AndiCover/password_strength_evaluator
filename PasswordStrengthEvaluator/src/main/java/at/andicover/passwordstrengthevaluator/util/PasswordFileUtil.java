package at.andicover.passwordstrengthevaluator.util;

import at.andicover.passwordstrengthevaluator.pse.PasswordService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public final class PasswordFileUtil {

    private PasswordFileUtil() {
        super();
    }

    public static void uploadWeakPasswords(final InputStream inputStream) {
        if (inputStream != null) {
            final List<String> weakPasswords =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                            .collect(Collectors.toList());
            PasswordService.insertWeakPassword(weakPasswords);
        }
    }
}