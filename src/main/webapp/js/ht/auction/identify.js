$package('WeiXin.identify');

WeiXin.identify = function(){
  var _box = null;
  var Grid = $('#data-list');
  var _this = {
    config:{
      action:{

      },
      event:{

      },
      dataGrid:{
        title:'参数列表',
        url:'dataList.do',
        columns:[[
          {field:'id',checkbox:true},
          {field:'name',title:'鉴定物品名称',width:120,align:'center',sortable:true},
          {field:'description',title:'鉴定描述',width:240,align:'center',sortable:true},
          {field:'result',title:'鉴定结果',align:'center',width:240,sortable:true},
          {field:'createtime',title:'创建时间',width:150,align:'center',sortable:true},
          {field:'status',title:'状态',width:80,align:'center',sortable:true,
            formatter:function(value,row,index){
              if(value == 1){
                return "已鉴定";
              } else {
                return "鉴定中";
              }
            }
          },
          {field:'opts',title:'操作',width:150,align:'center',formatter:function(value,row,index){
              var html ="";
              if(row.status == 0) {
                var auditHtml = " <a href='#' onclick='WeiXin.identify.audit("+row.id+")'>鉴定</a>";
                html += auditHtml;
              }

              return html;
            }
          }
        ]],
        toolbar:[
          {}
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
    audit : function(id){
      $("#dlg").hide()
      var data = {};
      data.id = id;
      data.result = "";
      $("#identifyForm").form('load',data);
      $("#identify-win").dialog({
        buttons:[
          {
            text:'保存',
            handler:WeiXin.identify.identify
          },{
            text:'关闭',
            handler:$("#identify-win").dialog('close')
          }
        ]
      });
      $("#identify-win").dialog('open');
    },
    identify : function () {
      if($("#identifyForm").form('validate')){
        WeiXin.progress();
        $("#identifyForm").attr('action','identify.do');
        WeiXin.saveForm($("#identifyForm"),function(data){
          WeiXin.closeProgress();
          $("#identify-win").dialog('close');
          var param = $("#searchForm").serializeObject();
          Grid.datagrid('reload',param);
          $("#identifyForm").resetForm();
          WeiXin.alert('提示','鉴定成功.','info');
        });
      }
    }
  }
  return _this;
}();

$(function(){
  WeiXin.identify.init();
});