package chat_client.gui;

import chat_client.client.ChatClient;

public interface ChatView {
    void showMessage(String message);
    void disconnectedFromServer();
    void setChat(ChatClient chatClient);
}
