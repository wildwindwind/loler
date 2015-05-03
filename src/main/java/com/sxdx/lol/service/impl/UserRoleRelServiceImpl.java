package com.sxdx.lol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.sxdx.lol.dao.UserRoleRelDao;
import com.sxdx.lol.entity.UserRoleRelDO;
import com.sxdx.lol.service.UserRoleRelService;

@Service
public class UserRoleRelServiceImpl implements UserRoleRelService {

	@Autowired
	UserRoleRelDao userRoleRelDao;
	
	public List<UserRoleRelDO> findByUserId(Integer userId) {
		Assert.notNull(userId);
		return userRoleRelDao.findByUserId(userId);
	}

	public void batchInsertUserRoleRel(List<UserRoleRelDO> userRoleRelDOs) {
		userRoleRelDao.batchInsertUserRoleRel(userRoleRelDOs);
	}

}
