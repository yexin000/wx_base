$package('WeiXin.business');

WeiXin.business = function(){
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
                    {field:'name',title:'商家名称',width:120,align:'center',sortable:true},
                    {field:'address',title:'商家地址',align:'center',width:240,sortable:true},
                    {field:'createTime',title:'创建时间',width:150,align:'center',sortable:true},
                    {field:'updateTime',title:'修改时间',width:150,align:'center',sortable:true},
                    {field:'telNum',title:'商家电话',width:120,align:'center',sortable:true},
                    {field:'status',title:'状态',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "正常";
                            }
                        }
                    },
                    {field:'opts',title:'操作',width:120,align:'center',formatter:function(value,row,index){
                            //var html ="<a href='#' onclick='WeiXin.wxCode.toList("+row.id+")'>参数管理("+row.subCount+")</a>";
                            return '';
                        }}
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
    WeiXin.business.init();
});