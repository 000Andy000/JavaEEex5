package com.lad.dao;

import com.lad.model.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andy
 * @date 2023/6/5 16:14
 */
@Mapper
@Repository
public interface PictureDao {
    //插入图片
    @Insert("INSERT INTO picture (name, fname, user_id, intro, tags, upload_time, click_num) " +
            "VALUES (#{name}, #{fname}, #{userId}, #{intro}, #{tags}, #{uploadTime}, #{clickNum})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPicture(Picture picture);

    //根据id查询
    @Select("SELECT * FROM picture WHERE id = #{id}")
    Picture selectPictureById(Integer id);

    //更新图片信息
    @Update("UPDATE picture SET name = #{name}, intro = #{intro}, tags = #{tags} WHERE id = #{id}")
    int updatePicture(Picture picture);

    //根据id删除图片
    @Delete("DELETE FROM picture WHERE id = #{id}")
    int deletePictureById(Integer id);

    //查询某用户的图片
    @Select("SELECT * FROM picture WHERE user_id = #{userId} LIMIT #{offset}, #{pageSize}")
    List<Picture> selectPicturesByUserId(String userId, int offset, int pageSize);

    //根据tag模糊查询
    List<Picture> searchPicturesByTag(List<String> tags, int offset, int pageSize);

    //根据name模糊查询
    @Select("SELECT * FROM picture WHERE name LIKE CONCAT('%', #{name}, '%') LIMIT #{offset}, #{pageSize}")
    List<Picture> searchPicturesByName(String name, int offset, int pageSize);

    List<Picture> selectPicturesByConcern(List<Integer> userIds, int offset, int pageSize);

}
