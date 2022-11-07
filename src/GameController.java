import java.net.ServerSocket;

import Console.ConsoleManager;
import game.*;
import utils.*;

public class GameController {
    public final ServerSocket socket;
    private final Grid game = new Grid(20);
    private boolean running;
    public GameController(ServerSocket socket) {
        this.socket = socket;
    }

    public void sendMessageToPlayer(String msg, Player player) {
        if(!player.client.send(msg)) {
            try { player.client.close(); } catch (Exception e) { }
            try {
                game.removePlayerByID(player.ID);
                sendMessageToPlayers("kick "+player.ID);
            } catch (Exception e) { }
        }
    }
    public void sendMessageToPlayersExceptionPlayerID(String value, String playerID) {
        for (Player player : game.getPlayers())
            if(player.ID != playerID) sendMessageToPlayer(value, player);
    }
    public void sendMessageToPlayers(String value) 
    { for (Player player : game.getPlayers()) sendMessageToPlayer(value, player); }


    public void handleAccept() { // usuário entra no servidor
        while(running) {
            // aceitar a conexao do cliente
            MessageController client = null;
            try {
                // recebendo uma conexão 
				client = new MessageController(socket.accept()); //métdo para travar e esperar que o usuário passe o username
                Player player = new Player(client, " "+client.socket.getLocalAddress(), client.getMsg(), ColorsPlayer.RANDOM(), 0, 0);
                
                // adicionar o player ao game
                game.addPlayer(player);

                // enviar todos os dados do game para o cliente
                client.send(game.toString());
                // evinar para todos os clinetes o novo cliente
                sendMessageToPlayersExceptionPlayerID(player.toString(), player.ID);
			} catch (Exception err) { 
                ConsoleManager.println(Console.Colors.RED_BOLD_BRIGHT,"Connection error");
                if(client != null) { // (roolback) voltar o que foi feito
                    try {
                        game.removePlayerByID(" "+client.socket.getLocalAddress());
                        client.sendMessage("error in accept");
                        client.close();
                    } catch (Exception err2) { }
                }
            }            
        }
    }

    public void handleRequest() { // aceitar uma requisição do usuário (mudança de posição)
        while(running) {
            // verificar se é possível o player andar nessa direção e fazer o player andar
            // mandar para todos os clientes a nova alteração do player atual
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
        handleRequest();
        handleApples();
    }
    public void kill() {
        running = false;
    }
}