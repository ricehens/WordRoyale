package net;

import backend.Controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// https://stackoverflow.com/questions/58162611/how-can-a-server-broadcast-a-message-to-other-clients
public class EchoChamber extends Thread {

    private Controller game;
    private static List<Socket> list = new ArrayList<>();
    private Socket socket;

    public EchoChamber(Controller game, Socket socket) {
        this.game = game;
        list.add(this.socket = socket);
    }

    @Override public void run() {
        DataOutputStream out = null;
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {
            String line;
            do {
                line = in.readUTF().trim();
                System.out.println("[Received] " + line);
                if (line.equals("REQUEST")) {
                    out = new DataOutputStream(socket.getOutputStream());
                }
                for (Socket dest : list) {
                    if (dest == socket) continue;
                    out = new DataOutputStream(dest.getOutputStream());
                    out.writeUTF(line);
                }
            } while (!line.equals("EOF"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
