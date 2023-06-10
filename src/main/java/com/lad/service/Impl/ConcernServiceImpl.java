package com.lad.service.Impl;

import com.lad.mapper.ConcernMapper;
import com.lad.model.Concern;
import com.lad.service.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author Andy
 * @date 2023/6/5 16:16
 */
@Service
public class ConcernServiceImpl implements ConcernService {

    @Autowired
    private ConcernMapper concernMapper;


    //添加、删除关注记录
    public boolean toggleConcern(Integer concernerId, Integer concernedId) {
        Concern concern = new Concern();
        concern.setConcernerId(concernerId);
        concern.setConcernedId(concernedId);

        Concern existingConcern = concernMapper.selectConcern(concernerId, concernedId);
        int rows;
        if (existingConcern != null) {
            // 已存在关注记录，删除该记录
            rows = concernMapper.deleteConcern(concernerId, concernedId);
        } else {
            // 不存在关注记录，添加新记录
            concern.setConcernTime(new Date());  // 设置关注时间为当前时间
            rows = concernMapper.insertConcern(concern);
        }
        return rows > 0;
    }
}

