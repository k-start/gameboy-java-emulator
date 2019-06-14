package com.kizzington.gameboy;

public class Memory {

    public int[] cartridge;

    public int[] io;
    public int[] vram;
    public int[] oam;
    public int[] ram;

    public Memory() {
        io = new int[0x100];
        vram = new int[0x2000];
        oam = new int[0x100];
        ram = new int[0x2000];
    }

    int read(int address) {

        if(address <= 0x7fff || (address >= 0xa000 && address <= 0xbfff)) {
            return cartridge[address];
        } else if(address >= 0x8000 && address <= 0x9fff) {
            return vram[address - 0x8000];
        } else if(address >= 0xc000 && address <= 0xdfff) {
            return ram[address - 0xC000];
        } else if(address >= 0xe000 && address <= 0xfdff) {
            return ram[address - 0xe000];
        } else if(address >= 0xfe00 && address <= 0xfeff) {
            return oam[address - 0xfe00];
        }

        return 0;
    }

    void write(int address, int value) {

        if (address >= 0xc000 && address <= 0xdfff) {
            ram[address - 0xc000] = value;
        } else if(address >= 0xfe00 && address <= 0xfeff) {
            oam[address - 0xfe00] = value;
        }
    }

    int readShort(int address) {
        return read(address) | (read(address + 1) << 8);
    }

    void writeShort(int address, int value) {
        write(address, (value & 0xff00));
        write(address + 1, (value & 0x00ff));
    }
}
