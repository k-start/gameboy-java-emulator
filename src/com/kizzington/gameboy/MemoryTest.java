package com.kizzington.gameboy;

public class MemoryTest {

    public int[] memory;

    public MemoryTest() {
        memory = new int[0x10000];

        for(int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
    }


    int read(int address) {
        return memory[address];
    }

    void write(int address, int value) {
        memory[address] = value;
    }

    int readShort(int address) {
        return read(address) | (read(address + 1) << 8);
    }

    void writeShort(int address, int value) {
        write(address, (value & 0xff00));
        write(address + 1, (value & 0x00ff));
    }
}
