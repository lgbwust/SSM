package com.fiberhome.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wlsq.model.News;
import com.wlsq.service.IUserMapperService;
import com.wlsq.util.Pagination;

@Controller 
@RequestMapping(value="/users")
public class UserController {
	@Autowired
	private IUserMapperService userService;
	
	@RequestMapping(value="/userAll")
	public ModelAndView searchNews(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = null;
		try{
			mv = new ModelAndView();
			int pageSize = Integer
					.parseInt(request.getParameter("pageSize") == null ? "10"
							: request.getParameter("pageSize"));
			int pageNum = Integer
					.parseInt(request.getParameter("pageNum") == null ? "1"
							: request.getParameter("pageNum"));
			Map<String, Object> maps = new HashMap<>();
			maps.put("pageSize", pageSize);
			maps.put("pageNum", (pageNum-1) * pageSize);
			List<News> list =userService.selectAllUsers(maps);
			int count = userService.selectCountUsers();
			Pagination page = new Pagination(count);
			page.setCurrentPage(pageNum);
			mv.addObject("pnums", page.getPageNumList());
			mv.addObject("currentPage", pageNum);
			mv.addObject("pnext_flag", page.nextEnable());
			mv.addObject("plast_flag", page.lastEnable());
			page.lastPage();
			mv.addObject("last_page", page.getCurrentPage());
			mv.addObject("count", count);
			mv.addObject("pageCount", page.getPages());
			if(list !=null && list.size()>0){
				//用户存在
				mv.addObject("partners", list); 				
				//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
				mv.setViewName("/users/users"); 
			}else{
				//用户存在
				mv.addObject("partners", list); 				
				//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
				mv.setViewName("/users/users"); 
			}
			
		}catch(Exception e){
					
			//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
			mv.setViewName("/users/users"); 
		}
		
		
		return mv;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addUser(@RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws IOException{
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		System.out.println("name:"+username);
		System.out.println("nickname:"+nickname);
		System.out.println("password:"+password);
		System.out.println("email:"+email);
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
		//如果想上传多个文件，那么 这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
		//并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
		for(MultipartFile myfile : myfiles){
			if(myfile.isEmpty()){
				System.out.println("文件未上传");
			}else{
				System.out.println("文件长度: " + myfile.getSize());
				System.out.println("文件类型: " + myfile.getContentType());
				System.out.println("文件名称: " + myfile.getName());
				System.out.println("文件原名: " + myfile.getOriginalFilename());
				System.out.println("========================================");
				//如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
				//这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
			}
		}
		
		return "../../index";
	}
}
//注意：在WEB-INF目录下建立文件夹upload