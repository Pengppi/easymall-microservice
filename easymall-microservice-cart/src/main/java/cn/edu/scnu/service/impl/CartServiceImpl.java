/**
 * @Author: Neo
 * @Date: 2023/04/22 星期六 19:09:34 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service.impl;

import cn.edu.scnu.feign.clients.ProductClient;
import cn.edu.scnu.mapper.CartMapper;
import cn.edu.scnu.service.CartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.Cart;
import com.easymall.pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductClient productClient;

    @Override
    public List<Cart> queryMyCart(String userId) {
        log.info("查询购物车信息，用户id为：{}", userId);
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        return cartMapper.selectList(queryWrapper);
    }

    @Override
    public void cartSave(Cart cart) {
        Cart exist = cartMapper.queryOne(cart);
        if (exist != null) {
            exist.setNum(exist.getNum() + cart.getNum());
            cartMapper.updateNum(exist);
        } else {
            Product product = productClient.queryById(cart.getProductId());
            cart.setProductPrice(product.getProductPrice());
            cart.setProductName(product.getProductName());
            cart.setProductImage(product.getProductImgurl());
            cartMapper.insert(cart);
        }
    }

    @Override
    public void updateNum(Cart cart) {
        cartMapper.updateNum(cart);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartMapper.deleteCart(cart);
    }


}
