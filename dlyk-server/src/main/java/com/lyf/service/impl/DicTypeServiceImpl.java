package com.lyf.service.impl;

import com.lyf.mapper.TDicTypeMapper;
import com.lyf.model.TDicType;
import com.lyf.service.DicTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DicTypeServiceImpl implements DicTypeService {

    @Resource
    private TDicTypeMapper tDicTypeMapper;
    @Override
    public List<TDicType> loadAllDicData() {
        return tDicTypeMapper.selectByAll();
    }
}
