package com.lad.model;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String headImg;
    private String name;
    private String intro;
    private String gender;
    private String province;
    private String city;
    private Date registTime;
    private String type;
    private String email;
    private String mobile;
    private String QQ;
    private String status;

    //关注数
    private int concerner;
    //粉丝数
    private int concerned;
    //图片预览
    private List<Picture> images;
    //是否关注 0未关注   1已关注
    private int isConcern;
    //旧密码
    private String oldPassword;

}
