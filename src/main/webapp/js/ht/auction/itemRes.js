$package('WeiXin.itemRes');

WeiXin.itemRes = function(){
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
                    $("#dlg").hide();
                    $('#conTypes').combobox('select',$('#conTypes').combobox('getValue'));
                },
                edit:function(){
                    _box.handler.edit(function(result){
                    });
                    $("#dlg").hide();
                }
            },
            dataGrid:{
                title:'参数列表',
                url:'dataList.do',
                queryParams: {
                    conid: $("#conidVal").val(),
                    conType : $("#conTypeVal").val()
                },
                columns:[[
                    {field:'id',checkbox:true},
                    {field:'conName',title:'对象名称',align:'center',width:120,sortable:true},
                    {field:'conType',title:'图片类型',align:'center',width:120,sortable:true},
                    {field:'idx',title:'排序',width:80,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:160,align:'center',formatter:function(value,row,index){
                            var html = "";
                            if(null != row.path && "" != row.path) {
                                var viewHtml = "  <a href='#' onclick='WeiXin.itemRes.showImage(\""+ urls.msUrl + "/"+ row.path +"\")'>查看图片</a>";
                                html += viewHtml
                            }
                            return html;
                        }
                    }
                ]],
                toolbar:[
                    {id:'btnadd',text:'添加',btnType:'add'},
                    {id:'btnedit',text:'编辑',btnType:'edit'},
                    {id:'btndelete',text:'删除',btnType:'remove'},
                    {
                        id:'btnback',
                        text:'返回',
                        iconCls:'icon-back',
                        handler:function(){
                            var conType = $('#conTypes').combobox('getValue');
                            if(conType == '1') {
                                $("#orgForm").attr("action", urls.msUrl + "/auction/list.do");
                            } else if(conType == '2') {
                                $("#orgForm").attr("action", urls.msUrl + "/auctionItem/list.do");
                            } else if(conType == '5') {
                              $("#orgForm").attr("action", urls.msUrl + "/integralMall/list.do");
                            }
                            $("#orgForm").submit();
                        }
                    }
                ]
            }
        },
        init:function(){
            _box = new YDataGrid(_this.config);
            _box.init();

            $("#conTypes").combobox('disable');
        },
        uploadLogo:function (businessid){
            $("#businessid").val(businessid);
            $("#edit-portrait").click();
        },
        showImage : function (imgUrl) {
            $("#dlg").show();
            _box.showImage(imgUrl);
        }
    }
    return _this;
}();

$(function(){
    WeiXin.itemRes.init();
});