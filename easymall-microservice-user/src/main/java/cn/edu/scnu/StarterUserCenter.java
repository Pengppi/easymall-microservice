/**
 * @Author: Neo
 * @Date: 2023/04/11 星期二 16:39:23 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterUserCenter {
    public static void main(String[] args) {
        SpringApplication.run(StarterUserCenter.class, args);
    }
}
