package com.siti.wisdomhydrologic.config;

import com.sun.istack.internal.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by DC on 2019/6/12.
 *
 * @data ${DATA}-14:58
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);

        return connectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public AmqpTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(msgReturnCallback);
        rabbitTemplate.setConfirmCallback(msgConfirmCallback);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


    /**
     * i->replyCode
     * s->replyText
     * s1->exchange
     * s2->routingKey
     * **/
    //消息从交换器发送到队列失败时触发
    RabbitTemplate.ReturnCallback msgReturnCallback=new RabbitTemplate.ReturnCallback() {

        @Override
        public void returnedMessage(Message message, int i, String s, String s1, String s2) {
            System.out.println("消费者回调");
         //   logger.info("消息：{}，错误码：{}，失败原因：{}，交换器：{}，路由key：{}",message.getMessageProperties().getCorrelationId(),i,s,s1,s2);
        }
    };

    //消息发送到交换器时触发
    RabbitTemplate.ConfirmCallback msgConfirmCallback=new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(@Nullable CorrelationData correlationData, boolean b, @Nullable String s) {
            if(b){
                logger.info("消息{}发送exchange成功",correlationData.getId());
            }else{
                logger.info("消息发送到exchange失败，原因：{}",s);
            }
        }
    };


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConnectionFactory connectionFactory;
    /**
     * 消费者线程数 设置大点 大概率是能通知到的
     */
    @Value("${http.notify.concurrency:5}")
    int concurrency;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    public static final String WH_EXCHANGE = "exchange_wh";

    public static final String WH_HISTORY_EXCHANGE = "exchange_wh_history";

    public static final String QUEUE_REAL= "wh_real";

    public static final String QUEUE_EXCEPTION= "wh_exception";

    public static final String ROUTINGKEY_REAL_EXCEPTION = "routingKey_real_exception";

    public static final String ROUTINGKEY_REAL_DELAY = "routingKey_real_delay";

    public static final String QUEUE_DELAY= "wh_delay";

    public static final String QUEUE_DAY= "wh_day";

    public static final String QUEUE_HOUR= "wh_hour";

    public static final String QUEUE_TSDB= "wh_tsdb";

    public static final String HISTORY_QUEUE_DAY= "wh_history_day";

    public static final String HISTORY_QUEUE_HOUR= "wh_history_hour";

    public static final String HISTORY_QUEUE_TSDB= "wh_history_tsdb";

    public static final String HISTORY_ROUTINGKEY_HOUR = "history_routingKey_hour";

    public static final String HISTORY_ROUTINGKEY_TSDB = "history_routingKey_tsdb";

    public static final String HISTORY_ROUTINGKEY_DAY = "history_routingKey_day";

    public static final String ROUTINGKEY_REAL = "routingKey_real";

    public static final String ROUTINGKEY_HOUR = "routingKey_hour";

    public static final String ROUTINGKEY_TSDB = "routingKey_tsdb";

    public static final String ROUTINGKEY_DAY = "routingKey_day";
    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public TopicExchange defaultExchange() {
        return new TopicExchange(WH_EXCHANGE,true,false);
    }

    @Bean
    public TopicExchange hisExchange() {
        return new TopicExchange(WH_HISTORY_EXCHANGE,true,false);
    }

    @Bean
    public Queue queuDelay() {
        return new Queue(QUEUE_DELAY, true,false,false); //队列持久
    }

    @Bean
    public Queue queueException() {
        return new Queue(QUEUE_EXCEPTION, true); //队列持久
    }

    @Bean
    public Queue queuReal() {
        return QueueBuilder.durable(RabbitMQConfig.QUEUE_REAL)
                //以下是重点：当变成死信队列时，会转发至 路由为x-dead-letter-exchange及x-dead-letter-routing-key的队列中
                .withArgument("x-dead-letter-exchange", RabbitMQConfig.WH_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", RabbitMQConfig.ROUTINGKEY_REAL_DELAY)
                .withArgument("x-message-ttl", 5*60*1000)//1分钟 过期时间（单位：毫秒），当过期后 会变成死信队列，之后进行转发
                .build(); //队列持久
    }

    @Bean
    public Queue queueTSDB() {
        return new Queue(QUEUE_TSDB, true); //队列持久
    }

    @Bean
    public Queue queueHour() {
        return new Queue(QUEUE_HOUR, true); //队列持久
    }

    @Bean
    public Queue queueDay()
    {
        return new Queue(QUEUE_DAY, true); //队列持久
    }

    @Bean
    public Queue hisqueueDay()
    {
        return new Queue(HISTORY_QUEUE_DAY, true); //队列持久
    }

    @Bean
    public Queue hisqueueTSDB() {
        return new Queue(HISTORY_QUEUE_TSDB, true); //队列持久
    }

    @Bean
    public Queue hisqueueHour() {
        return new Queue(HISTORY_QUEUE_HOUR, true); //队列持久
    }

    @Bean
    public Binding bindingException(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding = BindingBuilder.bind(queueException()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY_REAL_EXCEPTION);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    @Bean
    public Binding bindingDelay(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding = BindingBuilder.bind(queuDelay()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY_REAL_DELAY);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    /**
     * 将实时队列绑定到topic交换机上
     * @return
     */
    @Bean
    public Binding bindingReal(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding = BindingBuilder.bind(queuReal()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY_REAL);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    /**
     * 将日队列绑定到topic交换机
     * @return
     */
    @Bean
    public Binding bindingDay(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding = BindingBuilder.bind(queueDay()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY_DAY);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    /**
     * 将TSDB队列绑定到topic交换机
     * @return
     */
    @Bean
    public Binding bindingTSDB(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding = BindingBuilder.bind(queueTSDB()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY_TSDB);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    /**
     * 将hour队列绑定到topic交换机
     * @return
     */
    @Bean
    public Binding bindingHour(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding = BindingBuilder.bind(queueHour()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY_HOUR);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    //历史数据队列
    @Bean
    public Binding bindinghisHour(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding =  BindingBuilder.bind(hisqueueHour()).to(defaultExchange()).with(RabbitMQConfig.HISTORY_ROUTINGKEY_HOUR);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    @Bean
    public Binding bindinghisDay(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding =  BindingBuilder.bind(hisqueueDay()).to(defaultExchange()).with(RabbitMQConfig.HISTORY_ROUTINGKEY_DAY);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    @Bean
    public Binding bindinghisTSDB(RabbitAdmin rabbitAdmin) {
        //binding_key为topic.#,模糊匹配
        Binding binding =  BindingBuilder.bind(hisqueueTSDB()).to(defaultExchange()).with(RabbitMQConfig.HISTORY_ROUTINGKEY_TSDB);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }



}
