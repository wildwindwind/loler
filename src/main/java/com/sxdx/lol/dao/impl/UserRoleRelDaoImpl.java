package com.sxdx.lol.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sxdx.lol.dao.UserRoleRelDao;
import com.sxdx.lol.entity.UserRoleRelDO;

@Component
public class UserRoleRelDaoImpl extends SqlMapClientDaoSupport implements UserRoleRelDao {

	@SuppressWarnings("unchecked")
	public List<UserRoleRelDO> findByUserId(Integer userId) {
		Assert.notNull(userId);
		return getSqlMapClientTemplate().queryForList("findUserRoleRelByUserId", userId);
	}

	public void batchInsertUserRoleRel(List<UserRoleRelDO> userRoleRelDOs) {
        getSqlMapClientTemplate().insert("batchInsertUserRoleRel", userRoleRelDOs);
	}


}
