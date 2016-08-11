package com.mjl.controller;

import com.mjl.dao.UserLoginLogDaoI;
import com.mjl.model.PO.User;
import com.mjl.model.PO.UserLoginLog;
import com.mjl.service.Impl.UserServiceImpl;
import com.mjl.service.LoginLogServiceI;
import com.mjl.service.UserSerivceI;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @date 2016/08/09
 * @author fiberhome
 *
 */
@Controller
@RequestMapping("/user")
@Scope("prototype")
public class UserController {

	@Resource
	UserSerivceI userSerivce;

	@Resource
	LoginLogServiceI loginLogServiceI;

	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @param redirectAttributes
	 * @param kaptchaReceived
	 * @return
	 */
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	public String Userlogin(User user, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam(value = "kaptcha", required = true) String kaptchaReceived) {

		// test output
		System.out.println("username:" + user.getUserName());
		System.out.println("password:" + user.getPassWord());

		User dbuser = userSerivce.getUserByUserName(user.getUserName());
		String kaptchaExpected = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (kaptchaReceived == null || !kaptchaReceived.equals(kaptchaExpected)) {
			request.getSession().setAttribute("Msg", "您输入的验证码错误~~~ :)");
			return "user/loginStatus";
		} else if (dbuser != null && dbuser.getPassWord().equals(user.getPassWord())) {
			// 保存用户信息
			dbuser.setLastIp(request.getRemoteAddr());
			Timestamp lastloginTime = new Timestamp(new Date().getTime());
			dbuser.setLastLoginTime(lastloginTime);
			dbuser.setCredit(5 + dbuser.getCredit());
			userSerivce.updateUserByUserName(dbuser);
			// 保存入用户登录表的信息
			UserLoginLog userLoginLog = new UserLoginLog();
			userLoginLog.setUserName(dbuser.getUserName());
			userLoginLog.setLoginIp(request.getRemoteAddr());
			userLoginLog.setLoginDateTime(lastloginTime);
			loginLogServiceI.AddUserLoginLog(userLoginLog);

			request.getSession().setAttribute("username", dbuser.getUserName());
			return "redirect:/mainPage.do";
		}

		request.getSession().setAttribute("Msg", "登录失败,用户名或密码错误~~~ :)");

		return "user/loginStatus";
	}
