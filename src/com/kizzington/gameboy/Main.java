package com.kizzington.gameboy;

public class Main {

    public String filename = "Tetris.gb";

    public static Main main;
    public static Rom rom;
    public static Memory memory;
    public static CPU cpu;

    public boolean running = true;

    public static void main(String [] args) {
        main = new Main();
    }

    public Main(){
        cpu = new CPU();
        memory = new Memory();
        rom = new Rom(filename);

        run();
    }

    public void run() {
        while(running) {
            cpu.step();
        }
    }
}
