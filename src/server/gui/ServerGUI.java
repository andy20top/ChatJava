package server.gui;

import server.server_logic.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ServerView {

    private static final int X_POS = 550;
    private static final int Y_POS = 600;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private final JPanel panelBottom = new JPanel(new GridLayout(1, 2));
    private final JButton btnStop = new JButton("Stop");
    private final JButton btnStart = new JButton("Start");


    private final JTextArea logs = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(logs);

    private Server server;

    public ServerGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(X_POS, Y_POS, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);


        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.serverStart();
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.serverStop();
            }
        });
        panelBottom.add(btnStart);
        panelBottom.add(btnStop);
        add(panelBottom, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public Server getConnection(){
        return server;
    }

    @Override
    public void showMessage(String message) {
        logs.append(message);
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }
}
