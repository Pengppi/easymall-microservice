package cn.edu.scnu.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easymall.pojo.Order;
import com.easymall.pojo.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    void saveOrder(Order order);

    void saveOrderItems(Order order);

    List<Order> queryMyOrder(String userId);

    List<OrderItem> queryOrderItems(String orderId);

}
