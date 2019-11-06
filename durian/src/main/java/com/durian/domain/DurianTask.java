package com.durian.domain;

import java.util.Date;

public class DurianTask {
	/**
	 * 任务id
	 */
	public Long taskId;
	/**
	 * 任务类型
	 */
	public Long taskType;
	/**
	 * 接受者用户id
	 */
	public Long pickUpUserId;
	/**
	 * 发布者用户id
	 */
	public Long publishUserId;
	/**
	 * 任务状态
	 */
	public Byte taskStatus;
	/**
	 * 任务名称
	 */
	public String taskName;
	/**
	 * 表格/文书类别
	 */
	public String docType;
	/**
	 * 任务接收者所需资质
	 */
	public Long qualifications;
	/**
	 * 任务描述
	 */
	public String taskDescribe;
	/**
	 * 其他说明
	 */
	public String otherDescribe;
	/**
	 * 申请人数
	 */
	public int applyNumber;
	/**
	 * 主申请人国籍
	 */
	public String applicantNationality;
	/**
	 * 主申请人年龄
	 */
	public int applicantAge;
	/**
	 * 低于18岁副申请人年龄
	 */
	public int  viceApplicantAge;
	/**
	 * 页数
	 */
	public int pageNumber;
	/**
	 * 内容
	 */
	public String Taskcontent;
	/**
	 * 上传文件地址
	 */
	public String fileData;
	/**
	 * 图片地址
	 */
	public String imgData;
	/**
	 * 任务预算金额
	 */
	public String taskBudget;
	/**
	 * 付款时间
	 */
	public Date payTime;
	/**
	 * 付款金额
	 */
	public int payPrice;
	/**
	 * 完成时间
	 */
	public Date finishDate;
	/**
	 * 创建时间
	 */
	public Date taskCreateTime;
}
