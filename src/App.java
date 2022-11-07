import Console.Colors;
import Console.ConsoleManager;
import Input.Keyboard;

public class App {
    public static void main(String[] args) {
        ConsoleManager.print(Colors.CYAN,"Port: ");
        try {
            int port = Keyboard.getInt();
            
            new Server(port);
            ConsoleManager.println(Colors.GREEN, "Started in "+port);
        } catch (Exception err) {
            ConsoleManager.println(Colors.RED, "Error in started");
            System.exit(0);
        }
    }
}
