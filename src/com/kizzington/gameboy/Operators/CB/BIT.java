package com.kizzington.gameboy.Operators.CB;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Operators.Instruction;
import com.kizzington.gameboy.Operators.OperandEnum;

public class BIT extends Instruction {

    private int bit;

    public BIT(OperandEnum primaryOperand, int bit) {
        super(primaryOperand, 1);
        this.bit = bit;
    }

    @Override
    public void execute() {
        int value = primaryOperand.getValue();

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
