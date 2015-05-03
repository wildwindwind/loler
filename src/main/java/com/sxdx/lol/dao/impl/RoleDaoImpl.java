package com.sxdx.lol.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.dao.RoleDao;
import com.sxdx.lol.entity.RoleDO;

@Component
public class RoleDaoImpl extends SqlMapClientDaoSupport implements RoleDao {

	public RoleDO findById(Integer id) {
		return (RoleDO) getSqlMapClientTemplate().queryForObject("findRoleById", id);
	}

	@SuppressWarnings("unchecked")
	public List<RoleDO> getAllRoles() {
		return getSqlMapClientTemplate().queryForList("getAllRoles");
	}

	public PageBean<RoleDO> findUserByCond(Condition<RoleDO> condition) {
		PageBean<RoleDO> pageBean = condition.getPageBean();
		Map<String,Object> param = new LinkedHashMap<String,Object>();
		
		int offset = (pageBean.getPageNo()-1) * pageBean.getPageSize();
		int pageSize = pageBean.getPageSize();
		param.put("name", condition.getName());
		param.put("isSuper", condition.getIsSuper());
		param.put("offset", offset);
		param.put("pageSize", pageSize);
		
		int count = (Integer)getSqlMapClientTemplate().queryForObject("findRoleCountByCond", param);
		@SuppressWarnings("unchecked")
		List<RoleDO> roles = getSqlMapClientTemplate().queryForList("findRoleByCond", param);
		pageBean.setTotalCount(count);
		pageBean.setList(roles);
		return pageBean;
	}

	public Integer save(RoleDO role) {
		Assert.notNull(role);
        return (Integer) getSqlMapClientTemplate().insert("saveRole", role);
	}

	public void update(RoleDO role) {
		Assert.notNull(role);
        getSqlMapClientTemplate().update("updateRole", role);
	}

	public void delete(Integer id) {
		Assert.notNull(id);
        getSqlMapClientTemplate().delete("deleteRoleById", id);
	}

}
