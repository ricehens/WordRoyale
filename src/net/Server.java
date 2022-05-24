package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {

    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("hi");
                Socket server = serverSocket.accept();

                System.out.println("Connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                in.readUTF();
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("");
                server.close();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static void main(String[] args) {
        int port = 6969;
    }

}
