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
                            var html ="<a href='#' onclick='WeiXin.activity.uploadLogo("+row.id+")'>上传图片</a>";
                            if(null != row.activityBg && "" != row.activityBg) {
                              var viewHtml = "  <a href='#' onclick='WeiXin.activity.showImage(\""+ urls.msUrl + "/"+ row.activityBg +"\")'>查看图片</a>";
                              html += viewHtml
                            }
                            html +="  <a href='#' onclick='WeiXin.activity.showDescribe("+row.id+")'>查看活动内容</a>";
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
                        WeiXin.activity.refresh();
                      }
                    });
                  }
                }

                return false;
              }
          );
        },
        showDescribe:function (id){
          WeiXin.getById('getId.do',{id : id},function(result){
            $("#describe-win").dialog('open');
            $("#describeDlg").val(result.data.description);
          });

        },
        uploadLogo:function (activityid){
            $("#activityid").val(activityid);
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
    WeiXin.activity.init();
});