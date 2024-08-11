import chat_client.ChatGUI;
import server.ServerGUI;

public class Main {
    public static void main(String[] args) {

        ServerGUI server = new ServerGUI();
        new ChatGUI(server);
        new ChatGUI(server);

    }
}