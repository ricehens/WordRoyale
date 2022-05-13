package gui;

import backend.Dictionary;

public class Main {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        new MyFrame(dict);
    }
}
