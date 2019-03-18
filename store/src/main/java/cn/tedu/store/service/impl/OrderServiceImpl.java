package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;
@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IAddressService addressService;
	
	public Order createOrder(Integer uid, String username, Integer aid, Integer[] cids) {
				// 创建当前时间对象now
				Date now = new Date();
				// 调用cartService方法根据cids获取数据：List<CartVO> getByCids(Integer[] cids);
				// 遍历计算商品总价
				List<CartVO> carts = cartService.getByCids(cids);
				Long prices = 0L;
				for (CartVO cart : carts) {
					Integer num = cart.getNum();
					Long price = cart.getPrice();
					prices += price*num;
				}
				Order order = new Order();
				order.setPrice(prices);
				// 创建订单数据对象：new Order()
				// 封装订单数据的属性：uid
				order.setUid(uid);
				// 调用addressService获取收货地址数据：getByAid(Integer aid)
				Address address = addressService.getByAid(aid);
				if(address == null) {
					throw new AddressNotFoundException("地址不存在！");
				}
				// 封装订单数据的属性：name, phone, address
				order.setAddress(address.getDistrict()+" "+address.getAddress());
				order.setName(address.getName());
				order.setPhone(address.getPhone());
				// 封装订单数据的属性：status:0
				order.setStatus(0);
				// 封装订单数据的属性：price:null
				// 封装订单数据的属性：orderTime:now
				order.setOrderTime(now);
				// 封装订单数据的属性：payTime:null
				order.setPayTime(null);
				// 封装4项日志属性
				order.setCreatedTime(now);
				order.setCreatedUser(username);
				order.setModifiedUser(username);
				order.setModifiedTime(now);
				// 插入订单数据：insertOrder(Order order)
				insertOrder(order);
				// 遍历以上查询结果
				OrderItem orderItem = null;
				for (CartVO cart : carts) {
					orderItem = new OrderItem();
					orderItem.setOid(order.getOid());
					orderItem.setGid(cart.getGid());
					orderItem.setTitle(cart.getTitle());
					orderItem.setNum(cart.getNum());
					orderItem.setPrice(cart.getPrice());
					orderItem.setImage(cart.getImage());
				}
				orderItem.setCreatedTime(now);
				orderItem.setCreatedUser(username);
				orderItem.setModifiedUser(username);
				orderItem.setModifiedTime(now);
				// -- 创建订单商品数据对象：new OrderItem()
				// -- 封装订单商品数据的属性：oid
				// -- 封装订单商品数据的属性：gid,title,image,price,num
				// -- 封装4项日志属性
				// -- 插入订单商品数据：insertOrderItem(OrderItem orderItem)
				insertItem(orderItem);
		return order;
	}

	
	private void insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if(rows !=1) {
			throw new InsertException("出现未知异常【1】");
		}
	}
	
	private void insertItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertItem(orderItem);
		if(rows !=1) {
			throw new InsertException("出现未知异常【2】");
		}
	}
}
