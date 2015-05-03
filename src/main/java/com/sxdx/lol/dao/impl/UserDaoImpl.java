package com.sxdx.lol.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.dao.UserDao;
import com.sxdx.lol.entity.UserDO;

@Component
public class UserDaoImpl extends SqlMapClientDaoSupport implements UserDao {

	public UserDO findByUsername(String userName) {
		return (UserDO) getSqlMapClientTemplate().queryForObject("findByUsername", userName);
	}

	public PageBean<UserDO> findUserByCond(Condition<UserDO> cond) {
		PageBean<UserDO> pageBean = cond.getPageBean();
		Map<String,Object> param = new LinkedHashMap<String,Object>();
		
		int offset = (pageBean.getPageNo()-1) * pageBean.getPageSize();
		int pageSize = pageBean.getPageSize();
		param.put("userName", cond.getUserName());
		param.put("realName", cond.getRealName());
		param.put("startTime", cond.getStartTime());
		param.put("endTime", cond.getEndTime());
		param.put("offset", offset);
		param.put("pageSize", pageSize);
		
		int count = (Integer)getSqlMapClientTemplate().queryForObject("findUserCountByCond", param);
		@SuppressWarnings("unchecked")
		List<UserDO> users = getSqlMapClientTemplate().queryForList("findUserByCond", param);
		pageBean.setTotalCount(count);
		pageBean.setList(users);
		return pageBean;
	}

	public Integer save(UserDO user) {
		Assert.notNull(user);
        return (Integer) getSqlMapClientTemplate().insert("saveUser", user);
	}

	public UserDO findById(Integer id) {
		return (UserDO) getSqlMapClientTemplate().queryForObject("findUserById", id);
	}

	public void update(UserDO user) {
		Assert.notNull(user);
        getSqlMapClientTemplate().update("updateUser", user);
	}

	public void deleteById(Integer id) {
		Assert.notNull(id);
        getSqlMapClientTemplate().delete("deleteUser", id);
	}

}
