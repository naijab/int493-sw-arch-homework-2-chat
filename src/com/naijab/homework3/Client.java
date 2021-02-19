package com.naijab.homework3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static String username;
    private static final Scanner userScanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket();
        System.out.println("Connecting");
        clientSocket.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.printf("Connected from port %d\n", clientSocket.getLocalPort());

        Scanner serverScanner = new Scanner(clientSocket.getInputStream());

        new Thread(() -> {
            while (true) {
                if (username != null && username.equals("")) {
                    System.out.println();
                }
                System.out.println();
                String serverText = serverScanner.nextLine();
                System.out.println("Server: " + serverText);
            }
        }).start();

        while (true) {
            if (username == null) {
                System.out.print("Input Name: ");
                username = userScanner.nextLine();
            }
            System.out.print(": ");
            String userInput = userScanner.nextLine();
            sendMessage(clientSocket, userInput);
            System.out.println();
        }
    }

    private static void sendMessage(Socket client, String message) {
        try {
            client.getOutputStream().write((username + ": " + message + "\n").getBytes());
            client.getOutputStream().flush();
            System.out.println("--------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}