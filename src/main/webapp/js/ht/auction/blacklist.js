$package('WeiXin.blacklist');

WeiXin.blacklist = function(){
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
          {field:'wxid',title:'微信id',width:150,align:'center',sortable:true},
          {field:'nickname',title:'昵称',align:'center',width:120,sortable:true},
          {field:'status',title:'状态',width:80,align:'center',sortable:true,
            formatter:function(value,row,index){
              if(value == 1){
                return "黑名单";
              } else {
                return "正常";
              }
            }
          },
          {field:'opts',title:'操作',width:220,align:'left',formatter:function(value,row,index){
              var html ="";
              if(row.status == 0) {
                var auditHtml = " <a href='#' onclick='WeiXin.blacklist.addBlackList("+row.id+")'>加入黑名单</a>";
                html += auditHtml;
              } else {
                var auditHtml = " <a href='#' onclick='WeiXin.blacklist.removeBlackList("+row.id+")'>移除黑名单</a>";
                html += auditHtml;
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
    showImage : function (imgUrl) {
      $("#dlg").show();
      _box.showImage(imgUrl);
    },
    refresh : function () {
      _box.handler.refresh();
    },
    addBlackList : function(id){
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
    removeBlackList : function(id){
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
    }
  }
  return _this;
}();

$(function(){
  WeiXin.blacklist.init();
});