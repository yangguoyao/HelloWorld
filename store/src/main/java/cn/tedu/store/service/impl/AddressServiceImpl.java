package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 处理收货地址数据的业务层实现类
 */
@Service
public class AddressServiceImpl implements IAddressService {
	
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;

	@Override 
	public void addnew(Address address, String username) throws InsertException {
		// 查询用户的收货地址的数量：countByUid(Integer uid)，参数值来自address.getUid();
		Integer count = countByUid(address.getUid());
		// 判断数量是否为0
		// 是：当前将增加第1条收货地址，则：address.setIsDefault(1)
		// 否：当前增加的不是第1条收货地址，则：address.setIsDefault(0)
		address.setIsDefault(count == 0 ? 1 : 0);

		// 处理district
		String district = getDistrict(
				address.getProvince(), address.getCity(), address.getArea());
		address.setDistrict(district);

		// 4项日志：时间是直接创建对象得到，用户名使用参数username
		Date now = new Date();
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);

		// 执行增加：insert(Address address);
		insert(address);
	}
	
	@Override
	@Transactional
	public void delete(Integer uid, Integer aid)
			throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException {
		// 根据aid查询即将删除的数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException(
				"删除收货地址失败！尝试访问的数据不存在！");
		}

		// 检查数据归属是否不正确
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessDeniedException(
				"删除收货地址失败！数据归属错误！");
		}

		// 执行删除
		deleteByAid(aid);

		// 检查此前的查询结果中的isDefault是否为0
		if (result.getIsDefault().equals(0)) {
			return;
		}

		// 获取当前用户的收货地址数据的数量
		Integer count = countByUid(uid);
		// 判断数量是否为0
		if (count.equals(0)) {
			return;
		}

		// 获取当前用户最后修改的收货地址数据
		Address lastModifed = findLastModified(uid);
		// 将全部数据设置为非默认
		updateNonDefault(uid);
		// 将最后修改的数据设置为默认。
		updateDefault(lastModifed.getAid());
	}

	@Override
	@Transactional
	public void setDefault(Integer uid, Integer aid)
			throws AddressNotFoundException, AccessDeniedException, UpdateException {
		// 根据aid查询数据
		Address result = findByAid(aid);
		// 判断数据是否为null
		if (result == null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException(
				"设置默认收货地址失败！尝试访问的数据不存在！");
		}

		// 判断参数uid与查询结果中的uid是否不一致
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessDeniedException(
				"设置默认收货地址失败！数据归属错误！");
		}

		// 全部设置为非默认
		updateNonDefault(uid);

		// 把指定的设置为默认
		updateDefault(aid);
	}

	@Override
	public List<Address> getByUid(Integer uid) {
		return findByUid(uid);
	}
	
	@Override
	public Address getByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}

	
	/**
	 * 增加收货地址数据
	 * @param address 收货地址数据
	 */
	private void insert(Address address) {
		Integer rows = addressMapper.insert(address);
		if (rows != 1) {
			throw new InsertException(
				"增加收货地址数据时出现未知错误！");
		}
	}
	
	/**
	 * 根据id删除收货地址数据
	 * @param aid 将删除的收货地址数据的id
	 * @return 受影响的行数
	 */
	private void deleteByAid(Integer aid) {
		Integer rows = addressMapper.deleteByAid(aid);
		if (rows != 1) {
			throw new DeleteException(
				"删除收货地址时出现未知错误！");
		}
	}
	
	/**
	 * 将指定用户的所有收货地址设置为非默认
	 * @param uid 用户的id
	 */
	private void updateNonDefault(Integer uid) {
		Integer rows = addressMapper.updateNonDefault(uid);
		if (rows < 1) {
			throw new UpdateException(
				"设置默认收货地址时出现未知错误！");
		}
	}
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 收货地址id
	 */
	private void updateDefault(Integer aid) {
		Integer rows = addressMapper.updateDefault(aid);
		if (rows != 1) {
			throw new UpdateException(
				"设置默认收货地址时出现未知错误！");
		}
	}

	/**
	 * 统计指定用户的收货地址数据的数量
	 * @param uid 用户的id
	 * @return 用户的收货地址数据的数量
	 */
	private Integer countByUid(Integer uid) {
		return addressMapper.countByUid(uid);
	}
	
	/**
	 * 根据用户id查询收货地址列表
	 * @param uid 用户id
	 * @return 该用户的收货地址列表
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}
	
	/**
	 * 根据收货地址id查询匹配的数据
	 * @param aid 收货地址id
	 * @return 匹配的数据，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}
	
	/**
	 * 获取某用户最后修改的收货地址数据
	 * @param uid 用户的id
	 * @return 该用户最后修改的收货地址数据
	 */
	private Address findLastModified(Integer uid) {
		return addressMapper.findLastModified(uid);
	}
	
	/**
	 * 根据省、市、区的代号，获取名称
	 * @param province 省的代号
	 * @param city 市的代号
	 * @param area 区的代号
	 * @return 省、市、区的代号对应的名称
	 */
	private String getDistrict(
			String province, String city, String area) {
		District p = districtService.getByCode(province);
		District c = districtService.getByCode(city);
		District a = districtService.getByCode(area);
		String provinceName = p == null ? "NULL" : p.getName();
		String cityName = c == null ? "NULL" : c.getName();
		String areaName = a == null ? "NULL" : a.getName();
		StringBuffer result = new StringBuffer();
		result.append(provinceName);
		result.append(",");
		result.append(cityName);
		result.append(",");
		result.append(areaName);
		return result.toString();
	}




}



