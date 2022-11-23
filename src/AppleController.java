import java.util.Random;
import java.util.UUID;

import Console.ConsoleManager;
import controller.*;

public class AppleController extends ManagerClients implements Runnable {

    private boolean runningApples;
    private Thread handleApple;

    public AppleController(GameController gameGrid) {
        super(gameGrid);
        runningApples = false;
        this.handleApple = new Thread(this);
    }

    public void startApple() {
        runningApples = true;
        this.handleApple.start();
    }

    public void killApple() {
        this.runningApples = false;
        try { this.handleApple.join(); } 
        catch(InterruptedException e) {  }
    }

    public void run() {
        while(runningApples) {
           
        }
    }
}
