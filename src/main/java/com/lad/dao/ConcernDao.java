package com.lad.dao;

import com.lad.model.Concern;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andy
 * @date 2023/6/5 16:14
 */
@Mapper
@Repository
public interface ConcernDao {

    //插入关注记录
    @Insert("INSERT INTO concern (concerner_id, concerned_id, concern_time) " +
            "VALUES (#{concernerId}, #{concernedId}, #{concernTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertConcern(Concern concern);

    //删除关注记录
    @Delete("DELETE FROM concern WHERE concerner_id = #{concernerId} AND concerned_id = #{concernedId}")
    int deleteConcern(@Param("concernerId") Integer concernerId, @Param("concernedId") Integer concernedId);

    //查找关注记录
    @Select("SELECT * FROM concern WHERE concerner_id = #{concernerId} AND concerned_id = #{concernedId}")
    Concern selectConcern(@Param("concernerId") Integer concernerId, @Param("concernedId") Integer concernedId);

    //查出所有A关注人的记录
    @Select("SELECT * FROM concern WHERE concerner_id = #{concernerId}")
    List<Concern> selectConcernByConcerner(@Param("concernerId") Integer concernerId);

    //查出所有被关注者为A的记录
    @Select("SELECT * FROM concern WHERE concerned_id = #{concernedId}")
    List<Concern> selectConcernByConcerned(@Param("concernedId") Integer concernedId);
}
