<%@ page language="java" pageEncoding="UTF-8"%>
<form action="<%=request.getContextPath()%>/users/add" method="POST" enctype="multipart/form-data">
	username: <input type="text" name="username"/><br/>
	nickname: <input type="text" name="nickname"/><br/>
	password: <input type="password" name="password"/><br/>
	yourmail: <input type="text" name="email"/><br/>
	yourfile: <input type="file" name="myfiles"/><br/>	
	<input type="submit" value="�������û�"/>
</form>