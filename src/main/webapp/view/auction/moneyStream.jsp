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
            <label class="ui-label">流水号:</label>
            <input name="flowNumber" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">流水类型:</label>
            <!--1-保证金,2-支付,3-提现,4-退款,5-充值 -->
            <select class="easyui-combobox" name="streamType" id="streamType" data-options="required:true">
                <option value="" selected="selected">请选择</option>
                <option value="1">保证金</option>
                <option value="2">支付</option>
                <option value="3">提现</option>
                <option value="5">充值</option>
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

<script type="text/javascript" src="${msUrl}/js/ht/auction/moneyStream.js"></script>
</body>
</html>
