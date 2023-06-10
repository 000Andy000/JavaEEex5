package com.lad.service.Impl;

import com.lad.dao.ConcernDao;
import com.lad.dao.UserDao;
import com.lad.model.Concern;
import com.lad.model.User;
import com.lad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Andy
 * @date 2023/6/5 16:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConcernDao concernDao;

    //注册
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existingUser = userDao.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return false; // 用户名已存在，注册失败
        }

        user.setStatus("0");
        user.setRegistTime(new Date());
        user.setType("1");
        // 执行注册
        int rows = userDao.insertUser(user);
        return rows > 0;
    }

    //登录
    public User login(String username, String password) {
        User user = userDao.selectByUsernameAndPassword(username, password);
        user.setStatus("1");
        return user;
    }

    //登出
    public void signOut(Integer id) {
        User user = userDao.selectByUserId(id);
        user.setStatus("0");
    }


    //模糊查询用户
    public List<User> searchByUsername(String username) {
        return userDao.searchByUsername(username);
    }

    //修改密码
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // 验证旧密码是否正确
        User user = userDao.selectByUsernameAndPassword(username, oldPassword);
        if (user == null) {
            return false; // 旧密码不正确，修改密码失败
        }

        // 更新密码
        user.setPassword(newPassword);
        int rows = userDao.updateUser(user);
        return rows > 0;
    }

    //修改个人信息
    public boolean updateUser(User user) {
        int rows = userDao.updateUser(user);
        return rows > 0;
    }


    //获取个人信息
    public User getUserById(Integer id) {
        return userDao.selectByUserId(id);
    }

    //获取关注列表
    public List<User> getConcerned(Integer userId) {
        List<Concern> concerns = concernDao.selectConcerner(userId);
        ArrayList<User> users = new ArrayList<>();
        for (Concern concern:concerns){
            users.add(userDao.selectByUserId(concern.getConcernedId()));
        }
        return users;
    }

    //获取粉丝列表
    public List<User> getFans(Integer userId) {
        List<Concern> concerns = concernDao.selectConcerned(userId);
        ArrayList<User> users = new ArrayList<>();
        for (Concern concern:concerns){
            users.add(userDao.selectByUserId(concern.getConcernedId()));
        }
        return users;
    }
}

