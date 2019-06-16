package com.kizzington.gameboy;

public class Registers {

    static int a, b, c, d, e, h, l, f, ime;
    static int pc, sp;

    static void updateHL(int value) {
        h = value >> 8;
        l = value & 0xff;
    }

    static int getAF() {
        return (a << 8) | f;
    }

    static int getBC() {
        return (b << 8) | c;
    }

    static int getDE() {
        return (d << 8) | e;
    }

    static int getHL() {
        return (h << 8) | l;
    }
}
