package com.lyf.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.config.listener.UploadDataListener;
import com.lyf.constant.Constants;
import com.lyf.mapper.TClueMapper;
import com.lyf.model.TClue;
import com.lyf.model.TUser;
import com.lyf.query.BaseQuery;
import com.lyf.service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private TClueMapper tClueMapper;
    @Override
    public PageInfo<TClue> getClueByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TClue> list = tClueMapper.selectClueByPage(BaseQuery.builder().build());
        // 3.封装分页数据到PageInfo
        PageInfo<TClue> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public void importExcel(InputStream inputStream,String token) {
        //链式编程 3个参数,第一个参数是要读取到Excel文件,第二个参数是Excel模版类,第三个参数是文件读取的监听器
        EasyExcel.read(inputStream, TClue.class, new UploadDataListener(tClueMapper,token))
                .sheet()
                .doRead();

    }
}
