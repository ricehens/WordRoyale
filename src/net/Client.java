package net;

import backend.*;
import gui.JoinWait;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

/**
 * Represents a client when connecting to a multiplayer server.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
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

    /**
     * Constructs a client with a specified team number, server address, and port number
     * @param dict Dictionary of words
     * @param team Team number assigned to the client
     * @param serverAddress Server IP address
     * @param port Server port number
     */
    public Client(Dictionary dict, int team, String serverAddress, int port) {
        this.dict = dict;
        this.team = team;
        this.serverAddress = serverAddress;
        this.port = port;

        init();
    }

    /**
     * Links the client to a GUI panel for waiting players
     * @param jw Specified GUI panel
     */
    public void link(JoinWait jw) {
        potentialJw = jw;
    }

    /**
     * Initializes a connection to the host server
     */
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

    /**
     * Broadcasts a message to the server about a word selection event
     * @param we Word selection event with player, word, and time info
     */
    public void broadcast(WordEvent we) {
        System.out.println("Broadcasting " + we.getWord());
        try {
            out.writeUTF(String.format("%d %s %d%n", we.getPlayer(), we.getWord(), we.getTime()));
            System.out.println("Sent " + we.getWord());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses a message indicating game ready status
     * @param msg Message received from the server
     */
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
            game.receive(false, split[1], Integer.parseInt(split[0]), Integer.parseInt(split[2]));
        }
    }

    /**
     * Returns the client's game controller
     * @return client game
     */
    public Controller getGame() {
        return game;
    }
}
