import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import Console.ConsoleManager;
import controller.*;

public class ManagerClients {
    protected final GameController gameGrid;
    protected final Vector<Client> clients = new Vector<Client>();

    public ManagerClients(GameController gameGrid) {
        this.gameGrid = gameGrid;
    }

    public void sendObjectToPlayer(Communicate value, Client client) {
        if(!client.sendObject(value)) {
            try { client.close(); } catch (Exception e) { }
            try {
                ConsoleManager.println(Console.Colors.YELLOW_BOLD_BRIGHT,"client disconnected "+client.ID+" (timeout)");
                clients.remove(client);
                gameGrid.removePlayerByID(client.ID);
                sendObjectToPlayers(new Kick(client.ID, 1)); // timeout
            } catch (Exception e) { }
        }
    }

    public void sendObjectToPlayersExceptionPlayerID(Communicate value, String clientID) {
        for (Client client : clients)
            if(!client.ID.equals(clientID)) sendObjectToPlayer(value, client);
    }
    public void sendObjectToPlayers(Communicate value) 
    { for (Client client : clients) sendObjectToPlayer(value, client); }

    public void kill() {
        for (Client client : clients) {
            client.sendObject(new Kick(null, 4));
            try { client.close(); } catch (Exception e) { }
        }
    }

    public void HandleApple() {
        Random random = new Random();
        if(gameGrid.getSizePlayers() > 1 && gameGrid.getSizeApples() == 0)   {
            for(int i = 0; i < random.nextInt(1, 5);i++) {
                try {
                    String ID = UUID.randomUUID().toString();
                    Coordinate coords = gameGrid.getRandomEmptyCoord();
                    int valueMax = random.nextInt(10, 20);
                    int valueMin = random.nextInt(-5, 10);
                    int timeout = random.nextInt(500, 2000);

                    Apple a = new Apple(ID, coords, valueMax, valueMin, timeout);
                    gameGrid.addApple(a);
                    sendObjectToPlayers(a);
                } catch (Exception e) { }
            }
        }
    }
}
