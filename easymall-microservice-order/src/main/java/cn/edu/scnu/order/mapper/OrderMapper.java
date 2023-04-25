package cn.edu.scnu.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easymall.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
