/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 10:06:39 上午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.order.service;

import cn.edu.scnu.order.mapper.OrderItemMapper;
import cn.edu.scnu.order.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.Order;
import com.easymall.pojo.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper itemMapper;

    @Override
    public void saveOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderTime(new Date());
        if (order.getOrderPaystate() == null) {
            order.setOrderPaystate(0);
        }
        orderMapper.saveOrder(order);
        orderMapper.saveOrderItems(order);
    }

    @Override
    public List<Order> queryMyOrder(String userId) {
        return orderMapper.queryMyOrder(userId);
    }

    @Override
    public void deleteOrder(String orderId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<OrderItem> anotherWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderId, orderId);
        anotherWrapper.eq(OrderItem::getOrderId, orderId);
        orderMapper.delete(queryWrapper);
        itemMapper.delete(anotherWrapper);
    }
}
