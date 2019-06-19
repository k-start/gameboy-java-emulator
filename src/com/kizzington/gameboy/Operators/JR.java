package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class JR extends Instruction {

    public enum Condition {
        none,
        NZ,
        Z,
        C,
        NC
    }

    Condition condition;

    public JR(OperandEnum primaryOperand, Condition condition, int cost) {
        super(primaryOperand, cost);
        this.condition = condition;
    }

    @Override
    public void execute() {
        switch (condition) {
            case none:
                Registers.pc += n;
                break;
            case NZ:
                if(!Main.cpu.flagsIsZero()) {
                    Registers.pc += (byte)n;
                } else {
                    // tick cpu
                }
                break;
            case Z:
                if(Main.cpu.flagsIsZero()) {
                    Registers.pc += (byte)n;
                } else {
                    // tick cpu
                }
                break;
            default:
                System.out.println("Unimplemented JR");
                System.exit(0);
                break;
        }
    }
}
