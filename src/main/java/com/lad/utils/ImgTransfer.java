package com.lad.utils;

/**
 * @author Andy
 * @date 2023-6-27 027 17:08
 */
public class ImgTransfer {
    public static String pathToRequest(String path){
        return "http://localhost:8080/mine/testImg" + path.substring("D:/mine/testImg".length());
    }
}
