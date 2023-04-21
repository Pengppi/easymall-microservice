/**
 * @Author: Neo
 * @Date: 2023/03/31 星期五 23:27:51 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service;

import cn.edu.scnu.mapper.ProductMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

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
    public Product queryById(String prodId) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getProductId, prodId);
        return this.getOne(queryWrapper);
    }

    @Override
    public void productSave(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        this.save(product);
    }

    @Override
    public void productUpdate(Product product) {
        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Product::getProductName, product.getProductName());
        updateWrapper.set(Product::getProductCategory, product.getProductCategory());
        updateWrapper.set(Product::getProductNum, product.getProductNum());
        updateWrapper.set(Product::getProductPrice, product.getProductPrice());
        updateWrapper.set(Product::getProductDescription, product.getProductDescription());
        updateWrapper.set(Product::getProductImgurl, product.getProductImgurl());
        updateWrapper.eq(Product::getProductId, product.getProductId());
        this.update(updateWrapper);
    }
}

