package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class POP extends Instruction {

    public POP(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
    }

    @Override
    public void execute() {
        primaryOperand.setValue(Main.memory.readShort(Registers.getSP()));
        Registers.sp += 2;
    }
}
