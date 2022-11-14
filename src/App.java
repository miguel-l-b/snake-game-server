import java.net.ServerSocket;

import Console.Colors;
import Console.ConsoleManager;
import Input.Keyboard;

public class App {
    public static void main(String[] args) {
        AcceptClient accept = null;
        ConsoleManager.print(Colors.CYAN,"Port: ");
        try {
            int port = Keyboard.getInt();
            
            accept = new AcceptClient(new ServerSocket(port));
            accept.start();
            ConsoleManager.clear();
            ConsoleManager.println(Colors.GREEN, "Started in "+port);

            while(Keyboard.getString().equals("sair")) {
                accept.kill();
                System.exit(0);
            }
        } catch (Exception err) {
            ConsoleManager.println(Colors.RED, "Error in started");
            System.exit(0);
        }
    }
}
