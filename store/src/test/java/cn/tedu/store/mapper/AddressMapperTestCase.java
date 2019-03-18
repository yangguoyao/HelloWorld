 package cn.tedu.store.mapper;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTestCase {

	@Autowired
	public AddressMapper mapper;
	
	@Test
	public void insert() {
		Address address = new Address();
		address.setUid(8);
		address.setName("小李同学");
		Integer rows = mapper.insert(address);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void deleteByAid() {
		Integer aid = 8;
		Integer rows = mapper.deleteByAid(aid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateNonDefault() {
		Integer uid = 8;
		Integer rows = mapper.updateNonDefault(uid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateDefault() {
		Integer aid = 10;
		Integer rows = mapper.updateDefault(aid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void countByUid() {
		Integer uid = 8;
		Integer count = mapper.countByUid(uid);
		System.err.println("count=" + count);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 8;
		List<Address> list = mapper.findByUid(uid);
		System.err.println("BEGIN:");
		for (Address data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
	@Test
	public void findByAid() {
		Integer aid = 9;
		Address data = mapper.findByAid(aid);
		System.err.println(data);
	}
	
	@Test
	public void findLastModified() {
		Integer uid = 8;
		Address data = mapper.findLastModified(uid);
		System.err.println(data);
	}
	
}






