package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.nid.NidController;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.datepull.mapper.RealMapper;
import com.siti.wisdomhydrologic.util.DatesUtils;
import com.siti.wisdomhydrologic.util.NidListUtils;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2019/9/23.
 */
@RequestMapping("/realdata")
@RestController
public class RealController {
    public static final Logger logger = LoggerFactory.getLogger(RealController.class);
    @Resource
    PullBiz pullBiz;

    @Resource
    ProducerImpl producerImpl;

    @Resource
    NidController nidController;
    @Autowired
    private RealMapper realMapper;

    @GetMapping("getdata")
    public int getData(String startTime, String endTime) {
        Map<Integer, List<RealVo>> map = new HashMap<>();
        int index = 0;
        int sum = 0;
        List<Integer> nidList = nidController.getNidList();
        DatesUtils datesUtils = new DatesUtils();
        try {
            List<String> datesList = datesUtils.findMinDates(startTime, endTime);
            for (String date : datesList) {
                List<RealVo> list = realMapper.gethistory5MinDataTest(date, nidList);
                if (list.size() > 0) {
                    index = index +1;
                    sum = sum + list.size();
                    map = pullBiz.getRealMap(list);
                    for (int k : map.keySet()) {
                        producerImpl.sendRTSQMsg(map.get(k));
                    }
                    logger.info("处于{}的real数据,合计打包{}条数据,合计{}个包", date, sum,index);
                }
            }
            } catch (ParseException e) {
        }
        return sum;
    }

    @GetMapping("getTestData")
    public int getTestData(String startTime, String endTime) {
        Map<Integer, List<RealVo>> map = new HashMap<>();
        int index = 0;
        int sum = 0;
        List<Integer> nidList = nidController.getNidList();
        DatesUtils datesUtils = new DatesUtils();
        try {
            List<String> datesList = datesUtils.findMinDates(startTime, endTime);
            for (String date : datesList) {
                List<RealVo> list = realMapper.getTestHistory5MinDataTest(date, nidList);
                if (list.size() > 0) {
                    index = index +1;
                    sum = sum + list.size();
                    map = pullBiz.getRealMap(list);
                    for (int k : map.keySet()) {
                      producerImpl.sendRTSQMsg(map.get(k));
                    }
                    logger.info("处于{}的real测试数据,合计打包{}条数据,合计{}个包", date, sum,index);
                }
            }
        } catch (ParseException e) {
        }
        return sum;
    }


}
