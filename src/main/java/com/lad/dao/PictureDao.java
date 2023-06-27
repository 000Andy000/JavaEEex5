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
    List<Picture> selectPicturesByUserId(@Param("userId")String userId,@Param("offset")int offset, @Param("pageSize")int pageSize);

    //根据tag模糊查询
    List<Picture> searchPicturesByTag(List<String> tags,@Param("offset")int offset, @Param("pageSize")int pageSize);

    //根据name模糊查询
    @Select("SELECT * FROM picture WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Picture> searchPicturesByName(@Param("name") String name);

    //获取关注用户的图
    @Select("SELECT p.* FROM picture p "+
            "JOIN concern c ON c.concerned_id = p.user_id "+
            "WHERE c.concerner_id = #{userId} "+
            "LIMIT #{offset}, #{pageSize} "
    )
    List<Picture> selectPicturesByConcern(@Param("userId") int userId, @Param("offset")int offset, @Param("pageSize")int pageSize);

    //获取最新的
    @Select("SELECT * FROM " +
            "(SELECT * FROM picture " +
            "ORDER BY upload_time DESC) " +
            "AS sorted_pictures " +
            "ORDER BY upload_time DESC " +
            "LIMIT #{offset}, #{pageSize}"
    )
    List<Picture> selectPicturesByTime(@Param("offset")int offset, @Param("pageSize")int pageSize);

    //获取某用户前五张图
    @Select("SELECT * FROM " +
            "(SELECT * FROM picture " +
            "WHERE user_id = #{userId} " +
            "ORDER BY upload_time DESC) " +
            "AS sorted_pictures " +
            "LIMIT 0,5")
    List<Picture> selectFiveImg(@Param("userId") int userId);

    @Select("SELECT * FROM " +
            "picture " +
            "WHERE user_id=#{userId} " +
            "AND name LIKE CONCAT('%', #{name}, '%') ")
    List<Picture> searchPicturesByNameAndUserId(@Param("userId")String userId, @Param("name")String name);
}
