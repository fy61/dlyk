package com.lyf.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 统一封装web层向前端页面返回的结果*/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class R {
    //返回的结果码,如200成功 500失败 404错误
    private int code;

    //返回的结果信息,比如用户登录状态失效了
    private String msg;

    //返回的结果数据,数据可能是一个对象,也可能是list集合
    private Object data;

    public static R OK() {
        return R.builder()
                .code(CodeEnum.OK.getCode())
                .msg(CodeEnum.OK.getMsg())
                .build();
    }

    public static R OK(int code, String msg) {
        return R.builder()
                .code(code)
                .msg(msg)
                .build();
    }

    public static R OK(Object data) {
        return R.builder()
                .code(CodeEnum.OK.getCode())
                .msg(CodeEnum.OK.getMsg())
                .data(data)
                .build();
    }

    public static R OK(CodeEnum codeEnum) {
        return R.builder()
                .code(CodeEnum.OK.getCode())
                .msg(codeEnum.getMsg())
                .build();
    }

    public static R FALL() {
        return R.builder()
                .code(CodeEnum.FALL.getCode())
                .msg(CodeEnum.FALL.getMsg())
                .build();
    }

    public static R FALL(String msg) {
        return R.builder()
                .code(CodeEnum.FALL.getCode())
                .msg(msg)
                .build();
    }

    public static R FALL(CodeEnum codeEnum) {
        return R.builder()
                .code(codeEnum.getCode())
                .msg(codeEnum.getMsg())
                .build();
    }
}
