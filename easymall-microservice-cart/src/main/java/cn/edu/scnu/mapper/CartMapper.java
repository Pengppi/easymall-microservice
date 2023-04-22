package cn.edu.scnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easymall.pojo.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    @Select("select * from t_cart where user_id = #{userId} and product_id = #{productId}")
    Cart queryOne(Cart cart);

    @Update("update t_cart set num = #{num} where user_id = #{userId} and product_id = #{productId}")
    void updateNum(Cart cart);

    @Delete("delete from t_cart where user_id = #{userId} and product_id = #{productId}")
    void deleteCart(Cart cart);
}
