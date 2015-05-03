package com.sxdx.lol.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxdx.lol.bean.Menu;
import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.ConfigManager;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.dao.UserDao;
import com.sxdx.lol.entity.RoleDO;
import com.sxdx.lol.entity.UserDO;
import com.sxdx.lol.service.RolePermRelService;
import com.sxdx.lol.service.RoleService;
import com.sxdx.lol.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao; 
	
	@Autowired
	private RolePermRelService rolePermRelService;
	
	@Autowired
	private RoleService roleService;
	
	public UserDO findByUsername(String userName) {
		return userDao.findByUsername(userName);
	}

	public PageBean<UserDO> findUserByCond(Condition<UserDO> condition) {
		return userDao.findUserByCond(condition);
	}

	public Set<String> getCurrentStringPermissions() {
		Set<String> perms = new HashSet<String>();
		UserDO user = getCurrentUser();
	    if (user != null) {
	    	List<RoleDO> roles = roleService.getRolesByUserId(user.getId());
	        for (RoleDO role : roles) {
	        	//超级管理员
	        	if(role.getIsSuper()==1){
	               // perms = ConfigManager.getAllPerms();
	                break;
	            }else{
	        		perms = rolePermRelService.getPermissions(role.getId());
	        	}
	        }
	    }
	    return perms;
	}
	 
	public UserDO getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            return findByUsername((String) subject.getPrincipal());
        }
        return null;
    }

	public Menu getCurrentMenuTree(){
        return createMenuTree(ConfigManager.getMenuTree(), getCurrentStringPermissions());
    }
	    
    private Menu createMenuTree(Menu menu, Collection<String> authzPerms){
    	Menu newMenu = new Menu();
    	newMenu.setId(menu.getId());
    	newMenu.setName(menu.getName());
    	newMenu.setUrl(menu.getUrl());
    	newMenu.setPerm(menu.isPerm());
    	
    	for(Menu child : menu.getChildren()){
    		if(child.isPerm()){
    			if(authzPerms.contains(child.getUrl())){
    				Menu newChild = new Menu();
    				newChild.setId(child.getId());
    				newChild.setName(child.getName());
    				newChild.setUrl(child.getUrl());
    				newChild.setPerm(child.isPerm());
    				newMenu.getChildren().add(newChild);
    			}
    		}else{
    			Menu cMenu = createMenuTree(child, authzPerms);
    			if(cMenu.getChildren().size() > 0){
    				newMenu.getChildren().add(cMenu);
    			}
    		}
    	}
    	return newMenu;
    }

	public Integer save(UserDO user) {
		return userDao.save(user);
	}

	public UserDO findById(Integer id) {
		return userDao.findById(id);
	}

	public void update(UserDO user) {
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see com.sxdx.lol.service.UserService#deleteById(java.lang.Integer)
	 */
	public void deleteById(Integer id) {
		userDao.deleteById(id);
	}

	public String getCurrentUserName() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            return (String) subject.getPrincipal();
        }
        return null;
	}	
}
