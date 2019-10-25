package com.siti.wisdomhydrologic.rabbitmq.service.impl;

import com.siti.wisdomhydrologic.config.RabbitMQConfig;
import com.siti.wisdomhydrologic.datepull.vo.DayVo;
import com.siti.wisdomhydrologic.datepull.vo.HourVo;
import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.datepull.vo.TSDBVo;
import com.siti.wisdomhydrologic.rabbitmq.service.producer;
import com.siti.wisdomhydrologic.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DC on 2019/6/12.
 *
 * @data ${DATA}-15:08
 */
@Service
public class ProducerImpl implements producer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public ProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //生产者确认模式
    }

    /**
     * 实时day数据拉取
     */
    public void sendDayDBMsg(List<DayVo> list) {
        CorrelationData correlationId = new CorrelationData(new SnowFlake(1, 1).nextId()+"");
        //消息默认持久化
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列HISTORY_ROUTINGKEY_DAY
        rabbitTemplate.convertAndSend(RabbitMQConfig.WH_EXCHANGE, RabbitMQConfig.QUEUE_DAY, list, correlationId);
    }

    /**
     * 实时hour数据拉取
     */
    public void sendHourDBMsg(List<HourVo> list) {
        CorrelationData correlationId = new CorrelationData(new SnowFlake(1, 1).nextId()+"");
        //消息默认持久化
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列HISTORY_ROUTINGKEY_HOUR
        rabbitTemplate.convertAndSend(RabbitMQConfig.WH_EXCHANGE, RabbitMQConfig.QUEUE_HOUR, list, correlationId);
    }

    /**
     * 实时tsdb数据拉取
     */
    public void sendTSDBMsg(List<TSDBVo> list) {
        CorrelationData correlationId = new CorrelationData(new SnowFlake(1, 1).nextId()+"");

        //消息默认持久化
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列HISTORY_ROUTINGKEY_TSDB
        rabbitTemplate.convertAndSend(RabbitMQConfig.WH_EXCHANGE, RabbitMQConfig.QUEUE_TSDB, list, correlationId);
    }

    public void sendRTSQMsg(List<RealVo> real) {
        CorrelationData correlationId = new CorrelationData(new SnowFlake(1, 1).nextId()+"");
        //消息默认持久化
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMQConfig.WH_EXCHANGE, RabbitMQConfig.QUEUE_REAL, real, correlationId);
    }

}

