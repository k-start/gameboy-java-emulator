package com.kizzington.gameboy;

public class CPU {

    public CPU() {
        Registers.a = 0x01;
        Registers.f = 0xb0;
        Registers.b = 0x00;
        Registers.c = 0x13;
        Registers.d = 0x00;
        Registers.e = 0xd8;
        Registers.h = 0x01;
        Registers.l = 0x4d;
        Registers.sp = 0xfffe;
        Registers.pc = 0x100;
        Registers.ime = 0x00;
    }

    public void step() {

        int instruction = Main.memory.read(Registers.pc++);

        switch (instruction) {
            // NOP
            case 0x00:
                break;

            // LD HL,nn
            case 0x21:
                Registers.updateHL(Main.memory.readShort(Registers.pc));
                Registers.pc += 2;
                break;

            // INC A
            case 0x3c:
                Registers.a++;
                break;

            case 0x43:
                Registers.b = Registers.e;
                break;

            // LD H,B
            case 0x6:
                Registers.h = Registers.b;
                break;

            // XOR A
            case 0xAF:
                Registers.a = 0;
                break;

            case 0xc3:
                Registers.pc = Main.memory.readShort(Registers.pc);
                break;

            case 0xE:
                Main.memory.write(Main.memory.read(Registers.pc++) | 0xFF00, Registers.a);
                break;
            default:
                System.out.println("Undefined instruction: "  + String.format("0x%01X", instruction & 0xFF));
                System.exit(0);
        }
        System.out.println("Instruction: "  + String.format("0x%01X", instruction & 0xFF));
    }
}
