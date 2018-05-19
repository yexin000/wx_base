$package('WeiXin.middleMan');

WeiXin.middleMan = function(){
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
                    $('#grades').combobox('select',$('#grades').combobox('getValue'));
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
                columns:[[
                    {field:'id',checkbox:true},
                    {field:'grade',title:'级别',width:120,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "初级经纪人";
                            } else if(value == 2){
                                return "中级经纪人";
                            } else if(value == 3) {
                                return "高级经纪人";
                            }
                        }
                    },
                    {field:'wxAcount',title:'微信号',width:150,align:'center',sortable:true},
                    {field:'phoneNum',title:'经纪人电话',width:150,align:'center',sortable:true},
                    {field:'description',title:'修改时间',width:150,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:120,align:'left',formatter:function(value,row,index){
                            var html ="<a href='#' onclick='WeiXin.middleMan.uploadLogo("+row.id+")'>上传头像</a>";
                            if(null != row.avatarUrl && "" != row.avatarUrl) {
                                var viewHtml = "  <a href='#' onclick='WeiXin.middleMan.showImage(\""+ urls.msUrl + "/"+ row.avatarUrl +"\")'>查看头像</a>";
                                html += viewHtml;
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
            $("#edit-portrait").on("change",function(){
                $("#portraitform").submit();
            });

            $("#portraitform").submit(
                function() {
                    var f = document.getElementById("edit-portrait").value;
                    if(f == ""){
                        alert("请上传图片");
                    }else{
                        if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(f)){
                            alert("请选择图片文件");
                        }else{
                            var url=  $("#portraitform").attr("action");
                            var formData = new FormData($("#portraitform")[0]);
                            WeiXin.progress();
                            $.ajax({
                                url:url,
                                type: 'POST',
                                data: formData,
                                dataType: 'json',
                                cache: false,
                                processData: false,
                                contentType: false,
                                success:function(result){
                                    WeiXin.closeProgress();
                                    WeiXin.alert('提示',result.msg);
                                    WeiXin.middleMan.refresh();
                                }
                            });
                        }
                    }

                    return false;
                }
            );
        },
        uploadLogo:function (middleManId){
            $("#middleManId").val(middleManId);
            $("#edit-portrait").click();
        },
        showImage : function (imgUrl) {
            $("#dlg").show();
            _box.showImage(imgUrl);
        },
        refresh : function () {
            _box.handler.refresh();
        }
    }
    return _this;
}();

$(function(){
    WeiXin.middleMan.init();
});