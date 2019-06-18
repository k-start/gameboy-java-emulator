package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class PUSH extends Instruction {

    public PUSH(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
    }

    @Override
    public void execute() {
        Registers.sp -= 2;
        Main.memory.writeShort(Registers.sp, primaryOperand.getValue());
    }
}
