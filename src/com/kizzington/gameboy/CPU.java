package com.kizzington.gameboy;

import com.kizzington.gameboy.Operators.*;

public class CPU {

    public final int FLAGS_ZERO = 0x80;
    public final int FLAGS_NEGATIVE = 0x40;
    public final int FLAGS_HALFCARRY = 0x20;
    public final int FLAGS_CARRY = 0x10;

    long ticks = 0;

    public Instruction[] instructions = new Instruction[0xFF];

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
        Registers.pc = 0x000;
        Registers.ime = 0x00;

        Interrupts.master = true;
        Interrupts.enable = false;
        Interrupts.flags = false;

        instructions[0x00] = new Instruction(0);
        instructions[0x06] = new Instruction(OperandEnum.B, OperandEnum.n, 1);
        instructions[0x0C] = new INC(OperandEnum.C, 0);
        instructions[0x0E] = new LD(OperandEnum.C, OperandEnum.n, 1);
        instructions[0x11] = new LD(OperandEnum.DE, OperandEnum.nn, LD.LdType.read, 2);
        instructions[0x1A] = new LD(OperandEnum.A, OperandEnum.DE, LD.LdType.read, 0);
        instructions[0x20] = new JR(OperandEnum.n, JR.Condition.NZ, 1);
        instructions[0x21] = new LD(OperandEnum.HL, OperandEnum.nn, LD.LdType.read, 2);
        instructions[0x31] = new LD(OperandEnum.SP, OperandEnum.nn, LD.LdType.read, 2);
        instructions[0x32] = new LD(OperandEnum.HL, OperandEnum.A, LD.LdType.write, LD.SpecialType.decrement, 0);
        instructions[0x3E] = new LD(OperandEnum.A, OperandEnum.n, 1);
        instructions[0x4F] = new LD(OperandEnum.C, OperandEnum.A, 0);
        instructions[0x77] = new LD(OperandEnum.HL, OperandEnum.A, LD.LdType.write, 0);
        instructions[0xAF] = new XOR(OperandEnum.A, 0);
        instructions[0xC5] = new PUSH(OperandEnum.BC, 0);
        instructions[0xCB] = new Instruction(true);
        instructions[0xCD] = new CALL(OperandEnum.n, 2);
        instructions[0xE0] = new LD(OperandEnum.n, OperandEnum.A, LD.LdType.read, 1);
        instructions[0xE2] = new LD(OperandEnum.C, OperandEnum.A, LD.LdType.write, 0);
    }

    public void step() {

        int instructionHex = Main.memory.read(Registers.pc++);

        if(instructions[instructionHex] != null) {
            System.out.println("Instruction: " + String.format("0x%02X", instructionHex));

            Instruction instruction = instructions[instructionHex];

            if(instruction.primaryOperand == OperandEnum.n ||  instruction.secondaryOperand == OperandEnum.n || instruction.cost == 1) {
                instruction.n = get8BitImm();
            } else if(instruction.primaryOperand == OperandEnum.nn || instruction.secondaryOperand == OperandEnum.nn || instruction.cost == 2) {
                instruction.n = get16BitImm();
            }

            instruction.execute();
        } else {
            System.out.println("Unknown Instruction: "  + String.format("0x%02X", instructionHex));
            if(!Main.debugMode) {
                System.exit(0);
            }
        }

//        ticks += instructionTicks[instruction];
    }

    public int get8BitImm() {
        return Main.memory.read(Registers.pc++);
    }

    public int get16BitImm() {
        int imm = Main.memory.readShort(Registers.pc);
        Registers.pc += 2;
        return imm;
    }

    public boolean flagsIsZero() {
        return (Registers.f & FLAGS_ZERO) != 0;
    }

    public boolean flagsIsNegative() {
        return (Registers.f & FLAGS_NEGATIVE) != 0;
    }

    public boolean flagsIsCarry() {
        return (Registers.f & FLAGS_CARRY) != 0;
    }

    public boolean flagsIsHalfCarry() {
        return (Registers.f & FLAGS_HALFCARRY) != 0;
    }

    public boolean flags_isset(int x) {
        return (Registers.f & x) != 0;
    }

    public boolean flagsSet(int x) {
        return (Registers.f |= x) != 0;
    }

    public boolean flagsClear(int x) {
        return (Registers.f &= ~(x)) != 0;
    }
}
