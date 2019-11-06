package com.durian.domain;

import java.util.Date;

public class BuyOrder {
	/**
	 * 订单id
	 */
	public Long orderId;
	/**
	 * 订单类型
	 */
	public Byte orderType;
	/**
	 * 订单类型
	 */
	public Byte orderStatus;
	/**
	 * 买家id
	 */
	public String buyerId;
	/**
	 * 地址id
	 */
	public Long addressId;
	/**
	 * 商品id
	 */
	public Long goodsId;
	/**
	 * 卖家id
	 */
	public Long sellerId;
	/**
	 * 商品数量
	 */
	public int goodsNumber;
	/**
	 * 商品价格
	 */
	public int goodsPrice;
	/**
	 * 支付金额
	 */
	public int payment;
	/**
	 * 支付方式
	 */
	public String payWay;
	/**
	 * 创建订单时间
	 */
	public Date createTime;
	/**
	 * 支付时间
	 */
	public Date payTime;
	/**
	 * 发货时间
	 */
	public Date sendGoodsTime;
	/**
	 * 收货时间
	 */
	public Date receivingGoodsTime;
	/**
	 * 更改时间
	 */
	public Date updateTime;
}
