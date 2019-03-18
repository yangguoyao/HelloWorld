package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层实现类
 */
@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private CartMapper cartMapper;

	@Override
	public void addToCart(String username, Cart cart) throws InsertException, UpdateException {
		// 根据uid和gid去查询数据：findByUidAndGid(cart.getUid(), cart.getGid())
		Integer uid = cart.getUid();
		Long gid = cart.getGid();
		Cart result = findByUidAndGid(uid, gid);
		
		// 判断查询结果是否为null
		Date now = new Date();
		if (result == null) {
			// 是：向参数cart中封装日志数据
			cart.setCreatedUser(username);
			cart.setCreatedTime(now);
			cart.setModifiedUser(username);
			cart.setModifiedTime(now);
			// 插入数据：insert(cart)
			insert(cart);
		} else {
			// 否：计算新的num值：num = cart.getNum() + result.getNum();
			Integer num = cart.getNum() + result.getNum();
			// 执行更新：updateNum(result.getCid(), num, username, now)
			updateNum(result.getCid(), num, username, now);
		}
	}
	
	@Override
	public void addNum(Integer uid, String username, Integer cid)
			throws CartNotFoundException, AccessDeniedException, UpdateException {
		// 根据参数cid查询数据：findByCid(cid)
		Cart result = findByCid(cid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：CartNotFoundException
			throw new CartNotFoundException(
				"增加商品数量错误！尝试访问的数据不存在！");
		}

		// 判断查询结果中的uid与当前登录的用户id(参数uid)是否不一致
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new CartNotFoundException(
				"增加商品数量错误！数据归属错误！");
		}

		// 暂不实现：判断商品的状态、库存等，即某商品是否可以存在于购物车中

		// 将查询结果中的商品数量加1
		Integer num = result.getNum() + 1;
		// 执行更新：updateNum(cid, num, modifiedUser, modifiedTime)
		Date now = new Date();
		updateNum(cid, num, username, now);
	}

	@Override
	public List<CartVO> getByUid(Integer uid) {
		return findByUid(uid);
	}

	@Override
	public List<CartVO> getByCids(Integer[] cids) {
		return findByCids(cids);
	}

	/**
	 * 插入购物车数据
	 * @param cart 购物车数据
	 */
	private void insert(Cart cart) {
		Integer rows = cartMapper.insert(cart);
		if (rows != 1) {
			throw new InsertException(
				"添加购物车数据出现未知错误！");
		}
	}

	/**
	 * 修改购物车数据中商品的数量 
	 * @param cid 购物车数据的id
	 * @param num 新的数量
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateNum(Integer cid, Integer num, String modifiedUser, Date modifiedTime) {
		Integer rows = cartMapper.updateNum(
				cid, num, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException(
				"修改购物车中商品数量出现未知错误！");
		}
	}

	/**
	 * 获取某用户在购物车中添加的指定商品的数据
	 * @param uid 用户的id
	 * @param gid 商品的id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	private Cart findByUidAndGid(Integer uid, Long gid) {
		return cartMapper.findByUidAndGid(uid, gid);
	}
	
	/**
	 * 根据购物车数据id获取购物车数据
	 * @param cid 购物车数据id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}
	
	/**
	 * 获取某用户的购物车数据列表
	 * @param uid 用户的id
	 * @return 用户的购物车数据列表
	 */
	private List<CartVO> findByUid(Integer uid) {
		return cartMapper.findByUid(uid);
	}

	/**
	 * 获取指定的某些id的购物车数据列表
	 * @param cids 指定的一系列购物车数据id
	 * @return 购物车数据列表
	 */
	private List<CartVO> findByCids(Integer[] cids) {
		return cartMapper.findByCids(cids);
	}

	
}





