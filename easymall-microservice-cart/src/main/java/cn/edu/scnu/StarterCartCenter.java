/**
 * @Author: Neo
 * @Date: 2023/04/22 星期六 19:07:10 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@MapperScan("cn.edu.scnu.mapper")
public class StarterCartCenter {
    public static void main(String[] args) {
        SpringApplication.run(StarterCartCenter.class, args);
    }
}
