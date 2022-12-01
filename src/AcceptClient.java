import controller.*;
import utils.*;

import Console.ConsoleManager;
import java.net.ServerSocket;
import java.util.Random;
import java.util.UUID;

public class AcceptClient extends ManagerClients implements Runnable {
    public final ServerSocket socket;

    public AcceptClient(ServerSocket socket) throws Exception {
        super(new GameController(20));
        if(socket == null)
            throw new Exception("socket cannot be null");
        this.socket = socket;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true) {
            HandleApple();
            // aceitar a conexao do cliente
            MessageController mc = null;
            ConsoleManager.println(Console.Colors.CYAN, "Aguardando uma conexão...");
            try {
                // recebendo uma conexão 
				mc = new MessageController(socket.accept()); //métdo para travar e esperar que o usuário passe o username
                ConsoleManager.println(Console.Colors.MAGENTA, "client connected "+mc.getID());
                if(clients.size() >= 5) {
                    ConsoleManager.println(Console.Colors.YELLOW_BOLD_BRIGHT,"client disconnected "+mc.getID()+" (server full)");
                    mc.sendObject(new Kick(null, 2));
                    continue;
                }

                Object obj = mc.getObject(); // espera pela classe LogIn
                if(obj instanceof LogIn) {
                    Player player = new Player(mc.getID(), ((LogIn)obj).USERNAME, ColorsPlayer.RANDOM(), gameGrid.getRandomEmptyCoord());                 
                    // adicionar o player ao game
                    gameGrid.addPlayer(player);
                    Client client = new Client(player.ID, mc);
                    clients.add(client);
                    // enviar todos os dados do game para o cliente
                    mc.sendObject(new Game(mc.getID(), gameGrid.getApples(), gameGrid.getPlayers()));
                    // enviar para todos os clientes o novo cliente
                    sendObjectToPlayersExceptionPlayerID(player, player.ID);
                    // espera por uma request do player
                    new RequestHandle(client);
                } else {
                    // avisar o cliente e desconectar
                    ConsoleManager.println(Console.Colors.YELLOW_BOLD_BRIGHT,"client disconnected "+mc.getID());
                }
			} catch (Exception err) {
                err.printStackTrace();
                ConsoleManager.println(Console.Colors.RED_BOLD_BRIGHT,"Connection error");
                if(mc != null) { // (roolback) voltar o que foi feito
                    try {
                        mc.sendObject(new Kick(null, 3));
                        mc.close();
                    } catch (Exception err2) { }
                }
            }            
        }
    }

    protected class RequestHandle implements Runnable {
        private final Client client;
        public RequestHandle(Client client) {
            this.client = client;
            new Thread(this).start();
        }
        
        @Override
        public void run() {
            while(true) {
                Communicate request = null;
                try {
                    ConsoleManager.println(Console.Colors.CYAN, "Aguardando uma requisição...");
                    request = client.getObject();
                    if(request == null) break;
                    if(request instanceof AlterPosition) {
                        AlterPosition alterP = (AlterPosition)request;
                        int index = gameGrid.indexOfPlayerByID(alterP.ID);

                        if(alterP.getX() != 0) 
                            gameGrid.movingHorizontal(alterP.getX(), index);
                        else
                            gameGrid.movingVertical(alterP.getY(), index);
                        
                        Apple eatApple = gameGrid.handleCollision(index);

                        if(eatApple != null) {
                            sendObjectToPlayers(new EatApple(eatApple.ID, alterP.ID, eatApple.getValue()));
                            HandleApple();
                        }
                        sendObjectToPlayersExceptionPlayerID(new AlterPosition(alterP.ID, 'p', gameGrid.getPlayer(index).getCoords()), alterP.ID);
                    } else if(request instanceof Kick) {
                        // desconectar o cliente
                        try {
                            ConsoleManager.println(Console.Colors.YELLOW_BOLD_BRIGHT,"client disconnected "+client.ID+" to: "+((Kick)request).getReason()+" (kick)");
                            client.close();
                            clients.remove(client);
                            gameGrid.removePlayerByID(client.ID);
                            sendObjectToPlayers(new Kick(client.ID, 0));
                        } catch (Exception e) { }
                    }
                } catch(Exception err) {
                    try {
                        err.printStackTrace();
                        ConsoleManager.println(Console.Colors.RED_BOLD_BRIGHT,"Connection error");
                        ConsoleManager.println(Console.Colors.YELLOW_BOLD_BRIGHT,"client disconnected "+client.ID+" (request error)");
                        Kick kick = new Kick(client.ID, 0);

                        sendObjectToPlayer(kick, client);

                        clients.remove(client);
                        gameGrid.removePlayerByID(client.ID);
                        sendObjectToPlayers(kick);
                        client.close();
                    } catch (Exception e) { }
                }
            }
        }
    }
}
