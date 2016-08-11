	<link rel='stylesheet' type='text/css' href='../../css/bootstrap.min.css'>
	<link rel="shortcut icon" href="img/icon.png">
	<script type='text/javascript' src="../../js/jquery-1.7.2.min.js"></script>
	<script type='text/javascript' src="../../js/bootstrap.min.js"></script>

	<script type="text/javascript">
	 
	   function checkIsExist() {  
		   var userName = $.trim($("#userName").val());  
	        $.ajax({  
	            type:"POST",   //http请求方式  
	            url:"${pageContext.request.contextPath}/user/validate.do", //发送给服务器的url  
	            data:"userName="+userName, //发送给服务器的参数  
	            dataType:"json",  //告诉JQUERY返回的数据格式(注意此处数据格式一定要与提交的controller返回的数据格式一致,不然不会调用回调函数complete)  
	            complete:function(msg) {  
	                if (eval("(" + msg.responseText + ")")) {  
	                    $("#showResult").html("<font color='red'>用户名已存在</font>");  
	                } else {  
	                    $("#showResult").html("<font color='red'>恭喜，用户名可以注册</font>");  
	                }  
	            }//定义交互完成,并且服务器正确返回数据时调用回调函数   
	        });  
	    }  
	   function clearCss() {  
	       $("#showResult").html("");    
	   }  
	</script>
					<div class="modal-body">
						
							<div class="control-group">
								<label class="control-label" for="username">用户名</label>
								<div class="controls">
									<input type="text" name="userName" id="userName" onblur="checkIsExist();" onfocus="clearCss();" placeholder="账号" style="width:200px;height:30px;"><span id="showResult"></span>  
								</div>
							</div>
		
