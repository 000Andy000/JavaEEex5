package com.lad.service;

import com.lad.model.Picture;
import com.lad.model.vo.PictureVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Andy
 * @date 2023/6/6 11:40
 */
public interface PictureService {

    //上传图片
    public boolean uploadPicture(MultipartFile file, String userId, String name, String intro, String tags) throws IOException ;

    // 更改图片
    public boolean updatePicture(Integer id, String name,String tag, String intro) ;

    // 根据图片 ID 删除图片
    public boolean deletePictureById(Integer pictureId) ;

    //查询某用户的图片
    public List<PictureVo> searchPicturesByUser(String userId, int page, int pageSize) ;


    // 根据tag搜索图片
    public List<PictureVo> searchPicturesByTag(List<String> tags, int page, int pageSize);

    // 根据name搜索图片
    public List<PictureVo> searchPicturesByName(String name, int page, int pageSize);

    // 获取关注的人的图片
    public List<PictureVo> searchPicturesByConcern(Integer userId, int page, int pageSize) ;


}
