package uk.co.automatictester.security.sign;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class HmacSha256 {

    @Test
    public void testHmacSha256() throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] message = "Lorem ipsum dolor sit amet".getBytes();
        byte[] key = "secret123key".getBytes();
        String algorithm = "HmacSHA256";

        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
        mac.init(secretKeySpec);
        byte[] hmacSha256 = mac.doFinal(message);

        String base64encodedHmacSha265 = Base64.getEncoder().encodeToString(hmacSha256);
        log.info(base64encodedHmacSha265);
    }
}
