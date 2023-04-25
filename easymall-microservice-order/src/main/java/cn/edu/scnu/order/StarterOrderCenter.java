/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 9:50:55 上午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterOrderCenter {
    public static void main(String[] args) {
        SpringApplication.run(StarterOrderCenter.class, args);
    }
}
