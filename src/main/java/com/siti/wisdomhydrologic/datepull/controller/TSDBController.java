package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.mapper.TSDBMapper;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.util.DateOrTimeTrans;
import com.siti.wisdomhydrologic.util.DatesUtils;
import com.siti.wisdomhydrologic.util.NidListUtils;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2019/7/11.
 */
@RestController
@RequestMapping("/tsdbdata")
public class TSDBController {

    public static final Logger logger = LoggerFactory.getLogger(DataPullController.class);
    public static final int MAX_SIZE = 10000;
    public static final String Start_Time = "2015";
    public static final String End_Time = "2016";
    @Resource
    FetchDataImpl fetchDataImpl;
    @Resource
    ProducerImpl producerImpl;
    @Resource
    PullBiz pullBiz;
    @Resource
    TSDBMapper tsdbMapper;

    @GetMapping("/getdata")
    public void startPull() throws ParseException {
       /* //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");*/
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findDates(Start_Time, End_Time); /*simpleDateFormat.format(new Date())*/
        System.out.println("从数据时间asc到目前的年共有" + datesList);
        //获取所有传感器模块的NID
        List<Integer> nidList = NidListUtils.getNidList();
        Map<Integer, List<TSDBVo>> map = new HashMap<>();
        int index = 0;
        int Sum = 0;
        Long start = System.currentTimeMillis();
        //查询索引日期+NID
        for (String date : datesList) {
            for (Integer nid : nidList) {
                List<TSDBVo> list = fetchDataImpl.selectByTSDBCondition(date, MAX_SIZE, index * MAX_SIZE, (index + 1) * MAX_SIZE, nid);
                if (list.size() > 0) {
                    Sum = Sum + list.size();
                    map = pullBiz.getTSDBMap(list);
                    for (int k : map.keySet()) {
                        logger.info("nid为{}处于{}年的数据,合计打包{}条数据", nid, date, Sum);
                        producerImpl.sendTSDBMsg(map.get(k));
                    }
                }
            }
        }
    }

    @RequestMapping("/getRealTSDB")
    public void getTsdbTest(){
        Date today = new Date();
        String date = DateOrTimeTrans.Date2TimeString(today);
        List<Integer> nidList = NidListUtils.getNidList();
        List<TSDBVo> tsdbVoList = tsdbMapper.selectRealTSDB(nidList,date);
        producerImpl.sendTSDBMsg(tsdbVoList);
        logger.info("在{}获得的TSDB数据{}", date,tsdbVoList);
    }
}
