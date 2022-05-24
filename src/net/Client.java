package net;

import backend.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Client {

    private Controller game;
    private Dictionary dict;
    private int team;

    private String serverAddress;
    private int port;

    public Client(Dictionary dict, int team, String serverAddress, int port) {
        this.dict = dict;
        this.team = team;
        this.serverAddress = serverAddress;
        this.port = port;

        init();
    }

    public void init() {
        try (Socket server = new Socket(serverAddress, port);
             DataInputStream in = new DataInputStream(new BufferedInputStream(server.getInputStream()));
             DataOutputStream out = new DataOutputStream(server.getOutputStream()))
        {
            new Thread() {
                @Override public void run() {
                    String line;
                    do {
                        try {
                            line = in.readUTF();
                            parse(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    } while (!line.equals("EOF"));
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1); // TODO back to main menu
        }
    }

    public void broadcast(WordEvent we) {
        try (Socket server = new Socket(serverAddress, port);
             DataInputStream in = new DataInputStream(new BufferedInputStream(server.getInputStream()));
             DataOutputStream out = new DataOutputStream(server.getOutputStream()))
        {
            out.writeUTF(String.format("%d %s %d%n", we.getPlayer(), we.getWord(), we.getTime()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(String msg) {
        if (msg.startsWith("READY")) {
            String[] split = msg.trim().split(" ");
            int dim = Integer.parseInt(split[1]);
            int time = Integer.parseInt(split[2]);
            int numTeams = Integer.parseInt(split[3]);
            game = new Controller(dict, new LetterGrid(dim, split[4]), time, team % numTeams, numTeams);
        }
    }

    public Controller getGame() {
        return game;
    }
}
