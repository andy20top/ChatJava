package chat_client.gui;

import chat_client.client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatGUI extends JFrame implements ChatView {
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


    private ChatClient chatClient;


    public ChatGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
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
                    message();
                }
            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        panelBottom.add(message, textFieldConstraints);
        panelBottom.add(btnSend);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);

    }



    private void appendLog(String text){
        chatArea.append(text + "\n");
    }



    @Override
    public void showMessage(String message) {
        chatArea.append(message);
    }

    @Override
    public void disconnectedFromServer() {
        panelTop.setVisible(true);
    }

    public void disconnectFromServer(){
        chatClient.disconnectFromServer();
    }

    public void login(){
        if (chatClient.connectedToServer(login.getText())){
            panelTop.setVisible(false);
        }
    }

    private void message(){
        chatClient.sendMessage(message.getText());
        message.setText("");
    }

    @Override
    public void setChat(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
}
