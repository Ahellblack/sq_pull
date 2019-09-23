package com.siti.wisdomhydrologic.datepull.mapper;

import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by DC on 2019/7/6.
 *
 * @data ${DATA}-15:56
 */

public interface DayMapper extends Mapper<DayVo> {

    @Select("<script>select * from " +
            "(select s.*,rownum rowN from DAYDB s where TO_CHAR(TIME,'YYYY-MM-dd')=#{date} " +
            "and SENID in (<foreach collection=\"nidList\" item=\"item\" separator=\",\">#{item}</foreach>)" +
            ") m where m.rowN between #{begin} and #{end}</script>")
    List<DayVo> selectByConditions(@Param("date") String date, @Param("nidList") List<Integer> nidList, @Param("begin") int begin, @Param("end") int end);

    @Select("select count(*) from DAYDB where TO_CHAR(TIME,'YYYY-MM-dd')=#{date}")
    int selectNum(@Param("date") String date);

    @Select("<script>select * from DAYDB where SENID in (" +
            "<foreach collection=\"nidList\" item=\"item\" separator=\",\">" +
            "#{item}</foreach>) and  TO_CHAR(time,'YYYY-MM-dd') = #{date} </script>")
    List<DayVo> selectRealDay(@Param("nidList") List<Integer> nidList, @Param("date") String date);
}
