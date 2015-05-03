<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>网站后台登陆</title>
    
    <#include "/admin/include/common.ftl"/>
</head>
<body>
<div class="swap">
	<table class="list-box">
		<form id="pForm" method="post" action="${base}/admin/user_list.htm">	
		<input type="hidden" name="pageNo" value="${pageBean.pageNo}"/>
		<tr>
			<td colspan="20"  class="query">
			    <span>帐号：</span>
			    <input type="text" size="12" name="userName" value="${user.userName}" class="mr20"/>
				<span>姓名：</span>
				<input type="text" size="12" name="realName" value="${user.realName}" class="mr20"/>
				<input type="submit" value="查询" class="btn mr10"/>
				<input type="button" value="清空" class="btn" onclick="resetForm(this.form.id);"/>
	  		</td>
		</tr>
		<tr class="box-title">
			<th colspan="20">
				<span class="list-icon fl">用户列表</span>
				<span  class="fr">
				<a href="javascript:toAdd()">【新增用户】</a>
				</span>
	  		</th>
		</tr>	
		<tr>
			<th style="width:50px">序号</th>
			<th>帐号(email)</th>
			<th>姓名</th>
			<th>手机号码</th>
			<th>描述</th>
			<th>创建时间</th>
			<th>更新时间</th>
			<th style="width:200px">操作</th>
		</tr>
		<#list pageBean.list as user>
		<tr <#if (user_index%2==0)>class='t1'<#else>class='t2'</#if>>
			<td>${(pageBean.pageNo-1)*pageBean.pageSize+user_index+1}</td>
			<td>${user.userName}</td>
			<td>${user.realName}</td>
			<td>${user.phoneNum}</td>
			<td>${user.description}</td>
			<td>${user.createTime?datetime}</td>
			<td>${user.updateTime?datetime}</td>
			<td>
				<@shiro.hasPermission name="user_edit">【<a href="javascript:user_edit(${user.id});">修改</a>】</@shiro.hasPermission>
				<@shiro.hasPermission name="user_allocRole">【<a href="javascript:user_selectRole(${user.id});">设置角色</a>】</@shiro.hasPermission>
				<@shiro.hasPermission name="user_resetPwd">【<a href="javascript:user_resetPwd(${user.id})">重置密码</a>】</@shiro.hasPermission>
				<@shiro.hasPermission name="user_delete">【<a href="javascript:del(${user.id});">删除</a>】</@shiro.hasPermission>
			</td>
		</tr>
		</#list>
		<@m.page formId="pForm" pageNo=pageBean.pageNo pageSize=pageBean.pageSize totalCount=pageBean.totalCount columns=9/>
	</table><!-- end table -->

</div>
</body>

<script type="text/javascript">
	
	function queryList(){
		$("#pForm").submit();
	}
	
		
	function addCallback()
	{
		parent.showMsg("ok", "新增成功！");
		queryList();
	}
	
	function editCallback()
	{
		parent.showMsg("ok", "修改成功！");
		queryList();
	}
	
   function toAdd(){
   		Pop.window("${base}/admin/user_add.htm", 500, 320, "新增用户", 3000);
   }

   function user_edit(id){
   		Pop.window("${base}/admin/user_edit.htm?id="+id, 500, 320, "修改用户", 3000);
   }
   
   function user_selectRole(id)
   {
   		Pop.window("${base}/admin/user_allocRole.htm?userId="+id, 700, 420, "角色分配", 3000);
   }
   
   function user_resetPwd(id)
   {
   		Pop.confirm("确定要重置该用户的密码吗?", function() {
			var url = "${base}/admin/user_resetPwd.htm";
			$.ajax( {
				type : 'get',
				dataType : 'text',
				async : false,
				url : url,
				data : {id: id, ran: Math.random()},
				success : function(data) {
					if (data == 'ok') {
						parent.showMsg("ok", "密码重置成功！");
						queryList();
					} else {
						Pop.alert('error', '密码重置失败失败！');
					}
				}
			});
		
			}, function() {
	
		});
   }
   
   function del(id)
   {
	   	Pop.confirm("确定要删除这些记录吗?", function() {
			var url = "${base}/admin/user_delete.htm";
			$.ajax( {
				type : 'get',
				dataType : 'text',
				async : false,
				url : url,
				data : {id: id, ran: Math.random()},
				success : function(data) {
					if (data == 'ok') {
						parent.showMsg("ok", "删除用户成功！");
						queryList();
					} else {
						Pop.alert('error', '删除失败！');
					}
				}
			});
		
			}, function() {
		});
   } 
</script>
</html>