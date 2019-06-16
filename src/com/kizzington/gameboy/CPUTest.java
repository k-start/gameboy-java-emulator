package com.kizzington.gameboy;

public class CPUTest {

    final int FLAGS_ZERO = 0x80;
    final int FLAGS_NEGATIVE = 0x40;
    final int FLAGS_HALFCARRY = 0x20;
    final int FLAGS_CARRY = 0x10;

    long ticks = 0;



    public CPUTest() {
        Registers.a = 0x01;
        Registers.f = 0xb0;
        Registers.b = 0x00;
        Registers.c = 0x13;
        Registers.d = 0x00;
        Registers.e = 0xd8;
        Registers.h = 0x01;
        Registers.l = 0x4d;
        Registers.sp = 0xfffe;
        Registers.pc = 0x000;
        Registers.ime = 0x00;

        Interrupts.master = true;
        Interrupts.enable = false;
        Interrupts.flags = false;
    }

    public void step() {

        int instruction = Main.memory.read(Registers.pc++);



        System.out.println("Instruction: "  + String.format("0x%02X", instruction));
//        ticks += instructionTicks[instruction];
    }

    void ld(String into, int val) {
        switch (into.toLowerCase()) {
            case "a":
                break;
            case "sp":
                Registers.sp = val;
                break;
        }

    }



    int get8BitImm() {
        return Main.memory.read(Registers.pc++);
    }

    int get16BitImm() {
        int imm = Main.memory.readShort(Registers.pc);
        Registers.pc += 2;
        return imm;
    }




    boolean flagsIsZero() {
        return (Registers.f & FLAGS_ZERO) != 0;
    }

    boolean flagsIsNegative() {
        return (Registers.f & FLAGS_NEGATIVE) != 0;
    }

    boolean flagsIsCarry() {
        return (Registers.f & FLAGS_CARRY) != 0;
    }

    boolean flagsIsHalfCarry() {
        return (Registers.f & FLAGS_HALFCARRY) != 0;
    }

    boolean flags_isset(int x) {
        return (Registers.f & x) != 0;
    }

    boolean flagsSet(int x) {
        return (Registers.f |= x) != 0;
    }

    boolean flagsClear(int x) {
        return (Registers.f &= ~(x)) != 0;
    }
}
