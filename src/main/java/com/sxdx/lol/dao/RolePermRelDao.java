package com.sxdx.lol.dao;

import java.util.List;

import com.sxdx.lol.entity.RolePermRelDO;


public interface RolePermRelDao {

	/**
	 * 根据角色ID查找对应的权限集（一个角色有多种权限）
	 * @param roleId
	 * @return 权限列表
	 */
	List<RolePermRelDO> findByRoleId(Integer roleId);
	
	/**
	 * 批量添加权限
	 * @param rolePermRelDOs
	 */
	void batchInsertRolePermRel(List<RolePermRelDO> rolePermRelDOs);
	
	void deleteRolePermRelByRoleId(Integer roldId);
}
