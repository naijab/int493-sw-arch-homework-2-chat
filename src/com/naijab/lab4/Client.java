package com.naijab.lab4;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket();
        System.out.println("Connecting");
        clientSocket.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.printf("Connected from port %d\n", clientSocket.getLocalPort());

        Scanner inputSc = new Scanner(System.in);
        Scanner sc = new Scanner(clientSocket.getInputStream());


        new Thread(() -> {
            while (true) {
                System.out.println("Waiting for response");
                System.out.println("--------------");
                String time = sc.nextLine();
                System.out.println(time);
            }
        }).start();


        for (int i = 0; i < 10; i++) {
            System.out.println("Waiting for user input");
            System.out.println("--------------");

            String input = inputSc.nextLine();
            System.out.println("Got user input");
            System.out.println("--------------");
            clientSocket.getOutputStream().write((input + "\n").getBytes());
            clientSocket.getOutputStream().flush();
            System.out.println("Send command to server");
            System.out.println("--------------");

            System.out.println("sleep less second");
            System.out.println("--------------");
            Thread.sleep(1000);
        }

        clientSocket.close();
    }

}
