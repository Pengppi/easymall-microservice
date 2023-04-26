/**
 * @Author: Neo
 * @Date: 2023/04/26 星期三 0:21:51 上午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.seckill.listener;

import cn.edu.scnu.seckill.config.RabbitMqConfig;
import cn.edu.scnu.seckill.service.SeckillService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easymall.pojo.Seckill;
import com.easymall.pojo.Success;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SeckillListener {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitMqConfig.qName),
            exchange = @Exchange(name = RabbitMqConfig.exName, type = ExchangeTypes.DIRECT),
            key = {RabbitMqConfig.routingKey}
    ))
    public void processMsgOld(String msg) {
        log.error("接收到消息：{}", msg);
        String[] msgs = msg.split("/");
        String userId = msgs[0];
        Long seckillId = Long.parseLong(msgs[1]);
        Date nowTime = new Date();

        if (!redisTemplate.hasKey(seckillId.toString())) {
            if (!loadRedisData(seckillId, nowTime)) {
                log.error("{}秒杀失败,数据库库存为0", userId);
                return;
            }
        }
        String rpop = redisTemplate.opsForList().rightPop(seckillId.toString());
        if (rpop == null) {
            log.error("{}秒杀失败,redis库存为0", userId);
            return;
        }
        int result = seckillService.updateNum(seckillId, nowTime);
        if (result == 0) {
            log.error("{}秒杀失败,库存为0", userId);
            return;
        }
        Success success = new Success();
        success.setCreateTime(nowTime);
        success.setSeckillId(seckillId);
        success.setUserId(userId);
        success.setState(1);
        try {
            seckillService.saveSuccess(success);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean loadRedisData(Long seckillId, Date nowTime) {
        LambdaQueryWrapper<Seckill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Seckill::getSeckillId, seckillId);
        wrapper.lt(Seckill::getStartTime, nowTime);
        wrapper.gt(Seckill::getEndTime, nowTime);
        wrapper.gt(Seckill::getNumber, 0);
        Seckill product = seckillService.getOne(wrapper);
        if (product == null) {
            return false;
        }
        log.error("装载redis数据");
        int size = product.getNumber();
        redisTemplate.opsForList().leftPushAll(seckillId.toString(), new ArrayList<>(Collections.nCopies(size, "")));
        redisTemplate.expire(seckillId.toString(), 5L, TimeUnit.MINUTES);
        return true;
    }


    //@RabbitListener(bindings = @QueueBinding(
    //        value = @Queue(name = RabbitMqConfig.qName),
    //        exchange = @Exchange(name = RabbitMqConfig.exName, type = ExchangeTypes.DIRECT),
    //        key = {RabbitMqConfig.routingKey}
    //))
    //public void processMsgOld(String msg) {
    //    log.error("接收到消息：{}", msg);
    //    String[] msgs = msg.split("/");
    //    String userId = msgs[0];
    //    Long seckillId = Long.parseLong(msgs[1]);
    //    Date nowTime = new Date();
    //    int result = seckillService.updateNum(seckillId, nowTime);
    //    if (result == 0) {
    //        log.error("秒杀失败");
    //        return;
    //    }
    //    Success success = new Success();
    //    success.setCreateTime(nowTime);
    //    success.setSeckillId(seckillId);
    //    success.setUserId(userId);
    //    success.setState(1);
    //    seckillService.saveSuccess(success);
    //}
}
