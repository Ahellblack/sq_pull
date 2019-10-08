package com.siti.wisdomhydrologic.scheduTask;

import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.scheduTask.mapper.RealMapper;
import com.siti.wisdomhydrologic.util.DateTransform;
import com.siti.wisdomhydrologic.util.NidListUtils;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2019/7/24.
 */

@Component
public class RealDataTask {
    private Logger logger = LoggerFactory.getLogger(RealDataTask.class);

    @Resource
    private RealMapper realMapper;
    @Resource
    ProducerImpl producerImpl;
    @Resource
    PullBiz pullBiz;

    @Scheduled(cron = "0 2/5 * * * ?")//0 2/5 * * * ?
    public void testSca() throws Exception {
        Date today = new Date();
        //获取前一个整5分数据
        String dateStart = getCloseDate("yyyy-MM-dd HH:mm:ss", today, 5);/*
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateTransform.String2Date(dateStart, "yyyy-MM-dd HH:mm:ss"));
        //往前推5分钟
        cal.add(Calendar.MINUTE, -5);
        String dateEnd = DateTransform.Date2String(cal.getTime(), "yyyy-MM-dd HH:mm:ss");*/
        List<Integer> nidList = NidListUtils.getNidList();
        //获取276个传感器在这个时间前5分钟的数据
        List<RealVo> list = realMapper.getLatestDataNew(nidList);
        if (list.size() > 0) {
            Map<Integer, List<RealVo>> map = pullBiz.getRealMap(list);
            map.forEach((key, rlist) -> {
                //producerImpl.sendRealMsg(list);
                    logger.info("在{}时间,获取25分钟前的real数据共{}条", dateStart, list.size());
            });
        }
    }

    /**
     * 取当前日期的年月日
     *
     * @return
     * @throws ParseException
     */
    public static Date getMinDate(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = sdf.parse(sdf.format(date));
        return newDate;
    }

    /**
     * 获取最近的整5分时间点real表数据
     *
     * @Param dateFormat dateFormat的格式 如 YYYY-MM-dd
     * @Param date 当前时间
     * @Param min 相隔时间
     */
    public static String getCloseDate(String dateFormat, Date date, long min) throws Exception {
        long dateTime = date.getTime();
        long needTime = 0;
        if (min >= 8 * 60) {
            return new SimpleDateFormat(dateFormat).format(getMinDate(date));
        } else {
            needTime = dateTime - dateTime % (min * 60L * 1000L);
        }
        return new SimpleDateFormat(dateFormat).format(new Date(needTime));
    }


}
