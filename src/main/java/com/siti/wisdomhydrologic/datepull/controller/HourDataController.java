package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.mapper.HourMapper;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.HourVo;
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


    @GetMapping("/getdata")
    public void startPull(String startTime, String endTime) throws ParseException {
        //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findDayDates(startTime, endTime);
        System.out.println(datesList);
        List<Integer> nidList = NidListUtils.getNidList();
        Map<Integer, List<HourVo>> map = new HashMap<>();
        int sum = 0;
            for (String date : datesList) {
                List<HourVo> list = fetchDataImpl.selectByHourCondition(nidList, date);
                if (list.size() > 0) {
                    map = pullBiz.getHourMap(list);
                    for (int k : map.keySet()) {
                        sum = sum + map.get(k).size();
                        logger.info("在第{}年的Hour数据,合计共{}条", date, sum);
                        producerImpl.sendRealHourMsg(map.get(k));
                    }
                }
            }
    }
    @GetMapping("/getTestData")
    public void startTestPull(String startTime, String endTime) throws ParseException {
        //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findDayDates(startTime, endTime);
        System.out.println(datesList);
        List<Integer> nidList = NidListUtils.getNidList();
        Map<Integer, List<HourVo>> map = new HashMap<>();
        int sum = 0;
        for (String date : datesList) {
            List<HourVo> list = hourMapper.selectTestByConditions(nidList,date);
            if (list.size() > 0) {
                map = pullBiz.getHourMap(list);
                for (int k : map.keySet()) {
                    sum = sum + map.get(k).size();
                    logger.info("在第{}年的Hour数据,合计共{}条", date, sum);
                    producerImpl.sendRealHourMsg(map.get(k));
                }
            }
        }
    }

    @GetMapping("/getrealdata")
    public void startRealPull() throws ParseException {
        //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findMonthDates(Start_Time, End_Time);
        System.out.println(datesList);
        List<Integer> nidList = NidListUtils.getNidList();
        Map<Integer, List<HourVo>> map = new HashMap<>();
        int sum = 0;
        for (String date : datesList) {
            List<HourVo> list = fetchDataImpl.selectByHourCondition(nidList, date);
            if (list.size() > 0) {
                map = pullBiz.getHourMap(list);
                for (int k : map.keySet()) {
                    sum = sum + map.get(k).size();
                    logger.info("在{}的Hour数据,合计共{}条", date, sum);
                    producerImpl.sendRealHourMsg(map.get(k));
                }
            }
        }

    }


    /*@GetMapping("/getdata")
        public String startPull() {


        String local = "2018-04-01";
        int size = fetchDataImpl.selectDayCount(local.toString());

        if (size <= 0) {
            return "数据异常";
        }
        int count = pullBiz.getCount(size);
        Map<Integer, List<HourVo>> map = new HashMap<>();
        int index = 0;
        List<HourVo> list = fetchDataImpl.selectByHourCondition(local.toString(), size, index * MAX_SIZE, (index + 1) * MAX_SIZE);
        List<Element> elementList = elementService.getElement();
        List<Crunode> nodeList = nodeService.getCrunode();
        //定义list数据量
        int listSize = list.size();
        int eSize = elementList.size();
        int nSize = nodeList.size();
        //遍历list与element的nid作对比
        for (int i = 0; i < listSize; i++) {
            for (int j = 0; j < eSize; i++) {
                if (list.get(i).getSenId() == elementList.get(j).getNID()) {
                }
            }
        } map = pullBiz.getMap(list);
        for (int k : map.keySet()) {
            producerImpl.sendMsg(map.get(k));
        }
        String str = "数据发送结束 共有" + listSize + "条,共发出" + map.size() + "个List";
        return str;
    }*/
}
