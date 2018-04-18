<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript" src="./js/jquery.min.js"></script>
    <script type="text/javascript" src="./jquery-easyui-1.2.4/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="./jquery-easyui-1.2.4/themes/default/easyui.css" type="text/css"></link>
    <link rel="stylesheet" href="./jquery-easyui-1.2.4/themes/icon.css" type="text/css"></link>
    <script type="text/javascript">
    function addTab(tabId,title,url)
    {     		   
		var name = 'iframe_'+tabId; 
		if(!$('#centerTab').tabs('exists',title))
		{
			$('#centerTab').tabs('add',{   
				title: title,            
				closable:true,   
				cache : false,   
				content : '<iframe name="'+name+'"id="'+tabId+'"src="'+url+'" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>'   
			});   
		} 
		else
		{
			$('#centerTab').tabs('select',title);
		}		
	}
	</script>
    </head>
    <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
    <!-- 上面部分 -->
    <div region="north" split="true" href="./page/top.jsp" style="overflow: hidden; height: 95px; background: #D2E0F2 repeat-x center 50%;
        line-height: 50px; color: #fff;">
    </div>
    <!-- 下面部分 -->
    <div region="south" style="height: 30px; background: #D2E0F2;">
        <div style="text-align: center; font-weight: bold">
        </div>
    </div>
     <!-- 右边部分 -->
     <div region="east" iconCls="icon-reload" title="工具栏" split="true" style="width:100px;"></div> 
     <!-- 左边部分 -->
     <div region="west" split="true" title="导航菜单" style="width:250px;padding:1px;">
     	<!-- 
	     	<div class="easyui-accordion" fit="true" border="false">
	     		<div>
	     			
	     		</div>
	     	</div>
     	 -->
		<ul id="aa" class="easyui-tree" border="false" fit="false">
		<!-- 
			<c:forEach items="${moduleMap}" var="father" varStatus="num1">  
			    <li>   
			        <span>${father.key}</span>   
			        <ul>   
			        	<c:forEach items="${father.value}" var="child" varStatus="num2">
			            <li><a href="javascript:addTab('${child.rightCode}','${child.rightName}','${child.rightUrl}');">${child.rightName}</a></li> 
			            </c:forEach>
			        </ul>   
			    </li>  
			</c:forEach> 
		 -->
		 	<li>
		 		<span>系统管理</span>   
		 		<ul>   
			        <li>
			        	<span>公众账号管理</span> 
			        </li> 
			        <li>
			        	<span>菜单定义</span> 
			        </li> 
			        <li>
			        	<span>功能管理</span> 
			        </li> 
			        <li>
			        	<span>参数配置</span> 
			        </li>
			    </ul> 
		 	</li>	
		</ul>  
	</div>
	<!-- 中间部分 -->
    <div region="center" title="功能区" >  
        <div class="easyui-tabs" id="centerTab" fit="true" border="false">  
            <div title="欢迎页" closable="true" style="overflow:auto;padding:20px;overflow:hidden;">    
                <div style="margin-top:20px;"> 
                    <h3>你好，欢迎来到微信管理系统</h3>  
                </div>  
            </div>  
        </div>  
    </div> 
</body>
</html>
