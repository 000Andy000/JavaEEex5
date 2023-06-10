package com.lad.model;

import lombok.Data;

import java.util.Date;


@Data
public class Picture {
    Integer id;
    String name;
    String fname;
    String userId;
    String intro;
    String tags;
    Date uploadTime;
    long clickNum;
}
