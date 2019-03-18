package cn.tedu.store.service;

import cn.tedu.store.entity.Order;

public interface IOrderService {
	
	Order createOrder(Integer uid,String username,Integer aid,Integer [] cid);
}
