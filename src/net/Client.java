package net;

import backend.*;
import gui.JoinWait;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Client {

    private Controller game;
    private Dictionary dict;
    private int team;

    private String serverAddress;
    private int port;

    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;

    private JoinWait potentialJw;

    public Client(Dictionary dict, int team, String serverAddress, int port) {
        this.dict = dict;
        this.team = team;
        this.serverAddress = serverAddress;
        this.port = port;

        init();
    }

    // terrible design
    public void link(JoinWait jw) {
        potentialJw = jw;
    }

    public void init() {
        try {
            server = new Socket(serverAddress, port);
            in = new DataInputStream(new BufferedInputStream(server.getInputStream()));
            out = new DataOutputStream(server.getOutputStream());
            new Thread() {
                @Override public void run() {
                    String line = null;
                    do {
                        try {
                            line = in.readUTF();
                            parse(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                            //break;
                        }
                    } while (line == null || !line.equals("EOF"));
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
            // System.exit(-1); // TODO back to main menu
        }
    }

    public void broadcast(WordEvent we) {
        System.out.println("Broadcasting " + we.getWord());
        try {
            out.writeUTF(String.format("%d %s %d%n", we.getPlayer(), we.getWord(), we.getTime()));
            System.out.println("Sent " + we.getWord());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(String msg) {
        System.out.println("Parsing " + msg);
        String[] split = msg.trim().split(" ");
        if (msg.startsWith("READY")) {
            int dim = Integer.parseInt(split[1]);
            int time = Integer.parseInt(split[2]);
            int numTeams = Integer.parseInt(split[3]);
            game = new Controller(dict, new LetterGrid(dim, split[4]), time, team % numTeams, numTeams);
            game.linkClient(this);
            if (potentialJw != null)
                potentialJw.proceed();
        } else {
            game.receive(split[1], Integer.parseInt(split[0]), Integer.parseInt(split[2]));
        }
    }

    public Controller getGame() {
        return game;
    }
}
