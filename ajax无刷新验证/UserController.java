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
	 * ajax无刷新用户名验证，注意此处应添加json的jar包
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/validate", produces = "application/json")
	public boolean validate(User user, HttpServletRequest request, RedirectAttributes redirectAttributes
			/*String userName*/){
		/**
		 * 这里有三种方式取得ajax传过来的usrname
		 * 1.通过函数参数 String userName
		 * 2.通过String name = request.getParameter("name");
		 * 3.通过userdao中的get方法取得username
		 */
		// String name = request.getParameter("name");
		String name = user.getUserName();
		User dbuser = userSerivce.getUserByUserName(name);
		System.out.println("username:" + name);
	
	
		boolean flag = true;
		if (dbuser != null||name==null||name=="") {
			flag = false;
		}
		if (flag) {
			return false;
		} else {
			return true;
		}
	}


}
