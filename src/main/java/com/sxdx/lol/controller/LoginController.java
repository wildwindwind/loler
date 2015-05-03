package com.sxdx.lol.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxdx.lol.bean.Menu;
import com.sxdx.lol.service.UserService;
import com.sxdx.lol.util.Captcha;

/**
 * 类IndexController.java的实现描述：主控制器
 * @author 13075054 2015年4月30日 下午8:58:56
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

	@Autowired
	UserService userService;
	
	@RequestMapping("main")
	public String toMain(ModelMap model) {
		Menu menu = userService.getCurrentMenuTree();
		//获取当前登录用户
		String user = userService.getCurrentUserName();
    	model.addAttribute("menus", menu.getChildren());
    	model.addAttribute("user", user);
		return "admin/main";
	}

	@RequestMapping("welcome")
	public String welcome() {
		return "admin/index";
	}
	
	 /**
     * →登陆页
     */
    @RequestMapping("login")
    public String login( ModelMap model){
        return "admin/login";
    }
    
	/**
     * 登录
     * @throws IOException 
     */
    @RequestMapping("doLogin")
    public void doLogin(String username, String password, String captcha, boolean rememberMe, HttpServletRequest request, Writer writer) throws IOException{
        String captchaSession = (String)request.getSession().getAttribute(Captcha.CAPTCHA_SESSION);
        if(!captcha.equalsIgnoreCase(captchaSession)){
            writer.write("1");
            return;
        }
        try {
            Subject currentUser = SecurityUtils.getSubject(); 
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            currentUser.login(token); 
            writer.write("0");//验证通过
        } catch ( UnknownAccountException e ) {//用户名错误
        	writer.write("2");
        } catch ( DisabledAccountException e ) {//用户被禁用
        	writer.write("3");
        } catch ( IncorrectCredentialsException e ) {//密码错误
        	writer.write("4");
        } catch ( AuthenticationException e ) {//未知错误
        	writer.write("5");
        	e.printStackTrace();
        } 
    }
    
    @RequestMapping("unauthz")
    public String unauthz( ModelMap model){
        return "admin/common/unauthz";
    }  
    
    /**
     * 退出系统
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, ModelMap model){
        Subject currentUser = SecurityUtils.getSubject(); 
        currentUser.logout();
        return "redirect:login.htm";
    }

    /**
     * 图片验证码
     */
    @RequestMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Captcha captcha=new Captcha();
        request.getSession().setAttribute(Captcha.CAPTCHA_SESSION, captcha.getCaptchaString());
        captcha.outputCaptcha(response.getOutputStream());
    }
}
