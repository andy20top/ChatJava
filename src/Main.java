import chat_client.client.ChatClient;
import chat_client.gui.ChatGUI;
import server.gui.ServerGUI;
import server.repository.RepoWriter;
import server.server_logic.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new ServerGUI(), new RepoWriter());

        new ChatClient(new ChatGUI(), server);
        new ChatClient(new ChatGUI(), server);


    }
}