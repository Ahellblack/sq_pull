package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.mapper.HourMapper;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.HourVo;
import com.siti.wisdomhydrologic.nid.NidController;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.util.DatesUtils;
import com.siti.wisdomhydrologic.util.NidListUtils;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2019/7/11.
 */

@RestController
@RequestMapping("/hourdata")
public class HourDataController {
    public static final Logger logger = LoggerFactory.getLogger(DataPullController.class);
    public static final int MAX_SIZE = 10000;
    public static final String Start_Time = "2019-01";
    public static final String End_Time = "2019-12";
    @Resource
    FetchDataImpl fetchDataImpl;
    @Resource
    ProducerImpl producerImpl;
    @Resource
    PullBiz pullBiz;
    @Resource
    HourMapper hourMapper;
    @Resource
    NidController nidController;

    @GetMapping("/getdata")
    public int startPull(String startTime, String endTime) throws ParseException {
        //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findHourDates(startTime, endTime);
        System.out.println(datesList);
        List<Integer> nidList = NidListUtils.getNidList();
        Map<Integer, List<HourVo>> map = new HashMap<>();
        int sum = 0;
        int index = 0;
            for (String date : datesList) {
                List<HourVo> list = fetchDataImpl.selectByHourCondition(nidList, date);
                if (list.size() > 0) {
                    map = pullBiz.getHourMap(list);
                    for (int k : map.keySet()) {
                        index = index +1;
                        sum = sum + map.get(k).size();
                        logger.info("在第{}年的Hour数据,合计共{}条,合计{}个包", date, sum,index);
                        producerImpl.sendHourDBMsg(map.get(k));
                    }
                }
            }
        return sum;
    }
    @GetMapping("/getTestData")
    public int startTestPull(String startTime, String endTime) throws ParseException {
        //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findDayDates(startTime, endTime);
        System.out.println(datesList);
        List<Integer> nidList = nidController.getNidList();
        Map<Integer, List<HourVo>> map = new HashMap<>();
        int sum = 0;
        int index = 0;
        for (String date : datesList) {
            List<HourVo> list = hourMapper.selectTestByConditions(nidList,date);
            if (list.size() > 0) {
                map = pullBiz.getHourMap(list);
                for (int k : map.keySet()) {
                    index = index +1;
                    sum = sum + map.get(k).size();
                    logger.info("在第{}年的Hour数据,合计共{}条,合计{}个包", date, sum,index);
                    producerImpl.sendHourDBMsg(map.get(k));
                }
            }
        }
        return sum;
    }


}
