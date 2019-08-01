package com.siti.wisdomhydrologic.datepull.mapper;

import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/7/11.
 */
public interface HourMapper {

    @Select("select count(*) from HOURDB where TO_CHAR(TIME,'YYYY-MM-dd')=#{date}")
    int selectHourCount(@Param("date")String date);

    @Select("select * from " +
            "(select s.*,rownum rowN from HOURDB s where TO_CHAR(TIME,'YYYY-MM-dd')=#{date} and  rownum<#{all} \n" +
            ") m where m.rowN between #{begin} and #{end}")
    List<DayVo> selectByConditions(@Param("date")String date, @Param("all")int all, @Param("begin")int begin, @Param("end")int end);

}
