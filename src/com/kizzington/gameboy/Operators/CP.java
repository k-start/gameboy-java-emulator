package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class CP extends Instruction {

    public CP(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
    }

    @Override
    public void execute() {
        int value = primaryOperand.getValue();
        if(primaryOperand == OperandEnum.n) {
            value = n;
        }

        if(value == Registers.a) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_ZERO);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_ZERO);
        }

        if(Registers.a < value) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_CARRY);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_CARRY);
        }

        if((value & 0x0f) > (Registers.a & 0x0f)) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_HALFCARRY);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_HALFCARRY);
        }

        Main.cpu.flagsSet(Main.cpu.FLAGS_NEGATIVE);


    }
}
