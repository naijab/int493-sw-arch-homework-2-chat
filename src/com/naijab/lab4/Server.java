package com.naijab.lab4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(clientSocket.getInputStream());
            while(sc.hasNextLine()) {
                String command = sc.nextLine();
                System.out.printf("GOT %s\n", command);

                //check command
                String data;
                if (command.equalsIgnoreCase("UPDATE")) {
                    data = String.format("TIME:%d\n", System.currentTimeMillis());
                    clientSocket.getOutputStream().write(data.getBytes());
                    clientSocket.getOutputStream().flush();
                } else {
                    data = "Do not thing \n";
                    clientSocket.getOutputStream().write(data.getBytes());
                    clientSocket.getOutputStream().flush();
                }
            }
        } catch(Exception e) {
            // TODO: Handle Exception!
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {

            }
        }
    }
}