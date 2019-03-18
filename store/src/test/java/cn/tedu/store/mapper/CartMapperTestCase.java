 package cn.tedu.store.mapper;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTestCase {

	@Autowired
	public CartMapper mapper;
	
	@Test
	public void insert() {
		Cart cart = new Cart();
		cart.setUid(1);
		cart.setGid(2L);
		cart.setNum(3);
		Integer rows = mapper.insert(cart);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateNum() {
		Integer cid = 1;
		Integer num = 10;
		String modifiedUser = "Admin";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateNum(cid, num, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByUidAndGid() {
		Integer uid = 1;
		Long gid = 5L;
		Cart cart = mapper.findByUidAndGid(uid, gid);
		System.err.println(cart);
	}
	
	@Test
	public void findByCid() {
		Integer cid = 6;
		Cart cart = mapper.findByCid(cid);
		System.err.println(cart);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 8;
		List<CartVO> list = mapper.findByUid(uid);
		System.err.println("BEGIN:");
		for (CartVO data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
	@Test
	public void findByCids() {
		Integer[] cids = { 6, 7, 9 };
		List<CartVO> list = mapper.findByCids(cids);
		System.err.println("BEGIN:");
		for (CartVO data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
}






