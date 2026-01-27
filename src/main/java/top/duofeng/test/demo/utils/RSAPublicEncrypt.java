package top.duofeng.test.demo.utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * project test-demo
 * path top.duofeng.test.demo.utils.Rsa
 * author Rorschach
 * dateTime 2026/1/28 2:28
 */
public class RSAPublicEncrypt {

    private static final String ALGORITHM_RSA = "RSA";
    public static String encrypt(String plainText, String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
        plainText += "@" + UUID.randomUUID();
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes); // 转换为Base64字符串以便传输
    }

    private static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }
}