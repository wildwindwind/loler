package com.sxdx.lol.service;

import java.util.List;

import com.sxdx.lol.entity.UserRoleRelDO;


public interface UserRoleRelService {

	/**
	 * 根据用户ID查找对应的角色（一个用户有多种角色）
	 * @param userId
	 * @return 角色列表
	 */
	List<UserRoleRelDO> findByUserId(Integer userId);
	
	void batchInsertUserRoleRel(List<UserRoleRelDO> userRoleRelDOs);
	
}
