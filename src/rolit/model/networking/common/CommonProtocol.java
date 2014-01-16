package rolit.model.networking.common;

import java.io.*;

/**
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
     * Einde van regels
     */
    public static final String LINE_ENDING = "\r\n";

    /**
     * Delimiter van commando's
     */
    public static final String COMMAND_DELIMITER = " ";

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

        return line.split(" +");
    }
}
