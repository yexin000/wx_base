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
            <div class="ftitle">商品信息</div>
            <div class="fitem">
                <label>商品名称:</label>
                <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
            </div>
            <div class="fitem">
                <label>商品积分:</label>
                <input class="easyui-numberbox" type="text" name="consumeintegral" data-options="min:0">
            </div>
            <div class="fitem">
                <label>商品库存:</label>
                <input class="easyui-numberbox" type="text" name="stock" data-options="min:0">
            </div>
            <div class="fitem">
                <label>结束时间:</label>
                <input type="text" name="endtime" editable="false" class="easyui-datetimebox" data-options="required:true">
            </div>
        </div>
    </form>
    <form  id="portraitform" method="post" enctype="multipart/form-data" action="uploadLogo.do" style="display: none">
        <input type="file" accept="image/*" name="headImg" id="edit-portrait">
        <input type="hidden" id="integralid" name="integralid">
        <input type="submit">
    </form>
    <div id="dlg">
        <img id="simg" width="460px" height="280px"/>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/IntegralMallBack.js"></script>
</body>
</html>
