import java.net.ServerSocket;
import java.util.Vector;

import Console.ConsoleManager;
import controller.*;
import controller.Communicate;
import game.*;
import utils.*;

public class ServerController {
    public final ServerSocket socket;
    private final GameController gameGrid;
    private final Vector<String> ipsBaned = new Vector<>();
    private boolean running;
    public ServerController(ServerSocket socket) throws Exception {
        this.socket = socket;
        this.gameGrid = new GameController(20);
    }

    public void sendObjectToPlayer(Communicate value, Player player) {
        if(!player.client.sendObject(value)) {
            try { player.client.close(); } catch (Exception e) { }
            try {
                gameGrid.removePlayerByID(player.ID);
                sendObjectToPlayers(new Kick(player.ID, 1)); // timeout
            } catch (Exception e) { }
        }
    }

    public void sendObjectToPlayersExceptionPlayerID(Communicate value, String playerID) {
        for (Player player : gameGrid.getPlayers())
            if(player.ID != playerID) sendObjectToPlayer(value, player);
    }
    public void sendObjectToPlayers(Communicate value) 
    { for (Player player : gameGrid.getPlayers()) sendObjectToPlayer(value, player); }
    


    public void handleAccept() { // usuário entra no servidor
        while(running) {
            // aceitar a conexao do cliente
            MessageController client = null;
            try {
                // recebendo uma conexão 
				client = new MessageController(socket.accept()); //métdo para travar e esperar que o usuário passe o username
                Object obj = client.getObject(); // espera pela classe LogIn

                if(obj instanceof LogIn) {
                    Player player = new Player(client, " "+client.socket.getLocalAddress(), ((LogIn)obj).USERNAME, ColorsPlayer.RANDOM(), 0, 0);
                    
                    // adicionar o player ao game
                    gameGrid.addPlayer(player);
                    // enviar todos os dados do game para o cliente
                    sendObjectToPlayer(new Game(gameGrid.getApples(), gameGrid.getPlayers()), player);
                    // enviar para todos os clientes o novo cliente
                    sendObjectToPlayersExceptionPlayerID(new NewPlayer(player), player.ID);
                    // espera por uma request do player
                    new Thread(() -> handleRequest(player)).start();
                }
                
			} catch (Exception err) {
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

    public void handleRequest(Player player) { // aceitar uma requisição do usuário (mudança de posição)
        while(running) {
            Object request = player.client.getObject();
            if(request instanceof AlterPosition) {
                // verificar se é possível o player andar nessa direção e fazer o player andar
                int index = -1;
                try {
                    index = gameGrid.indexOfPlayerByID(player.ID);
                } catch(Exception e) { }
                
                if(((AlterPosition)request).X > 0)
                    gameGrid.movingHorizontal(+1, index);
                else if(((AlterPosition)request).X < 0)
                    gameGrid.movingHorizontal(-1, index);
                else if(((AlterPosition)request).Y > 0)
                    gameGrid.movingVertical(+1, index);
                else if(((AlterPosition)request).Y < 0)
                    gameGrid.movingVertical(-1, index);
                // mandar para todos os clientes a nova alteração do player atual
                
            }

            if(request instanceof Kick) {

            }
        }
    }

    public void handleApples() { // gerar as maças
        while(running) { // verificar se existem no máximo 1 maça
            // tempo de espera de 10 segundos ou 10000 mile segundos
            // gerar 5 maças
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