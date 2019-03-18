package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;

/**
 * 处理用户数据的业务层接口
 */
public interface IUserService {

	/**
	 * 用户注册
	 * @param user 尝试注册的用户数据
	 * @throws UsernameDuplicateException 用户名被占用时的异常
	 * @throws InsertException 插入数据失败时的异常
	 */
	void reg(User user) 
			throws UsernameDuplicateException, 
				InsertException;
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户信息
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws PasswordNotMatchException 密码错误 
	 */
	User login(String username, String password) 
			throws UserNotFoundException, 
				PasswordNotMatchException;
	
	/**
	 * 修改用户的密码
	 * @param uid 当前登录的用户id
	 * @param username 当前登录的用户名
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws PasswordNotMatchException 验证密码失败
	 * @throws UpdateException 更新数据异常
	 */
	void changePassword(
		Integer uid, String username, 
		String oldPassword, String newPassword) 
			throws UserNotFoundException, 
				PasswordNotMatchException, 
					UpdateException;
	
	/**
	 * 更新个人资料
	 * @param user 个人资料数据
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changeInfo(User user) 
			throws UserNotFoundException, 
				UpdateException;
	
	/**
	 * 更新个人头像
	 * @param avatar 头像路径
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changeAvatar(Integer uid, String avatar) 
			throws UserNotFoundException, 
				UpdateException;
	
	/**
	 * 根据用户id查询用户数据
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User getByUid(Integer uid);
	
}





