package com.siti.wisdomhydrologic.datepull.service.impl;

import com.siti.wisdomhydrologic.datepull.entity.RTSQ;
import com.siti.wisdomhydrologic.datepull.mapper.DayMapper;
import com.siti.wisdomhydrologic.datepull.mapper.HourMapper;
import com.siti.wisdomhydrologic.datepull.mapper.TSDBMapper;
import com.siti.wisdomhydrologic.datepull.service.FetchData;
import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.datepull.vo.HourVo;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by DC on 2019/7/6.
 *
 * @data ${DATA}-16:39
 */
@Service
public class FetchDataImpl implements FetchData{
    @Resource
    DayMapper dayMapper;
    @Resource
    HourMapper hourMapper;
    @Resource
    TSDBMapper tsdbMapper;


    public int selectDayCount(String date) {return dayMapper.selectNum(date);}

    public int selectHourCount(String date) { return hourMapper.selectHourCount(date); }

    public int selectTSDBCount(String date) { return tsdbMapper.selectTSDBNum(date); }



    public List<DayVo> selectByDayCondition(String date, List<Integer> nidList, int begin, int end) {

        return dayMapper.selectByConditions( date,nidList, begin, end);
    }

    public List<HourVo> selectByHourCondition(List<Integer> nidList, String date) {

        return hourMapper.selectByConditions(nidList,date);
    }

    public List<TSDBVo> selectByTSDBCondition(String date, int all, int begin, int end, List<Integer> nidList) {

        return tsdbMapper.selectByConditions( date,all, begin, end, nidList);

    }


    public List<RTSQ> selectByRTSQCondition(String s, int size, int i, int i1) {
        return null;
    }


    public List<Integer> selectTSDBSenId() {
        return tsdbMapper.selectTSDBSenId();
    }


}
