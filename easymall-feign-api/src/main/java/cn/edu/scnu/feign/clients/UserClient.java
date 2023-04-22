package cn.edu.scnu.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "userservice")
public interface UserClient {

}
