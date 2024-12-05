package com.bjpowernode.service.impl;

import com.bjpowernode.dao.TTranHistoryDao;
import com.bjpowernode.domain.po.TTranHistory;
import com.bjpowernode.domain.po.TTranRemark;
import com.bjpowernode.domain.query.TranHistoryQuery;
import com.bjpowernode.service.TranHistoryService;
import com.bjpowernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TranHistoryServiceImpl implements TranHistoryService {

    @Resource
    private TTranHistoryDao tTranHistoryDao;

    /**
     * 更新交易历史（阶段）
     *
     * @param tranHistoryQuery
     * @return
     */
    @Override
    public int saveTranHistory(TranHistoryQuery tranHistoryQuery) {
        TTranHistory tTranHistory = new TTranHistory();

        //把前端提交过来的参数数据对象TranHistoryQuery复制到TTranHistory对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(tranHistoryQuery, tTranHistory);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(tranHistoryQuery.getToken());

        tTranHistory.setCreateTime(new Date()); //创建时间
        tTranHistory.setCreateBy(loginUserId); //创建人id

        return tTranHistoryDao.insertSelective(tTranHistory);
    }

    /**
     * 查询交易历史/阶段记录
     *
     * @param tranId
     * @return
     */
    @Override
    public List<TTranHistory> getTranHistoryByTranId(Integer tranId) {
        return tTranHistoryDao.selectByTranId(tranId);
    }
}
