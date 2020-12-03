package org.springboot.chatroom.security;

import org.springframework.beans.factory.annotation.Value;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RsaUtil {
    private static final String KEY_ALGORITHM = "RSA";

    /** PrivateKey * 生成秘钥
     * openssl genrsa -out rsa_private_key.pem 2048
     * 转换成PKCS8格式
     * openssl pkcs8 -topk8 -inform * PEM -in rsa_private_key.pem -outform PEM -nocrypt
     * 在终端输出结果，去掉“-----BEGIN PRIVATE KEY-----” * “-----END PRIVATE KEY-----”
     * @return PrivateKey
     */
    public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // PKCS8格式的密钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(privateKey.getBytes()));
        // RSA 算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /** PublicKey 根据 秘钥 生成public key
     * openssl rsa -in rsa_private_key.pem -out rsa_public_key.pem -pubout
     * @return PublicKey
     */
    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getMimeDecoder().decode(publicKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
