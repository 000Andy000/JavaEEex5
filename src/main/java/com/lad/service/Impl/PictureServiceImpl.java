package com.lad.service.Impl;

/**
 * @author Andy
 * @date 2023/6/5 16:17
 */
import com.lad.mapper.ConcernMapper;
import com.lad.mapper.PictureMapper;
import com.lad.mapper.UserMapper;
import com.lad.model.Concern;
import com.lad.model.Picture;
import com.lad.model.vo.PictureVo;
import com.lad.service.PictureService;
import com.lad.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service

public class PictureServiceImpl implements PictureService {

    private final PictureMapper pictureMapper;
    private final UserMapper userMapper;
    private final ConcernMapper concernMapper;

    @Autowired
    public PictureServiceImpl(PictureMapper pictureMapper, UserMapper userMapper, ConcernMapper concernMapper) {
        this.pictureMapper = pictureMapper;
        this.userMapper = userMapper;
        this.concernMapper = concernMapper;
    }

    //上传图片
    public boolean uploadPicture(MultipartFile file, String userId, String name, String intro, String tags) throws IOException {
        // 上传文件并获取保存后的路径
        String fileName = FileUploadUtil.uploadFile(file, userId);

        // 创建 Picture 对象
        Picture picture = new Picture();
        picture.setName(name);
        picture.setFname(fileName);
        picture.setUserId(userId);
        picture.setIntro(intro);
        picture.setTags(tags);

        // 将 Picture 对象保存到数据库
        int row = pictureMapper.insertPicture(picture);

        return row > 0;
    }

    // 更改图片
    public boolean updatePicture(Integer id, String name,String tag, String intro) {
        Picture picture = new Picture();
        picture.setId(id);
        picture.setName(name);
        picture.setTags(tag);
        picture.setIntro(intro);
        int row = pictureMapper.updatePicture(picture);
        return row > 0;
    }

    // 根据图片 ID 查找图片
    public Picture selectPictureById(Integer pictureId) {
        return pictureMapper.selectPictureById(pictureId);
    }

    // 根据图片 ID 删除图片
    public boolean deletePictureById(Integer pictureId) {
        int row = pictureMapper.deletePictureById(pictureId);
        return row > 0;
    }

    //将Picture数组转为PictureVo数组
    public List<PictureVo> toPicVo(List<Picture> pictures){
        List<PictureVo> pictureVos = new ArrayList<>();
        for (Picture picture : pictures) {
            //获取图片发布者用户名
            Integer userId = Integer.valueOf(picture.getUserId());
            String username = userMapper.selectByUserId(userId).getUsername();
            PictureVo pictureVo = new PictureVo();
            pictureVo.setId(picture.getId());
            pictureVo.setName(picture.getName());
            pictureVo.setUsername(username);
            pictureVo.setFname(picture.getFname());
            pictureVo.setUploadTime(picture.getUploadTime());
            pictureVos.add(pictureVo);
        }
        return pictureVos;
    }

    //查询某用户的图片
    public List<PictureVo> searchPicturesByUser(String userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Picture> pictures = pictureMapper.selectPicturesByUserId(userId, offset, pageSize);
        return  toPicVo(pictures);
    }

    // 根据tag搜索图片
    public List<PictureVo> searchPicturesByTag(List<String> tags, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Picture> pictures = pictureMapper.searchPicturesByTag(tags, offset, pageSize);
        return  toPicVo(pictures);
    }

    // 根据name搜索图片
    public List<PictureVo> searchPicturesByName(String name, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Picture> pictures = pictureMapper.searchPicturesByName(name, offset, pageSize);
        return  toPicVo(pictures);
    }

    // 获取关注的人的图片
    public List<PictureVo> searchPicturesByConcern(Integer userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        ArrayList<Integer> userIds = new ArrayList<>();
        List<Concern> concerns = concernMapper.selectConcerner(userId);
        for (Concern concern:concerns){
            userIds.add((userMapper.selectByUserId(concern.getConcernedId())).getId());
        }
        List<Picture> pictures = pictureMapper.selectPicturesByConcern(userIds,offset,pageSize);
        return toPicVo(pictures);
    }


}
