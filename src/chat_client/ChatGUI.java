package chat_client;

import server.ServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField serverIPadress = new JTextField("127.0.0.1");
    private final JTextField chPort = new JTextField("8080");
    private final JTextField login = new JTextField("ivan_ivanovich");
    private final JPasswordField password = new JPasswordField();
    private final JButton btnLogin = new JButton("Login");

    private final JTextArea chatArea = new JTextArea();
    private final JScrollPane scrollPaneChat = new JScrollPane(chatArea);

    private final JPanel panelBottom = new JPanel(new GridBagLayout());
    GridBagConstraints textFieldConstraints = new GridBagConstraints();
    private final JTextField message = new JTextField("Message");
    private final JButton btnSend = new JButton("Send");

    private boolean connected;
    private String nickname;

    public ServerGUI server;

    public ChatGUI(ServerGUI server) {
        this.server = server;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectedToServer();
            }
        });
        panelTop.add(serverIPadress);
        panelTop.add(chPort);
        panelTop.add(login);
        panelTop.add(password);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        add(scrollPaneChat);

        textFieldConstraints.fill = GridBagConstraints.BOTH;
        textFieldConstraints.weightx = 0.8f;
        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    sendMessage();
                }
            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panelBottom.add(message, textFieldConstraints);
        panelBottom.add(btnSend);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);

    }

    private void connectedToServer() {
        if (server.connectedClient(this)) {
            appendLog("Вы успешно подключились!\n");
            panelTop.setVisible(false);
            connected = true;
            nickname = login.getText();
            String log = server.getLog();
            if (log != null){
                appendLog(log);
            } else {
                appendLog("Подключение не удалось");
            }
        }
    }

    public void disconnectFromServer() {
        if (connected) {
            panelTop.setVisible(true);
            connected = false;
            server.disconnectedClient(this);
            appendLog("Вы были отключены от сервера!");
        }
    }


    private void appendLog(String text){
        chatArea.append(text + "\n");
    }

    public void answer(String text){
        appendLog(text);
    }

    public void sendMessage() {
        if (connected) {
            String text = message.getText();
            if (!text.equals("")) {
                server.messageSending(nickname + ": " + text);
//                message.setText("");
            }
        } else {
            appendLog("Нет подключения к серверу");
        }
    }



}
