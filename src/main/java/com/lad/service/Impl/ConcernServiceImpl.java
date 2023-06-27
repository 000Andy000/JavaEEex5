package com.lad.service.Impl;

import com.lad.dao.ConcernDao;
import com.lad.dao.UserDao;
import com.lad.model.Concern;
import com.lad.model.User;
import com.lad.service.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


/**
 * @author Andy
 * @date 2023/6/5 16:16
 */
@Service
public class ConcernServiceImpl implements ConcernService {

    @Autowired
    private ConcernDao concernDao;


    //添加、删除关注记录
    public int toggleConcern(Integer concernerId, Integer concernedId) {
        Concern concern = new Concern();
        concern.setConcernerId(concernerId);
        concern.setConcernedId(concernedId);

        Concern existingConcern = concernDao.selectConcern(concernerId, concernedId);
        if (existingConcern != null) {
            // 已存在关注记录，删除该记录
            concernDao.deleteConcern(concernerId, concernedId);
            return 0;//取关成功
        } else {
            // 不存在关注记录，添加新记录
            concern.setConcernTime(new Date());  // 设置关注时间为当前时间
            concernDao.insertConcern(concern);
        }
        return 1 ;//关注成功
    }
}

