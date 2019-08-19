package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.mapper.DayMapper;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.DayVo;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DC on 2019/7/6.
 *
 * @data ${DATA}-21:31
 */
@RestController
@RequestMapping("/daydata")
public class DataPullController {
    public static final Logger logger = LoggerFactory.getLogger(DataPullController.class);
    public static final int MAX_SIZE = 10000;
    public static final String Start_Time = "2019";
    public static final String End_Time = "2019";

    @Resource
    FetchDataImpl fetchDataImpl;
    @Resource
    ProducerImpl producerImpl;
    @Resource
    PullBiz pullBiz;
    @Resource
    private DayMapper dayMapper;


    @GetMapping("/getdata")
    public void startPull() throws ParseException {
        //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findDates(Start_Time, End_Time);
        List<Integer> nidList = NidListUtils.getNidList();
        Map<Integer, List<DayVo>> map = new HashMap<>();
        int index = 0;
        int sum = 0;
        for (Integer nid : nidList) {
            for (String date : datesList) {
                List<DayVo> list = fetchDataImpl.selectByDayCondition(date, nid, index * MAX_SIZE, (index + 1) * MAX_SIZE);
                if (list.size() > 0) {
                    map = pullBiz.getMap(list);
                    for (int k : map.keySet()) {
                        sum = sum + map.get(k).size();
                        logger.info("第{}年的day数据,合计共{}条", date, sum);
                        producerImpl.sendRealDayMsg(map.get(k));
                    }
                }
            }
        }
    }

    @RequestMapping("/getRealDay")
    public void testSca() throws Exception {
        Date today = new Date();
        String date = DateOrTimeTrans.Date2TimeString(today);
        List<Integer> nidList = NidListUtils.getNidList();
        List<DayVo> dayVoList = dayMapper.selectRealDay(nidList, date);
        System.out.println(dayVoList);
        producerImpl.sendRealDayMsg(dayVoList);
        logger.info("在{}获得的daydb数据", date);
    }

        /*int size = fetchDataImpl.selectDayCount(Earlist_Time.toString());

        System.out.println("count == " + size);
        if (size <= 0) {
            return "数据异常";
        }
        int count = pullBiz.getCount(size);*/
       /* IntStream.range(0, count).forEach(i -> {*/

}
