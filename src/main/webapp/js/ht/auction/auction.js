$package('WeiXin.auction');

WeiXin.auction = function(){
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
                    $('#businessids').combobox('select',$('#businessids').combobox('getValue'));
                    $('#types').combobox('select',$('#types').combobox('getValue'));
                    $('#isShows').combobox('select',$('#isShows').combobox('getValue'));
                },
                edit:function(){
                    _box.handler.edit(function(result){
                    });
                    $('#isShows').combobox('select',$('#isShows').combobox('getValue'));
                }
            },
            dataGrid:{
                title:'参数列表',
                url:'dataList.do',
                columns:[[
                    {field:'id',checkbox:true},
                    {field:'name',title:'拍卖会名称',width:120,align:'center',sortable:true},
                    {field:'starttime',title:'开始时间',width:150,align:'center',sortable:true},
                    {field:'endtime',title:'结束时间',width:150,align:'center',sortable:true},
                    {field:'modifytime',title:'修改时间',width:150,align:'center',sortable:true},
                    {field:'modifierName',title:'修改人',width:120,align:'center',sortable:true},
                    {field:'businessName',title:'所属商家',width:120,align:'center',sortable:true},
                    {field:'isShow',title:'首页显示',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    },
                    {field:'status',title:'状态',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "正常";
                            } else if(value == 2){
                                return "进行中";
                            } else if(value == 3) {
                                return "已结束";
                            }
                        }
                    },
                    {field:'viewnum',title:'浏览数量',width:80,align:'center',sortable:true},
                    {field:'typeName',title:'类型',width:80,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:120,align:'center',formatter:function(value,row,index){
                            var html ="<a href='#' onclick='WeiXin.auction.resManage(\""+row.id + "\"," + "\"" + row.name +"\")'>图片管理("+row.picCount+")</a>";
                            return html;
                        }}
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
            $.post("/weixin/business/dataList.do",{rows:100},function(data){
                if(data.total > 0) {
                    data.rows[0].selected = "true";
                    $('#businessids').combobox({
                        data : data.rows,
                        valueField : 'id',
                        textField : 'name',
                        editable:false
                    });
                }
            });

            $.post("/weixin/wxCode/getAuctionItemType.do",{rows:100},function(data){
                if(data.total > 0) {
                    data.rows1[0].selected = "true";
                    $('#types').combobox({
                        data : data.rows1,
                        valueField : 'code',
                        textField : 'name',
                        editable:false
                    });
                }
            });

        },
        resManage:function (auctionId, auctionName) {
            $("#conid").val(auctionId);
            $("#conName").val(auctionName);
            $("#resForm").submit();
        }
    }
    return _this;
}();

$(function(){
    WeiXin.auction.init();
});