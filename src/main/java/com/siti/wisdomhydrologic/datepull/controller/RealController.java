package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.scheduTask.mapper.RealMapper;
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
import java.text.SimpleDateFormat;
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

    @Autowired
    private RealMapper realMapper;

    @GetMapping("getdata")
    public void getData(String startTime, String endTime) {
        Map<Integer, List<RealVo>> map = new HashMap<>();
        int index = 0;
        int sum = 0;
        List<Integer> nidList = NidListUtils.getNidList();
        DatesUtils datesUtils = new DatesUtils();
        try {
            List<String> datesList = datesUtils.findMinDates(startTime, endTime);
            for (String date : datesList) {
                List<RealVo> list = realMapper.gethistory5MinDataTest(date, nidList);
                if (list.size() > 0) {
                    sum = sum + list.size();
                    map = pullBiz.getRealMap(list);
                    for (int k : map.keySet()) {
                        //producerImpl.sendRealMsg(map.get(k));
                    }
                    logger.info("处于{}的real数据,合计打包{}条数据", date, sum);
                }
            }
            } catch (ParseException e) {
        }
    }

    @GetMapping("getTestData")
    public void getTestData(String startTime, String endTime) {
        Map<Integer, List<RealVo>> map = new HashMap<>();
        int index = 0;
        int sum = 0;
        List<Integer> nidList = NidListUtils.getNidList();
        DatesUtils datesUtils = new DatesUtils();
        try {
            List<String> datesList = datesUtils.findMinDates(startTime, endTime);
            for (String date : datesList) {
                List<RealVo> list = realMapper.getTestHistory5MinDataTest(date, nidList);
                if (list.size() > 0) {
                    sum = sum + list.size();
                    map = pullBiz.getRealMap(list);
                    for (int k : map.keySet()) {
                      //  producerImpl.sendRealMsg(map.get(k));
                    }
                    logger.info("处于{}的real数据,合计打包{}条数据", date, sum);
                }
            }
        } catch (ParseException e) {
        }
    }


}
