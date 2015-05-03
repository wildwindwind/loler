package com.sxdx.lol.dao;

import java.util.List;

import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.entity.RoleDO;

public interface RoleDao {
	
	/**
	 * 根据ID查找角色
	 * @param id
	 * @return
	 */
	RoleDO findById(Integer id);
	
	/**
	 * 获取所有角色记录
	 * @return
	 */
	List<RoleDO> getAllRoles();
	
	PageBean<RoleDO> findUserByCond(Condition<RoleDO> condition);
	
	Integer save(RoleDO role);
	
	void update(RoleDO role);
	
	void delete(Integer id);
}
