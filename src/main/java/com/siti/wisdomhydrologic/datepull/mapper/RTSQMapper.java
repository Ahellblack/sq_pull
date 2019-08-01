package com.siti.wisdomhydrologic.datepull.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by dell on 2019/7/11.
 */
public interface RTSQMapper {

    @Select("select count(*) from RTSQ where TIME = #{date}")
    int selectCount(@Param("date") String date);
}
