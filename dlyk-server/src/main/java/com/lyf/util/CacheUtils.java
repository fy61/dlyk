package com.lyf.util;

import org.springframework.util.ObjectUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CacheUtils {

    /**
     * 带有缓存的查询工具方法
     * @param catchSelector
     * @param databaseSelector
     * @param cacheSave
     * @param <T>
     * @return
     */

    public static <T> T getCatchData(Supplier<T> catchSelector, Supplier<T> databaseSelector, Consumer<T> cacheSave){
        //从redis中查询
        T data=catchSelector.get();//从缓存中获取数据
        //如果redis中没查到
        if(ObjectUtils.isEmpty(data)){//判断是否有元素,objectutil.isnull判断是否为空不可用,list里面有对象只是没有数据
            //从数据库查
            data = databaseSelector.get();//从缓存中获取数据
            if (!ObjectUtils.isEmpty(data)) {
                //把数据放到redis
                cacheSave.accept(data);//存入缓存。
            }
        }
        //返回数据
        return  data;
    }
}
