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
	            	frameElement.api.opener.editCallback();
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
		<form id="pForm" action='${base}/admin/user_update.htm' method="post">
		<input type="hidden" name="id" value="${user.id}"/>
		<tr class="box-title">
			<th colspan="50">
			<span class="edit-icon">修改个人信息</span>
			</th>
		</tr>
		 <tr>
	         <th width="150px"><span class="required">*</span>帐号：</th>
	         <td>
	            	${user.userName}
	         </td>
	      </tr>
          <tr>
	         <th><span class="required">*</span>真实姓名：</th>
	         <td>
	            <input type="text" name="realName" value="${user.realName}" valid="{required:true,regex:'cn_en',rangelength:[2,10]}"/>
	         </td>
	      </tr>
          <tr>
	         <th><span class="required">*</span>手机号码：</th>
	         <td>
	            <input type="text" name="phoneNum" value="${user.phoneNum}" valid="{required:true,maxlength:50}"/>
	         </td>
	      </tr>
	       <tr>
	         <th>个人描述：</th>
	         <td>
	            <textarea rows="3" cols="60" name="description">${user.description}</textarea>
	         </td>			         
          </tr> 
        <tr>
	        <td colspan="2" style="text-align: center;">
	            <input type="button" value="保存" class="btn mr10" onclick="saveInfo()"/>
	            <input type="button" value="返回" class="btn" onclick="frameElement.api.close();"/>
	        </td>
        </tr>		          		            		          		           
		</form>
	</table> 	
</div>
</body>
</html>