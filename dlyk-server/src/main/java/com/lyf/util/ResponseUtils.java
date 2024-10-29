package com.lyf.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtils {

    /**
     * 使用response，把结果写出到前端
     *
     * @param response
     * @param result
     */
    public static void write(HttpServletResponse response, String result) {
        //设置响应的内容类型为 JSON 格式，并指定字符编码为 UTF-8。
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = null;//将格式化数据写入字符输出流
        try {
            writer = response.getWriter();
            writer.write(result);//将传入的字符串写入到响应中
            writer.flush();//刷新
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
