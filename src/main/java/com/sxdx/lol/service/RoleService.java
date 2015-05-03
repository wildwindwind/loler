package com.sxdx.lol.service;

import java.util.List;
import java.util.Set;

import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.entity.RoleDO;


public interface RoleService {

	/**
	 * 根据ID查找角色
	 * @param id
	 * @return 角色
	 */
	RoleDO findById(Integer id);
	
	/**
	 * 根据用户ID获取角色全集
	 * @param userId
	 * @return 角色全集
	 */
	List<RoleDO> getRolesByUserId(Integer userId);
	
	/**
	 * 获取所有角色记录
	 * @return
	 */
	List<RoleDO> getAllRoles();
	
	/**
	 * 根据条件查找角色，分页
	 * @param condition
	 * @return
	 */
	PageBean<RoleDO> findUserByCond(Condition<RoleDO> condition);
	
	/**
	 * 保存角色
	 * @param role
	 * @return
	 */
	Integer save(RoleDO role);
	
	void update(RoleDO role);
	
	void delete(Integer id);
	
}
