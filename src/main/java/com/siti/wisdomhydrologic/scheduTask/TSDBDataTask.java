package com.siti.wisdomhydrologic.scheduTask;

import com.siti.wisdomhydrologic.datepull.mapper.TSDBMapper;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
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
 * Created by dell on 2019/8/1.
 */
@Component
public class TSDBDataTask {

    private Logger logger = LoggerFactory.getLogger(RealDataTask.class);
    @Resource
    private TSDBMapper tsdbMapper;
    @Resource
    private ProducerImpl producerImpl;

    //每个整点04分执行方法
    @Scheduled(cron = "0 4 0/1 * * ?")//0 0 0/1 * * ?
    public void testSca() throws Exception {
        Date today = new Date();
        String date = DateOrTimeTrans.Date2TimeString(today);
        List<Integer> nidList = NidListUtils.getNidList();
        List<TSDBVo> TSDBVoList = tsdbMapper.selectRealTSDB(nidList,date);
        logger.info("{}的tsdb数据",date);
        producerImpl.sendRealTSDBMsg(TSDBVoList);
    }



}
