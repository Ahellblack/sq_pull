package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.scheduTask.mapper.RealMapper;
import com.siti.wisdomhydrologic.util.NidListUtils;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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

        List<Integer> nidList = NidListUtils.getNidList();
        List<RealVo> list = realMapper.gethistory5MinData(startTime, endTime, nidList);
        Map<Integer, List<RealVo>> map = pullBiz.getRealMap(list);
        map.forEach((key, rlist) -> {
            producerImpl.sendRealMsg(rlist);
            logger.info("在{}到{}的时间内,获取5分钟内的real数据共{}条", startTime, endTime, rlist.size());
        });
    }


}
