package com.lad.controller;

import com.lad.model.Picture;
import com.lad.model.Result;
import com.lad.model.User;
import com.lad.model.vo.PictureVo;
import com.lad.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Andy
 * @date 2023/6/5 16:15
 */
@RestController
@RequestMapping("/pictures")
public class PictureController {

    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        System.out.println("sadsdasd");
        this.pictureService = pictureService;
    }

    //上传图片信息
    @PostMapping
    public Result uploadPicture(@RequestParam("file") MultipartFile file,
                                @RequestParam("userId") String userId,
                                @RequestParam("name") String name,
                                @RequestParam("intro") String intro,
                                @RequestParam("tags") String tags) {
        try {
            boolean success = pictureService.uploadPicture(file, userId, name, intro, tags);
            if (success) {
                return new Result(200, null, "上传成功");
            } else {
                return new Result(400, null, "上传失败");
            }
        } catch (IOException e) {
            return new Result(500, null, "服务器内部错误");
        }
    }

    //修改图片信息
    @PutMapping
    public Result updatePicture(@RequestParam("id") Integer id,
                                @RequestParam("name") String name,
                                @RequestParam("tag") String tag,
                                @RequestParam("intro") String intro) {
        boolean success = pictureService.updatePicture(id, name, tag, intro);
        if (success) {
            return new Result(200, null, "更新成功");
        } else {
            return new Result(400, null, "更新失败");
        }
    }

    //删除图片
    @DeleteMapping("/{pictureId}")
    public Result deletePicture(@PathVariable("pictureId") Integer pictureId) {
        boolean success = pictureService.deletePictureById(pictureId);
        if (success) {
            return new Result(200, null, "删除成功");
        } else {
            return new Result(400, null, "删除失败");
        }
    }

    //分页按用户id查询图片，请求路径示例：/user/{userId}?pageNum=2
    @GetMapping("/user/{userId}")
    public Result searchPicturesByUser(@PathVariable("userId") String userId, @RequestParam("pageNum") String pageNum) {
        int pageSize = 8;
        List<PictureVo> pictures = pictureService.searchPicturesByUser(userId, Integer.parseInt(pageNum),pageSize);
        return new Result(200, pictures, "查询成功");
    }

    //分页按标签查询图片
    @GetMapping("/tag")
    public Result searchPicturesByTag(@RequestParam("tags") List<String> tags, @RequestParam("pageNum") String pageNum) {
        int pageSize = 8;
        List<PictureVo> pictures = pictureService.searchPicturesByTag(tags,Integer.parseInt(pageNum),pageSize);
        return new Result(200, pictures, "查询成功");
    }

    //分页按图片名查询图片
    @GetMapping("/name/{name}")
    public Result searchPicturesByName(@PathVariable("name") String name, @RequestParam("pageNum") String pageNum) {
        int pageSize = 8;
        List<PictureVo> pictures = pictureService.searchPicturesByName(name,Integer.parseInt(pageNum),pageSize);
        return new Result(200, pictures, "查询成功");
    }

    //分页查询关注的人的图片
    @GetMapping("/concern")
    public Result searchByConcern(HttpSession session, @RequestParam("pageNum") String pageNum){
        int pageSize = 8;
        //获取userId
        Integer userId = (Integer) session.getAttribute("userId");
        List<PictureVo> pictures = pictureService.searchPicturesByConcern(userId, Integer.parseInt(pageNum),pageSize);
        return new Result(200, pictures, "查询成功");
    }

}
