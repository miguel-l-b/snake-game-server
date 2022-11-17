import controller.*;
import utils.*;


import java.util.Vector;

import Console.ConsoleManager;
import java.net.ServerSocket;


public class AcceptClient extends Thread {
    public final ServerSocket socket;
    private final GameController gameGrid;
    private final Vector<Client> clients = new Vector<Client>();

    public AcceptClient(ServerSocket socket) throws Exception {
        if(socket == null)
            throw new Exception("socket cannot be null");
        this.socket = socket;
        this.gameGrid = new GameController(20); 
    }

    public void sendObjectToPlayer(Communicate value, Client client) {
        ConsoleManager.print("Sent object to player " + client.ID+" ");
        if(!client.sendObject(value)) {
            try { client.close(); } catch (Exception e) { }
            try {
                System.out.println(client.ID);
                gameGrid.removePlayerByID(client.ID);
                sendObjectToPlayers(new Kick(client.ID, 1)); // timeout
            } catch (Exception e) { }
        }
    }

    public void sendObjectToPlayersExceptionPlayerID(Communicate value, String clientID) {
        for (Client client : clients)
            if(client.ID != clientID) sendObjectToPlayer(value, client);
    }
    public void sendObjectToPlayers(Communicate value) 
    { for (Client client : clients) sendObjectToPlayer(value, client); }

    public void kill() {
        for (Client client : clients) {
            client.sendObject(new Kick(null, 4));
            try { client.close(); } catch (Exception e) { }
        }
    }

    @Override
    public void run() {
        while(true) {
            // aceitar a conexao do cliente
            MessageController client = null;
            ConsoleManager.println(Console.Colors.CYAN, "Aguardando uma conexão...");
            try {
                // recebendo uma conexão 
				client = new MessageController(socket.accept()); //métdo para travar e esperar que o usuário passe o username
                ConsoleManager.println(Console.Colors.MAGENTA, "client connected "+client.getID());
                Object obj = client.getObject(); // espera pela classe LogIn
                if(obj instanceof LogIn) {
                    Player player = new Player(client.getID(), ((LogIn)obj).USERNAME, ColorsPlayer.RANDOM(), gameGrid.getRandomEmptyCoord());                 
                    // adicionar o player ao game
                    gameGrid.addPlayer(player);
                    clients.add(new Client(player.ID, client));
                    // enviar todos os dados do game para o cliente
                    client.sendObject(new Game(client.getID(), gameGrid.getApples(), gameGrid.getPlayers()));
                    // enviar para todos os clientes o novo cliente
                    sendObjectToPlayersExceptionPlayerID(player, player.ID);
                    // espera por uma request do player
                    // new RequestHandle(player);
                } else {
                    // avisar o cliente e desconectar
                    ConsoleManager.println(Console.Colors.YELLOW_BOLD_BRIGHT,"client disconnected "+client.getID());
                }
			} catch (Exception err) {
                err.printStackTrace();
                ConsoleManager.println(Console.Colors.RED_BOLD_BRIGHT,"Connection error");
                if(client != null) { // (roolback) voltar o que foi feito
                    try {
                        client.sendObject(new Kick(null, 3));
                        client.close();
                    } catch (Exception err2) { }
                }
            }            
        }
    }
}
