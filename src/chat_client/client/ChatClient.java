package chat_client.client;

import chat_client.gui.ChatView;
import server.server_logic.Server;

public class ChatClient {

    private boolean connected;
    private String nickname;
    private ChatView chatView;
    public Server server;

    public ChatClient(ChatView chatView, Server server) {
        this.chatView = chatView;
        this.server = server;
        chatView.setChat(this);
    }

    public boolean connectedToServer(String nickname) {
        this.nickname = nickname;
        if (server.connectClient(this)) {
            showText("Вы успешно подключились\n");
            connected = true;
            String log = server.getLog();
            if (log != null){
                showText(log);
            }
            return true;
        } else {
            showText("Подключение не удалось");
            return false;
        }
    }

    public void disconnectFromServer() {
        if (connected) {
            connected = false;
            chatView.disconnectedFromServer();
            showText("Вы были отключены от сервера");
        }
    }

    public void answerFromServer(String text){
        showText(text);
    }

    public void sendMessage(String text) {
        if (connected) {
            if (!text.equals("")) {
                server.messageSending(nickname + ": " + text + "\n");
            }
        } else {
            showText("Нет подключения к серверу");
        }
    }

    public String getNickname() {
        return nickname;
    }

    private void showText(String text) {
        chatView.showMessage(text + "\n");
    }
}
