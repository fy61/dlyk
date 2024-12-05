package com.lyf.service.impl;


import com.lyf.mapper.TTranHistoryMapper;
import com.lyf.model.TTranHistory;
import com.lyf.query.TranHistoryQuery;
import com.lyf.service.TranHistoryService;
import com.lyf.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TranHistoryServiceImpl implements TranHistoryService {

    @Resource
    private TTranHistoryMapper tTranHistoryMapper;

    /**
     * 更新交易历史（阶段）
     *
     * @param tranHistoryQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveTranHistory(TranHistoryQuery tranHistoryQuery) {
        TTranHistory tTranHistory = new TTranHistory();

        //把前端提交过来的参数数据对象TranHistoryQuery复制到TTranHistory对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(tranHistoryQuery, tTranHistory);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseUserFromJWT(tranHistoryQuery.getToken()).getId();

        tTranHistory.setCreateTime(new Date()); //创建时间
        tTranHistory.setCreateBy(loginUserId); //创建人id

        return tTranHistoryMapper.insertSelective(tTranHistory);
    }

    /**
     * 查询交易历史/阶段记录
     *
     * @param tranId
     * @return
     */
    @Override
    public List<TTranHistory> getTranHistoryByTranId(Integer tranId) {
        return tTranHistoryMapper.selectByTranId(tranId);
    }
}
