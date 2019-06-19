package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class DEC extends Instruction {

    public DEC(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
    }

    @Override
    public void execute() {
        int value = primaryOperand.getValue();

        if((value & 0x0f) != 0){
            Main.cpu.flagsClear(Main.cpu.FLAGS_HALFCARRY);
        } else {
            Main.cpu.flagsSet(Main.cpu.FLAGS_HALFCARRY);
        }

        primaryOperand.setValue(value-1);

        // deal with overflow
        int newVal = primaryOperand.getValue();
        if(primaryOperand.name().length() == 1 && newVal < 0x00) {
            primaryOperand.setValue(0xFF + newVal + 1);
            newVal = primaryOperand.getValue();
        } else if(primaryOperand.name().length() == 2 && newVal < 0x0000) {
            primaryOperand.setValue(0xFFFF + newVal + 1);
            newVal = primaryOperand.getValue();
        }

        if(newVal == 0) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_ZERO);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_ZERO);
        }

        Main.cpu.flagsSet(Main.cpu.FLAGS_NEGATIVE);
    }
}
