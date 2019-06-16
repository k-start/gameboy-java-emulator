package com.kizzington.gameboy;

public class Interrupts {

    static boolean master;
    static boolean enable;
    static boolean flags;

    static void vblank() {
        master = false;
        Registers.sp -= 2;
        Main.memory.writeShort(Registers.sp, Registers.pc);
        Registers.pc = 0x40;
    }

    static void returnFromInterrupt() {
        master = true;
        Registers.pc = Main.memory.readShort(Registers.sp);
        Registers.sp += 2;
    }
}
