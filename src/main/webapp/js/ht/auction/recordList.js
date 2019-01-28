$package('WeiXin.record');

WeiXin.record = function(){
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
        url:'recordDataList.do',
        queryParams: {
          icId: $("#icId").val()
        },
        columns:[[
          {field:'id',checkbox:true},
          {field:'icName',title:'商品名称',align:'center',width:120,sortable:true},
          {field:'wxidName',title:'兑换人',align:'center',width:120,sortable:true},
          {field:'num',title:'兑换数量',width:60,align:'center',sortable:true},
          {field:'createTime',title:'兑换时间',width:120,align:'center',sortable:true},
          {field:'opts',title:'操作',width:120,align:'left',formatter:function(value,row,index){
              var html = "";
              if(row.status == 0) {
                var auditHtml = " <a href='#' onclick='WeiXin.record.audit("+row.id+")'>确认处理</a>"
                html += auditHtml;
              }

              return html;
            }
          }
        ]],
        toolbar:[
          {},
          {
            id:'btnback',
            text:'返回',
            iconCls:'icon-back',
            handler:function(){
              $("#orgForm").attr("action", urls.msUrl + "/integralMall/list.do");
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
    audit : function(id){
      $.messager.confirm('提示','确定处理该积分兑换请求?',function(r){
        if (r){
          WeiXin.progress();
          WeiXin.auditForm('recordAudit.do',{'id':id},function(result){
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
  WeiXin.record.init();
});