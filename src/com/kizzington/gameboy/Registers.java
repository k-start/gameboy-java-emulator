package com.kizzington.gameboy;

public class Registers {

    static int a, b, c, d, e, h, l, f, ime;
    static int pc, sp;

    static void updateHL(int value) {
        String hex = Integer.toHexString(value);
        String first = hex.substring(0, 2);
        String second = hex.substring(2, 4);
        System.out.println(hex);

        h = (int) Long.parseLong(first, 16);
        l = (int) Long.parseLong(second, 16);
    }

    static int getHL() {
        String first = Integer.toHexString(h);
        String second = Integer.toHexString(l);
        String total = first+second;

        return (int) Long.parseLong(total, 16);
    }
}
