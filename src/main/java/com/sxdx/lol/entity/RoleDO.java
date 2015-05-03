package com.sxdx.lol.entity;

import java.io.Serializable;
import java.util.Date;

public class RoleDO implements Serializable{
	
	private static final long serialVersionUID = -971818065984481747L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 角色名
	 */
	private String name;
	
	/**
	 * 是否是超级管理员
	 */
	private Integer isSuper;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
