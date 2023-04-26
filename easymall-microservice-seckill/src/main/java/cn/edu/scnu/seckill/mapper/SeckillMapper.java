package cn.edu.scnu.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easymall.pojo.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface SeckillMapper extends BaseMapper<Seckill> {

    int updateNum(@Param("seckillId") Long seckillId, @Param("nowTime") Date nowTime);
}
