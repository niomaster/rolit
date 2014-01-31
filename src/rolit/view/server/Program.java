package rolit.view.server;

import rolit.model.event.ServerListener;
import rolit.model.networking.server.ClientHandler;
import rolit.model.networking.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {
    public enum PromptType {
        Int,
        String
    }

    public static String prompt(String prompt, PromptType type) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.print(prompt);
            System.out.print(": ");
            String input = reader.readLine();

            switch(type) {
                case Int:
                    try {
                        Integer.parseInt(input);
                        return input;
                    } catch(Throwable e) { }
                    break;
                case String:
                    return input;
            }
        } while(true);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        do {
            String bindAddress = prompt("Vul een bind address in", PromptType.String);
            int port = Integer.parseInt(prompt("Vul een poortnummer in", PromptType.Int));

            try {
                Server server = new Server(bindAddress, port);
                server.serveForever();
                System.out.println("Server gestart.");
                server.addListener(new ServerListener() {
                    @Override
                    public void serverError(String reason) {
                        System.out.println("Serverfout: " + reason);
                    }

                    @Override
                    public void newClient(ClientHandler handler) {
                        System.out.println("Nieuwe client!");
                    }

                    @Override
                    public void clientError(String reason) {
                        System.out.println("Clientfout: " + reason);
                    }

                    @Override
                    public void clientMessage(String clientName, String text) {
                        System.out.println(clientName + " zegt: " + text);
                    }
                });
                server.getServerThread().join();
                System.out.println("Server afgesloten.");
            } catch(IOException e) {
                System.out.println("Kon niet binden op " + bindAddress + ":" + port);
            } catch(IllegalArgumentException e) {
                System.out.println("Kon niet binden op " + bindAddress + ":" + port);
            }
        } while(true);
    }
}
