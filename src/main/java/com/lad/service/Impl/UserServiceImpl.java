package com.lad.service.Impl;

import com.lad.dao.ConcernDao;
import com.lad.dao.PictureDao;
import com.lad.dao.UserDao;
import com.lad.model.Concern;
import com.lad.model.Picture;
import com.lad.model.User;
import com.lad.service.UserService;
import com.lad.utils.ImgTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private PictureDao pictureDao;

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
        if (user != null) {
            user.setStatus("1");
        }
        return user;
    }

    //登出
    public void signOut(Integer id) {
        User user = userDao.selectByUserId(id);
        user.setStatus("0");
    }


    //模糊查询用户
    public List<User> searchByUsername(String name,Integer myId) {
        List<User> users = userDao.searchByName(name);
        for (User user : users) {
            user.setConcerned(userDao.searchConcernerNum(user.getId()));
            user.setConcerner(userDao.searchConcernedNum(user.getId()));
            List<Picture> pictures = pictureDao.selectFiveImg(user.getId());
            for (Picture picture : pictures) {
                picture.setFname(ImgTransfer.pathToRequest(picture.getFname()));
            }
            user.setImages(pictures);
            user.setIsConcern((concernDao.selectConcern(myId, user.getId())) == null ? 0 : 1);
        }
        return users;
    }

    //修改密码
    public boolean changePassword(User user) {
        // 验证旧密码是否正确
        User oldUser = userDao.selectByUserId(user.getId());

        String oldPassword = oldUser.getPassword();

        String confirmPassword = user.getOldPassword();

        if (!Objects.equals(oldPassword, confirmPassword)) {
            return false; // 旧密码不正确，修改密码失败
        }

        // 更新密码
        int rows = userDao.updatePassword(user);
        return rows > 0;
    }

    //修改个人信息
    public boolean updateUser(User user) {
        int rows = userDao.updateUser(user);
        return rows > 0;
    }

    //修改个性签名
    public boolean updateUserIntro(User user) {
        int rows = userDao.updateUserIntro(user);
        return rows > 0;
    }


    //获取个人信息
    public User getUserById(Integer id,int myId) {
        User user = userDao.selectByUserId(id);
        user.setConcerned(userDao.searchConcernerNum(id));
        user.setConcerner(userDao.searchConcernedNum(id));
        user.setHeadImg(ImgTransfer.pathToRequest(user.getHeadImg()));
        user.setIsConcern((concernDao.selectConcern(myId, id)) == null ? 0 : 1);
        if (myId == user.getId()){
            user.setIsConcern (2);
        }
        return user;
    }


    //获取关注列表（我关注谁）
    public List<User> getConcerned(Integer userId,Integer myId) {
        List<Concern> concerns = concernDao.selectConcernByConcerner(userId);
        ArrayList<User> users = new ArrayList<>();
        for (Concern concern : concerns) {
            User user = userDao.selectByUserId(concern.getConcernedId());
            user.setConcerned(userDao.searchConcernerNum(user.getId()));
            user.setConcerner(userDao.searchConcernedNum(user.getId()));
            List<Picture> pictures = pictureDao.selectFiveImg(user.getId());
            for (Picture picture : pictures) {
                picture.setFname(ImgTransfer.pathToRequest(picture.getFname()));
            }
            user.setImages(pictures);
            user.setIsConcern((concernDao.selectConcern(myId, user.getId())) == null ? 0 : 1);
            users.add(user);

        }
        return users;
    }

    //获取粉丝列表（谁关注我）
    public List<User> getFans(Integer userId,Integer myId) {
        List<Concern> concerns = concernDao.selectConcernByConcerned(userId);
        ArrayList<User> users = new ArrayList<>();
        for (Concern concern : concerns) {
            User user = userDao.selectByUserId(concern.getConcernerId());
            user.setConcerned(userDao.searchConcernerNum(user.getId()));
            user.setConcerner(userDao.searchConcernedNum(user.getId()));
            List<Picture> pictures = pictureDao.selectFiveImg(user.getId());
            for (Picture picture : pictures) {
                picture.setFname(ImgTransfer.pathToRequest(picture.getFname()));
            }
            user.setImages(pictures);
            user.setIsConcern((concernDao.selectConcern(myId, user.getId())) == null ? 0 : 1);
            users.add(user);
        }
        return users;
    }
}

