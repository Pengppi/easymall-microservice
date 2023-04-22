/**
 * @Author: Neo
 * @Date: 2023/04/22 星期六 17:36:38 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class StarterConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(StarterConfigServer.class, args);
    }
}
