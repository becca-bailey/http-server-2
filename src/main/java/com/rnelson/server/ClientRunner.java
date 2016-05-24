package com.rnelson.server;

import java.io.*;
import java.net.Socket;

public class ClientRunner implements Runnable {
    String hostName;
    Integer port;

    public ClientRunner(String hostName, int port) throws IOException {
        this.hostName = hostName;
        this.port = port;
    }

    private void echo(BufferedReader in, PrintWriter out) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String request = stdIn.readLine();
        out.println(request);
        String response = in.readLine();
        System.out.println(response);
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(hostName, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.echo(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}