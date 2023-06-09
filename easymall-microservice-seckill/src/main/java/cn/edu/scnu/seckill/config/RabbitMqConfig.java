/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 23:41:37 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.seckill.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String exName = "seckillEx.direct";
    public static final String qName = "seckillQueue";
    public static final String routingKey = "seckill";
}
