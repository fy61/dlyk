package com.lyf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.manager.RedisManager;
import com.lyf.mapper.TRoleMapper;
import com.lyf.mapper.TUserMapper;
import com.lyf.model.TRole;
import com.lyf.model.TUser;
import com.lyf.query.BaseQuery;
import com.lyf.query.UserQuery;
import com.lyf.service.UserService;
import com.lyf.util.CacheUtils;
import com.lyf.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RedisManager redisManager;

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private TRoleMapper tRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);

        if (tUser == null) {
            throw new UsernameNotFoundException("登录账号不存在");
        }

        //查询一下当前用户的角色
        List<TRole> tRoleList = tRoleMapper.selectByUserId(tUser.getId());

        //字符串的角色列表
        List<String> stringRoleList = new ArrayList<>();

        tRoleList.forEach(tRole -> {
            stringRoleList.add(tRole.getRole());
        });

        tUser.setRoleList(stringRoleList); //设置用户的角色

        return tUser;
    }

    //查询用户
    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TUser> list = tUserMapper.selectUserByPage(BaseQuery.builder().build());
        // 3.封装分页数据到PageInfo
        PageInfo<TUser> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TUser getUserById(Integer id) {
        return tUserMapper.selectDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveUser(UserQuery userQuery) {
        TUser tUser = new TUser();
        //将userQuery对象里面的属性 复制到TUser对象里面(其中两个对象的属性名和属性类型都需要相同)
        BeanUtils.copyProperties(userQuery,tUser);

        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));//密码加密
        tUser.setCreateTime(new Date()); //创建时间

        //登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setCreateBy(loginUserId); //创建人

        return tUserMapper.insertSelective(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUser(UserQuery userQuery) {
        TUser tUser = new TUser();
        //将userQuery对象里面的属性 复制到TUser对象里面(其中两个对象的属性名和属性类型都需要相同)
        BeanUtils.copyProperties(userQuery,tUser);
        if(StringUtils.hasText(userQuery.getLoginPwd())){
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));//密码加密
        }
        tUser.setEditTime(new Date()); //编辑时间

        //登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setCreateBy(loginUserId); //编辑人

        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delUserById(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelUserIds(List<String> idList) {
        return tUserMapper.deleteByIds(idList);
    }

    @Override
    public List<TUser> getOwnerList() {
        //1.从redis中查
        //2.redis查不到,就从数据库查询,并把数据放入redis(5分钟过期)
        //处理带有缓存的查询操作,通过lambda表达式,传递数据进去
        return  CacheUtils.getCatchData(() -> {
            //生产，从缓存redis查询数据
            return (List<TUser>)redisManager.getValue(Constants.REDIS_OWNER_KEY);
        },
        () -> {
          //生产，从mysql查询数据
            return (List<TUser>)tUserMapper.selectByOwner();
        },
         (t) -> {
            //消费,把数据放入缓存redis
             redisManager.setValue(Constants.REDIS_OWNER_KEY,t);
        });

    }
}
