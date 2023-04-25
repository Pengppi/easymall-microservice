/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 23:51:59 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.seckill.service;

import cn.edu.scnu.seckill.mapper.SeckillMapper;
import cn.edu.scnu.seckill.mapper.SuccessMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.pojo.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
