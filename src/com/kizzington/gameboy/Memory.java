package com.kizzington.gameboy;

public class Memory {

    public int[] cartridge;

    public int[] io;
    public int[] vram;
    public int[] oam;
    public int[] ram;
    public int[] hram;

    public Memory() {
        cartridge = new int[0x8000];
        io = new int[0x80];
        vram = new int[0x2000];
        oam = new int[0x100];
        ram = new int[0x2000];
        hram = new int[0x80];

        for(int i = 0; i < io.length; i++) {
            io[i] = 0;
        }
        for(int i = 0; i < vram.length; i++) {
            vram[i] = 0;
        }
        for(int i = 0; i < oam.length; i++) {
            oam[i] = 0;
        }
        for(int i = 0; i < ram.length; i++) {
            ram[i] = 0;
        }
        for(int i = 0; i < hram.length; i++) {
            hram[i] = 0;
        }
    }

    int read(int address) {

        if(address <= 0x7fff) {
            return cartridge[address];
        } else if(address >= 0x8000 && address <= 0x9fff) {
            return vram[address - 0x8000];
        } else if(address >= 0xc000 && address <= 0xdfff) {
            return ram[address - 0xC000];
        } else if(address >= 0xe000 && address <= 0xfdff) {
            return ram[address - 0xe000];
        } else if(address >= 0xfe00 && address <= 0xfeff) {
            return oam[address - 0xfe00];
        } else if(address >= 0xFF00 && address <= 0xFF7F) {
            return io[address - 0xFF00];
        } else if(address >= 0xFF80 && address <= 0xFFFE) {
            return hram[address - 0xFF80];
        }

        else if(address == 0xff0f) return (Interrupts.flags ? 1 : 0);
        else if(address == 0xffff) return (Interrupts.enable ? 1 : 0);

        System.out.println("(R) Unknown memory address: " + String.format("0x%04X", address));
        System.exit(0);

        return 0;
    }

    void write(int address, int value) {

        if(address >= 0x8000 && address <= 0x9fff) {
            vram[address - 0x8000] = value;
        } else if (address >= 0xc000 && address <= 0xdfff) {
            ram[address - 0xc000] = value;
        } else if(address >= 0xfe00 && address <= 0xfeff) {
            oam[address - 0xfe00] = value;
        } else if(address >= 0xFF00 && address <= 0xFF7F) {
            io[address - 0xFF00] = value;
        } else if(address >= 0xFF80 && address <= 0xFFFE) {
            hram[address - 0xFF80] = value;
        } else {
            System.out.println("(W) Unknown memory address: " + String.format("0x%04X", address));
            System.exit(0);
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
