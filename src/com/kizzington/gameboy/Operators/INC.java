package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;

public class INC extends Instruction {

    public INC(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
    }

    @Override
    public void execute() {
        int value = primaryOperand.getValue();

        if((value & 0x0f) == 0x0f){
            Main.cpu.flagsSet(Main.cpu.FLAGS_HALFCARRY);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_HALFCARRY);
        }

        primaryOperand.setValue(value+1);

        // deal with overflow
        int newVal = primaryOperand.getValue();
        if(primaryOperand.name().length() == 1 && newVal > 0xFF) {
            primaryOperand.setValue(newVal - 0xFF);
            newVal = primaryOperand.getValue();
        } else if(primaryOperand.name().length() == 2 && newVal > 0xFFFF) {
            primaryOperand.setValue(newVal - 0xFFFF);
            newVal = primaryOperand.getValue();
        }

        if(newVal == 0) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_ZERO);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_ZERO);
        }

        Main.cpu.flagsClear(Main.cpu.FLAGS_NEGATIVE);
    }
}
