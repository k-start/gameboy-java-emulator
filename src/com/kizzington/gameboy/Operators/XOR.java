package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class XOR extends Instruction {

    public XOR(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
    }

    @Override
    public void execute() {
        int value = primaryOperand.getValue();
        Registers.a ^= value;

        if(Registers.a != 0) {
            Main.cpu.flagsClear(Main.cpu.FLAGS_ZERO);
        } else {
            Main.cpu.flagsSet(Main.cpu.FLAGS_ZERO);
        }

        Main.cpu.flagsClear(Main.cpu.FLAGS_CARRY | Main.cpu.FLAGS_NEGATIVE | Main.cpu.FLAGS_HALFCARRY);
    }
}
