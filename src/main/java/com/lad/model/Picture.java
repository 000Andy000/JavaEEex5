package com.lad.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    //暂存图片
    MultipartFile selectedImage;
    long clickNum;
}
