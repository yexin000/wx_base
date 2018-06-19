$package('WeiXin.auctionItem');

WeiXin.auctionItem = function(){
    var _box = null;
    var Grid = $('#data-list');
    var _this = {
        config:{
            action:{
                save:'', //新增&修改 保存Action
                getId:'',//编辑获取的Action
                delele:''//删除数据的Action
            },
            event:{
                add : function(){
                    _box.handler.add();//调用add方法
                    $('#auctionids').combobox('select',$('#auctionids').combobox('getValue'));
                    $('#types').combobox('select',$('#types').combobox('getValue'));
                    $('#isshows').combobox('select',$('#isshows').combobox('getValue'));
                    $('#isShowBanners').combobox('select',$('#isShowBanners').combobox('getValue'));
                    $('#attributes').combobox('select',$('#attributes').combobox('getValue'));
                },
                edit:function(){
                    _box.handler.edit(function(result){
                    });
                }
            },
            dataGrid:{
                title:'参数列表',
                url:'dataList.do',
                columns:[[
                    {field:'id',checkbox:true},
                    {field:'name',title:'商品名称',width:120,align:'center',sortable:true},
                    {field:'typeName',title:'类型',width:60,align:'center',sortable:true},
                    {field:'description',title:'介绍描述',width:120,align:'center',sortable:true},
                    {field:'startTime',title:'开始拍卖时间',width:115,align:'center',sortable:true},
                    {field:'endTime',title:'结束拍卖时间',width:115,align:'center',sortable:true},
                    {field:'startPrice',title:'起拍价格',width:60,align:'center',sortable:true},
                    {field:'addPrice',title:'最低加价',width:60,align:'center',sortable:true},
                    {field:'curPrice',title:'当前价格',width:60,align:'center',sortable:true},
                    {field:'finalPrice',title:'成交价格',width:60,align:'center',sortable:true},
                    {field:'rate',title:'手续费比率%',width:75,align:'center',sortable:true},
                    {field:'detail',title:'拍品详情',width:120,align:'center',sortable:true},
                    {field:'standard',title:'拍品规格',width:60,align:'center',sortable:true},
                    {field:'age',title:'拍品年代',width:60,align:'center',sortable:true},
                    {field:'degree',title:'拍品等级',width:60,align:'center',sortable:true},
                    {field:'isShowBanner',title:'是否轮播',width:60,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    },
                    {field:'isShow',title:'首页显示',width:60,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    },
                    {field:'status',title:'状态',width:65,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "未审核";
                            } else if(value == 2){
                                return "审核未通过";
                            } else if(value == 3){
                                return "正常";
                            }
                        }
                    },
                    {field:'auctionStatus',title:'拍卖状态',width:60,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 0){
                                return "未开拍";
                            } else if(value == 1){
                                return "正在拍卖";
                            } else if(value == 2){
                                return "拍卖成功";
                            } else if(value == 3){
                                return "流拍";
                            }
                        }
                    },
                    {field:'auctionName',title:'拍卖会',width:160,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:180,align:'center',formatter:function(value,row,index){
                            var html ="<a href='#' onclick='WeiXin.auctionItem.resManage(\""+row.id + "\"," + "\"" + row.name +"\")'>图片管理("+row.picCount+")</a>";
                            if(row.status == "1"){
                                html += "<a href='#' onclick='WeiXin.auctionItem.audit("+row.id+")'> 审核通过</a>";
                                html += "<a href='#' onclick='WeiXin.auctionItem.auditDeny("+row.id+")'> 审核不通过</a>";
                            }
                            return html;
                        }
                    }
                ]],
                toolbar:[
                ]
            }
        },
        init:function(){
            _box = new YDataGrid(_this.config);
            _box.init();
            _this.initSelect();
        },
        initSelect:function() {
            $.post("/weixin/auction/dataList.do",{rows:100},function(data){
                if(data.total > 0) {
                    data.rows[0].selected = "true";
                    $('#auctionids').combobox({
                        data : data.rows,
                        valueField : 'id',
                        textField : 'name',
                        editable:false
                    });
                }
            });

            $.post("/weixin/wxCode/getAllAuctionItemSecondType.do",{rows:100},function(data){
                if(data.total > 0) {
                    data.rows[0].selected = "true";
                    $('#types').combobox({
                        data : data.rows,
                        valueField : 'code',
                        textField : 'name',
                        editable:false
                    });

                    var rows = data.rows;
                    rows[0].selected = "";
                    rows.splice(0,0,{'code':'','name':'全部','selected':'true'})
                    $('#querytypes').combobox({
                        data : rows,
                        valueField : 'code',
                        textField : 'name',
                        editable:false
                    });
                }
            });
        },
        resManage:function (auctionItemId, auctionItemName) {
            $("#conid").val(auctionItemId);
            $("#conName").val(auctionItemName);
            $("#resForm").submit();
        },
        audit : function(id){
            $.messager.confirm('提示','确定审核通过该商品上传申请?',function(r){
                if (r){
                    WeiXin.progress();
                    WeiXin.auditForm('audit.do',{'id':id},function(result){
                        WeiXin.closeProgress();
                        if(result.success){
                            WeiXin.alert('提示',result.msg);
                        }else{
                            WeiXin.alert('提示',data.msg,'error');
                        }
                        var param = $("#searchForm").serializeObject();
                        Grid.datagrid('reload',param);
                    });
                }
            });
        },
        auditDeny : function(id){
            $.messager.confirm('提示','确定审核驳回该商品上传申请?',function(r){
                if (r){
                    WeiXin.progress();
                    WeiXin.auditForm('auditDeny.do',{'id':id},function(result){
                        WeiXin.closeProgress();
                        if(result.success){
                            WeiXin.alert('提示',result.msg);
                        }else{
                            WeiXin.alert('提示',data.msg,'error');
                        }
                        var param = $("#searchForm").serializeObject();
                        Grid.datagrid('reload',param);
                    });
                }
            });
        },
    }
    return _this;
}();

$(function(){
    WeiXin.auctionItem.init();
});