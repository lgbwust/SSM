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
				//�û�����
				mv.addObject("partners", list); 				
				//�����߼���ͼ������ͼ����������ݸ����ֽ������������ͼҳ��
				mv.setViewName("/users/users"); 
			}else{
				//�û�����
				mv.addObject("partners", list); 				
				//�����߼���ͼ������ͼ����������ݸ����ֽ������������ͼҳ��
				mv.setViewName("/users/users"); 
			}
			
		}catch(Exception e){
					
			//�����߼���ͼ������ͼ����������ݸ����ֽ������������ͼҳ��
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
		//���ֻ���ϴ�һ���ļ�����ֻ��ҪMultipartFile���ͽ����ļ����ɣ�����������ʽָ��@RequestParamע��
		//������ϴ�����ļ�����ô �����Ҫ��MultipartFile[]�����������ļ������һ�Ҫָ��@RequestParamע��
		//�����ϴ�����ļ�ʱ��ǰ̨���е�����<input type="file"/>��name��Ӧ����myfiles������������myfiles�޷���ȡ�������ϴ����ļ�
		for(MultipartFile myfile : myfiles){
			if(myfile.isEmpty()){
				System.out.println("�ļ�δ�ϴ�");
			}else{
				System.out.println("�ļ�����: " + myfile.getSize());
				System.out.println("�ļ�����: " + myfile.getContentType());
				System.out.println("�ļ�����: " + myfile.getName());
				System.out.println("�ļ�ԭ��: " + myfile.getOriginalFilename());
				System.out.println("========================================");
				//����õ���Tomcat�����������ļ����ϴ���\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\�ļ�����
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
				//���ﲻ�ش���IO���رյ����⣬��ΪFileUtils.copyInputStreamToFile()�����ڲ����Զ����õ���IO���ص������ǿ�����Դ���֪����
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
			}
		}
		
		return "../../index";
	}
}
//ע�⣺��WEB-INFĿ¼�½����ļ���upload