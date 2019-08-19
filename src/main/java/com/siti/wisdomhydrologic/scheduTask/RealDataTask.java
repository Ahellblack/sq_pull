package com.siti.wisdomhydrologic.scheduTask;

import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.rabbitmq.service.impl.ProducerImpl;
import com.siti.wisdomhydrologic.scheduTask.mapper.RealMapper;
import com.siti.wisdomhydrologic.util.PullBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Scheduled(cron = "0 0/5 * * * ?")//0 2/5 * * * ?
    public void testSca() throws Exception {
        Date today = new Date();
        String date = getCloseDate("YYYY-MM-dd HH:mm:ss", today, 5);
        String date1 = getCloseDate("YYYY-MM-dd HH:mm:ss", today, 10);
        String date2 = getCloseDate("YYYY-MM-dd HH:mm:ss", today, 15);
        System.out.println(date+">>>"+date1+">>>"+date2);
        List<RealVo> list = realMapper.getLatest5MinData(date,date1,date2);
        Map<Integer, List<RealVo>> map = pullBiz.getRealMap(list);
        map.forEach((key, rlist) -> {
            producerImpl.sendRealMsg(list);
            logger.info("第{}个list,在{},{},{}时间,获取5分钟内的real数据共{}条", key,date,date1,date2,list.size());
        });
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
