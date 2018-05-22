$package('WeiXin.group');

WeiXin.group = function(){
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
                    {field:'groupName',title:'群名称',width:150,align:'center',sortable:true},
                    {field:'createTime',title:'创建时间',width:150,align:'center',sortable:true},
                    {field:'opts',title:'操作',width:240,align:'center',formatter:function(value,row,index){
                            var html ="<a href='#' onclick='WeiXin.group.uploadLogo("+row.id+")'>上传头像</a>" +
                                "  <a href='#' onclick='WeiXin.group.uploadCodePic("+row.id+")'>上传二维码</a>";
                            if(null != row.logoUrl && "" != row.logoUrl) {
                                var viewHtml = "  <a href='#' onclick='WeiXin.group.showImage(\""+ urls.msUrl + "/"+ row.logoUrl +"\")'>查看头像</a>";
                                html += viewHtml;
                            }
                            if(null != row.codeUrl && "" != row.codeUrl) {
                                var viewHtml = "  <a href='#' onclick='WeiXin.group.showImage(\""+ urls.msUrl + "/"+ row.codeUrl +"\")'>查看二维码</a>";
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
                                    WeiXin.group.refresh();
                                }
                            });
                        }
                    }

                    return false;
                }
            );
        },
        uploadLogo:function (groupId){
            $("#groupId").val(groupId);
            $("#uploadType").val("1");
            $("#edit-portrait").click();
        },
        uploadCodePic:function (groupId){
            $("#groupId").val(groupId);
            $("#uploadType").val("2");
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
    WeiXin.group.init();
});