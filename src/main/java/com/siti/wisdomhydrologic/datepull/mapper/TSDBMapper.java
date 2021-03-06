package com.siti.wisdomhydrologic.datepull.mapper;

import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/7/11.
 */
public interface TSDBMapper {

    @Select("select count(*) from TSDB WHERE TO_CHAR(TIME,'YYYY-MM-dd') = #{date}")
    int selectTSDBNum(@Param("date") String date);

    @Select("<script>select * from " +
            "(select s.*,rownum rowN from TSDB s " +
            "where SENID in (<foreach collection=\"nidList\" item=\"item\" separator=\",\"> #{item}</foreach>)  " +
            " and time = TO_DATE(#{date} ,'YYYY-MM-dd HH24:mi:ss')) m " +
            "where m.rowN between #{begin} and #{end}</script>")
    List<TSDBVo> selectByConditions(@Param("date") String date, @Param("all") int all, @Param("begin") int begin, @Param("end") int end, @Param("nidList")List<Integer> nidList);

    @Select("<script>select * from " +
            "(select s.*,rownum rowN from T_TSDB s " +
            "where SENID in (<foreach collection=\"nidList\" item=\"item\" separator=\",\"> #{item}</foreach>)  " +
            " and DATE_FORMAT(time,'%Y-%m-%d') = #{date} ) m " +
            "where m.rowN between #{begin} and #{end}</script>")
    List<TSDBVo> selectTestByConditions(@Param("date") String date, @Param("all") int all, @Param("begin") int begin, @Param("end") int end, @Param("nidList")List<Integer> nidList);



    @Select("SELECT SENID FROM TSDB GROUP BY SENID")
    List<Integer> selectTSDBSenId();

    @Select("<script>select * from TSDB where SENID in (" +
            "<foreach collection=\"nidList\" item=\"item\" separator=\",\">" +
            "#{item}</foreach>) and TIME = TO_DATE(#{date},'YYYY-MM-dd HH24:mi:ss')</script>")
    List<TSDBVo> selectRealTSDB(@Param("nidList") List<Integer> nidList, @Param("date") String date);
}
