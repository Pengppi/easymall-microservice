/**
 * @Author: Neo
 * @Date: 2023/03/31 星期五 23:53:04 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StaterGateway {
    public static void main(String[] args) {
        SpringApplication.run(StaterGateway.class, args);
    }
}
