/*
package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.entity.DayEntity;
import com.siti.wisdomhydrologic.datepull.entity.RTSQ;
import com.siti.wisdomhydrologic.datepull.service.impl.FetchDataImpl;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

*/
/**
 * Created by dell on 2019/7/11.
 *//*


@RestController
@RequestMapping("/rtsqdata")
public class RTSQController {

    public static final Logger logger = LoggerFactory.getLogger(DataPullController.class);
    public static final int MAX_SIZE = 10000;
    @Resource
    FetchDataImpl fetchDataImpl;
    @Resource
    ProducerImpl producerImpl;
    @Resource
    PullBiz pullBiz;

    @GetMapping("/getdata")
    public String hourData() {
        LocalDate local = LocalDate.parse("2018-09-13 08:00:00");
        int size = fetchDataImpl.selectRTSQCount(local.toString());
        if (size <= 0) {
            return "数据为空";
        }
        //List<Entity>  json
              int count = pullBiz.getCount(size);
        IntStream.range(0, count).forEach(i -> {
            int index = i;
            List<RTSQ> list = fetchDataImpl.selectByRTSQCondition(local.toString(), size, index * MAX_SIZE, (index + 1) * MAX_SIZE);
            list.forEach(e -> producerImpl.sendRTSQMsg(e));
        });

        return "数据传输完毕";


    }
}
*/
