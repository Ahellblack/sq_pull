package com.siti.wisdomhydrologic.datepull.service.impl;

import com.siti.wisdomhydrologic.datepull.entity.RTSQ;
import com.siti.wisdomhydrologic.datepull.mapper.DayMapper;
import com.siti.wisdomhydrologic.datepull.mapper.HourMapper;
import com.siti.wisdomhydrologic.datepull.mapper.RTSQMapper;
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
    @Resource
    RTSQMapper rtsqMapper;


    @Override
    public int selectDayCount(String date) {return dayMapper.selectNum(date);}
    @Override
    public int selectHourCount(String date) { return hourMapper.selectHourCount(date); }
    @Override
    public int selectTSDBCount(String date) { return tsdbMapper.selectTSDBNum(date); }


    @Override
    public List<DayVo> selectByDayCondition(String date, int nid, int begin, int end) {

        return dayMapper.selectByConditions( date,nid, begin, end);
    }
    @Override
    public List<HourVo> selectByHourCondition(Integer nid, String date) {

        return hourMapper.selectMonthByNid(nid,date);
    }
    @Override
    public List<TSDBVo> selectByTSDBCondition(String date, int all, int begin, int end, Integer senid) {

        return tsdbMapper.selectByConditions( date,all, begin, end, senid);

    }

    @Override
    public int selectRTSQCount(String date) {
        return rtsqMapper.selectCount(date);
    }

    @Override
    public List<RTSQ> selectByRTSQCondition(String s, int size, int i, int i1) {
        return null;
    }

    @Override
    public List<Integer> selectTSDBSenId() {
        return tsdbMapper.selectTSDBSenId();
    }


}
