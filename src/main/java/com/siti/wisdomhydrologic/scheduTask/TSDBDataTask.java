package com.siti.wisdomhydrologic.scheduTask;

import com.siti.wisdomhydrologic.datepull.mapper.TSDBMapper;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.util.DateTransform;
import com.siti.wisdomhydrologic.util.NidListUtils;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Resource
    PullBiz pullBiz;

    //每个整点10分执行方法
    @Scheduled(cron = "0 10 0/1 * * ?")//0/5 * * * * ?   0 4 0/1 * * ?
    public void testSca() throws Exception {
        Date today = new Date();

        //每小时数据获取
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.add(Calendar.HOUR,-1);
        today = ca.getTime();

        String date = DateTransform.Date2String(today, "yyyy-MM-dd HH:mm:ss");

        List<Integer> nidList = NidListUtils.getNidList();
        List<TSDBVo> TSDBVoList = tsdbMapper.selectRealTSDB(nidList,date);
        Map<Integer, List<TSDBVo>> map = pullBiz.getTSDBMap(TSDBVoList);
        for (int k : map.keySet()) {
            logger.info("在{}的tsdb数据{}条", date,TSDBVoList.size());
            producerImpl.sendTSDBMsg(map.get(k));
        }
    }



}
