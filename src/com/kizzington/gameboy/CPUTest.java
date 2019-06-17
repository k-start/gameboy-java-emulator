package com.kizzington.gameboy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CPUTest {

    final int FLAGS_ZERO = 0x80;
    final int FLAGS_NEGATIVE = 0x40;
    final int FLAGS_HALFCARRY = 0x20;
    final int FLAGS_CARRY = 0x10;

    long ticks = 0;

    public Instruction[] instructions = new Instruction[0xFF];

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

        instructions[0x00] = new Instruction(OperationEnum.nop, "", 0);
        instructions[0x20] = new Instruction(OperationEnum.jr, "nz", 1);
        instructions[0x21] = new Instruction(OperationEnum.ld, "hl", 2);
        instructions[0x31] = new Instruction(OperationEnum.ld, "sp", 2);
        instructions[0x32] = new Instruction(OperationEnum.ldd, "hl", "a", 0);
        instructions[0xAF] = new Instruction(OperationEnum.xor, "a", 0);
        instructions[0xCB] = new Instruction(OperationEnum.cb, "", 1);
    }

    public void step() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        int instructionHex = Main.memory.read(Registers.pc++);

        if(instructions[instructionHex] != null && instructions[instructionHex].operation != OperationEnum.unknown) {
            System.out.println("Instruction: " + String.format("0x%02X", instructionHex));

            Instruction instruction = instructions[instructionHex];

            int value = 0;
            switch (instruction.cost) {
                case 0:
                    // load register into value if exists
                    if (instruction.reg2 != null) {
                        Method method = Registers.class.getMethod("get" + instruction.reg2.toUpperCase());
                        value = (int) method.invoke(null);
                    }
                    break;
                case 1:
                    value = get8BitImm();
                    break;
                case 2:
                    value = get16BitImm();
                    break;
            }

            switch (instruction.operation) {
                case nop:
                    break;
                case ld:
                    ld(instruction.reg, value);
                    break;
                case ldd:
                    ldd(instruction.reg, value);
                    break;
                case xor:
                    xor(instruction.reg);
                    break;
                case cb:
                    Main.cb.step(value);
                    break;
                case jr:
                    // in this case instruction.reg is actually a condition, not a register name
                    jr(instruction.reg, value);
                    break;
            }
        } else {
            System.out.println("Unknown Instruction: "  + String.format("0x%02X", instructionHex));
            if(!Main.debugMode) {
                System.exit(0);
            }
        }


//        ticks += instructionTicks[instruction];
    }

    // load value into reg
    void ld(String reg, int val) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = Registers.class.getMethod("set" + reg.toUpperCase(), int.class);
        method.invoke(null, val);
    }

    // save value into address pointed by reg
    void ld_addr(String reg, int val) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = Registers.class.getMethod("get" + reg.toUpperCase());
        int address = (int)method.invoke(null);

        Main.memory.write(address, val);
    }

    // save value into address pointed by reg and decrease reg
    void ldd(String reg, int val) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ld_addr(reg, val);

        //decrement register
        Method method = Registers.class.getMethod("decrement" + reg.toUpperCase());
        method.invoke(null);

        // maybe modify flags here
    }

    // xor a against register
    void xor(String reg) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = Registers.class.getMethod("get" + reg.toUpperCase());
        int value = (int)method.invoke(null);

        Registers.a ^= value;

        if(Registers.a != 0) {
            flagsClear(FLAGS_ZERO);
        } else {
            flagsSet(FLAGS_ZERO);
        }

        flagsClear(FLAGS_CARRY | FLAGS_NEGATIVE | FLAGS_HALFCARRY);
    }

    void jr(String condition, int value) {
        switch (condition.toLowerCase()) {
            case "":
                Registers.pc += value;
                break;
            case "nz":
                if(!flagsIsZero()) {
                    Registers.pc += (byte)value;
                } else {

                }
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
