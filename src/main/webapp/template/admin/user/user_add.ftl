<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Author" content="kudychen@gmail.com" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>网站后台登陆</title>
    <#include "/admin/include/common.ftl"/>
    
<script type="text/javascript" >
	$(document).ready(function(){
		$('#pForm').valid();
	});
	
	function saveInfo(){
	       var options = { success: function(data) {
	            if(data == "ok")
	            {
	            	frameElement.api.opener.addCallback();
	            }
	            else
	            {
	                Pop.error(data);
	            }
	        }};
	        
	        $('#pForm').check(function(){
	        	$('#pForm').ajaxSubmit(options);
	        });
	}
</script>
</head>
<body>
<div style="margin:10px">
	<table class="field-box">
			<tr class="box-title"><th colspan="50"><span class="edit-icon">用户信息</span></th></tr>
		      <form id="pForm" action='${base}/admin/user_save.htm' method="post">
		      	<input type="hidden" name="id"/>
		          <tr>
			         <th width="150px"><span class="required">*</span>帐号：</th>
			         <td>
			            <input type="text" name="userName"
			            	valid="{required:true,regex:'username',rangelength:[4,20],ajax:'${base}/admin/user_exist.htm',messages:{ajaxError:'登录名已存在'}}"/>
			         </td>
			      </tr>
		          <tr>
			         <th><span class="required">*</span>真实姓名：</th>
			         <td>
			            <input type="text" name="realName" valid="{required:true,regex:'cn_en',rangelength:[2,10]}"/>
			         </td>
			      </tr>
		          <tr>
			         <th><span class="required">*</span>手机号码：</th>
			         <td>
			            <input type="text" name="phoneNum" valid="{required:true,maxlength:50}"/>
			         </td>
			      </tr>
			       <tr>
			         <th>个人描述：</th>
			         <td>
			            <textarea rows="3" cols="60" name="description"></textarea>
			         </td>			         
		          </tr> 
                  <tr>
                  <td colspan="4" style="text-align: center;">
                      <input type="button" value="保存" class="btn" onclick="saveInfo()"/>
                      <input type="button"  onclick="frameElement.api.close();" value="返回"  class="btn" style='margin-left:20px'/>
                  </td>
                  </tr>		          		            		          		           
		      </form>
		</tbody>
	</table> 	

</div>
</body>
</html>