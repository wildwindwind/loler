package com.sxdx.lol.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.sxdx.lol.dao.RolePermRelDao;
import com.sxdx.lol.entity.RolePermRelDO;
import com.sxdx.lol.service.RolePermRelService;

@Service
public class RolePermRelServiceImpl implements RolePermRelService {

	@Autowired
	private RolePermRelDao rolePermRelDao;
	
	public Set<String> getPermissions(Integer roleId) {
		Assert.notNull(roleId);
		List<RolePermRelDO> rolePermRelDOs = rolePermRelDao.findByRoleId(roleId);
		Set<String> perms = new HashSet<String>();
		for(RolePermRelDO rolePermRelDO : rolePermRelDOs){
			perms.add(rolePermRelDO.getPermUrl());
		}
		return perms;
	}

	public void batchInsertRolePermRel(List<RolePermRelDO> rolePermRelDOs) {
		rolePermRelDao.batchInsertRolePermRel(rolePermRelDOs);
	}

	public void deleteRolePermRelByRoleId(Integer roleId) {
		Assert.notNull(roleId);
		rolePermRelDao.deleteRolePermRelByRoleId(roleId);
	}
}
