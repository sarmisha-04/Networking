import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Sarmis extends Frame implements Runnable, ActionListener {
    TextField textfield;
    TextArea textarea;
    Button press;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    Sarmis (Socket socket) {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        textfield = new TextField();
        textarea = new TextArea();
        press = new Button("PRESS");
        press.addActionListener(this);
        setLayout(new FlowLayout());
        add(textfield);
        add(textarea);
        add(press);
        setSize(500, 500);
        setTitle("SARMISHA");
        setVisible(true);
        chat = new Thread(this);
        chat.start();
    }
    public void actionPerformed(ActionEvent e) {
        String msg = textfield.getText();
        textarea.append("SARMISHA: " + msg + "\n");
        textfield.setText("");

        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(port); // Specify a port number
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Sarmis(socket); // Create a new Sarmi instance for each client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (true) {
            try {
                String msg = dataInputStream.readUTF();
                textarea.append("MARY: " + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
