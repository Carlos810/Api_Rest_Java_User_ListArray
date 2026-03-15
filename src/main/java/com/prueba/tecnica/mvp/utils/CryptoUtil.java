package com.prueba.tecnica.mvp.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtil {

    private static final String SECRET = "1234567890123456";
    public static String encrypt(String value) {
        if(value == null){
            return null;
        }

        try {

            Cipher cipher = Cipher.getInstance("AES");

            SecretKeySpec key =
                    new SecretKeySpec(SECRET.getBytes(), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(value.getBytes()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
