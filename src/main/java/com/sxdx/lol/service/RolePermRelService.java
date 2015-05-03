package com.sxdx.lol.service;

import java.util.List;
import java.util.Set;

import com.sxdx.lol.entity.RolePermRelDO;


public interface RolePermRelService {

	/**
	 * 根据角色ID查找对应的权限集（一个角色有多种权限）
	 * @param roleId
	 * @return 权限列表
	 */
	Set<String> getPermissions(Integer roleId);
	
	/**
	 * 批量添加权限
	 * @param rolePermRelDOs
	 */
	void batchInsertRolePermRel(List<RolePermRelDO> rolePermRelDOs);
	
	void deleteRolePermRelByRoleId(Integer roleId);
}
