/**
 * @Author: Neo
 * @Date: 2023/04/22 星期六 19:09:34 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.service.impl;

import cn.edu.scnu.mapper.CartMapper;
import cn.edu.scnu.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;
}
