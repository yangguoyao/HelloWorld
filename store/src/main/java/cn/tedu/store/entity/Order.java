package cn.tedu.store.entity;

import java.util.Date;

public class Order extends BaseEntity{

	private static final long serialVersionUID = -4332236177444575687L;
	
	private Integer oid;//id
	private Integer uid;//归属哪个用户
	private String name;//收货人
	private String phone;//收货电话
	private String address ;//收货地址
	private Integer status ;//订单状态：0-未支付，1-已支付，2-已取消…………
	private Long price ;//商品总价
	private Date orderTime ;//下单时间
	private Date payTime ;//支付时间
	private String createdUser ;//创建执行人
	private Date createdTime ;//创建时间
	private String modifiedUser ;//修改执行人
	private Date modifiedTime ;//修改时间
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", uid=" + uid + ", name=" + name + ", phone=" + phone + ", address=" + address
				+ ", status=" + status + ", price=" + price + ", orderTime=" + orderTime + ", payTime=" + payTime
				+ ", createdUser=" + createdUser + ", createdTime=" + createdTime + ", modifiedUser=" + modifiedUser
				+ ", modifiedTime=" + modifiedTime + "]";
	}

	
	
}