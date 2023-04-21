package cn.edu.scnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easymall.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Integer queryUserName(String userName);

    @Select("select * from t_user where user_name=#{userName} and user_password=#{userPassword}")
    User queryUserByNameAndPassword(User user);
}
