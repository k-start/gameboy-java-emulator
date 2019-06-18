package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class CALL extends Instruction {

    public enum Condition {
        none,
        NZ,
        Z,
        C,
        NC
    }

    Condition condition;

    public CALL(OperandEnum primaryOperand, Condition condition, int cost) {
        super(primaryOperand, cost);
        this.condition = condition;
    }

    public CALL(OperandEnum primaryOperand, int cost) {
        super(primaryOperand, cost);
        condition = Condition.none;
    }

    @Override
    public void execute() {
        switch (condition) {
            case none:
                System.out.println("CALL: " + String.format("0x%02X", n) + " - ROUTINE");
                Registers.sp -= 2;
                Main.memory.writeShort(Registers.sp, Registers.pc);

                Registers.pc = n;
                break;
        }
    }
}
