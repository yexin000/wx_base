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
            <label class="ui-label">微信号:</label>
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
            <div class="ftitle">经纪人信息</div>
            <div class="fitem">
                <label>级别:</label>
                <select class="easyui-combobox" name="grade" id="grades" data-options="required:true">
                    <option value="3" selected="selected">高级经纪人</option>
                    <option value="2">中级经纪人</option>
                    <option value="1">初级经纪人</option>
                </select>
            </div>

            <div class="fitem">
                <label>微信号:</label>
                <input type="text" class="easyui-validatebox" name="wxAcount" data-options="required:true">
            </div>
            <div class="fitem">
                <label>经纪人电话:</label>
                <input class="easyui-numberbox" type="text" name="phoneNum" data-options="required:true,validType:'phoneNum'">
            </div>
            <div class="fitem">
                <label>个人简介:</label>
                <textarea rows="3" name="description" class="easyui-validatebox" data-options="required:true" style="width: 157px;"></textarea>
            </div>
        </div>
    </form>
    <form  id="portraitform" method="post" enctype="multipart/form-data" action="uploadLogo.do" style="display: none">
        <input type="file" accept="image/*" name="headImg" id="edit-portrait">
        <input type="hidden" id="middleManId" name="middleManId">
        <input type="submit">
    </form>
    <div id="dlg">
        <img id="simg" width="460px" height="280px"/>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/middleMan.js"></script>
</body>
</html>
