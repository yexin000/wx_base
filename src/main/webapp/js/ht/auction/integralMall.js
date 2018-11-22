$package('WeiXin.integralMall');

WeiXin.integralMall = function(){
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
          {field:'name',title:'商品名称',width:120,align:'center',sortable:true},
          {field:'consumeintegral',title:'商品积分',align:'center',width:120,sortable:true},
          {field:'stock',title:'商品库存',align:'center',width:120,sortable:true},
          {field:'endtime',title:'结束时间',width:120,align:'center',sortable:true},
          {field:'opts',title:'操作',width:220,align:'left',formatter:function(value,row,index){
              var html ="<a href='#' onclick='WeiXin.integralMall.resManage(\""+row.id + "\"," + "\"" + row.name +"\")'>图片管理("+row.picCount+")</a>";

              html +="  <a href='#' onclick='WeiXin.integralMall.showDescribe("+row.id+")'>查看介绍</a>";
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
      $("#dialog-div").show();
    },
    showDescribe:function (id){
      WeiXin.getById('getId.do',{id : id},function(result){
        $("#describe-win").dialog('open');
        $("#describeDlg").val(result.data.describes);
      });

    },
    refresh : function () {
      _box.handler.refresh();
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
  WeiXin.integralMall.init();
});