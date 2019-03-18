 package cn.tedu.store.service;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTestCase {

	@Autowired
	public ICartService service;
	
	@Test
	public void addToCart() {
		try {
			String username = "ROOT";
			Cart cart = new Cart();
			cart.setUid(1);
			cart.setGid(2L);
			cart.setNum(3);
			service.addToCart(username, cart);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 8;
		List<CartVO> list = service.getByUid(uid);
		System.err.println("BEGIN:");
		for (CartVO data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
	@Test
	public void addNum() {
		try {
			Integer uid = 8;
			String username = "系统管理员";
			Integer cid = 8;
			service.addNum(uid, username, cid);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void getByCids() {
		Integer[] cids = { 6, 7, 9 };
		List<CartVO> list = service.getByCids(cids);
		System.err.println("BEGIN:");
		for (CartVO data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
}






