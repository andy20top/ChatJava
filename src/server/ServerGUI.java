package server;

import chat_client.ChatGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerGUI extends JFrame {


    private static final int X_POS = 550;
    private static final int Y_POS = 600;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    public static final String LOGS_PATH = "src" + File.separator + "server" + File.separator + "logs.txt";

    public List<ChatGUI> chatGUIList;

    private final JPanel panelBottom = new JPanel(new GridLayout(1, 2));
    private final JButton btnStop = new JButton("Stop");
    private final JButton btnStart = new JButton("Start");


    private final JTextArea logs = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(logs);

    private boolean isServerWorking;


    public ServerGUI() {
        isServerWorking = false;
        chatGUIList = new ArrayList<>();
        power();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(X_POS, Y_POS, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);

        panelBottom.add(btnStart);
        panelBottom.add(btnStop);
        add(panelBottom, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }


    private void power() {
        serverStop();
        serverStart();
    }

    private void serverStart() {
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    logs.setText(logs.getText() + "\nСервер запущен\n");
                    return;
                }
                isServerWorking = true;
                logs.setText(logs.getText() + "\nServer started\n");
            }
        });
    }

    private void serverStop() {
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    logs.setText(logs.getText() + "\nСервер не был запущен\n");
                    return;
                } else {
                    isServerWorking = false;
                    while (!chatGUIList.isEmpty()){
                        disconnectedClient(chatGUIList.get(chatGUIList.size()-1));
                    }
                    logs.setText(logs.getText() + "\nServer stoped\n");
                }
            }
        });
    }

    public boolean connectedClient (ChatGUI chatGUI) {
        if (!isServerWorking) {
            return false;
        }
        chatGUIList.add(chatGUI);
        return true;
    }

    public void disconnectedClient(ChatGUI chatGUI) {
        chatGUIList.remove(chatGUI);
        if (chatGUI != null) {
            chatGUI.disconnectFromServer();
        }
    }

    private void appendLog(String text){
        logs.append(text + "\n");
    }

    private String readLog(){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOGS_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void answerAll(String text){
        for (ChatGUI clientGUI: chatGUIList){
            clientGUI.answer(text);
        }
    }

    private void saveInLog(String text){
        try (FileWriter writer = new FileWriter(LOGS_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void messageSending(String message){
        if (!isServerWorking){
            return;
        }
        message += ";";
        appendLog(message);
        answerAll(message);
        saveInLog(message);
    }

    public String getLog() {
        return readLog();
    }
}
