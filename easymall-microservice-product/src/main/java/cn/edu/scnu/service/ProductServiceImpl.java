/**
 * @Author: Neo
 * @Date: 2023/03/31 星期五 23:27:51 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service;

import cn.edu.scnu.mapper.ProductMapper;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.Product;
import com.easymall.utils.PrefixKey;
import com.easymall.vo.EasyUIResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public EasyUIResult productPageQuery(Integer page, Integer rows) {
        EasyUIResult result = new EasyUIResult();
        Page<Product> productPage = new Page<>((long) page, (long) rows);
        this.page(productPage);
        result.setTotal((int) productPage.getTotal());
        result.setRows(productPage.getRecords());
        return result;
    }

    @Override
    public Product queryById(String productId) {
        String productKey = PrefixKey.PRODUCT_QUERY + productId;
        try {
            if (!redisTemplate.hasKey(productKey + "_lock") && redisTemplate.hasKey(productKey)) {
                String productJson = redisTemplate.opsForValue().get(productKey);
                log.info("从redis中获取数据");
                return JSON.parseObject(productJson, Product.class);
            }
            LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Product::getProductId, productId);
            Product product = this.getOne(queryWrapper);
            //缓存到redis
            log.info("从数据库中获取数据");
            redisTemplate.opsForValue().set(productKey, JSON.toJSONString(product), 1L, TimeUnit.HOURS);
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void productSave(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        this.save(product);
    }

    @Override
    public void productUpdate(Product product) {
        //删除redis缓存
        String productKey = PrefixKey.PRODUCT_QUERY + product.getProductId();
        //加锁
        String productLock = productKey + "_lock";
        Long leftTime = redisTemplate.getExpire(productKey, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(productLock, "lock", leftTime, TimeUnit.SECONDS);
        redisTemplate.delete(productKey);
        //更新数据库
        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Product::getProductName, product.getProductName());
        updateWrapper.set(Product::getProductCategory, product.getProductCategory());
        updateWrapper.set(Product::getProductNum, product.getProductNum());
        updateWrapper.set(Product::getProductPrice, product.getProductPrice());
        updateWrapper.set(Product::getProductDescription, product.getProductDescription());
        updateWrapper.set(Product::getProductImgurl, product.getProductImgurl());
        updateWrapper.eq(Product::getProductId, product.getProductId());
        this.update(updateWrapper);
        //解锁
        redisTemplate.delete(productLock);
    }
}

