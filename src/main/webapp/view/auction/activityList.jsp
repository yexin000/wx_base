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
            <label class="ui-label">活动名称:</label>
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
<div id="edit-win" class="easyui-dialog" title="编辑菜单" data-options="closed:true,iconCls:'',modal:true"  style="width:400px;height:320px;">
    <form id="editForm" class="ui-form" method="post">
        <!-- 隐藏文本框 -->
        <input class="hidden" name="id">
        <div class="ui-edit">
            <div class="ftitle">活动信息</div>
            <div class="fitem">
                <label>活动名称:</label>
                <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
            </div>
            <div class="fitem">
                <label>活动金额:</label>
                <input class="easyui-numberbox" type="text" name="money" data-options="min:0">
            </div>
            <div class="fitem">
                <label>开始时间:</label>
                <input type="text" name="starttime" editable="false" class="easyui-datetimebox" data-options="required:true">
            </div>
            <div class="fitem">
                <label>结束时间:</label>
                <input type="text" name="endtime" editable="false" class="easyui-datetimebox" data-options="required:true">
            </div>
            <div class="fitem">
                <label>活动内容:</label>
                <textarea rows="5" name="description" class="easyui-validatebox" data-options="required:true" style="width: 157px;"></textarea>
            </div>
        </div>
    </form>
</div>
<div id="describe-win" class="easyui-dialog" title="活动内容" data-options="closed:true,iconCls:'',modal:true"  style="width:400px;height:250px;">
    <form id="describeForm" class="ui-form" method="post">
        <div class="ui-edit">
            <div class="fitem">
                <textarea rows="7" id="describeDlg" class="easyui-validatebox" data-options="required:true" style="width: 350px;height:180px;"></textarea>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/activity.js"></script>
</body>
</html>
