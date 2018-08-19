package com.avior.academic.comunes;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

public class Cifrador {
	
	private static final String secretKey = "avior_academic"; //llave para encriptar datos

    public static String encriptar(String texto) {

    
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);

            //Convierte los bytes cifrados a Base64
            //byte[] base64Bytes = Base64.encodeBase64(buf);
            //base64EncryptedString = new String(base64Bytes);

            //Convierte los bytes cifrados a Base32,los tokens solo contendran [A-Z] [2-7]
            Base32 b32 = new Base32();
            base64EncryptedString = b32.encodeAsString(buf);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String desencriptar(String textoEncriptado) throws Exception {

        
        String base64EncryptedString = "";

        try {
            //byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));

            Base32 b32 = new Base32();
            byte[] message = b32.decode(textoEncriptado.getBytes("utf-8"));

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

}
