package com.sxdx.lol.entity;

import java.io.Serializable;
import java.util.Date;

public class RolePermRelDO implements Serializable{
	
	private static final long serialVersionUID = -971818065984481747L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 角色ID
	 */
	private Integer roleId;
	
	/**
	 * 权限URL
	 */
	private String permUrl;
	
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getPermUrl() {
		return permUrl;
	}

	public void setPermUrl(String permUrl) {
		this.permUrl = permUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
