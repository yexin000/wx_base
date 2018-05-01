$package('WeiXin.auctionItem');

WeiXin.auctionItem = function(){
    var _box = null;
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
                    {field:'name',title:'拍品名称',width:120,align:'center',sortable:true},
                    {field:'type',title:'类型',width:40,align:'center',sortable:true},
                    {field:'description',title:'介绍描述',width:120,align:'center',sortable:true},
                    {field:'starttime',title:'开始拍卖时间',width:115,align:'center',sortable:true},
                    {field:'endtime',title:'结束拍卖时间',width:115,align:'center',sortable:true},
                    {field:'startprice',title:'起拍价格',width:60,align:'center',sortable:true},
                    {field:'addprice',title:'最低加价',width:60,align:'center',sortable:true},
                    {field:'curprice',title:'当前价格',width:60,align:'center',sortable:true},
                    {field:'finalprice',title:'成交价格',width:60,align:'center',sortable:true},
                    {field:'rate',title:'手续费比率',width:70,align:'center',sortable:true},
                    {field:'detail',title:'拍品详情',width:120,align:'center',sortable:true},
                    {field:'isshow',title:'首页显示',width:60,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    },
                    {field:'status',title:'状态',width:50,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "正常";
                            }
                        }
                    },
                    {field:'auctionstatus',title:'拍卖状态',width:60,align:'center',sortable:true},
                    {field:'auctionname',title:'拍卖会',width:100,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:100,align:'center',formatter:function(value,row,index){
                            //var html ="<a href='#' onclick='WeiXin.wxCode.toList("+row.id+")'>参数管理("+row.subCount+")</a>";
                            return '';
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

        }
    }
    return _this;
}();

$(function(){
    WeiXin.auctionItem.init();
});