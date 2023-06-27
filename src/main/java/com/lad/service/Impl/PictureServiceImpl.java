package com.lad.service.Impl;

/**
 * @author Andy
 * @date 2023/6/5 16:17
 */
import com.lad.dao.ConcernDao;
import com.lad.dao.PictureDao;
import com.lad.dao.UserDao;
import com.lad.model.Concern;
import com.lad.model.Picture;
import com.lad.model.User;
import com.lad.model.vo.PictureVo;
import com.lad.service.PictureService;
import com.lad.utils.FileUploadUtil;
import com.lad.utils.ImgTransfer;
import com.lad.utils.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureDao pictureDao;
    private final UserDao userDao;
    private final ConcernDao concernDao;

    @Autowired
    public PictureServiceImpl(PictureDao pictureDao,UserDao userDao, ConcernDao concernDao) {
        this.pictureDao = pictureDao;
        this.userDao = userDao;
        this.concernDao = concernDao;
    }

    //上传图片
    public boolean uploadPicture(Picture picture,String userId) throws IOException {
        // 上传文件并获取保存后的路径
        String fileName = FileUploadUtil.uploadFile(picture.getSelectedImage(), userId);

        // 完善 Picture 对象
        picture.setFname(fileName);
        picture.setUserId(userId);
        picture.setUploadTime(new Date());
        // 将 Picture 对象保存到数据库
        int row = pictureDao.insertPicture(picture);

        return row > 0;
    }

    // 更改图片
    public boolean updatePicture(Picture picture) {
        int row = pictureDao.updatePicture(picture);
        return row > 0;
    }

    // 根据图片 ID 查找图片
    public Picture selectPictureById(Integer pictureId) {
        return pictureDao.selectPictureById(pictureId);
    }

    // 根据图片 ID 删除图片
    public boolean deletePictureById(Integer pictureId) {
        int row = pictureDao.deletePictureById(pictureId);
        return row > 0;
    }

    //将Picture数组转为PictureVo数组
    public List<PictureVo> toPicVo(List<Picture> pictures){
        List<PictureVo> pictureVos = new ArrayList<>();
        for (Picture picture : pictures) {
            //获取图片发布者用户名
            Integer userId = Integer.valueOf(picture.getUserId());
            String username = userDao.selectByUserId(userId).getName();
            PictureVo pictureVo = new PictureVo();
            pictureVo.setId(picture.getId());
            pictureVo.setUserId(userId);
            pictureVo.setName(picture.getName());
            pictureVo.setUsername(username);
            pictureVo.setFname(ImgTransfer.pathToRequest(picture.getFname()));
            pictureVo.setUploadTime(TimestampUtils.toString(picture.getUploadTime()));
            pictureVos.add(pictureVo);
        }
        return pictureVos;
    }

    //查询某用户的图片
    public List<PictureVo> searchPicturesByUser(String userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Picture> pictures = pictureDao.selectPicturesByUserId(userId, offset, pageSize);
        return  toPicVo(pictures);
    }

    // 根据tag搜索图片
    public List<PictureVo> searchPicturesByTag(List<String> tags, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Picture> pictures = pictureDao.searchPicturesByTag(tags, offset, pageSize);
        return  toPicVo(pictures);
    }

    // 根据name搜索图片
    public List<PictureVo> searchPicturesByName(String name) {
        List<Picture> pictures = pictureDao.searchPicturesByName(name);
        return  toPicVo(pictures);
    }

    //按userid和name查询
    @Override
    public List<PictureVo> searchPicturesByNameAndUserId(String userId, String name) {
        List<Picture> pictures = pictureDao.searchPicturesByNameAndUserId(userId,name);
        return  toPicVo(pictures);
    }

    // 获取关注的人的图片
    public List<PictureVo> searchPicturesByConcern(Integer userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Picture> pictures = pictureDao.selectPicturesByConcern(userId,offset,pageSize);
        return toPicVo(pictures);
    }

    @Override
    public List<PictureVo> searchPicturesByTime(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Picture> pictures = pictureDao.selectPicturesByTime(offset,pageSize);
        return toPicVo(pictures);
    }


}
