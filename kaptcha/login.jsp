

	<script type="text/javascript">
	
	$(function(){           
        $('#kaptchaImage').click(function () {//生成验证码  
         $(this).hide().attr('src', '<%=basePath%>captcha-image.do?' + Math.floor(Math.random()*100) ).fadeIn(); })      
              }); 	
		 </script>
	
	
							<div class="control-group">
								<label class="control-label" for="test">验证码</label>
								<div class="controls">
									 <input name="kaptcha" type="text" id="kaptcha" maxlength="4" placeholder="验证码" style="width:200px;height:30px;" />  
									 <img src="<%=basePath%>captcha-image.do"  id="kaptchaImage" title="看不清，点击换一张" />    
                                      
								</div>
