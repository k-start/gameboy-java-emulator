package com.kizzington.gameboy;

public class Memory {

    public int[] cartridge;

    public int[] io;
    public int[] vram;
    public int[] oam;
    public int[] ram;
    public int[] zeroPage;

    public Memory() {
        cartridge = new int[0x8000];
        io = new int[0x100];
        vram = new int[0x2000];
        oam = new int[0x100];
        ram = new int[0x2000];
        zeroPage = new int[0x80];

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
        for(int i = 0; i < zeroPage.length; i++) {
            zeroPage[i] = 0;
        }
    }

    int read(int address) {
//        System.out.println(String.format("%04X", address));

        if(address <= 0x7fff) {
            System.out.println("r cart");
            return cartridge[address];
        } else if(address >= 0x8000 && address <= 0x9fff) {
            System.out.println("r vram");
            return vram[address - 0x8000];
        } else if(address >= 0xc000 && address <= 0xdfff) {
            System.out.println("r ram");
            return ram[address - 0xC000];
        } else if(address >= 0xe000 && address <= 0xfdff) {
            System.out.println("r ram");
            return ram[address - 0xe000];
        } else if(address >= 0xfe00 && address <= 0xfeff) {
            System.out.println("r oam");
            return oam[address - 0xfe00];
        } else if(address >= 0xff80 && address <= 0xfffe) {
            System.out.println("r zero");
            return zeroPage[address - 0xff80];
        }

        else if(address == 0xff0f) return (Interrupts.flags ? 1 : 0);
        else if(address == 0xffff) return (Interrupts.enable ? 1 : 0);

        return 0;
    }

    void write(int address, int value) {

        if (address >= 0xc000 && address <= 0xdfff) {
            System.out.println("w ram");
            ram[address - 0xc000] = value;
        } else if(address >= 0xfe00 && address <= 0xfeff) {
            System.out.println("w oam");
            oam[address - 0xfe00] = value;
        } else if(address >= 0xff80 && address <= 0xfffe) {
            System.out.println("w zero");
            zeroPage[address - 0xff80] = value;
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
