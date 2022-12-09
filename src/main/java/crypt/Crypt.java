package crypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Crypt {
    private static final byte[] enc = {76, 41, -77, -57, 97, -89, 31, -12};

    public static byte[] encrypt(String text) {
        try {
            SecretKey myDesKey = new SecretKeySpec(enc, "DES");

            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            byte[] textByte = text.getBytes(StandardCharsets.UTF_8);

            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            return desCipher.doFinal(textByte);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(byte[] text) {
        try {
            SecretKey myDesKey = new SecretKeySpec(enc, "DES");

            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            byte[] textDecrypted = desCipher.doFinal(text);

            return new String(textDecrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}