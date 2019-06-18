package com.kizzington.gameboy;

public enum OperationEnum {
    unknown, nop,
    ld, ldd, ldh, ld_addr_w, ld_addr_r, ldh_imm,
    xor,
    cb,
    jr,
    inc,
    call,

    bit
}
