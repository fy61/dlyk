package com.lyf.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public enum DicEnum {

    APPELLATION("appellation"),

    SOURCE("source"),

    STATE("clueState"),

    INTENTIONSTATE("intentionState"),

    NEEDLOAN("needLoan"),

    PRODUCT("product"),

    ACTIVITY("activity")

    ;

    @Setter
    @Getter
    private String code;
}
