$package('WeiXin.identify');

WeiXin.identify = function(){
  var curImgIdx = 0;
  var imgList = null;
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
              var imgHtml = " <a href='#' onclick='WeiXin.identify.showImage("+row.id+")'>查看图片</a>";
              html += imgHtml;
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
    showImage : function (id) {
      WeiXin.getById('getId.do',{id : id},function(result){
        WeiXin.identify.curImgIdx = 0;
        WeiXin.identify.imgList = null;
        if(null != result.data.resList && result.data.resList.length > 0) {
          WeiXin.identify.imgList = result.data.resList;
          $("#dlg").show();
          _box.showImage(urls['msUrl'] + "/" + WeiXin.identify.imgList[WeiXin.identify.curImgIdx].path, 340);
        }
      });
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
    },
    nextImg : function (index) {
      if(null != WeiXin.identify.imgList) {
        if(index > 0) {
          if(WeiXin.identify.curImgIdx == WeiXin.identify.imgList.length) {
            WeiXin.identify.curImgIdx = 0;
          } else {
            WeiXin.identify.curImgIdx ++;
          }
        } else {
          if(WeiXin.identify.curImgIdx == 0) {
            WeiXin.identify.curImgIdx = WeiXin.identify.imgList.length - 1;
          } else {
            WeiXin.identify.curImgIdx --;
          }
        }
        $("#simg").attr("src",urls['msUrl'] + "/" + WeiXin.identify.imgList[WeiXin.identify.curImgIdx].path);
      }
    }
  }
  return _this;
}();

$(function(){
  WeiXin.identify.init();
});