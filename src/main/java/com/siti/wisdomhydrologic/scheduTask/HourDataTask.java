package com.siti.wisdomhydrologic.scheduTask;

import com.siti.wisdomhydrologic.datepull.mapper.HourMapper;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.util.DateOrTimeTrans;
import com.siti.wisdomhydrologic.util.NidListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2019/8/4.
 */
@Component
public class HourDataTask {

    private Logger logger = LoggerFactory.getLogger(RealDataTask.class);

    @Resource
    private HourMapper hourMapper;
    @Resource
    FetchDataImpl fetchDataImpl;
    @Resource
    ProducerImpl producerImpl;

    /**
     * HourDB数据拉取
     * 每天中午12点触发
     * */
    @Scheduled(cron = "0 0 12 * * ?")
    public void testSca() throws Exception {
        Date today = new Date();
        String date = DateOrTimeTrans.Date2TimeString(today);
        List<Integer> nidList = NidListUtils.getNidList();
        List<DayVo> dayVoList = new ArrayList<>();
        for (Integer nid : nidList){
            List<DayVo> listByNid = hourMapper.selectDayByNid(nid,date);
            dayVoList.addAll(listByNid);
        }
        producerImpl.sendRealHourMsg(dayVoList);
        logger.info("在{}获得的hour数据量{}条", date,dayVoList.size());
    }
}
