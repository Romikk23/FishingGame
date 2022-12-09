package crypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypt {
    private static final byte[] enc = {76, 41, -77, -57, 97, -89, 31, -12};

    public static byte[] encrypt(String text) {
        try {
            SecretKey myDesKey = new SecretKeySpec(enc, "DES");

            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            byte[] textByte = text.getBytes("UTF8");

            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(textByte);
            return textEncrypted;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
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

            String str = new String(textDecrypted);
            return str;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        byte[] dec = encrypt("time: 22 12\ng");
        System.out.println(decrypt(dec));
    }
}