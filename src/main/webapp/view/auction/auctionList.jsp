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
            <label class="ui-label">拍卖会名称:</label>
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
            <div class="ftitle">拍卖会信息</div>
            <div class="fitem">
                <label>拍卖会名称1:</label>
                <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
            </div>

            <div class="fitem">
                <label>开始时间2:</label>
                <input type="text" name="starttime" editable="false" class="easyui-datetimebox" data-options="required:true">
            </div>
            <div class="fitem">
                <label>结束时间:</label>
                <input type="text" name="endtime" editable="false" class="easyui-datetimebox" data-options="required:true">
            </div>
            <div class="fitem">
                <label>所属商家:</label>
                <select class="easyui-combobox" name="businessid" id="businessids" data-options="required:true" style="width: 156px;">
                </select>
            </div>
            <div class="fitem">
                <label>类型:</label>
                <select class="easyui-combobox" name="type" id="types" data-options="required:true" style="width: 156px;">
                </select>
            </div>
            <div class="fitem">
                <label>首页显示:</label>
                <select class="easyui-combobox" name="isShow" id="isShows" data-options="required:true">
                    <option value="1">是</option>
                    <option value="0" selected="selected">否</option>
                </select>
            </div>
        </div>
    </form>
    <form id="resForm" class="ui-form" method="post" action="/weixin/itemRes/list.do">
        <input class="hidden" name="conid" id="conid">
        <input class="hidden" name="conName" id="conName">
        <input class="hidden" name="conType" id="conType" value="1">
    </form>
</div>
<script type="text/javascript" src="${msUrl}/js/ht/auction/auction.js"></script>
</body>
</html>
