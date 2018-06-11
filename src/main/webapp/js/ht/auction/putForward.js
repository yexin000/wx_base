$package('WeiXin.putForward');

WeiXin.putForward = function(){
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
                            debugger
                            if(row.status == "0"){
                                html ="<a href='#' onclick='WeiXin.putForward.toExamine("+row.id+")'>审核</a>";
                            }else{
                                html ="<a href='javaScript:void(0)'  >已审核</a>";
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
        },
        toExamine:function(id){
            debugger
            _box.form.search.resetForm();
            if(id){
                $('#examineId').val(id);
                $("#examineForm").submit();
            }
        }
    }
    return _this;
}();

$(function(){
    WeiXin.putForward.init();
});