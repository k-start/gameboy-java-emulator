package com.kizzington.gameboy;

import com.kizzington.gameboy.Operators.CB.BIT;
import com.kizzington.gameboy.Operators.Instruction;
import com.kizzington.gameboy.Operators.OperandEnum;

public class CB {

    public Instruction[] instructions = new Instruction[0xFF];

    public CB() {
        instructions[0x7C] = new BIT(OperandEnum.H, 7);
    }

    public void step(int instructionHex) {

        if(instructions[instructionHex] != null) {
            System.out.println("Instruction: xCB"  + String.format("%02X", instructionHex));

            Instruction instruction = instructions[instructionHex];

            instruction.execute();

        } else {
            System.out.println("Unknown Instruction: xCB"  + String.format("%02X", instructionHex));
            System.exit(0);
        }
    }
}
