package com.kizzington.gameboy;

public class Instruction {

    public String reg, reg2;
    public OperationEnum operation;
    public int cost;

    public Instruction(OperationEnum operation, String reg, int cost) {
        this.operation = operation;
        this.reg = reg;
        this.cost = cost;
    }

    public Instruction(OperationEnum operation, String reg, String reg2, int cost) {
        this.operation = operation;
        this.reg = reg;
        this.reg2 = reg2;
        this.cost = cost;
    }
}
