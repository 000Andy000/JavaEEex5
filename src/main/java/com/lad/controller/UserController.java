package com.lad.controller;

import com.lad.model.Result;
import com.lad.model.User;
import com.lad.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Andy
 * @date 2023/6/5 16:15
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user, HttpServletRequest request) {
        boolean success = userService.register(user);
        if (success) {
            //注册成功后直接登录，即将用户ID存入Session中
            request.getSession().setAttribute("userId", user.getId());
            return new Result(200, user, "注册成功，并已自动登录");
        } else {
            return new Result(400, null, "用户名已存在，注册失败");
        }
    }


    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {
        User checkUser = userService.login(user.getUsername(), user.getPassword());
        if (checkUser != null) {
            // 登录成功，将用户 ID 存储在 session 中
            session.setAttribute("userId", checkUser.getId());
            //验证
            Integer userId = (Integer) session.getAttribute("userId");
            System.out.println(userId);

            return new Result(200, null, "登录成功");
        } else {
            return new Result(400, null, "用户名或密码错误，登录失败");
        }
    }
    //查询用户详细信息
    @GetMapping("/info")
    public Result getByUserId(HttpSession session) {
        //获取userId
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println(userId);
        userId = 42;
        User user = userService.getUserById(userId);
        return new Result(200, user);
    }

    //登出
    @PostMapping("/signout")
    public Result signOut(HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        userService.signOut((Integer) request.getSession().getAttribute("userId"));
        request.getSession().setAttribute("userId",-1);
        return new Result(200, null, "退出成功");
    }

    //模糊查询用户
    @GetMapping("/search")
    public Result searchByUsername(@RequestParam String username) {
        List<User> users = userService.searchByUsername(username);
        return new Result(200, users);
    }



    //修改密码
    @PutMapping("/password")
    public Result changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        boolean success = userService.changePassword(username, oldPassword, newPassword);
        if (success) {
            return new Result(200, null, "密码修改成功");
        } else {
            return new Result(400, null, "旧密码不正确，密码修改失败");
        }
    }

    //更新用户信息
    @PutMapping("/info")
    public Result updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        if (success) {
            return new Result(200, user, "用户信息更新成功");
        } else {
            return new Result(400, null, "用户信息更新失败");
        }
    }
}
