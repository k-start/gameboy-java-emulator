package com.kizzington.gameboy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public String filename = "bios.gb";

    public static Main main;
    public static Rom rom;
    public static Memory memory;
    public static CPUTest cpu;

    public boolean running = true;

    private JFrame frame;
    private JButton button;
    private JTextArea label;

    public static void main(String [] args) {
        main = new Main();
    }

    public Main(){
        cpu = new CPUTest();
        memory = new Memory();
        rom = new Rom(filename);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("step");
        button.setBounds(10,10,360, 40);

        label = new JTextArea("test\ntest");
        label.setBounds( 10, 60, 360, 280);

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
        frame.setSize(400,400);
//        frame.pack();
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

//        run();
    }

    public void run() {
        while(running) {
            cpu.step();
        }
    }
}
