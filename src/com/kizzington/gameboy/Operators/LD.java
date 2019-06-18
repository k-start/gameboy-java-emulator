package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;

public class LD extends Instruction {

    // action on register/address
    public enum SpecialType {
        none,
        decrement,
        increment
    }

    // load from or save to memory?
    public enum LdType {
        none,
        read,
        write
    }

    public SpecialType specialType;
    public LdType ldType;

    public LD(OperandEnum primaryOperand, OperandEnum secondaryOperand, int cost) {
        super(primaryOperand, secondaryOperand, cost);
        ldType = LdType.none;
        specialType = SpecialType.none;
    }

    public LD(OperandEnum primaryOperand, OperandEnum secondaryOperand, LdType ldType, int cost) {
        super(primaryOperand, secondaryOperand, cost);
        this.ldType = ldType;
        specialType = SpecialType.none;
    }

    public LD(OperandEnum primaryOperand, OperandEnum secondaryOperand, SpecialType specialType, int cost) {
        super(primaryOperand, secondaryOperand, cost);
        ldType = LdType.none;
        this.specialType = specialType;
    }

    public LD(OperandEnum primaryOperand, OperandEnum secondaryOperand, LdType ldType, SpecialType specialType, int cost) {
        super(primaryOperand, secondaryOperand, cost);
        this.ldType = ldType;
        this.specialType = specialType;
    }

    @Override
    public void execute() {
        switch (ldType){
            case none:
                primaryOperand.setValue(secondaryOperand.getValue());
                break;
            case read:
                if(secondaryOperand == OperandEnum.nn || secondaryOperand == OperandEnum.n) {
                    primaryOperand.setValue(n);
                } else {
                    primaryOperand.setValue(secondaryOperand.getMemory());

                    if(specialType == SpecialType.decrement) {
                        secondaryOperand.setValue(secondaryOperand.getValue() - 1);
                    } else if(specialType == SpecialType.increment) {
                        secondaryOperand.setValue(secondaryOperand.getValue() + 1);
                    }
                }
                break;
            case write:
                if(primaryOperand == OperandEnum.nn) {
                    Main.memory.write(n, secondaryOperand.getValue());
                } else if(primaryOperand == OperandEnum.n) {
                    Main.memory.write(n + 0xFF00, secondaryOperand.getValue());
                } else {
                    Main.memory.write(primaryOperand.getMemoryAddress(), secondaryOperand.getValue());

                    if(specialType == SpecialType.decrement) {
                        primaryOperand.setValue(primaryOperand.getValue() - 1);
                    } else if(specialType == SpecialType.increment) {
                        primaryOperand.setValue(primaryOperand.getValue() + 1);
                    }
                }
        }
    }
}
