package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public enum OperandEnum {

    A, F, AF,
    B, C, BC,
    D, E, DE,
    H, L, HL,
    SP, PC,

    n, nn;

    // gets value of register
    public int getValue() {
        switch (this) {
            case A:
                return Registers.getA();
            case F:
                return Registers.getF();
            case B:
                return Registers.getB();
            case C:
                return Registers.getC();
            case D:
                return Registers.getD();
            case E:
                return Registers.getE();
            case H:
                return Registers.getH();
            case L:
                return Registers.getL();
            case AF:
                return Registers.getAF();
            case BC:
                return Registers.getBC();
            case DE:
                return Registers.getDE();
            case HL:
                return Registers.getHL();
            case SP:
                return Registers.getSP();
            case PC:
                return Registers.getPC();
        }
        return 0;
    }

    // sets value of register
    public void setValue(int value) {
        switch (this) {
            case A:
                Registers.setA(value);
                break;
            case F:
                Registers.setF(value);
                break;
            case B:
                Registers.setB(value);
                break;
            case C:
                Registers.setC(value);
                break;
            case D:
                Registers.setD(value);
                break;
            case E:
                Registers.setE(value);
                break;
            case H:
                Registers.setH(value);
                break;
            case L:
                Registers.setL(value);
                break;
            case AF:
                Registers.setAF(value);
                break;
            case BC:
                Registers.setBC(value);
                break;
            case DE:
                Registers.setDE(value);
                break;
            case HL:
                Registers.setHL(value);
                break;
            case SP:
                Registers.setSP(value);
                break;
            case PC:
                Registers.setPC(value);
                break;
        }
    }

    // returns data in memory the register points to
    public int getMemory() {
        return Main.memory.read(getMemoryAddress());
    }

    // returns memory address of register
    public int getMemoryAddress() {
        int value = getValue();

        // If 8-bit return 16-bit address
        if(this.name().length() == 1) {
            value = 0xFF00 + value;
        }
        return value;
    }

}
