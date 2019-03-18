package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTestCase {

	@Autowired
	public IAddressService service;
	
	@Test
	public void addnew() {
		try {
			Address address = new Address();
			address.setUid(10);
			address.setName("小刘同学");
			String username = "小森同学";
			service.addnew(address, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void delete() {
		try {
			Integer uid = 8;
			Integer aid = 6; 
			service.delete(uid, aid);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void setDefault() {
		try {
			Integer uid = 8;
			Integer aid = 700; 
			service.setDefault(uid, aid);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 8;
		List<Address> list = service.getByUid(uid);
		System.err.println("BEGIN:");
		for (Address data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
}










