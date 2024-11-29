package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TUser;
import com.lyf.query.UserQuery;
import com.lyf.result.R;
import com.lyf.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 登录人信息
     * @param authentication
     * @return
     */
    @GetMapping("/api/login/info")
    public R loginInfo(Authentication authentication) {//主体 负责人信息
        TUser tUser = (TUser) authentication.getPrincipal();
        return R.OK(tUser);
    }

    /**
     * 免登录
     * @return
     */
    @GetMapping("/api/login/free")
    public R freeLogin() {
        return R.OK();
    }

    /**
     * 用户列表分页查询
     * @param current
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:list')")
    @GetMapping("/api/users")
    public R userPage(@RequestParam(value = "current" ,required = false) Integer current){
        //required = false 可以不传,也可以传
        if (current == null) {
            current = 1;
        }
        PageInfo<TUser> pageInfo = userService.getUserByPage(current);
        return R.OK(pageInfo);
    }

    @PreAuthorize(value = "hasAuthority('user:view')")
    @GetMapping("/api/user/{id}")
    public R userDetail(@PathVariable(value = "id") Integer id){
        TUser user = userService.getUserById(id);
        return R.OK(user);
    }

    /**
     * 新增用户
     * @param userQuery
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:add')")
    @PostMapping("/api/user")
    public  R addUser(UserQuery userQuery,@RequestHeader(value = "Authorization") String token){
        userQuery.setToken(token);
        int save =userService.saveUser(userQuery);
        return save >= 1 ? R.OK() : R.FALL();
    }

    /**
     * 编辑用户
     * @param userQuery
     * @param token
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:edit')")
    @PutMapping("/api/user")
    public  R editUser(UserQuery userQuery,@RequestHeader(value = "Authorization") String token){
        userQuery.setToken(token);
        int update =userService.updateUser(userQuery);
        return update >= 1 ? R.OK() : R.FALL();
    }

    @PreAuthorize(value = "hasAuthority('user:delete')")
    @DeleteMapping("/api/user/{id}")
    public R delUser(@PathVariable(value = "id")Integer id){
        int del = userService.delUserById(id);
        return del >= 1 ? R.OK() : R.FALL();
    }

    @PreAuthorize(value = "hasAuthority('user:delete')")
    @DeleteMapping("/api/user")
    public R batchDelUser(@RequestParam(value = "ids")String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = userService.batchDelUserIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FALL();
    }

    /**
     * 加载负责人
     * @return
     */
    @GetMapping("/api/owner")
    public R owner(){
        List<TUser> ownerList = userService.getOwnerList();
        return R.OK(ownerList);
    }
}
