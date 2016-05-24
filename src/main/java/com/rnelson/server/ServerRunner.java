package com.rnelson.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner implements Runnable {
    int serverPort;

    public ServerRunner(int port) {
        this.serverPort = port;
    }

    private void echo(BufferedReader in, PrintWriter out) throws IOException {
        System.out.println("Echoing...");
        String request = in.readLine();
        System.out.println(request);
        out.println("Echo: " + request);
        out.close();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is running...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client has connected!");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.echo(in, out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}