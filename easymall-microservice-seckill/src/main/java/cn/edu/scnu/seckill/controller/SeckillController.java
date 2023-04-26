/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 23:50:48 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.seckill.controller;


import cn.edu.scnu.seckill.config.RabbitMqConfig;
import cn.edu.scnu.seckill.service.SeckillService;
import com.easymall.pojo.Seckill;
import com.easymall.pojo.Success;
import com.easymall.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill/manage")
@Slf4j
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/list")
    public List<Seckill> queryAll() {
        return seckillService.queryAll();
    }

    @RequestMapping("/detail")
    public Seckill queryOne(String seckillId) {
        return seckillService.queryOne(seckillId);
    }

    @GetMapping("/{seckillId}")
    public SysResult startSeckill(@PathVariable Long seckillId) {
        
        String userId = "100866" + RandomUtils.nextInt(10000, 99999);
        String msg = userId + "/" + seckillId;
        rabbitTemplate.convertAndSend(RabbitMqConfig.exName, RabbitMqConfig.routingKey, msg);
        return SysResult.ok();
    }

    @RequestMapping("/{seckillId}/userPhone")
    public List<Success> querySuccess(@PathVariable Long seckillId) {
        return seckillService.querySuccess(seckillId);
    }

}
