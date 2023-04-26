/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 23:51:59 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.seckill.service;

import cn.edu.scnu.seckill.mapper.SeckillMapper;
import cn.edu.scnu.seckill.mapper.SuccessMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.Seckill;
import com.easymall.pojo.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {
    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessMapper successMapper;

    @Override
    public List<Seckill> queryAll() {
        return seckillMapper.selectList(null);
    }

    @Override
    public Seckill queryOne(String seckillId) {
        LambdaQueryWrapper<Seckill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Seckill::getSeckillId, Long.parseLong(seckillId));
        return seckillMapper.selectOne(wrapper);
    }

    @Override
    public int updateNum(Long seckillId, Date nowTime) {
        return seckillMapper.updateNum(seckillId, nowTime);

    }

    @Override
    public void saveSuccess(Success success) {
        successMapper.insert(success);
    }

    @Override
    public List<Success> querySuccess(Long seckillId) {
        LambdaQueryWrapper<Success> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Success::getSeckillId, seckillId);
        wrapper.orderByDesc(Success::getCreateTime);
        return successMapper.selectList(wrapper);
    }
}
