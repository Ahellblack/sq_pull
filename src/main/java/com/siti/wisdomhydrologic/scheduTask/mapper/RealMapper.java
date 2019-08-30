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

    @Select("<script>select * from rtsq t where TIME = TO_DATE(#{dateStart},'YYYY-MM-DD HH24:MI:SS') " +
            " and senid in(<foreach collection=\"nidList\" item=\"item\" separator=\",\">#{item}</foreach>)</script>")
    List<RealVo> getLatest5MinData(@Param("dateStart") String date,@Param("nidList") List<Integer> nidList);
}
