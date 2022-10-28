package trickster;

import javax.crypto.Cipher;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeClient(socket, bufferedReader, bufferedWriter);
        }
    }

    public void createMessage(BufferedWriter bufferedWriter, String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }




    // vi sender - bruger Writer - loop
    public void sendMessage() {
        try {
            // bruges til "SERVER: username has entered the chat!" - køres kun 1 gang
/*            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();*/
            createMessage(bufferedWriter, username);

            // bruges til resterende chats - loopes
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                System.out.print(">");

                String messageToSend = scanner.nextLine();

                /*

                */

                //TODO encrypt messageToSend and return the encrypted string
/*                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();*/                                                                 //replace with encryptedMessageToSend
                createMessage(bufferedWriter, username + ": " + messageToSend);
            }
        } catch (IOException e) {
            closeClient(socket, bufferedReader, bufferedWriter);
        }
    }

    // vi modtager - bruger Reader - tråd / loop
    public void listenForMessage() {
        // Køres i ny tråd, så vi kan lytte sideløbende med at sende. Runnable erstattet med lamda
        new Thread(() -> {
            String msgFromGroupChat;
            while(socket.isConnected()) {
                try {
                    System.out.print(">");
                    msgFromGroupChat = bufferedReader.readLine();
                    System.out.println(msgFromGroupChat);
                } catch (IOException e) {
                    closeClient(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }

    public static void closeClient(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);
        String password;
        System.out.println("password?");
        password = scanner.nextLine();
        if (password.equals("1234")) {
            System.out.println("succes!");
            System.out.println("Enter your username for the groupchat: ");
            String username = scanner.nextLine();
            //Socket socket = new Socket("localhost", 1234);
            Socket socket = new Socket("10.200.130.32", 9090);
            Client client = new Client(socket, username);
            client.listenForMessage();
            client.sendMessage();
        } else {
            System.out.println("du kan ikke komme ind");
        }
    }
}