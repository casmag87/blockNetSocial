package security;


import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class SharedSecret {
    private static SecretKey secret;

    private static final Logger logger =  LogManager.getLogger(SharedSecret.class);

    public static SecretKey generateKey(int keySize) {
        try {
            logger.info("Generating secret key...");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize);
            SecretKey generatedKey = keyGenerator.generateKey();
            logger.info("Secret key generated successfully.");
            return generatedKey;
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating secret key", e);
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    public static SecretKey getSharedKey() {
        if (secret == null) {
            // Replace 256 with your desired key size (128, 192, or 256)
            secret = generateKey(256);
        }
        return secret;
    }
}
