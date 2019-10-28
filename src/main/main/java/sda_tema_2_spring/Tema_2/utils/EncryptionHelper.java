package sda_tema_2_spring.Tema_2.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class EncryptionHelper {

    private static EncryptionHelper instance;
    private static final String LOCK = "";

    private EncryptionHelper() {

    }

    public static EncryptionHelper getInstance() {
        synchronized (LOCK) {
            if (null == instance) {
                instance = new EncryptionHelper();
            }
        }
        return instance;
    }

    public String plainTextToHash(String plainText) {
        return Hashing.sha256().hashString(plainText, StandardCharsets.UTF_8).toString();
    }
}
