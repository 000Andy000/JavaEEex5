package com.lad.dao;

import com.lad.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andy
 * @date 2023/6/5 16:14
 */
@Mapper
@Repository
public interface UserDao {

    //添加用户
    @Insert("INSERT INTO user (username, password, name, intro, gender, province, city, regist_time, type, email, mobile, QQ, status) " +
            "VALUES (#{username}, #{password}, #{name}, #{intro}, #{gender}, #{province}, #{city}, #{registTime}, #{type}, #{email}, #{mobile}, #{QQ}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    //更新用户
    @Update("UPDATE user SET name = #{name}, password = #{password}, intro = #{intro}, gender = #{gender}, province = #{province}, city = #{city}, " +
            "type = #{type}, email = #{email}, mobile = #{mobile}, QQ = #{QQ}, status = #{status} WHERE id = #{id}")
    int updateUser(User user);

    //根据用户名和密码查找
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    //根据用户名查找
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(@Param("username") String username);

    //根据Id查找
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectByUserId(@Param("id") Integer id);


    //根据用户名模糊查询
    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{username}, '%')")
    List<User> searchByUsername(@Param("username") String username);

}