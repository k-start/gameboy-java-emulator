package com.kizzington.gameboy.Operators;

import com.kizzington.gameboy.Main;
import com.kizzington.gameboy.Registers;

public class RET extends Instruction {

    public RET(int cost) {
        super(cost);
    }

    @Override
    public void execute() {
        System.out.println("END ROUTINE");
        Registers.setPC(Main.memory.readShort(Registers.getSP()));
        Registers.sp += 2;
    }
}
