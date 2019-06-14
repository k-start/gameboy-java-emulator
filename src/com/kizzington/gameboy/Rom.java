package com.kizzington.gameboy;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Rom {

    private final int ROM_OFFSET_NAME = 0x134;
    private final int ROM_OFFSET_TYPE = 0x147;
    private final int ROM_OFFSET_ROM_SIZE = 0x148;
    private final int ROM_OFFSET_RAM_SIZE = 0x149;

    private byte[] header;

    private String name;
    private RomType type;
    private int romSize;
    private int ramSize;

    public Rom(String filename) {
        try {
            loadROM(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadROM(String filename) throws IOException {
        Path p = FileSystems.getDefault().getPath("", filename);
        byte[] data = Files.readAllBytes(p);

        // load header
        header = new byte[0x180];
        for(int i = 0; i < header.length; i++) {
            header[i] = data[i];
        }

        // get name from header
        name = "";
        for(int i = 0; i < 16; i++) {
            if(header[i + ROM_OFFSET_NAME] == 0x80 || header[i + ROM_OFFSET_NAME] == 0xc0) {

            } else {
                name += (char) (header[i + ROM_OFFSET_NAME] & 0xFF);
            }
        }
        System.out.println("Internal Name: " + name);

        // get ROM type from header
        for(RomType romType : RomType.values()) {
            if(romType.getId() == header[ROM_OFFSET_TYPE]) {
                type = romType;
                break;
            }
        }
        System.out.println("ROM type: " + type.name());

        // get ROM size from header
        romSize = header[ROM_OFFSET_ROM_SIZE];

        if((romSize & 0xF0) == 0x50) {
            romSize = (int)Math.pow(2, ((0x52) & 0xf) + 1) + 64;
        } else {
            romSize = (int)Math.pow(2, romSize + 1);
        }
        System.out.println("ROM size: " + romSize*16 + "KB");

        if(data.length != romSize * 16 * 1024) {
            System.out.println("ROM file size doesn't match ROM size");
        }

        //get RAM size from header
        ramSize = header[ROM_OFFSET_RAM_SIZE];

        ramSize = (int)Math.pow(4, ramSize)/2;

        System.out.println("RAM size: " + ramSize + "KB");

        Main.memory.cartridge = new int[data.length];
        for(int i = 0; i < data.length; i++) {
            Main.memory.cartridge[i] = (data[i] & 0xFF);
        }
//        Main.memory.cartridge = data;
    }
}
