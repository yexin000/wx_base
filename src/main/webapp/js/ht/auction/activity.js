$package('WeiXin.activity');

WeiXin.activity = function(){
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
                    $("#dlg").hide();
                    $('#isShows').combobox('select',$('#isShows').combobox('getValue'));
                },
                edit:function(){
                    _box.handler.edit(function(result){
                    });
                    $("#dlg").hide();
                    $('#isShows').combobox('select',$('#isShows').combobox('getValue'));
                }
            },
            dataGrid:{
                title:'参数列表',
                url:'dataList.do',
                columns:[[
                    {field:'id',checkbox:true},
                    {field:'name',title:'活动名称',width:120,align:'center',sortable:true},
                    {field:'money',title:'活动金额',align:'center',width:120,sortable:true},
                    {field:'starttime',title:'开始时间',width:150,align:'center',sortable:true},
                    {field:'endtime',title:'结束时间',width:120,align:'center',sortable:true},
                    {field:'description',title:'活动内容',width:280,align:'center',sortable:true},
                    {field:'status',title:'活动状态',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 0){
                                return "删除";
                            }else if(value == 1){
                                return "正常";
                            }else if(value == 2){
                                return "未开始";
                            }else if(value == 3){
                                return "已开始";
                            }else if(value == 4){
                                return "已结束";
                            }
                        }
                    },
                    {field:'opts',title:'操作',width:220,align:'left',formatter:function(value,row,index){
                            var html ="<a href='#' onclick='WeiXin.activity.showDescribe("+row.id+")'>查看活动内容</a>";
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
        showDescribe:function (id){
          WeiXin.getById('getId.do',{id : id},function(result){
            $("#describe-win").dialog('open');
            $("#describeDlg").val(result.data.description);
          });

        },
        refresh : function () {
            _box.handler.refresh();
        }
    }
    return _this;
}();

$(function(){
    WeiXin.activity.init();
});