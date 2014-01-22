package rolit.util;

import java.security.*;
import java.util.Arrays;

/**
 * Created by laurens on 1/21/14.
 */
public class Crypto {
    /**
     * Alle characters van base64 in volgorde.
     */
    private static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static final char BASE64_PADDING = '=';

    private static final int NONCE_LENGTH = 256;

    /**
     * Converteert bytes naar een Base64-String. Er is namelijk geen standaardmethode in Java om dit te doen.
     * @param data De bytes
     * @return De String
     */
    public static String base64Encode(byte[] data) {
        String result = "";

        for(int i = 0; i < data.length / 3; i++) {
            int byte1 = (data[i * 3] + 256) % 256,
                    byte2 = (data[i * 3 + 1] + 256) % 256,
                    byte3 = (data[i * 3 + 2] + 256) % 256;

            result += BASE64_CHARS.charAt(byte1 / 4);
            result += BASE64_CHARS.charAt((byte1 % 4) * 16 + byte2 / 16);
            result += BASE64_CHARS.charAt((byte2 % 16) * 4 + byte3 / 64);
            result += BASE64_CHARS.charAt(byte3 % 64);
        }

        if(data.length % 3 == 1) {
            byte byte1 = data[data.length - 1];

            result += BASE64_CHARS.charAt(byte1 / 4);
            result += BASE64_CHARS.charAt((byte1 % 4) * 16);
            result += BASE64_PADDING;
            result += BASE64_PADDING;
        } else if(data.length % 3 == 2) {
            byte byte1 = data[data.length - 2], byte2 = data[data.length - 1];

            result += BASE64_CHARS.charAt(byte1 / 4);
            result += BASE64_CHARS.charAt((byte1 % 4) * 16 + byte2 / 16);
            result += BASE64_CHARS.charAt((byte2 % 16) * 4);
            result += BASE64_PADDING;
        }

        return result;
    }

    /**
     * Converteert een Bas64-String naar bytes.
     * @param data De String
     * @return De bytes
     */
    public static byte[] base64Decode(String data) {
        byte[] result = new byte[data.length() / 4 * 3];

        for(int i = 0; i < data.length() / 4; i++) {
            char char1 = data.charAt(i * 4), char2 = data.charAt(i * 4 + 1),
                    char3 = data.charAt(i * 4 + 2), char4 = data.charAt(i * 4 + 3);

            int result1 = BASE64_CHARS.indexOf(char1) * 4 + BASE64_CHARS.indexOf(char2) / 16;
            int result2 = (BASE64_CHARS.indexOf(char2) % 16) * 16 + BASE64_CHARS.indexOf(char3) / 4;
            int result3 = ((BASE64_CHARS.indexOf(char3) + 4) % 4) * 64 + BASE64_CHARS.indexOf(char4);

            result[i * 3] = (byte) (result1 < 128 ? result1 : (result1 - 256));
            result[i * 3 + 1] = (byte) (result2 < 128 ? result2 : (result2 - 256));
            result[i * 3 + 2] = (byte) (result3 < 128 ? result3 : (result3 - 256));
        }

        if(data.charAt(data.length() - 2) == '=') {
            return Arrays.copyOfRange(result, 0, result.length - 2);
        } else if(data.charAt(data.length() - 1) == '=') {
            return Arrays.copyOfRange(result, 0, result.length - 1);
        } else {
            return result;
        }
    }

    public static String getNonce() {
        byte[] b = "helloéíï".getBytes();
        byte[] bytes = new byte[NONCE_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return base64Encode(bytes);
    }

    public static boolean verify(byte[] cypherText, byte[] original, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signature.update(original);
            return signature.verify(cypherText);
        } catch (NoSuchAlgorithmException e) {

        } catch (InvalidKeyException e) {

        } catch (SignatureException e) {

        }

        return false;
    }
}
