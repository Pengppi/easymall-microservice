/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 23:50:48 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.seckill.controller;


import cn.edu.scnu.seckill.service.SeckillService;
import com.easymall.pojo.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill/manage")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @RequestMapping("/list")
    public List<Seckill> queryAll() {
        return seckillService.queryAll();
    }

}
