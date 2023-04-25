package cn.edu.scnu.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easymall.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
