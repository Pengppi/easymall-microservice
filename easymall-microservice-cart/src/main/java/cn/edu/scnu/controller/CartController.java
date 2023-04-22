/**
 * @Author: Neo
 * @Date: 2023/04/22 星期六 19:08:42 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.controller;

import cn.edu.scnu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
}
