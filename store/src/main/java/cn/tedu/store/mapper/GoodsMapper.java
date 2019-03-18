package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Goods;

/**
 * 处理商品数据的持久层接口
 */
public interface GoodsMapper {

	/**
	 * 获取热销商品列表
	 * @return 热销商品列表
	 */
	List<Goods> findHotGoods();
	
	/**
	 * 根据id查询商品详情
	 * @param id 商品的id
	 * @return 匹配的商品的详情，如果没有匹配的数据，则返回null
	 */
	Goods findById(Long id);
	
}






