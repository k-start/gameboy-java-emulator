package com.kizzington.gameboy;

public class Main {

    public String filename = "Tetris.gb";

    public static Main main;
    public static Rom rom;

    public static void main(String [] args) {
        main = new Main();
    }

    public Main(){
        rom = new Rom(filename);
    }
}
