$package('WeiXin.order');

WeiXin.order = function(){
    var _box = null;
    var Grid = $('#data-list');
    var _this = {
        config:{
            action:{

            },
            event:{

            },
            dataGrid:{
                title:'参数列表',
                url:'dataList.do',
                columns:[[
                    {field:'id',checkbox:true},
                    {field:'orderType',title:'订单类型',width:70,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "竞拍";
                            } else if(value == 2){
                                return "买卖";
                            }
                        }
                    },
                    {field:'itemName',title:'商品名称',width:150,align:'center',sortable:true},
                    {field:'wxName',title:'用户昵称',width:120,align:'center',sortable:true},
                    {field:'orderMoney',title:'订单金额',width:70,align:'center',sortable:true},
                    {field:'transactionId',title:'微信支付订单号',width:170,align:'center',sortable:true},
                    {field:'addressName',title:'订单地址',width:150,align:'center',sortable:true},
                    {field:'createTime',title:'创建时间',width:130,align:'center',sortable:true},
                    {field:'status',title:'订单状态',width:70,align:'center',sortable:true,
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
                    {field:'invalidTime',title:'失效时间',width:120,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:120,align:'center',formatter:function(value,row,index){
                            var html ="";
                            var msgHtml = " <a href='#' onclick='WeiXin.order.sendMsg(\""+row.wxid+"\")'>发送通知</a>";
                            html += msgHtml;

                            return html;
                        }
                    }
                ]],
                toolbar:[
                    {}
                ]
            }
        },
        sendMsg : function(wxid){
            $("#msgWxid").val(wxid);
            $("#message-win").dialog({
              buttons:[
                {
                  text:'保存',
                  handler:WeiXin.order.sendMessage
                }
              ]
            });
            $("#message-win").dialog('open');
        },
        sendMessage : function () {
            if($("#messageForm").form('validate')){
              WeiXin.progress();
              $("#messageForm").attr('action','sendMessage.do');
              WeiXin.saveForm($("#messageForm"),function(data){
                WeiXin.closeProgress();
                $("#message-win").dialog('close');
                var param = $("#searchForm").serializeObject();
                Grid.datagrid('reload',param);
                $("#messageForm").resetForm();
                WeiXin.alert('提示','发送成功.','info');
              });
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