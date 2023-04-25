package cn.edu.scnu.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easymall.pojo.Seckill;

import java.util.List;

public interface SeckillService extends IService<Seckill> {

    List<Seckill> queryAll();
}
