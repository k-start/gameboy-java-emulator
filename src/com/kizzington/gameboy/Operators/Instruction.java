package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;

public class Instruction {

    public int cost;
    public OperandEnum primaryOperand, secondaryOperand;
    public int n;

    private boolean cb = false;

    public Instruction(OperandEnum primaryOperand, OperandEnum secondaryOperand, int cost) {
        this.primaryOperand = primaryOperand;
        this.secondaryOperand = secondaryOperand;
        this.cost = cost;
    }

    public Instruction(OperandEnum primaryOperand, int cost) {
        this.primaryOperand = primaryOperand;
        this.cost = cost;
    }

    public Instruction(int cost) {

    }

    public Instruction(boolean cb) {
        this.cb = cb;
    }

    // nop or cb execution
    public void execute() {
        if(cb) {
            Main.cb.step(Main.cpu.get8BitImm());
        }
    }
}
