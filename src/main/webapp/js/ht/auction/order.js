$package('WeiXin.order');

WeiXin.order = function(){
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
                    {field:'orderType',title:'订单类型',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "拍卖";
                            } else if(value == 2){
                                return "买卖";
                            }
                        }
                    },
                    {field:'itemName',title:'商品名称',width:150,align:'center',sortable:true},
                    {field:'wxName',title:'用户昵称',width:120,align:'center',sortable:true},
                    {field:'orderMoney',title:'订单金额',width:80,align:'center',sortable:true},
                    {field:'addressName',title:'订单地址',width:150,align:'center',sortable:true},
                    {field:'createTime',title:'创建时间',width:150,align:'center',sortable:true},
                    {field:'status',title:'订单状态',width:120,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "失效";
                            } else if(value == 2){
                                return "待支付";
                            } else if(value == 3){
                                return "已支付";
                            }
                        }
                    },
                    {field:'payTime',title:'支付时间',width:120,align:'center',sortable:true},
                    {field:'invalidTime',title:'失效时间',width:120,align:'center',sortable:true}
                ]],
                toolbar:[
                ]
            }
        },
        init:function(){
            _box = new YDataGrid(_this.config);
            _box.init();
        }
    }
    return _this;
}();

$(function(){
    WeiXin.order.init();
});