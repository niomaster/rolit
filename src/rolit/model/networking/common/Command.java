package rolit.model.networking.common;

import rolit.model.networking.client.ClientProtocol;
import rolit.model.networking.server.ServerProtocol;
import rolit.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Command {
    private final static HashMap<String, ArgumentType[]> serverArgumentTypes = new LinkedHashMap<String, ArgumentType[]>();
    private final static HashMap<String, ArgumentType[]> clientArgumentTypes = new LinkedHashMap<String, ArgumentType[]>();

    static {
        serverArgumentTypes.put(ServerProtocol.HANDSHAKE, new ArgumentType[] { ArgumentType.Integer, ArgumentType.String });
        serverArgumentTypes.put(ServerProtocol.ERROR, new ArgumentType[] { ArgumentType.Integer });
        serverArgumentTypes.put(ServerProtocol.GAME, new ArgumentType[] { ArgumentType.String, ArgumentType.Boolean, ArgumentType.Integer });
        serverArgumentTypes.put(ServerProtocol.START, new ArgumentType[] { ArgumentType.MultiString });
        serverArgumentTypes.put(ServerProtocol.MOVE, new ArgumentType[] {  });
        serverArgumentTypes.put(ServerProtocol.MOVE_DONE, new ArgumentType[] { ArgumentType.String, ArgumentType.Integer, ArgumentType.Integer });
        serverArgumentTypes.put(ServerProtocol.GAME_OVER, new ArgumentType[] { ArgumentType.Integer, ArgumentType.MultiString });
        serverArgumentTypes.put(ServerProtocol.MESSAGE, new ArgumentType[] { ArgumentType.String, ArgumentType.String });
        serverArgumentTypes.put(ServerProtocol.CHALLENGE, new ArgumentType[] { ArgumentType.MultiString } );
        serverArgumentTypes.put(ServerProtocol.CHALLENGE_RESPONSE, new ArgumentType[] { ArgumentType.String, ArgumentType.Boolean });
        serverArgumentTypes.put(ServerProtocol.CAN_BE_CHALLENGED, new ArgumentType[] { ArgumentType.String, ArgumentType.Boolean });
        serverArgumentTypes.put(ServerProtocol.HIGHSCORE, new ArgumentType[] { ArgumentType.MultiString });
        serverArgumentTypes.put(ServerProtocol.ONLINE, new ArgumentType[] { ArgumentType.String, ArgumentType.Boolean });

        clientArgumentTypes.put(ClientProtocol.HANDSHAKE, new ArgumentType[] { ArgumentType.String, ArgumentType.Integer, ArgumentType.String });
        clientArgumentTypes.put(ClientProtocol.CREATE_GAME, new ArgumentType[] {  });
        clientArgumentTypes.put(ClientProtocol.JOIN_GAME, new ArgumentType[] { ArgumentType.String });
        clientArgumentTypes.put(ClientProtocol.START_GAME, new ArgumentType[] {  });
        clientArgumentTypes.put(ClientProtocol.MOVE, new ArgumentType[] { ArgumentType.Integer, ArgumentType.Integer });
        clientArgumentTypes.put(ClientProtocol.MESSAGE, new ArgumentType[] { ArgumentType.String });
        clientArgumentTypes.put(ClientProtocol.CHALLENGE, new ArgumentType[] { ArgumentType.MultiString });
        clientArgumentTypes.put(ClientProtocol.CHALLENGE_RESPONSE, new ArgumentType[] { ArgumentType.Boolean });
        clientArgumentTypes.put(ClientProtocol.HIGHSCORE, new ArgumentType[] { ArgumentType.String, ArgumentType.String });
    }

    private final Object[] arguments;
    private final String command;



    public Command(String command, Object... arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public Object getArgument(int i) {
        return arguments[i];
    }

    public int getArgumentCount() {
        return arguments.length;
    }

    public void writeTo(PrintStream output) throws IOException {
        output.print(this.command);

        for(Object argument : arguments) {
            output.print(CommonProtocol.COMMAND_DELIMITER);

            if(argument instanceof String[]) {
                output.print(Strings.join(CommonProtocol.COMMAND_DELIMITER, (String[]) argument));
            } else {
                output.print(argument);
            }
        }

        output.print(CommonProtocol.LINE_ENDING);
        output.flush();

        if(output.checkError()) {
            throw new IOException("Write failed");
        }
    }

    private static Object parse(String data, ArgumentType arg) throws ProtocolException {
        switch(arg) {
            case Integer:
                return Integer.parseInt(data);
            case Boolean:
                if(!data.equals(CommonProtocol.T_BOOLEAN_TRUE) && !data.equals(CommonProtocol.T_BOOLEAN_FALSE)) {
                    throw new ProtocolException("Boolean must be true or false.", ServerProtocol.ERROR_GENERIC);
                }

                return data.equals(CommonProtocol.T_BOOLEAN_TRUE);
            case String:
                return data;
            case MultiString:
                throw new ProtocolException("Cannot parse a MultiString as a singular argument.", ServerProtocol.ERROR_GENERIC);
        }

        // Impossible
        return null;
    }

    public static Command readFromClient(BufferedReader input) throws IOException, ProtocolException {
        return readFrom(input, clientArgumentTypes);
    }

    public static Command readFromServer(BufferedReader input) throws IOException, ProtocolException {
        return readFrom(input, serverArgumentTypes);
    }

    private static Command readFrom(BufferedReader input, HashMap<String, ArgumentType[]> argumentTypes) throws IOException, ProtocolException {
        String[] parts = input.readLine().split(CommonProtocol.COMMAND_DELIMITER);
        String command = parts[0];
        parts = Arrays.copyOfRange(parts, 1, parts.length);

        ArgumentType[] args = argumentTypes.get(command);

        if(args == null) {
            throw new ProtocolException("Can't parse unregistered command " + command, ServerProtocol.ERROR_GENERIC);
        }

        boolean multiString = false;
        int multiStringLocation = -1;

        for(int i = 0; i < args.length; i++) {
            if(args[i] == ArgumentType.MultiString) {
                multiString = true;
                multiStringLocation = i;
                break;
            }
        }

        Object[] result = new Object[args.length];

        if(multiString) {
            for(int i = 0; i < multiStringLocation; i++) {
                result[i] = parse(parts[i], args[i]);
            }

            int left = args.length - multiStringLocation - 1;

            for(int i = parts.length - left, resultI = multiStringLocation + 1; i < parts.length; i++, resultI++) {
                result[resultI] = parse(parts[i], args[resultI]);
            }

            result[multiStringLocation] = Arrays.copyOfRange(parts, multiStringLocation, multiStringLocation + parts.length - args.length + 1);
        } else {
            if(parts.length > args.length) {
                throw new ProtocolException("Length of parts exceeds argument count.", ServerProtocol.ERROR_GENERIC);
            }

            for(int i = 0; i < parts.length; i++) {
                result[i] = parse(parts[i], args[i]);
            }
        }

        return new Command(command, result);
    }
}
