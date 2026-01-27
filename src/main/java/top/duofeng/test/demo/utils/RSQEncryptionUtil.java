package top.duofeng.test.demo.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * project test-demo
 * path top.duofeng.test.demo.utils.RSQEncryptionUtil
 * author Rorschach
 * dateTime 2026/1/28 1:33
 */
@Slf4j
public class RSQEncryptionUtil {
    // 加密算法
    private static final String ALGORITHM_RSA = "RSA";
    private static final KeyPair KEY_PAIR_GENERATOR = initPairGenerator();

    @SneakyThrows
    private static KeyPair initPairGenerator() {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }


    //生成公钥，私钥的密钥对
    @SneakyThrows
    public static List<String> getRSAKeyString(String module) {
        List<String> keyList = new ArrayList<>(2);
        //生成密钥对
        String publicKey = Base64.getEncoder().encodeToString(KEY_PAIR_GENERATOR.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(KEY_PAIR_GENERATOR.getPrivate().getEncoded());
        keyList.add(publicKey);
        keyList.add(privateKey);
        return keyList;
    }

    // Java中RSAPublicKeySpec、X509EncodedKeySpec支持生成RSA公钥
    // 此处使用X509EncodedKeySpec生成
    private static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    // Java中只有RSAPrivateKeySpec、PKCS8EncodedKeySpec支持生成RSA私钥
    // 此处使用PKCS8EncodedKeySpec生成
    private static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }


    public static String encrypt(String plainText, String publicKey) {
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            byte[] bytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        }catch (Exception ex){
            log.error("加密异常", ex);
        }
        return null;
    }

    public static String decrypt(String encryptedText, String privateKey) {
        try{
            byte[] decodeBytes = Base64.getDecoder().decode(encryptedText);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            byte[] bytes = cipher.doFinal(decodeBytes);
            return new String(bytes);
        }catch (Exception ex){
            log.error("加密异常", ex);
        }
        return null;
    }

    public static void main(String[] args) {
        String name = "huizhi@".concat(UUID.randomUUID().toString());
        System.out.println("origin: "+name);
        List<String> huizhi = getRSAKeyString("huizhi");
        System.out.println("public key: " + huizhi.get(0));
        System.out.println("private key: " + huizhi.get(1));

        String encryptedStr = encrypt(name, huizhi.get(0));
        System.out.println("encrypted: "+ encryptedStr);
        System.out.println("decrypted: " + decrypt(encryptedStr, huizhi.get(1)));
    }
}
