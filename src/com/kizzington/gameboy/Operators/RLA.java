package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;

public class RLA extends Instruction {

    public RLA(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
    }

    @Override
    public void execute() {
        int carry = Main.cpu.flags_isset(Main.cpu.FLAGS_CARRY) ? 1 : 0;

        int value = primaryOperand.getValue();

        if((value & 0x80)!= 0) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_CARRY);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_CARRY);
        }

        value <<= 1;
        value += carry;

        Main.cpu.flagsClear(Main.cpu.FLAGS_NEGATIVE | Main.cpu.FLAGS_ZERO | Main.cpu.FLAGS_HALFCARRY);

        primaryOperand.setValue(value);
    }
}
