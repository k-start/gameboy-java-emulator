package com.kizzington.gameboy;

public class Registers {

    static int a, b, c, d, e, h, l, f, ime;
    static int pc, sp;

    public static void setAF(int value) {
        a = value >> 8;
        f = value & 0xff;
    }

    public static void setBC(int value) {
        b = value >> 8;
        c = value & 0xff;
    }

    public static void setDE(int value) {
        d = value >> 8;
        e = value & 0xff;
    }

    public static void setHL(int value) {
        h = value >> 8;
        l = value & 0xff;
    }

    public static int getAF() {
        return (a << 8) | f;
    }
    public static int getBC() {
        return (b << 8) | c;
    }
    public static int getDE() {
        return (d << 8) | e;
    }
    public static int getHL() {
        return (h << 8) | l;
    }

    public static void setPC(int value) { pc = value; }
    public static void setSP(int value) { sp = value; }
    public static void setA(int value) { a = value; }
    public static void setB(int value) { b = value; }
    public static void setC(int value) { c = value; }
    public static void setD(int value) { d = value; }
    public static void setE(int value) { e = value; }
    public static void setH(int value) { h = value; }
    public static void setL(int value) { l = value; }
    public static void setF(int value) { f = value; }

    public static int getPC() { return pc; }
    public static int getSP() { return sp; }
    public static int getA() { return a; }
    public static int getB() { return b; }
    public static int getC() { return c; }
    public static int getD() { return d; }
    public static int getE() { return e; }
    public static int getH() { return h; }
    public static int getL() { return l; }
    public static int getF() { return f; }

    public static void decrementHL() {
        setHL(getHL()-1);
        if(getHL() < 0) {
            setHL(0xFFFF + getHL());
        }
    }
}
