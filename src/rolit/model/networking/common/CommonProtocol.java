package rolit.model.networking.common;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Het deel van het protocol dat de server en de client delen.
 * @author Pieter Bos
 * @author Martijn de Bijl
 *
 *
 */
public class CommonProtocol {
    /**
     * Constante om aan te geven dat de client of server alleen de basisdingen kan doen.
     */
    public static final int SUPPORTS_BAREBONE = 0;

    /**
     * Constante om aan te geven dat de client of server ook kan chatten.
     */
    public static final int SUPPORTS_CHAT = 1;

    /**
     * Constante om aan te geven dat de client of server ook kan uitdagen / uitgedaagd worden.
     */
    public static final int SUPPORTS_CHALLENGE = 2;

    /**
     * SUPPORTS_CHAT | SUPPORTS_CHALLENGE
     */
    public static final int SUPPORTS_CHAT_CHALLENGE = 3;

    /**
     * String-waarde voor true in het protocol
     */
    public static final String T_BOOLEAN_TRUE = "true";

    /**
     * String-waarde voor false in het protocol
     */
    public static final String T_BOOLEAN_FALSE = "false";

    /**
     * Versie in de handshake voor een standaardimplementatie
     */
    public static final String VERSION_NONE = "Standaard";

    /**
     * Minimum aantal spelers om een spel te laten beginnen
     */
    public static final int MINIMUM_PLAYER_COUNT = 2;

    /**
     * Maximum aantal spelers om een spel te kunnen laten beginnen
      */
    public static final int MAXIMUM_PLAYER_COUNT = 4;

    /**
     * Einde van regels
     */
    public static final String LINE_ENDING = "\r\n";

    /**
     * Delimiter van commando's
     */
    public static final String COMMAND_DELIMITER = " ";

    /**
     * Alle characters van base64 in volgorde.
     */
    private static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static final char BASE64_PADDING = '=';

    /**
     * Methode om van een {@code OutputStream} een correcte {@code PrintStream} te maken.
     * @param output De {@code OutputStream}
     * @return de {@code PrintStream}
     */
    public PrintStream getPrintStream(OutputStream output) {
        try {
            return new PrintStream(output, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * Methode om van een {@code InputStream} een correcte {@code BufferedReader} te maken.
     * @param input De {@code InputStream}
     * @return De {@code BufferedReader}
     */
    public BufferedReader getBufferedReader(InputStream input) {
        try {
            return new BufferedReader(new InputStreamReader(input, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * Methode om een commando naar een {@code PrintStream} te schrijven.
     * @param commandParts De gedeeltes van het commando.
     * @param output De {@code PrintStream}
     */
    public void writeCommandTo(String[] commandParts, PrintStream output) {
        boolean first = true;
        String command = "";

        for(String argument : commandParts) {
            if(!first) {
                command += " ";
            }

            first = false;

            command += argument;
        }

        output.print(command);
        output.print("\r\n");
    }

    /**
     * Methode om een commando van een {@code BufferedReader} te lezen.
     * @param input De {@code BufferedReader}
     * @return De array van {@code String}s
     * @throws IOException
     */
    public String[] readCommandFrom(BufferedReader input) throws IOException {
        String line = input.readLine();

        return line.split(" ");
    }

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

    /**
     * Converteert een String van de ss-security-server naar een {@code PrivateKey}
     * @param data De data van de ss-security-server
     * @return De {@code PrivateKey}
     * @throws InvalidKeySpecException Als de data van de ss-security-server niet klopt.
     */
    public PrivateKey stringToPrivateKey(String data) throws InvalidKeySpecException {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(data.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Signt een bericht met een {@code PrivateKey} en retourneert het resultaat in Base64
     * @param message Het bericht
     * @param key De {@code PrivateKey}
     * @return Het resultaat in Base64
     * @throws InvalidKeyException
     */
    public String sign(String message, PrivateKey key) throws InvalidKeyException {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(key);
            signature.update(message.getBytes());
            return base64Encode(signature.sign());
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (SignatureException e) {
            return null;
        }
    }

    /**
     * Converteert een String van de ss-security-server naar een {@code PublicKey}
     * @param data De data van de ss-security-server
     * @return De {@code PublicKey}
     * @throws InvalidKeySpecException
     */
    public PublicKey stringToPublicKey(String data) throws InvalidKeySpecException {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Verifieert een gesignd bericht voor een {@code PublicKey}.
     * @param message Het bericht
     * @param signedMessage Het gesignde bericht
     * @param key De {@code PublicKey}
     * @return Of het signen goed is gegaan
     * @throws InvalidKeyException
     */
    public boolean verify(String message, String signedMessage, PublicKey key) throws InvalidKeyException {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(key);
            signature.update(message.getBytes());
            return signature.verify(base64Decode(signedMessage));
        } catch (NoSuchAlgorithmException e) {
            return false;
        } catch (SignatureException e) {
            return false;
        }
    }
}
