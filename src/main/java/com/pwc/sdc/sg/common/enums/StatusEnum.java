package com.pwc.sdc.sg.common.enums;

/**
 * @author Xinhua X Yang
 */

public enum StatusEnum {
    ENABLE(1),
    DISABLE(0),

    YES(1),

    NO(0),

    ;

    private final int value;

    StatusEnum(int value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }
}
