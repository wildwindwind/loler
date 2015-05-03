package com.sxdx.lol.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxdx.lol.bean.Menu;
import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.ConfigManager;
import com.sxdx.lol.common.Constants;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.entity.RoleDO;
import com.sxdx.lol.entity.RolePermRelDO;
import com.sxdx.lol.service.RolePermRelService;
import com.sxdx.lol.service.RoleService;
import com.sxdx.lol.service.UserRoleRelService;
import com.sxdx.lol.service.UserService;
import com.sxdx.lol.util.StringUtils;

/**
 * 类UserController.java的实现描述：用户操作控制器
 * @author 13075054 2015年4月30日 下午8:58:56
 */
@Controller
@RequestMapping("/admin")
public class RoleController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleRelService userRoleRelService;
	
	@Autowired
	private RolePermRelService rolePermRelService;
	
	@RequestMapping("role_list")
	public String toMain(RoleDO role, PageBean<RoleDO> pageBean, HttpServletRequest request, ModelMap model) {
		
		Condition<RoleDO> cond = new Condition<RoleDO>();
		cond.setPageBean(pageBean);
		cond.setName(role.getName());
		cond.setIsSuper(role.getIsSuper());
		PageBean<RoleDO> pageBean_ = roleService.findUserByCond(cond);
		model.addAttribute("role", role);
		model.addAttribute("pageBean", pageBean_);
		return "admin/role/role_list";
	}
	
	@RequestMapping("role_add")
	public String role_add(HttpServletRequest request, ModelMap model) {
		String json = createJsonTree(ConfigManager.getMenuList(), null);
		model.addAttribute("menus", json);
		return "admin/role/role_add";
	}
	
	/**
	 * 设置菜单权限列表
	 * 
	 */
	private String createJsonTree(List<Menu> menus, Set<String> authzSet) {
		JSONArray jsArr = new JSONArray();
		Set<Menu> permSet = new HashSet<Menu>();
		if (authzSet != null) {
			for (String perm : authzSet) {
				for (Menu menu : menus) {
					if (menu.isPerm() && perm.equals(menu.getUrl())) {
						permSet.add(menu);
					}
				}
			}
		}
		Set<String> menuCodes = new HashSet<String>();
		for (Menu perm : permSet) {
			menuCodes.add(perm.getId());

			Menu pMenu = perm.getParent();
			while (pMenu != null) {
				menuCodes.add(pMenu.getId());
				pMenu = pMenu.getParent();
			}
		}

		for (Menu menu : menus) {
			try {
				JSONObject jsObj = new JSONObject();
				jsObj.put("id", menu.getId());
				jsObj.put("name", menu.getName());
				jsObj.put("url", menu.getUrl());
				if (menu.getParent() != null) {
					jsObj.put("pId", menu.getParent().getId());
				}
				if (menuCodes.contains(menu.getId())) {
					jsObj.put("checked", true);
					jsObj.put("open", true);
				}

				jsArr.add(jsObj);
			} catch (Exception e) {
				e.getMessage();
			}
		}

		return jsArr.toString();
	}

	/**
	 * 保存角色
	 * 
	 * @throws IOException
	 */
	@RequestMapping("role_save")
	public void role_save(RoleDO role, String urls, Writer writer)
			throws IOException {
		//插入角色记录返回角色ID
		Integer roleId = roleService.save(role);
		
		List<RolePermRelDO> rolePermRelDOs = new ArrayList<RolePermRelDO>();
		if(StringUtils.isNotBlank(urls)) {
			String[] strArr = urls.split(",");
			for(int i=0; i<strArr.length; i++){
				RolePermRelDO rolePermRelDO = new RolePermRelDO();
				rolePermRelDO.setRoleId(roleId);
				rolePermRelDO.setPermUrl(strArr[i]);
				rolePermRelDOs.add(rolePermRelDO);
			}
		}
		rolePermRelService.batchInsertRolePermRel(rolePermRelDOs);
		writer.write(Constants.OK_FLAG);
	}
	
	@RequestMapping("role_edit")
	public String role_edit(Integer id, ModelMap model) {
		Assert.notNull(id);
		RoleDO role = roleService.findById(id);
		Set<String> perms = rolePermRelService.getPermissions(id);
		String json = createJsonTree(ConfigManager.getMenuList(), perms);
		model.addAttribute("menus", json);
		model.addAttribute("role", role);
		return "admin/role/role_edit";
	}
	/**
	 * 修改权限
	 * 
	 * @throws IOException
	 */
	@RequestMapping("role_update")
	public void role_update(RoleDO role, String urls, Writer writer)
			throws IOException {
		// 修改菜单
		RoleDO qrole = roleService.findById(role.getId());

		qrole.setName(role.getName());
		qrole.setRemark(role.getRemark());
		qrole.setIsSuper(role.getIsSuper());
		roleService.update(qrole);
		//清空原来的权限列表
		rolePermRelService.deleteRolePermRelByRoleId(role.getId());
		List<RolePermRelDO> rolePermRelDOs = new ArrayList<RolePermRelDO>();
		if(StringUtils.isNotBlank(urls)) {
			String[] strArr = urls.split(",");
			for(int i=0; i<strArr.length; i++){
				RolePermRelDO rolePermRelDO = new RolePermRelDO();
				rolePermRelDO.setRoleId(role.getId());
				rolePermRelDO.setPermUrl(strArr[i]);
				rolePermRelDOs.add(rolePermRelDO);
			}
		}
		rolePermRelService.batchInsertRolePermRel(rolePermRelDOs);
		writer.write(Constants.OK_FLAG);
	}

	/**
	 * 删除权限
	 * 
	 * @throws IOException
	 */
	@RequestMapping("role_delete")
	public void delete(Integer id, Writer writer) throws IOException {
		roleService.delete(id);
		writer.write(Constants.OK_FLAG);
	}
}
