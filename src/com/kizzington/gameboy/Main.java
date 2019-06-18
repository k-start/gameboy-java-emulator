package com.kizzington.gameboy;

import javax.swing.*;

public class Main {

    public String filename = "bios.gb";

    public static boolean debugMode = false;

    public static Main main;
    public static Rom rom;
    public static Memory memory;
    public static CPU cpu;
    public static CB cb;

    public boolean running = true;

    private JFrame frame;
    private JButton button;
    private JTextArea label;

    public static void main(String [] args) {
        main = new Main();
    }

    public Main(){
        cpu = new CPU();
        cb = new CB();
        memory = new Memory();
        rom = new Rom(filename);

        if(debugMode) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            button = new JButton("step");
            button.setBounds(10, 10, 360, 40);

            label = new JTextArea("test\ntest");
            label.setBounds(10, 60, 360, 280);

            cpu.step();

            label.setText("AF: " + String.format("%04X", Registers.getAF())
                    + "\nBC: " + String.format("%04X", Registers.getBC())
                    + "\nDE: " + String.format("%04X", Registers.getDE())
                    + "\nHL: " + String.format("%04X", Registers.getHL())
                    + "\nSP: " + String.format("%04X", Registers.sp)
                    + "\nPC: " + String.format("%04X", Registers.pc)
                    + "\n"
                    + "\nIME: " + String.format("%04X", Registers.ime)
                    + "\n"
                    + "\nZ: " + cpu.flagsIsZero()
                    + "\nN: " + cpu.flagsIsNegative()
                    + "\nH: " + cpu.flagsIsHalfCarry()
                    + "\nC: " + cpu.flagsIsCarry());

            frame.add(button);
            frame.add(label);
            frame.setLayout(null);
            frame.setTitle("Gameboy Emulator");
            frame.setVisible(true);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);


            button.addActionListener(fn -> {
                label.setText("AF: " + String.format("%04X", Registers.getAF())
                        + "\nBC: " + String.format("%04X", Registers.getBC())
                        + "\nDE: " + String.format("%04X", Registers.getDE())
                        + "\nHL: " + String.format("%04X", Registers.getHL())
                        + "\nSP: " + String.format("%04X", Registers.sp)
                        + "\nPC: " + String.format("%04X", Registers.pc)
                        + "\n"
                        + "\nIME: " + String.format("%04X", Registers.ime)
                        + "\n"
                        + "\nZ: " + cpu.flagsIsZero()
                        + "\nN: " + cpu.flagsIsNegative()
                        + "\nH: " + cpu.flagsIsHalfCarry()
                        + "\nC: " + cpu.flagsIsCarry());

                cpu.step();
            });
        } else {
            run();
        }
    }

    public void run() {
        while(running) {
            cpu.step();
        }
    }
}
