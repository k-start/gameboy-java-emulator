package com.kizzington.gameboy;

public class CPU {

    final int FLAGS_ZERO = 0x80;
    final int FLAGS_NEGATIVE = 0x40;
    final int FLAGS_HALFCARRY = 0x20;
    final int FLAGS_CARRY = 0x10;

    long ticks = 0;

    int instructionTicks[] = {
            2, 6, 4, 4, 2, 2, 4, 4, 10, 4, 4, 4, 2, 2, 4, 4,
            2, 6, 4, 4, 2, 2, 4, 4,  4, 4, 4, 4, 2, 2, 4, 4,
            0, 6, 4, 4, 2, 2, 4, 2,  4, 4, 4, 4, 2, 2, 4, 2,
            4, 6, 4, 4, 6, 6, 6, 2,  4, 4, 4, 4, 2, 2, 4, 2,
            2, 2, 2, 2, 2, 2, 4, 2,  2, 2, 2, 2, 2, 2, 4, 2,
            2, 2, 2, 2, 2, 2, 4, 2,  2, 2, 2, 2, 2, 2, 4, 2,
            2, 2, 2, 2, 2, 2, 4, 2,  2, 2, 2, 2, 2, 2, 4, 2,
            4, 4, 4, 4, 4, 4, 2, 4,  2, 2, 2, 2, 2, 2, 4, 2,
            2, 2, 2, 2, 2, 2, 4, 2,  2, 2, 2, 2, 2, 2, 4, 2,
            2, 2, 2, 2, 2, 2, 4, 2,  2, 2, 2, 2, 2, 2, 4, 2,
            2, 2, 2, 2, 2, 2, 4, 2,  2, 2, 2, 2, 2, 2, 4, 2,
            2, 2, 2, 2, 2, 2, 4, 2,  2, 2, 2, 2, 2, 2, 4, 2,
            4, 6, 6, 6, 6, 8, 4, 8,  4, 2, 6, 0, 6, 6, 4, 8,
            4, 6, 6, 0, 6, 8, 4, 8,  4, 8, 6, 0, 6, 0, 4, 8,
            6, 6, 4, 0, 0, 8, 4, 8,  8, 2, 8, 0, 0, 0, 4, 8,
            6, 6, 4, 2, 0, 8, 4, 8,  6, 4, 8, 2, 0, 0, 4, 8
    };

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

