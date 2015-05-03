package com.sxdx.lol.common;

import java.util.Date;

/**
 * 查询条件封装类
 * @author wildwind
 * 
 */
public class Condition<T> {
	
	private Integer id; 
	
	private String name;
	
	private Integer isSuper;
	
	private String userName;
	
	private String realName;
	
	private Integer isToBeOnDuty;
	
	private Integer accountStatus;
	
	private PageBean<T> pageBean;
	
	private Date startTime;
	
	private Date endTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIsToBeOnDuty() {
		return isToBeOnDuty;
	}

	public void setIsToBeOnDuty(Integer isToBeOnDuty) {
		this.isToBeOnDuty = isToBeOnDuty;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public PageBean<T> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<T> pageBean) {
		this.pageBean = pageBean;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Integer isSuper) {
		this.isSuper = isSuper;
	}
	
}
