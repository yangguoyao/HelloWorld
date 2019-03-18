package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTestCase {

	@Autowired
	public IDistrictService service;
	
	@Test
	public void getByParent() {
		String parent = "86";
		List<District> list = service.getByParent(parent);
		System.err.println("BEGIN:");
		for (District data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
	@Test
	public void getByCode() {
		String code = "330102";
		District data = service.getByCode(code);
		System.err.println(data);
	}
	
}






