package com.kizzington.gameboy.Operators.CB;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Operators.Instruction;
import com.kizzington.gameboy.Operators.OperandEnum;

public class RL extends Instruction {

    public RL(OperandEnum primaryOperand, int cost) {
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

        if(value == 0) {
            Main.cpu.flagsSet(Main.cpu.FLAGS_ZERO);
        } else {
            Main.cpu.flagsClear(Main.cpu.FLAGS_ZERO);
        }

        Main.cpu.flagsClear(Main.cpu.FLAGS_NEGATIVE | Main.cpu.FLAGS_HALFCARRY);

    }
}
