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
            <label class="ui-label">性质:</label>
            <select class="easyui-combobox" name="attribute" data-options="required:true" style="width: 60px;">
                <option value="" selected="selected">全部</option>
                <option value="0">拍卖品</option>
                <option value="1">商品</option>
            </select>
            <label class="ui-label">类型:</label>
            <select class="easyui-combobox" name="type" id="querytypes" data-options="required:true" style="width: 100px;">
                <option value="" selected="selected">请选择</option>
            </select>
            <label class="ui-label">状态:</label>
            <select class="easyui-combobox" name="status" data-options="required:true" style="width: 80px;">
                <option value="" selected="selected">全部</option>
                <option value="1">未审核</option>
                <option value="2">审核未通过</option>
                <option value="3">正常</option>
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
<div id="edit-win" class="easyui-dialog" title="编辑菜单" data-options="closed:true,iconCls:'',modal:true"  style="width:400px;height:380px;">
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
                <label>性质:</label>
                <select class="easyui-combobox" name="attribute" id="attributes" data-options="required:true" style="width: 80px;">
                    <option value="">请选择</option>
                    <option value="0">拍卖品</option>
                    <option value="1">商品</option>
                    <option value="3" selected="selected">V5商品</option>
                </select>
            </div>
            <div class="fitem">
                <label>类型:</label>
                <select class="easyui-combobox" name="type" id="types" data-options="required:true" style="width: 156px;">
                </select>
            </div>
            <div class="fitem auction-item-div">
                <label>开始拍卖时间:</label>
                <input type="text" id="startTime" name="startTime" editable="false" class="easyui-datetimebox" data-options="required:true">
            </div>
            <div class="fitem auction-item-div">
                <label>结束拍卖时间:</label>
                <input type="text" id="endTime" name="endTime" editable="false" class="easyui-datetimebox" data-options="required:true">
            </div>
            <div class="fitem">
                <label id="priceText">起拍价格:</label>
                <input class="easyui-validatebox" type="text" name="startPrice" value="0" data-options="required:true">
            </div>
            <div class="fitem auction-item-div">
                <label>最低加价:</label>
                <input class="easyui-validatebox" type="text" name="addPrice" value="0" data-options="required:true">
            </div>
            <div class="fitem auction-item-div">
                <label>手续费比率:</label>
                <input class="easyui-validatebox" type="text" name="rate" data-options="required:true" value="6">
            </div>
            <div class="fitem auction-item-div">
                <label>首页展示:</label>
                <select class="easyui-combobox" name="isShow" id="isshows" data-options="required:true">
                    <option value="0" selected="selected">否</option>
                    <option value="1">是</option>
                </select>
            </div>
            <div class="fitem auction-item-div">
                <label>是否轮播:</label>
                <select class="easyui-combobox" name="isShowBanner" id="isShowBanners" data-options="required:true">
                    <option value="0" selected="selected">否</option>
                    <option value="1">是</option>
                </select>
            </div>
            <div class="fitem v5-item-div">
                <label>是否V5轮播:</label>
                <select class="easyui-combobox" name="isV5Show" id="isV5Show" data-options="required:true">
                    <option value="0" selected="selected">否</option>
                    <option value="1">是</option>
                </select>
            </div>
            <div class="fitem auction-item-div">
                <label>拍卖会:</label>
                <select class="easyui-combobox" name="auctionId" id="auctionids" data-options="required:true" style="width: 156px;">
                </select>
            </div>
            <div class="fitem">
                <label>介绍描述:</label>
                <input class="easyui-validatebox" type="text" name="description" data-options="required:true">
            </div>
            <div class="fitem">
                <label>拍品详情:</label>
                <textarea rows="3" name="detail" class="easyui-validatebox" data-options="required:true" style="width: 157px;"></textarea>
            </div>
        </div>
    </form>
    <form id="resForm" class="ui-form" method="post" action="/weixin/itemRes/list.do">
        <input class="hidden" name="conid" id="conid">
        <input class="hidden" name="conName" id="conName">
        <input class="hidden" name="conType" id="conType" value="2">
    </form>
</div>
<script type="text/javascript" src="${msUrl}/js/ht/auction/auctionItem.js"></script>
</body>
</html>
