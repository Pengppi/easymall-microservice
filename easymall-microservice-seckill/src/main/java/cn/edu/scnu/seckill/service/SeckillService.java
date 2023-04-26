package cn.edu.scnu.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easymall.pojo.Seckill;
import com.easymall.pojo.Success;

import java.util.Date;
import java.util.List;

public interface SeckillService extends IService<Seckill> {

    List<Seckill> queryAll();

    Seckill queryOne(String seckillId);

    int updateNum(Long seckillId, Date nowTime);

    void saveSuccess(Success success);

    List<Success> querySuccess(Long seckillId);
}
