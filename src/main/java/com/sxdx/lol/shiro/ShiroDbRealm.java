/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.sxdx.lol.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sxdx.lol.entity.UserDO;
import com.sxdx.lol.service.UserService;
import com.sxdx.lol.util.DigestUtils;

/**
 * 权限认证
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class ShiroDbRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	@Autowired
	private UserService userService;

	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
	{
		UsernamePasswordToken taken = (UsernamePasswordToken)authenticationToken;
		String username = taken.getUsername();
		String password = new String(taken.getPassword());
		
		if (username != null && password != null) {
			UserDO user = userService.findByUsername(username);
			if (user == null) {
				throw new UnknownAccountException();
			}
			/*if (user.getIsDisabled()==1) {
				throw new DisabledAccountException();
			}*/
			
			if (!DigestUtils.md5Hex(password).equalsIgnoreCase(user.getPassword())) {
				throw new IncorrectCredentialsException();
			}
			
			return new SimpleAuthenticationInfo(username, password, getName());
		}
		
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 * @param principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Set<String> perms = userService.getCurrentStringPermissions();
	    if (perms == null) {
	    	return null;
	    }
	    
	    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	    authorizationInfo.addStringPermissions(perms);
	    
	    logger.debug("shiro has perms:"+authorizationInfo.getStringPermissions().toString());
	    return authorizationInfo;
	}

}