package com.sxdx.lol.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sxdx.lol.dao.RolePermRelDao;
import com.sxdx.lol.entity.RolePermRelDO;

@Component
public class RolePermRelDaoImpl extends SqlMapClientDaoSupport implements RolePermRelDao {

	@SuppressWarnings("unchecked")
	public List<RolePermRelDO> findByRoleId(Integer roleId) {
		Assert.notNull(roleId);
		return getSqlMapClientTemplate().queryForList("findRolePermRelByUserId", roleId);
	}

	public void batchInsertRolePermRel(List<RolePermRelDO> RolePermRels) {
        getSqlMapClientTemplate().insert("batchInsertRolePermRel", RolePermRels);
	}

	public void deleteRolePermRelByRoleId(Integer roldId) {
		Assert.notNull(roldId);
        getSqlMapClientTemplate().delete("deleteRolePermRelByRoleId", roldId);
	}
}
