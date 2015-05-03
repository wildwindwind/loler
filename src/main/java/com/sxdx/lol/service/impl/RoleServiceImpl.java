package com.sxdx.lol.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.dao.RoleDao;
import com.sxdx.lol.dao.UserRoleRelDao;
import com.sxdx.lol.entity.RoleDO;
import com.sxdx.lol.entity.UserRoleRelDO;
import com.sxdx.lol.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRoleRelDao userRoleRelDao;
	
	
	public RoleDO findById(Integer id) {
		return roleDao.findById(id);
	}

	public List<RoleDO> getRolesByUserId(Integer userId) {
		Assert.notNull(userId);
		List<UserRoleRelDO> userRoleRelDOs =  userRoleRelDao.findByUserId(userId);
		List<RoleDO> roles = new ArrayList<RoleDO>();
		for(UserRoleRelDO userRoleRelDO : userRoleRelDOs){
			RoleDO role = findById(userRoleRelDO.getRoleId());
			roles.add(role);
		}
		return roles;
	}

	public List<RoleDO> getAllRoles() {
		return roleDao.getAllRoles();
	}

	public PageBean<RoleDO> findUserByCond(Condition<RoleDO> condition) {
		return roleDao.findUserByCond(condition);
	}

	public Integer save(RoleDO role) {
		return roleDao.save(role);
	}

	public void update(RoleDO role) {
		roleDao.update(role);
	}

	public void delete(Integer id) {
		roleDao.delete(id);
	}

}