        Interrupts.master = true;
        Interrupts.enable = false;
        Interrupts.flags = false;
    }

    public void step() {

        int instruction = Main.memory.read(Registers.pc);
        Registers.pc++;

        if(Registers.pc == 0x2817) {
            System.out.println("vram");
        }

        switch (instruction) {
            // NOP
            case 0x00:
                break;

            // LD (BC),A
            case 0x02:
                Main.memory.write(Registers.getBC(), Registers.a);
                break;

            // DEC B
            case 0x05:
                if((Registers.b & 0x0f) != 0) {
                    flagsClear(FLAGS_HALFCARRY);
                } else {
                    flagsSet(FLAGS_HALFCARRY);
                }

                Registers.b--;
                if(Registers.b == -1) {
                    Registers.b = 0xFF;
                }

                if(Registers.b != 0) {
                    flagsClear(FLAGS_ZERO);
                } else {
                    flagsSet(FLAGS_ZERO);
                }

                flagsSet(FLAGS_NEGATIVE);
                break;

            // LD B,n
            case 0x06:
                Registers.b = Main.memory.read(Registers.pc);
                Registers.pc += 1;
                break;

            // DEC C
            case 0x0D:
                if((Registers.c & 0x0f) != 0) {
                    flagsClear(FLAGS_HALFCARRY);
                } else {
                    flagsSet(FLAGS_HALFCARRY);
                }

                Registers.c--;
                if(Registers.c == -1) {
                    Registers.c = 0xFF;
                }

                if(Registers.c != 0) {
                    flagsClear(FLAGS_ZERO);
                } else {
                    flagsSet(FLAGS_ZERO);
                }

                flagsSet(FLAGS_NEGATIVE);
                break;

            // LD C,n
            case 0x0E:
                Registers.c = Main.memory.read(Registers.pc);
                Registers.pc += 1;
                break;

            // DEC D
            case 0x15:
                if((Registers.d & 0x0f) != 0) {
                    flagsClear(FLAGS_HALFCARRY);
                } else {
                    flagsSet(FLAGS_HALFCARRY);
                }

                Registers.d--;
                if(Registers.d == -1) {
                    Registers.d = 0xFF;
                }

                if(Registers.d != 0) {
                    flagsClear(FLAGS_ZERO);
                } else {
                    flagsSet(FLAGS_ZERO);
                }

                flagsSet(FLAGS_NEGATIVE);
                break;

            // LD D,n
            case 0x16:
                Registers.d = Main.memory.read(Registers.pc);
                Registers.pc++;
                break;

            // RR A
            case 0x1F:
                int carry = (flags_isset(FLAGS_CARRY) ? 1 : 0) << 7;

                if((Registers.a & 0x01) != 0){
                    flagsSet(FLAGS_CARRY);
                } else {
                    flagsClear(FLAGS_CARRY);
                }

                Registers.a >>= 1;
                Registers.a += carry;

                flagsClear(FLAGS_NEGATIVE | FLAGS_ZERO | FLAGS_HALFCARRY);
                break;


            //
            case 0x20:
                if(flagsIsZero()) {
                    ticks += 8;
                    Registers.pc++;
                } else {
                    Registers.pc += Main.memory.read(Registers.pc);
                    Registers.pc++;
                    ticks += 12;
                }

                break;

            // LD HL,nn
            case 0x21:
                Registers.setHL(Main.memory.readShort(Registers.pc));
                Registers.pc += 2;
                break;

            // DEC H
            case 0x25:
                if((Registers.h & 0x0f) != 0) {
                    flagsClear(FLAGS_HALFCARRY);
                } else {
                    flagsSet(FLAGS_HALFCARRY);
                }

                Registers.h--;
                if(Registers.h == -1) {
                    Registers.h = 0xFF;
                }

                if(Registers.h != 0) {
                    flagsClear(FLAGS_ZERO);
                } else {
                    flagsSet(FLAGS_ZERO);
                }

                flagsSet(FLAGS_NEGATIVE);
                break;

            // LDD (HL),A
            case 0x32:
                Main.memory.write(Registers.getHL(), Registers.a);
                Registers.setHL(Registers.getHL()-1);
                break;

            // INC A
            case 0x3c:
                Registers.a++;
                break;

            // LD A,n
            case 0x3E:
                Registers.a = Main.memory.read(Registers.pc);
                Registers.pc += 1;
                break;

            case 0x43:
                Registers.b = Registers.e;
                break;

            // XOR A
            case 0xAF:
                Registers.a = 0;
                flagsSet(FLAGS_ZERO);
                flagsClear(FLAGS_CARRY | FLAGS_NEGATIVE | FLAGS_HALFCARRY);
                break;

            case 0xC3:
                Registers.pc = Main.memory.readShort(Registers.pc);
//                Registers.pc += 2;
                break;

            // CALL nn
            case 0xCD:
                Registers.sp -= 2;
                Main.memory.writeShort(Registers.sp, Registers.pc);
                Registers.pc = Main.memory.readShort(Registers.pc);
                Registers.pc += 2;
                break;

            // SUB A,n
            case 0xD6:
                flagsSet(FLAGS_NEGATIVE);
                int op1 = Main.memory.read(Registers.pc);

                if(op1 > Registers.a){
                    flagsSet(FLAGS_CARRY);
                } else {
                    flagsClear(FLAGS_CARRY);
                }

                if((op1 & 0x0f) > (Registers.a & 0x0f)) {
                    flagsSet(FLAGS_HALFCARRY);
                } else {
                    flagsClear(FLAGS_HALFCARRY);
                }

                Registers.a -= op1;

                if(Registers.a < 0) {
                    Registers.a = 0xFF + Registers.a;
                }

                if(Registers.a != 0) {
                    flagsClear(FLAGS_ZERO);
                } else {
                    flagsSet(FLAGS_ZERO);
                }

                Registers.pc++;
                break;

            // LDH (n),A
            case 0xE0:
                Main.memory.write(0xff00 + Main.memory.read(Registers.pc), Registers.a);
                Registers.pc++;
                break;

            // LDH A,(n)
            case 0xF0:
                Registers.a = Main.memory.read(0xff00 + Main.memory.read(Registers.pc));
                Registers.pc++;
                break;

            // DI
            case 0xF3:
                Interrupts.master = false;
                break;

            // LD SP,HL
            case 0xF9:
                Registers.sp = Registers.getHL();
                break;

            // EI
            case 0xFB:
                Interrupts.master = true;
                break;

            // CP n
            case 0xFE:
                flagsSet(FLAGS_NEGATIVE);
                int op = Main.memory.read(Registers.pc);

                if(Registers.a == op) {
                    flagsSet(FLAGS_ZERO);
                } else {
                    flagsClear(FLAGS_ZERO);
                }

                if(Registers.a < op) {
                    flagsSet(FLAGS_CARRY);
                } else {
                    flagsClear(FLAGS_CARRY);
                }

                if(Registers.a > op) {
                    flagsSet(FLAGS_HALFCARRY);
                } else {
                    flagsClear(FLAGS_HALFCARRY);
                }
                Registers.pc++;
                break;

            default:
                System.out.println("Undefined instruction: "  + String.format("0x%02X", instruction));
//                System.exit(0);
        }
        System.out.println("Instruction: "  + String.format("0x%02X", instruction));
        ticks += instructionTicks[instruction];
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
