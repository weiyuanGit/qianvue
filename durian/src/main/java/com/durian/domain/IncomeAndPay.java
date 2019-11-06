package com.durian.domain;

import java.util.Date;

public class IncomeAndPay {
	/**
	 * 收入支出表id
	 */
	public Long incomeId;
	/**
	 * 用户id
	 */
	public Long incomeUserId;
	/**
	 * 收入支出类型
	 */
	public Byte incomeType;
	/**
	 * 收入
	 */
	public Byte incomeWay;
	public Long sourceId;
	public Date createTime;
}
