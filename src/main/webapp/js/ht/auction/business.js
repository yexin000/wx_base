$package('WeiXin.business');

WeiXin.business = function(){
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
                    {field:'name',title:'商家名称',width:120,align:'center',sortable:true},
                    {field:'address',title:'商家地址',align:'center',width:240,sortable:true},
                    {field:'createTime',title:'创建时间',width:150,align:'center',sortable:true},
                    {field:'telNum',title:'商家电话',width:100,align:'center',sortable:true},
                    {field:'wxAccount',title:'商家微信',width:100,align:'center',sortable:true},
                    {field:'isExcellent',title:'优质商户',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 2){
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    },
                    {field:'isShow',title:'是否推荐',width:80,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    },
                    {field:'status',title:'状态',width:60,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 1){
                                return "正常";
                            }
                        }
                    },
                    {field:'auditStatus',title:'审核状态',width:60,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            if(value == 0){
                                return "未审核";
                            }else if(value == 1){
                                return "通过";
                            }else if(value == 2){
                                return "未通过";
                            }
                        }
                    },
                    {field:'opts',title:'操作',width:280,align:'left',formatter:function(value,row,index){
                            var html ="<a href='#' onclick='WeiXin.business.uploadLogo("+row.id+")'>上传封面</a>";
                            if(null != row.logoPath && "" != row.logoPath) {
                                var viewHtml = "  <a href='#' onclick='WeiXin.business.showImage(\""+ urls.msUrl + "/"+ row.logoPath +"\")'>查看封面</a>";
                                html += viewHtml
                            }
                            if(row.auditStatus == 0) {
                                var auditHtml = " <a href='#' onclick='WeiXin.business.audit("+row.id+")'>审核通过 </a>"
                                auditHtml += " <a href='#' onclick='WeiXin.business.auditDeny("+row.id+")'> 审核不通过</a>";
                                html += auditHtml;
                            } else if(row.auditStatus == 1 && row.isExcellent == 1) {
                                var excellentHtml = " <a href='#' onclick='WeiXin.business.excellent("+row.id+")'>优质商户通过 </a>"
                                excellentHtml += " <a href='#' onclick='WeiXin.business.excellentDeny("+row.id+")'> 优质商户不通过</a>";
                                html += excellentHtml;
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
                                    WeiXin.business.refresh();
                                }
                            });
                        }
                    }

                    return false;
                }
            );
        },
        uploadLogo:function (businessid){
            $("#businessid").val(businessid);
            $("#edit-portrait").click();
        },
        showImage : function (imgUrl) {
            $("#dlg").show();
            _box.showImage(imgUrl);
        },
        refresh : function () {
            _box.handler.refresh();
        },
        excellent : function(id) {
          $.messager.confirm('提示','确定审核通过该商家成为优质商户?',function(r){
            if (r){
              WeiXin.progress();
              WeiXin.auditForm('auditExcellent.do',{'id':id, 'result':'2'},function(result){
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
        excellentDeny : function(id) {
          $.messager.confirm('提示','确定审核驳回该商家成为优质商户?',function(r){
            if (r){
              WeiXin.progress();
              WeiXin.auditForm('auditExcellent.do',{'id':id, 'result':'0'},function(result){
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
        audit : function(id){
            $.messager.confirm('提示','确定审核通过该商家加入申请?',function(r){
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
            $.messager.confirm('提示','确定审核驳回该商家加入申请?',function(r){
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
        }
    }
    return _this;
}();

$(function(){
    WeiXin.business.init();
});