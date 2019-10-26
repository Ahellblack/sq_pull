package com.siti.wisdomhydrologic.scheduTask;

import com.siti.wisdomhydrologic.datepull.mapper.HourMapper;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.HourVo;
import com.siti.wisdomhydrologic.nid.NidController;
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
    PullBiz pullBiz = new PullBiz();
    @Resource
    ProducerImpl producerImpl;

    @Resource
    NidController nidController;
    /**
     * HourDB数据拉取
     * 每个整点10分执行方法
     */
    @Scheduled(cron = "0 10 0/1 * * ?")//0 0/1 * * * ?
    public void testSca() throws Exception {
        Date today = new Date();
        //每小时数据获取
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        today = ca.getTime();

        String date = DateTransform.Date2String(today, "yyyy-MM-dd HH:mm:ss");

        List<Integer> nidList = nidController.getNidList();
        List<HourVo> listByNid = hourMapper.selectDayByNid(nidList, date);
        Map<Integer, List<HourVo>> map = pullBiz.getHourMap(listByNid);
        for (int k : map.keySet()) {
           //producerImpl.sendRealHourMsg(map.get(k));
            logger.info("在{}获得的hour数据量{}条", date, listByNid.size());
        }
    }



}
