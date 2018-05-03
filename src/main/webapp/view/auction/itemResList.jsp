<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@include file="/view/resource.jsp" %>
</head>
<body class="easyui-layout">

<!--  Search panel end -->
<!-- DataList  -->
<div region="center" border="false" >
    <table id="data-list" ></table>
</div>

<!-- Edit Win&From -->
<input type="hidden" id="conidVal" value="${conid}">
<input type="hidden" id="conTypeVal" value="${conType}">
<div id="edit-win" class="easyui-dialog" title="编辑菜单" data-options="closed:true,iconCls:'',modal:true"  style="width:400px;height:280px;">
    <form id="editForm" class="ui-form" enctype="multipart/form-data" method="post">
        <!-- 隐藏文本框 -->
        <input class="hidden" name="id">
        <input type="hidden" id="conid" name="conid" value="${conid}">
        <div class="ui-edit">
            <div class="ftitle">图片信息</div>
            <div class="fitem">
                <label>对象名称:</label>
                <input class="easyui-validatebox" type="text" name="conName" data-options="required:true">
            </div>

            <div class="fitem">
                <label>对象类型:</label>
                <select class="easyui-combobox" name="conType" id="conTypes" data-options="required:true">
                    <option value="1" selected="selected">拍卖会</option>
                    <option value="2">拍卖品</option>
                </select>
            </div>
            <div class="fitem">
                <label>排序:</label>
                <input class="easyui-numberbox" type="text" value="0" name="idx" data-options="required:true,min:0,max:999">
            </div>
            <div class="fitem">
                <label>图片:</label>
                <input type="file" accept="image/*" name="picFile" id="edit-portrait">
            </div>
        </div>
    </form>

    <form id="searchForm" style="display: none;">
        <input name="conid" value="${conid}">
        <input name="conType" value="${conType}">
    </form>
</div>

<script type="text/javascript" src="${msUrl}/js/ht/auction/itemRes.js"></script>
</body>
</html>
