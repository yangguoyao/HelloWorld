package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 处理收货地址数据的业务层接口
 */
public interface IAddressService {

	/**
	 * 增加新的收货地址
	 * @param address 收货地址数据
	 * @param username 当前登录的用户名
	 * @throws InsertException 插入数据异常
	 */
	void addnew(Address address, String username) 
			throws InsertException;
	
	/**
	 * 根据id删除收货地址数据
	 * @param uid 当前登录的用户的id
	 * @param aid 将删除的收货地址数据的id
	 * @throws AddressNotFoundException 收货地址数据不存在的异常
	 * @throws AccessDeniedException 拒绝访问，可能因为权限不足，或数据归属有误
	 * @throws DeleteException 删除数据异常
	 * @throws UpdateException 更新数据异常
	 */
	void delete(Integer uid, Integer aid) 
			throws AddressNotFoundException, 
				AccessDeniedException, 
				DeleteException, UpdateException;
	
	/**
	 * 设置默认收货地址
	 * @param uid 当前登录的用户的id
	 * @param aid 将要设置为默认收货地址的数据的id
	 * @throws AddressNotFoundException 收货地址数据不存在的异常
	 * @throws AccessDeniedException 拒绝访问，数据归属有误
	 * @throws UpdateException 更新数据异常
	 */
	void setDefault(Integer uid, Integer aid) 
			throws AddressNotFoundException, 
				AccessDeniedException, UpdateException;

	/**
	 * 根据用户id查询收货地址列表
	 * @param uid 用户id
	 * @return 该用户的收货地址列表
	 */
	List<Address> getByUid(Integer uid);
	/**
	 * 根据aid查询地址信息
	 * @param aid aid
	 * @return 地址对象
	 */
	Address getByAid(Integer aid);
}




