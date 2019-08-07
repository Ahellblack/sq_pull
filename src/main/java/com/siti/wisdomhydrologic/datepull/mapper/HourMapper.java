package com.siti.wisdomhydrologic.datepull.mapper;

import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/7/11.
 */
public interface HourMapper {

    @Select("select count(*) from HOURDB where TO_CHAR(TIME,'YYYY-MM')=#{date}")
    int selectHourCount(@Param("date")String date);


    @Select("<script>select * from HOURDB where TO_CHAR(TIME,'YYYY-MM')=#{date} and" +
            " SENID in (<foreach collection=\"nidList\" item=\"item\" separator=\",\">#{item}</foreach>) " +
            "</script> ")
    List<DayVo> selectByConditions(@Param("nidList") List<Integer> nidList,@Param("date")String date);

    @Select("<script>select * from HOURDB where SENID = #{nid} " +
            "and TO_CHAR(TIME,'YYYY-MM')=#{date} </script> ")
    List<DayVo> selectMonthByNid(@Param("nid")Integer nid,@Param("date")String date);

    @Select("<script>select * from HOURDB where SENID = #{nid} " +
            "and TO_CHAR(TIME,'YYYY-MM-dd')=#{date} </script> ")
    List<DayVo> selectDayByNid(@Param("nid")Integer nid,@Param("date")String date);


}
