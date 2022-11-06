package game;

public class Message {
    public static final String[] messages = new String[] {
        "🫣",
        "Oi",
        "Olá, bom jogo",
        "Boa sorte",
        "Que vença o melhor",


        "🫠",
        "3 capa",
        "É penta",
        "Olha o hexa vindo",
        "GG",

        "🤬",
        "F#@, para de me fechar",
        "Ahhhhhhhhhhhhhhhh",
        "Por um, ummm!",
        "Ohhh desgrama",
        
        "🤡",
        "olha a água mineral",
        "Vou lancar um feitiço",
        "Truque de mágica",
        "Você é fraco",
        "Escuta só esse som",
    };

    public final String playerID;
    public final int messageID;

    public Message(String playerID, int messageID) {
        this.playerID = playerID;
        this.messageID = messageID;
    }
}