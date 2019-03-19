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
                    {field:'putForwardNo',title:'提现编号',width:150,align:'center',sortable:true},
                    {field:'wxName',title:'用户昵称',width:120,align:'center',sortable:true},
                    {field:'bankName',title:'银行名称',width:120,align:'center',sortable:true},
                    {field:'cardNum',title:'银行卡号',width:120,align:'center',sortable:true},
                    {field:'phone',title:'预留手机',width:120,align:'center',sortable:true},
                    {field:'money',title:'流水金额',width:80,align:'center',sortable:true},
                    {field:'createtime',title:'创建时间',width:150,align:'center',sortable:true},
                    {field:'status',title:'受理状态',width:120,align:'center',formatter:function(value,row,index){
                            if(value == 0){
                                return "未审核";
                            } else if(value == 1){
                                return "审核通过";
                            } else if(value == 2) {
                                return "审核不通过";
                            }
                        }
                    },
                    {field:'opts',title:'操作',width:120,align:'center',formatter:function(value,row,index){
                            var html ="";
                            if(row.status == "0"){
                                html = "<a href='#' onclick='WeiXin.putForward.audit("+row.id+")'>审核通过 </a>"
                                html += "<a href='#' onclick='WeiXin.putForward.auditDeny("+row.id+")'> 审核不通过</a>";
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
        auditDeny : function(id){
            $.messager.confirm('提示','确定审核驳回该提现申请?',function(r){
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