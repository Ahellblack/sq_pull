package com.siti.wisdomhydrologic.scheduTask.mapper;

import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/7/24.
 */
public interface RealMapper {
/*
    @Select("select * from REAL where to_char(TIME,'YYYY-MM-dd') = #{date}")
    List<Real> getLatest30MinData(@Param("date") int todayStr);
*/

    @Select("select * from REAL where to_char(TIME,'YYYY-MM-dd HH24:mi:ss')in( #{date},#{date1},#{date2})")
    List<RealVo> getLatest5MinData(@Param("date") String date,@Param("date1") String date1,@Param("date2") String date2);
}
