package com.lad.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Andy
 * @date 2023/6/6 14:11
 */
@Data
public class PictureVo {

    private Integer id;
    private String username;
    private String name;
    private String fname;
    private Date uploadTime;

}

