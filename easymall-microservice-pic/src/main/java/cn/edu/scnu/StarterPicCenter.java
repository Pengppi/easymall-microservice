/**
 * @Author: Neo
 * @Date: 2023/04/10 星期一 19:47:28 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterPicCenter {
    public static void main(String[] args) {
        SpringApplication.run(StarterPicCenter.class, args);
    }
}
