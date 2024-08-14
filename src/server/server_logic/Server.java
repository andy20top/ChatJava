package server.server_logic;

import chat_client.client.ChatClient;
import server.gui.ServerView;
import server.repository.Repository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends JFrame {
    private ServerView serverView;
    private List<ChatClient> chatClientList;
    private boolean isServerWorking;
    private Repository repo;


    public Server(ServerView serverView, Repository repo) {
        this.serverView = serverView;
        this.repo = repo;
        chatClientList = new ArrayList<>();
        serverView.setServer(this);
    }



    public void serverStart() {
        if (isServerWorking) {
            showText("Cервер запущен\n");
            return;
        }
        isServerWorking = true;
        showText("Server started\n");
    }

    public void serverStop() {
        if (!isServerWorking) {
            showText("Cервер уже остановлен\n");
            return;
        }
        isServerWorking = false;
        while (!chatClientList.isEmpty()){
            disconnectClient(chatClientList.get(chatClientList.size() - 1));
        }
        showText("Сервер остановлен!");
    }

    public void disconnectClient(ChatClient chatClient){
        chatClientList.remove(chatClient);
        if (chatClient != null){
            chatClient.disconnectFromServer();
            showText(chatClient.getNickname() + " отключился от беседы");
        }
    }


    public boolean connectClient(ChatClient chatClient){
        if (!isServerWorking){
            return false;
        }
        chatClientList.add(chatClient);
        showText(chatClient.getNickname() + " подключился к беседе");
        return true;
    }

    public void messageSending(String text){
        if (!isServerWorking){
            return;
        }
        showText(text);
        answerAll(text);
        saveInHistory(text);
    }

    public String getLog() {
        return repo.load();
    }

    private void answerAll(String text){
        for (ChatClient chatClient : chatClientList){
            chatClient.answerFromServer(text);
        }
    }

    private void saveInHistory(String text){
        repo.save(text);
    }

    private void showText(String text) {
        serverView.showMessage(text + "\n");
    }

}