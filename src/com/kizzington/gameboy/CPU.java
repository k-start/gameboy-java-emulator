package com.kizzington.gameboy;

public class CPU {

    final int FLAGS_ZERO = 0x80;
    final int FLAGS_NEGATIVE = 0x40;
    final int FLAGS_HALFCARRY = 0x20;
    final int FLAGS_CARRY = 0x10;

    int ticks = 0;

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

        if(Registers.pc == 0x2817) {
            System.out.println("vram");
        }

        switch (instruction) {
            // NOP
            case 0x00:
                break;

            // DEC B
            case 0x05:
                Registers.b--;
                break;

            // LD B,n
            case 0x06:
                Registers.b = Main.memory.read(Registers.pc);
                Registers.pc += 1;
                break;

                // DEC C
            case 0x0D:
                Registers.c--;

            // LD C,n
            case 0x0E:
                Registers.c = Main.memory.read(Registers.pc);
                Registers.pc += 1;
                break;

            //
            case 0x20:
                if(flagsIsZero()) {
                    ticks += 8;
                } else {
                    Registers.pc += 1;
                    ticks += 12;
                }
                break;

            // LD HL,nn
            case 0x21:
                Registers.updateHL(Main.memory.readShort(Registers.pc));
                Registers.pc += 2;
                break;

            // LDD (HL),A
            case 0x32:
                Main.memory.write(Registers.getHL(), Registers.a);
                Registers.updateHL(Registers.getHL()-1);
                break;

            // INC A
            case 0x3c:
                Registers.a++;
                break;

            case 0x43:
                Registers.b = Registers.e;
                break;

            // LD H,B
//            case 0x60:
//                Registers.h = Registers.b;
//                break;

            // XOR A
            case 0xAF:
                Registers.a = 0;
                flagsSet(FLAGS_ZERO);
                flagsClear(FLAGS_CARRY | FLAGS_NEGATIVE | FLAGS_HALFCARRY);
                break;

            case 0xc3:
                Registers.pc = Main.memory.readShort(Registers.pc);
                break;

            case 0xFC:
                break;

//            case 0xE0:
//                Main.memory.write(Main.memory.read(Registers.pc++) | 0xFF00, Registers.a);
//                break;
            default:
                System.out.println("Undefined instruction: "  + String.format("0x%02X", instruction));
                System.exit(0);
        }
        System.out.println("Instruction: "  + String.format("0x%02X", instruction));
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

    int flags_isset(int x) {
        return (Registers.f & x);
    }

    void flagsSet(int x) {
        Registers.f |= x;
    }

    void flagsClear(int x) {
        Registers.f &= ~(x);
    }
}
