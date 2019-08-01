package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.service.ElementService;
import com.siti.wisdomhydrologic.datepull.service.NodeService;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.util.DatesUtils;
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
 * Created by dell on 2019/7/11.
 */

@RestController
@RequestMapping("/hourdata")
public class HourDataController {

    public static final Logger logger = LoggerFactory.getLogger(DataPullController.class);
    public static final int MAX_SIZE = 10000;
    public static final String Earliest_Time = "2002-06-06";
    @Resource
    FetchDataImpl fetchDataImpl;
    @Resource
    ProducerImpl producerImpl;
    @Resource
    PullBiz pullBiz;
    @Resource
    private ElementService elementService;
    @Resource
    private NodeService nodeService;


    @GetMapping("/getdata")
    public void startPull() throws ParseException {
        //获取最新日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        DatesUtils datesUtils = new DatesUtils();
        //得到一个从数据存在的最早日期到当前日期的list
        List<String> datesList = datesUtils.findDates(Earliest_Time, simpleDateFormat.format(new Date()));
        Map<Integer, List<DayVo>> map = new HashMap<>();
        int index = 0;
        for (String date :datesList){
            List<DayVo> list = fetchDataImpl.selectByHourCondition(date, MAX_SIZE, index * MAX_SIZE, (index + 1) * MAX_SIZE);
            if(list.size()<0){
                System.out.println("数据异常");
                break;
            }else{
                System.out.println(list);
                map = pullBiz.getMap(list);
                for (int k : map.keySet()) {
                    producerImpl.sendHourMsg(map.get(k));
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
        Map<Integer, List<DayVo>> map = new HashMap<>();
        int index = 0;
        List<DayVo> list = fetchDataImpl.selectByHourCondition(local.toString(), size, index * MAX_SIZE, (index + 1) * MAX_SIZE);
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
