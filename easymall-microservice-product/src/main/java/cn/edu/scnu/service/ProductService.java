/**
 * @Author: Neo
 * @Date: 2023/03/31 星期五 23:16:58 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easymall.pojo.Product;
import com.easymall.vo.EasyUIResult;

public interface ProductService extends IService<Product> {

    EasyUIResult productPageQuery(Integer page, Integer rows);

    Product queryById(String prodId);

    void productSave(Product product);

    void productUpdate(Product product);
}
