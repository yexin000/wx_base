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
                                return "商品竞拍";
                            } else if(value == 2){
                                return "商品购买";
                            } else if(value == 3) {
                                return "充值";
                            }  else if(value == 4) {
                                return "报名活动";
                            } else if(value == 5) {
                                return "鉴定支付";
                            } else if(value == 6) {
                                return "V5支付";
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
                            } else if(value == 4){
                                return "已发货";
                            } else if(value == 5){
                                return "已完成";
                            } else if(value == 6){
                                return "退货中";
                            } else if(value == 7){
                                return "已退货";
                            }
                        }
                    },
                    {field:'payTime',title:'支付时间',width:120,align:'center',sortable:true},
                    {field:'ydbh',title:'运单编号',width:100,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:120,align:'center',formatter:function(value,row,index){
                            var html ="";
                            var msgHtml = " <a href='#' onclick='WeiXin.order.sendMsg(\""+row.wxid+"\")'>发送通知</a>";
                            html += msgHtml;
                            if(row.status == 4 && row.ydbh != null && row.ydbh != "") {
                              var confirmHtml = " <a href='#' onclick='WeiXin.order.confirmOrder(\""+row.id+"\",\"" + row.wxid + "\")'> 确认订单</a>";
                              html += confirmHtml;
                            }

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

        confirmOrder : function(orderId, wxid) {
            $.messager.confirm('提示','确定完成此订单?',function(r){
                if (r){
                    WeiXin.progress();
                    WeiXin.auditForm('confirmationOrder.do',{'id':orderId, 'wxid':wxid},function(result){
                        WeiXin.closeProgress();
                        if(result.success){
                            WeiXin.alert('提示',"操作成功");
                        }else{
                            WeiXin.alert('提示',"操作失败",'error');
                        }
                        var param = $("#searchForm").serializeObject();
                        Grid.datagrid('reload',param);
                    });
                }
            });
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