package rolit.model.networking.sssecurity;

import rolit.util.Crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SSSecurity {
    private static final int SS_SECURITY_PORT = 2013;
    private static final String SS_SECURITY_HOST = "ss-security.student.utwente.nl";

    private static String get(String command) throws IOException {
        Socket socket = new Socket(SS_SECURITY_HOST, SS_SECURITY_PORT);
        new PrintStream(socket.getOutputStream()).println(command);
        return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
    }

    public static PublicKey getPublicKey(String user) {
        try {
            byte[] data = Crypto.base64Decode(get("PUBLICKEY " + user).split(" ")[1]);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            return fact.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {

        } catch (NoSuchAlgorithmException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(SSSecurity.getPublicKey("player_trollit"));
    }
}