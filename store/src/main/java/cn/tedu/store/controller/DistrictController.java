package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController {

	@Autowired
	private IDistrictService districtService;
	
	@GetMapping("/")
	public ResponseResult<List<District>> 
		getByParent(@RequestParam("parent") String parent) {
		List<District> data
			= districtService.getByParent(parent);
		return new ResponseResult<>(SUCCESS, data);
	}
	
}









