package com.naijab.homework3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080), 30000);
        System.out.println("Listening to 8080");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client Connected %s:%d\n", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());

            Thread t1 = new Thread(new ClientHandler(clientSocket));
            t1.start();
        }
    }

}

class ClientHandler implements Runnable {

    private static List<String> messages = new ArrayList<>();

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(clientSocket.getInputStream());
            while (sc.hasNextLine()) {
                String text = sc.nextLine();
                messages.add(text);
                messages.forEach((message) -> {
                    System.out.println(message);
                    try {
                        clientSocket.getOutputStream().write((message + "\n").getBytes());
                        clientSocket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            // TODO: Handle Exception!
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {

            }
        }
    }
}