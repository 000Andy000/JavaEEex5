package com.lad.model;

import lombok.Data;

import java.util.Date;


@Data
public class User {
    private Integer id;
    private String username;
    private String password;
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

}
