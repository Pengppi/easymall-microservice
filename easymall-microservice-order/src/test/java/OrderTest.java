import cn.edu.scnu.order.StarterOrderCenter;
import cn.edu.scnu.order.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Neo
 * @Date: 2023/04/25 星期二 19:47:24 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

@SpringBootTest(classes = StarterOrderCenter.class)
public class OrderTest {
    @Autowired
    private OrderMapper orderMapper;


    @Test
    public void select() {

        orderMapper.selectList(null);
    }

    @Test
    void testInsert() {

    }
}
