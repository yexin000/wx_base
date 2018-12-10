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
            <label class="ui-label">商品名称:</label>
            <input name="itemName" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">订单类型:</label>
            <select class="easyui-combobox" name="orderType" id="orderTypes" data-options="required:true">
                <option value="" selected="selected">全部</option>
                <option value="1">竞拍</option>
                <option value="2">购买</option>
            </select>
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

        </div>
    </form>
</div>

<div id="message-win" class="easyui-dialog" title="发送通知" data-options="closed:true,iconCls:'',modal:true"  style="width:400px;height:280px;">
    <form id="messageForm" class="ui-form" method="post">
        <input class="hidden" name="toWxid" id="msgWxid">
        <div class="ui-edit">
            <label>通知内容:</label>
            <div class="fitem">
                <textarea rows="5" name="messagenote" class="easyui-validatebox" data-options="required:true" style="width: 350px;height: 150px"></textarea>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/order.js"></script>
</body>
</html>
