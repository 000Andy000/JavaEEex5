package com.lad.service;


import com.lad.model.User;

import java.util.List;

/**
 * @author Andy
 * @date 2023/6/6 11:40
 */
public interface ConcernService {
    //添加、删除关注记录
    public boolean toggleConcern(Integer concernerId, Integer concernedId) ;





}
