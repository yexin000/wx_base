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
        <p class="ui-fields">
            <label class="ui-label">鉴定状态:</label>
            <select class="easyui-combobox" name="status" data-options="required:true" style="width: 80px;">
                <option value="" selected="selected">全部</option>
                <option value="0">未鉴定</option>
                <option value="1">已鉴定</option>
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
    <div id="dlg">
        <img id="simg" width="460px" height="280px"/>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/identify.js"></script>
</body>
</html>
