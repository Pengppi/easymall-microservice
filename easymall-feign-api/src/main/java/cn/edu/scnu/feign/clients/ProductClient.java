package cn.edu.scnu.feign.clients;


import com.easymall.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("productservice")
public interface ProductClient {

    @GetMapping("/product/manage/item/{prodId}")
    Product queryById(@PathVariable String prodId);

}
