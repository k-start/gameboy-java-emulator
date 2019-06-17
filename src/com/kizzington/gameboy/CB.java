package com.kizzington.gameboy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CB {

    public Instruction[] instructions = new Instruction[0xFF];

    public CB() {
        instructions[0x7C] = new Instruction(OperationEnum.bit, "h", 7);
    }

    public void step(int instructionHex) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        if(instructions[instructionHex] != null && instructions[instructionHex].operation != OperationEnum.unknown) {
            System.out.println("Instruction: xCB"  + String.format("%02X", instructionHex));

            Instruction instruction = instructions[instructionHex];

            switch (instruction.operation) {
                case bit:
                    bit(instruction.reg, instruction.cost);
                    break;
            }
        } else {
            System.out.println("Unknown Instruction: xCB"  + String.format("%02X", instructionHex));
        }
    }

    // test bit
    public void bit(String reg, int bit) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = Registers.class.getMethod("get" + reg.toUpperCase());
        int value = (int)method.invoke(null);

        boolean test = ((value >> bit) & 1) != 0;

        if(!test) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_ZERO);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_ZERO);
        }

        Main.cpu.flagsSet(Main.cpu.FLAGS_HALFCARRY);
        Main.cpu.flagsSet(Main.cpu.FLAGS_NEGATIVE);
    }
}
