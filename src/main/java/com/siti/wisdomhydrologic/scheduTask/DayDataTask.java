package com.siti.wisdomhydrologic.scheduTask;

import com.siti.wisdomhydrologic.datepull.mapper.DayMapper;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.scheduTask.mapper.RealMapper;
import com.siti.wisdomhydrologic.util.DateOrTimeTrans;
import com.siti.wisdomhydrologic.util.NidListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2019/7/29.
 * */

@Component
public class DayDataTask {

    private Logger logger = LoggerFactory.getLogger(RealDataTask.class);

    @Resource
    private RealMapper realMapper;
    @Resource
    private DayMapper dayMapper;
    @Resource
    FetchDataImpl fetchDataImpl;
    @Resource
    ProducerImpl producerImpl;

    /**
     * 每天中午12点触发
     * */
    @Scheduled(cron = "0 0 12 * * ?")
    public void testSca() throws Exception {
        Date today = new Date();
        String date = DateOrTimeTrans.Date2TimeString(today);
        List<Integer> nidList = NidListUtils.getNidList();
        List<DayVo> dayVoList = dayMapper.selectRealDay(nidList,date);
        producerImpl.sendRealDayMsg(dayVoList);
        logger.info("在{}获得的day数据", date);
    }
}