$package('WeiXin.putForward');

WeiXin.putForward = function(){
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
                    {field:'streamtype',title:'流水类型',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "保证金";
                            } else if(value == 2){
                                return "支付";
                            }else if(value == 3){
                                return "提现";
                            }else if(value == 5){
                                return "充值";
                            }
                        }
                    },
                    {field:'flownumber',title:'提现编号',width:150,align:'center',sortable:true},
                    {field:'wxName',title:'用户昵称',width:120,align:'center',sortable:true},
                    {field:'whereabouts',title:'微信号',width:120,align:'center',sortable:true},
                    {field:'streammoney',title:'流水金额',width:80,align:'center',sortable:true},
                    {field:'createtime',title:'创建时间',width:150,align:'center',sortable:true},
                    {field:'status',title:'受理状态',width:120,align:'center',formatter:function(value,row,index){
                            var html ="";
                            if(row.status == "0"){
                                html ="<a href='#' onclick='WeiXin.putForward.audit("+row.id+")'>审核</a>";
                            } else if(row.status == "1"){
                                html ="<a href='javaScript:void(0)'>审核通过</a>";
                            } else if(row.status == "2"){
                                html ="<a href='javaScript:void(0)'>审核不通过</a>";
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
        init:function(){
            _box = new YDataGrid(_this.config);
            _box.init();
        },
        toExamine:function(id){
            _box.form.search.resetForm();
            if(id){
                $('#examineId').val(id);
                $("#examineForm").submit();
            }
        },
        audit : function(id){
            $.messager.confirm('提示','确定审核通过该提现申请?审核通过后请执行转账操作',function(r){
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
        checkSelect : function(rows){//检查grid是否有勾选的行, 有返回 true,没有返回true
            var records =  rows;
            if(records && records.length > 0){
                return true;
            }
            WeiXin.alert('警告','未选中记录.','warning');
            return false;

        }
    }
    return _this;
}();

$(function(){
    WeiXin.putForward.init();
});