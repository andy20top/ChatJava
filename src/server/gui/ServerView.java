package server.gui;

import server.server_logic.Server;

public interface ServerView {
    void showMessage(String message);
    void setServer(Server server);
}
