package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.LogManager;

import constants.ApplicationConstants;
import constants.LoggerMessage;
import constants.SecurityAccessConstants;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

/**
 * Class that provide data encoding.
 *
 * @author Yaroslav Baranov
 **/
public class EncodingProvider {

    private static Logger LOGGER = Logger.getLogger(EncodingProvider.class);

    /**
     * Encodes given string according application hashing algorithm
     *
     * @param string string to be encoded
     * @return encoded string
     */
    public static String encode(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ApplicationConstants.HASHING_ALGORITHM);
            string += SecurityAccessConstants.DEFAULT_PASSWORD_SALT;
            byte[] hash = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(LoggerMessage.ON_INVALID_HASHING_ALGORITHM_MESSAGE + e);
        }

        return "";
    }

}
