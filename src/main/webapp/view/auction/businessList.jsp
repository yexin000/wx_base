<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@include file="/view/resource.jsp" %>
</head>
<body class="easyui-layout">
<!-- Search panel start -->
<div class="ui-search-panel" region="north" style="height: 80px;" title="查询条件" data-options="striped: false,collapsible:false,iconCls:'icon-search',border:false" >
    <form id="searchForm">
        <input class="hidden" id='search_parentId' name="parentId">
        <p class="ui-fields">
            <label class="ui-label">商家名称:</label>
            <input name="name" class="easyui-box ui-text" style="width:100px;">
        </p>
        <a id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </form>
</div>
<!--  Search panel end -->
<!-- DataList  -->
<div region="center" border="false" >
    <table id="data-list" ></table>
</div>

<!-- Edit Win&From -->
<div id="edit-win" class="easyui-dialog" title="编辑菜单" data-options="closed:true,iconCls:'',modal:true"  style="width:400px;height:280px;">
    <form id="editForm" class="ui-form" method="post">
        <!-- 隐藏文本框 -->
        <input class="hidden" name="id">
        <div class="ui-edit">
            <div class="ftitle">商家信息</div>
            <div class="fitem">
                <label>商家名称:</label>
                <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
            </div>

            <div class="fitem">
                <label>商家地址:</label>
                <input type="text" name="address"></input>
            </div>
            <div class="fitem">
                <label>商家电话:</label>
                <input class="easyui-numberbox" type="text" name="telNum" data-options="required:true,validType:'phoneNum'">
            </div>
            <div class="fitem">
                <label>商家微信:</label>
                <input class="easyui-validatebox" type="text" name="wxAccount" data-options="required:true">
            </div>
            <div class="fitem">
                <label>是否推荐:</label>
                <select class="easyui-combobox" name="isShow" id="isShows" data-options="required:true">
                    <option value="1" selected="selected">是</option>
                    <option value="0">否</option>
                </select>
            </div>
        </div>
    </form>
    <form  id="portraitform" method="post" enctype="multipart/form-data" action="uploadLogo.do" style="display: none">
        <input type="file" accept="image/*" name="headImg" id="edit-portrait">
        <input type="hidden" id="businessid" name="businessid">
        <input type="submit">
    </form>
    <div id="dlg">
        <img id="simg" width="460px" height="280px"/>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/business.js"></script>
</body>
</html>
