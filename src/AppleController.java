import controller.GameController;
import controller.Grid;

public class AppleController extends ManagerClients implements Runnable {

    private boolean runningApples;
    private Thread handleApple;

    public AppleController(GameController gameGrid) {
        super(gameGrid);
        runningApples = false;
        this.handleApple = new Thread(this);
    }

    public void startApple() {
        this.handleApple.start();
    }

    public void killApple() {
        this.runningApples = false;
        try { this.handleApple.join(); } 
        catch(InterruptedException e) {  }
    }

    public void run() {
        Grid g ;
        while(runningApples) {
         if(g.getSizeApples() == 0)   {
                for(int i; g.getSizeApples()<5;i++) {
                    Apple result; 
                    g.addApple();
                }
            }
        }
    }
}
