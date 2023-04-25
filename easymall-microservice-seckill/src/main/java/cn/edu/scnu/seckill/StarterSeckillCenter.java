/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 23:39:16 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterSeckillCenter {
    public static void main(String[] args) {
        SpringApplication.run(StarterSeckillCenter.class, args);
    }
}
