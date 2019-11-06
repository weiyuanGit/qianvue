package com.durian.domain;

import java.util.Date;

public class Goods {
	/**
	 * 商品id
	 */
	public Long goodsId;
	/**
	 * 发布者id
	 */
	public Long senderId;
	/**
	 * 商品名称
	 */
	public String goodsName;
	/**
	 * 商品类型
	 */
	public Byte goodsType;
	/**
	 * 商品分类id
	 */
	public Long goodsClassifyId;
	/**
	 * 库存
	 */
	public int stock;
	/**
	 * 商品价格
	 */
	public int goodsPrice;
	/**
	 * 商品平台币售价
	 */
	public int goodsCurrency;
	/**
	 * 商品描述
	 */
	public String goodsDescribe;
	/**
	 * 商品图片数据
	 */
	public String goodsData;
	/**
	 * 商品关键字
	 */
	public String keyword;
	/**
	 * 商品状态
	 */
	public Byte goodsStatus;
	/**
	 * 创建时间
	 */
	public Date createTime;

}
