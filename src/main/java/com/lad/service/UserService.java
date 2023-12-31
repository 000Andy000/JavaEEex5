package com.lad.service;

import com.lad.model.User;

import java.util.List;

/**
 * @author Andy
 * @date 2023/6/6 11:41
 */
public interface UserService {
    //注册
    public boolean register(User user) ;

    //登录
    public User login(String username, String password) ;

    //登出
    public void signOut(Integer id);

    //获取个人信息
    public User getUserById(Integer id,int myId);

    //模糊查询用户
    public List<User> searchByUsername(String username,Integer myId) ;

    //修改密码
    public boolean changePassword(User user) ;

    //修改个人信息
    public boolean updateUser(User user) ;

    //获取关注列表
    public List<User> getConcerned(Integer userId,Integer myId) ;

    //获取粉丝列表
    public List<User> getFans(Integer userId,Integer myId) ;
}
