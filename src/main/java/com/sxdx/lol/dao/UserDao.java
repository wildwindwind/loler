package com.sxdx.lol.dao;

import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.entity.UserDO;

public interface UserDao {

	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return User
	 */
	UserDO findByUsername(String userName);
	
	UserDO findById(Integer id);
	
	PageBean<UserDO> findUserByCond(Condition<UserDO> cond);
	
	Integer save(UserDO user);
	
	void update(UserDO user);
	
	void deleteById(Integer id);
	
}
