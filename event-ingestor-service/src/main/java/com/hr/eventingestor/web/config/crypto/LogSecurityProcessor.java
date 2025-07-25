package com.hr.eventingestor.web.config.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogSecurityProcessor {

    @Value("${env.aes-key}")
    private String aesKey;

    private SecretKeySpec secretKey;

    public LogSecurityProcessor() {
        try {
            // Inicializar la clave AES al construir el procesador
            byte[] keyBytes = aesKey.getBytes(StandardCharsets.UTF_8);
            this.secretKey = new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            log.error("Error al inicializar la clave AES", e);
            throw new RuntimeException("Error al configurar la seguridad.", e);
        }
    }

    /**
     * Hashea una cadena usando SHA-256.
     * @param data La cadena a hashear.
     * @return El hash SHA-256 en formato hexadecimal.
     */
    public String hashSha256(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Algoritmo SHA-256 no disponible.", e);
            throw new RuntimeException("Error en el hashing de seguridad.", e);
        }
    }

    /**
     * Cifra una cadena usando AES (ejemplo básico).
     * @param plainText La cadena a cifrar.
     * @return La cadena cifrada en Base64.
     */
    public String encryptAes(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // ECB es simple, pero no recomendado para datos reales
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            log.error("Error al cifrar el mensaje.", e);
            throw new RuntimeException("Error en el cifrado de seguridad.", e);
        }
    }
}
