package com.lyf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.manager.RedisManager;
import com.lyf.mapper.TTranMapper;
import com.lyf.model.TTran;
import com.lyf.query.TranQuery;
import com.lyf.service.TranService;
import com.lyf.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TTranMapper tTranMapper;

    @Resource
    private RedisManager redisManager;

    /**
     * 创建交易
     *
     * @param tranQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveTran(TranQuery tranQuery) {
        TTran tTran = new TTran();

        //把前端提交过来的参数数据对象TranQuery复制到TTran对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(tranQuery, tTran);

        String tranNo = redisManager.getOnlyNumber(Constants.REDIS_ONLY_NUMBER_KEY); //交易流水号是不能重复的
        tTran.setTranNo(tranNo);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseUserFromJWT(tranQuery.getToken()).getId();

        tTran.setCreateTime(new Date()); //创建时间
        tTran.setCreateBy(loginUserId); //创建人id

        return tTranMapper.insertSelective(tTran);
    }

    /**
     * 分页查询交易列表数据
     *
     * @param current
     * @return
     */
    @Override
    public PageInfo<TTran> getTranByPage(Integer current) {

        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TTran> list = tTranMapper.selectTranPage();
        //3.封装分页数据到PageInfo
        PageInfo<TTran> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public TTran getTranById(Integer tranId) {
        return tTranMapper.selectById(tranId);
    }
}
