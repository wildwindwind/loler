package com.sxdx.lol.service;

import java.util.Set;

import com.sxdx.lol.bean.Menu;
import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.entity.UserDO;

public interface UserService {

	/**
	 * 根据userName查找记录
	 * @param userName
	 * @return
	 */
	UserDO findByUsername(String userName);
	
	/**
	 * 根据ID查找记录
	 * @param id
	 * @return
	 */
	UserDO findById(Integer id);
	
	/**
	 * 根据条件查找会员，分页
	 * @param condition
	 * @return
	 */
	PageBean<UserDO> findUserByCond(Condition<UserDO> condition);
	
	/**
	 * 获取当前登录用户拥有的权限集
	 * @return
	 */
	Set<String> getCurrentStringPermissions();
	
	/**
	 * 获取当前登录用户
	 * @return 当前登录用户
	 */
	UserDO getCurrentUser();
	
	/**
	 * 获取当前用户所对应的目录树
	 * @return
	 */
	Menu getCurrentMenuTree();
	
	/**
	 * 保存用户
	 * @param user
	 * @return ID
	 */
	Integer save(UserDO user);
	
	void update(UserDO user);
	
	void deleteById(Integer id);
	
	String getCurrentUserName();
}
