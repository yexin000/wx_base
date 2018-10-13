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
<div id="identify-win" class="easyui-dialog" title="鉴定" data-options="closed:true,iconCls:'',modal:true"  style="width:400px;height:280px;">
    <form id="identifyForm" class="ui-form" method="post">
        <input class="hidden" name="id">
        <div class="ui-edit">
            <label>鉴定结果:</label>
            <div class="fitem">
                <textarea rows="5" name="result" class="easyui-validatebox" data-options="required:true" style="width: 350px;height: 150px"></textarea>
            </div>
        </div>
    </form>
    <div id="dlg">
        <img id="simg" width="460px" height="280px"/>
        <a href="#" style="font-size: 16px;" onclick="WeiXin.identify.nextImg(-1);">上一张</a>
        <a href="#" style="font-size: 16px;float: right" onclick="WeiXin.identify.nextImg(1);">下一张</a>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/identify.js"></script>
</body>
</html>
