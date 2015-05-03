package com.sxdx.lol.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxdx.lol.common.Condition;
import com.sxdx.lol.common.Constants;
import com.sxdx.lol.common.PageBean;
import com.sxdx.lol.entity.RoleDO;
import com.sxdx.lol.entity.UserDO;
import com.sxdx.lol.entity.UserRoleRelDO;
import com.sxdx.lol.service.RoleService;
import com.sxdx.lol.service.UserRoleRelService;
import com.sxdx.lol.service.UserService;
import com.sxdx.lol.util.DigestUtils;
import com.sxdx.lol.util.StringUtils;

/**
 * 类UserController.java的实现描述：用户操作控制器
 * @author 13075054 2015年4月30日 下午8:58:56
 */
@Controller
@RequestMapping("/admin")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleRelService userRoleRelService;
	
	@RequestMapping("user_list")
	public String toMain(UserDO user, PageBean<UserDO> pageBean, HttpServletRequest request, ModelMap model) {
		
		Condition<UserDO> cond = new Condition<UserDO>();
		cond.setPageBean(pageBean);
		cond.setUserName(user.getUserName());
		cond.setRealName(user.getRealName());
		PageBean<UserDO> pageBean_ = userService.findUserByCond(cond);
		model.addAttribute("user", user);
		model.addAttribute("pageBean", pageBean_);
		return "admin/user/user_list";
	}

	/**
     * →增加用户页面
     */
    @RequestMapping("user_add")
    public String user_add(HttpServletRequest request, ModelMap model) {
        return "admin/user/user_add";
    }
    
    /**
     * 保存用户
     * @throws IOException 
     */
    @RequestMapping("user_save")
    public void user_save(UserDO user, Writer writer) throws IOException {
    	user.setPassword(DigestUtils.md5Hex(user.getPassword()));
    	userService.save(user);
    	writer.write(Constants.OK_FLAG);
    }
    
    /**
     * 判断用户是否存在
     * @throws IOException 
     */
    @RequestMapping("user_exist")
    public void user_exist(String userName, Writer writer) throws IOException {
    	UserDO user = userService.findByUsername(userName);
    	if(user != null) {
    		writer.write(Constants.ERROR_FLAG);
    		return;
    	}
    	writer.write(Constants.OK_FLAG);
    }
    
    /**
     * →修改用户页面
     */
    @RequestMapping("user_edit")
    public String user_edit(Integer id, HttpServletRequest request, ModelMap model)
    {
        UserDO user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user/user_edit";
    }
    
    /**
     * 修改用户
     * @throws IOException 
     */
    @RequestMapping("user_update")
    public void user_update(UserDO user, Writer writer) throws IOException{
    	UserDO quser = userService.findById(user.getId());
    	quser.setUserName(user.getUserName());
    	quser.setRealName(user.getRealName());
    	quser.setPhoneNum(user.getPhoneNum());
    	quser.setDescription(user.getDescription());
    	userService.update(quser);
    	writer.write(Constants.OK_FLAG);
    }
    
    /**
     * 重置密码  初始密码都为123456
     * @param id
     * @param writer
     * @throws IOException
     */
    @RequestMapping("user_resetPwd")
    public void user_resetPwd(Integer id, Writer writer) throws IOException{
    	Assert.notNull(id);
        UserDO user = userService.findById(id);;
        user.setPassword(DigestUtils.md5Hex("123456"));
        userService.update(user);
   		writer.write(Constants.OK_FLAG);
    }
    
    /**
     * 删除用户
     * @throws IOException 
     */
    @RequestMapping("user_delete")
    public void user_delete(Integer id, Writer writer) throws IOException{
    	userService.deleteById(id);
    	writer.write(Constants.OK_FLAG);
    }
    
    /**
     * 角色分配
     */
    @RequestMapping("user_allocRole")
    public String user_allocRole(Integer userId, HttpServletRequest request, HttpServletResponse response, ModelMap model){
        Set<Integer> roleIds = new HashSet<Integer>();
        List<RoleDO> roles = roleService.getRolesByUserId(userId);
        for(RoleDO role : roles){
        	roleIds.add(role.getId());
        }
        List<RoleDO> allRoles = roleService.getAllRoles();
        model.addAttribute("userId", userId);
        model.addAttribute("roles", allRoles);
        model.addAttribute("roleIds", roleIds);
        
        return "admin/user/user_allocRole";
    }
    
    /**
     * 设置员工的角色 <功能详细描述>
     * @throws IOException 
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("user_doAllocRole")
    public void user_doAllocRole(Integer userId, String roleIds, Writer writer) throws IOException {
        
    	Assert.notNull(userId);
    	userRoleRelService.deleteUserRoleRelByUserId(userId);
    	List<UserRoleRelDO> userRoleRelDOs = new ArrayList<UserRoleRelDO>();
        if (StringUtils.isNotBlank(roleIds)){
            String[] ids=roleIds.split(",");
            for(int i=0; i<ids.length; i++){
            	UserRoleRelDO userRoleRelDO = new UserRoleRelDO();
            	userRoleRelDO.setUserId(userId);
            	userRoleRelDO.setRoleId(new Integer(ids[i]));
            	userRoleRelDOs.add(userRoleRelDO);
            }
        }
        userRoleRelService.batchInsertUserRoleRel(userRoleRelDOs);
   		writer.write(Constants.OK_FLAG);
    }
    
    /**
     * 去修改用户页面
     */
    @RequestMapping("self_changeInfo")
    public String self_edit(HttpServletRequest request, ModelMap model) {
        UserDO user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "admin/user/self_changeInfo";
    }
    
    @RequestMapping("self_doChangeInfo")
    public void self_update(UserDO user, Writer writer) throws IOException {
        UserDO quser = userService.findById(user.getId());
        if(StringUtils.isNotBlank(user.getPassword())) {
            quser.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
        quser.setRealName(user.getRealName());
        quser.setPhoneNum(user.getPhoneNum());
        quser.setDescription(user.getDescription());
        userService.update(quser);
   		writer.write(Constants.OK_FLAG);
    }
    
    @RequestMapping("self_changePwd")
    public String self_changePwd(String password, String newpassword, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	 return "admin/user/self_changePwd";
    }
    
    @RequestMapping("self_doChangePwd")
    public void self_doChangePwd(String password, String newpassword,Writer writer) throws IOException {
    	UserDO user = userService.getCurrentUser();
    	if(user.getPassword().equals(DigestUtils.md5Hex(password))) {
    		user.setPassword(DigestUtils.md5Hex(newpassword));
    		userService.update(user);
    		writer.write(Constants.OK_FLAG);
    		return;
    	}else{
    		writer.write(Constants.PASSWORD_ERROR_FLAG);
    	}
    }
}
