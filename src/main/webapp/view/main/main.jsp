<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>微信后台管理系统</title>
	<%@include file="/view/resource.jsp" %>
	<link rel="stylesheet" type="text/css" href="${msUrl}/css/main.css">
	<script type="text/javascript" src="${msUrl}/js/ht/main/main.js"></script>
</head>
<body class="easyui-layout">
<div class="ui-header" data-options="region:'north',split:true,border:false" style="height:40px;overflow: hidden;">
	<div  class="ui-login">
		<div class="ui-login-info">
			欢迎 <span class="orange">${user.nickName}</span>
			<!--
	 		第[<span class="orange">${user.loginCount}</span>]次登录系统 
	 		-->
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="modify-pwd-btn"  href="javascript:void(0);">修改密码</a> |
			<a class="logout-btn" href="${msUrl}/logout.shtml">退出</a>
		</div>
	</div>
</div>
<!-- 树形菜单 -->
<div data-options="region:'west',split:true,title:'导航菜单'" style="width:200px;">
	<div id="tree-box" class="easyui-accordion" data-options="fit:true,border:false">
		<c:forEach var="item" items="${menuList}">
			<div title="${item.text}">
				<c:forEach var="node" items="${item.children}">
					<a class="menu-item" href="${msUrl}${node.url}">${node.text}</a>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
</div>
<div data-options="region:'south',split:true,border:false" style="height: 30px;overflow:hidden;">
	<div class="panel-header" style="border: none;text-align: center;" ></div>
</div>
<!-- 中间内容页面 -->
<div data-options="region:'center'" >
	<div class="easyui-tabs" id="tab-box" data-options="fit:true,border:false">
		<div title="欢迎首页" style="padding:20px;overflow:hidden;">
			你好，欢迎来到微信管理系统
		</div>
	</div>
</div>
<!--  modify password start -->
<div id="modify-pwd-win"  class="easyui-dialog" buttons="#editPwdbtn" title="修改用户密码" data-options="closed:true,iconCls:'',modal:true" style="width:350px;height:200px;">
	<form id="pwdForm" action="modifyPwd.do" class="ui-form" method="post">
		<input class="hidden" name="id">
		<input class="hidden" name="username">
		<div class="ui-edit">
			<div class="fitem">
				<label>旧密码:</label>
				<input id="oldPwd" name="oldPwd" type="password" class="easyui-validatebox"  data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>新密码:</label>
				<input id="newPwd" name="newPwd" type="password" class="easyui-validatebox" data-options="required:true" />
			</div>
			<div class="fitem">
				<label>重复密码:</label>
				<input id="rpwd" name="rpwd" type="password" class="easyui-validatebox"   required="required" validType="equals['#newPwd']" />
			</div>
		</div>
	</form>
	<div id="editPwdbtn" class="dialog-button" >
		<a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-submit">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-close">关闭</a>
	</div>
</div>
<!-- modify password end  -->
</body>
<script type="text/javascript">
  setInterval(refreshCount, 180000);

  function refreshCount() {
    WeiXin.ajaxJson("/weixin/order/refundCount.do",{},function(data){
      if(data.success){
        var refundCount = data.total;
        if(refundCount > 0) {
          $.messager.show({
            title:'退货订单提醒',
            msg:'您有订单需要退货处理,请前往订单查询处理',
            timeout:20000,
            showType:'show'
          });
        }
      }
    });

  }
</script>
</html>
