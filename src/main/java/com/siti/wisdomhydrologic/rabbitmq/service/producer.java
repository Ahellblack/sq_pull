package com.siti.wisdomhydrologic.rabbitmq.service;

import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.datepull.vo.HourVo;
import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;

import java.util.List;

/**
 * Created by DC on 2019/7/7.
 *
 * @data ${DATA}-8:38
 */
public interface producer {

    public void sendDayDBMsg(List<DayVo> dayVoList);
    public void sendHourDBMsg(List<HourVo> hourVoList);
    public void sendTSDBMsg(List<TSDBVo> tsdbVoList);
    public void sendRTSQMsg(List<RealVo> realVoList);

}
