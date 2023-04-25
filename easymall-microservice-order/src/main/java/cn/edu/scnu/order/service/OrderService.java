package cn.edu.scnu.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easymall.pojo.Order;

import java.util.List;

public interface OrderService extends IService<Order> {
    void saveOrder(Order order);

    List<Order> queryMyOrder(String userId);

    void deleteOrder(String orderId);
}
