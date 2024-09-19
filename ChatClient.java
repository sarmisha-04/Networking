import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame implements Runnable, ActionListener {
    TextField textfield;
    TextArea textarea;
    Button sendButton;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    ChatClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        textfield = new TextField();
        textarea = new TextArea();
        sendButton = new Button("SEND");
        sendButton.addActionListener(this);
        setLayout(new FlowLayout());
        add(textfield);
        add(textarea);
        add(sendButton);
        setSize(500, 500);
        setTitle("Chat Client");
        setVisible(true);
        chat = new Thread(this);
        chat.start();
    }
    public void actionPerformed(ActionEvent e) {
        String msg = textfield.getText();
        textarea.append("You: " + msg + "\n");
        textfield.setText("");

        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String args[]) {
        String serverAddress = "localhost"; // Change this to your server's address if needed
        int port = 12345; // Must match the server port

        new ChatClient(serverAddress, port);
    }
    public void run() {
        while (true) {
            try {
                String msg = dataInputStream.readUTF();
                textarea.append("Other: " + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
