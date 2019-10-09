package com.siti.wisdomhydrologic.datepull.mapper;

import com.siti.wisdomhydrologic.datepull.vo.HourVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/7/11.
 */
public interface HourMapper {

    @Select("select count(*) from HOURDB where TO_CHAR(TIME,'YYYY-MM')=#{date}")
    int selectHourCount(@Param("date") String date);


    @Select("<script>select * from T_HOURDB where time = TO_DATE(#{date} ,'YYYY-MM-dd') and" +
            " SENID in (<foreach collection=\"nidList\" item=\"item\" separator=\",\">#{item}</foreach>) " +
            "</script> ")
    List<HourVo> selectByConditions(@Param("nidList") List<Integer> nidList, @Param("date") String date);

    @Select("<script>select * from T_HOURDB where time = #{date} and" +
            " SENID in (<foreach collection=\"nidList\" item=\"item\" separator=\",\">#{item}</foreach>) " +
            "</script> ")
    List<HourVo> selectTestByConditions(@Param("nidList") List<Integer> nidList, @Param("date") String date);


    @Select("<script>select * from HOURDB where " +
            "SENID in (<foreach collection=\"nidList\" item=\"item\" separator=\",\"> #{item}</foreach>) " +
            "and TO_CHAR(TIME,'YYYY-MM-dd')=#{date} </script> ")
    List<HourVo> selectMonthByNid(@Param("nid") List<Integer> nidList, @Param("date") String date);

    @Select("<script>"+
            "select * from HOURDB where SENID in (" +
            "<foreach collection=\"nidList\" item=\"item\" separator=\",\">#{item}</foreach>) " +
            "and time = TO_DATE(#{date} ,'YYYY-MM-dd HH24:mi:ss')</script> ")
    List<HourVo> selectDayByNid(@Param("nidList") List<Integer> nid, @Param("date") String date);

}
