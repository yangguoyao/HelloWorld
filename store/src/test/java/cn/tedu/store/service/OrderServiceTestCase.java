package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTestCase {

	@Autowired
	public IOrderService service;
	
	@Test
	public void getByParent() {
		Order order = new Order();
		OrderItem item = new OrderItem();

		String username = "huangwenwu";
		Integer uid = 1;
		Integer aid = 4;
		Integer[] cid = {1,2};
		System.err.println(order);
		service.createOrder(uid, username, aid, cid);
		System.err.println(order);
	}
	
	
}






