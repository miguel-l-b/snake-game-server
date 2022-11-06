import java.net.Socket;

import game.*;

public class GameController {
    public final Socket socket;
    private final Grid game = new Grid(20);
    private boolean running;
    public GameController(Socket socket) {
        this.socket = socket;
    }


    public void handleApples() {
        while(running) {
            
        }
    }
    
    public void handleAccept() {
        while(running) {
            
        }
    }

    public synchronized void start() {
        running = true;
        handleAccept();
        handleApples();
    }
    public void kill() {
        running = false;
    }
}
