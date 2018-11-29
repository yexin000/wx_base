var pageId = 1;
var isRef = true;
$(function(){
  loadData();
})

function loadData()
{
  $("#loadMore").hide();
  pageId =1;
  var AuctionModel = {};
  var url= '/weixin/wxCode/getAuctionItemType.do';
  $.ajax({
    url: url,
    type: 'post',
    data: JSON.stringify(AuctionModel) ,
    dataType: 'JSON',
    contentType : "application/json;charset=utf-8",
    cache: false,
    success:function(data){
      var dataList1 = data.rows1;
      if(dataList1.length> 0)
      {
        var str1 = '';
        str1+='<p class="scrollItem scrollItemChecked firstType" onclick="loadType2Date(\'\',this);">' + '全部' +'</p>';
        $.each(dataList1,function(i,obj){
          str1+='<p class="scrollItem firstType" id="'+obj.code+'" onclick="loadType2Date(\''+obj.code+'\', this)">' + obj.name;
          str1+='</p>';
        });
        $("#dataList1").append(str1);
      }
      loadAuctionItem();
    }
  })
}

function loadType2Date(code, obj)
{
  isRef = true;
  pageId = 1;
  $("#loadMore").hide();
  $(".firstType").removeClass("scrollItemChecked");
  $(obj).addClass("scrollItemChecked");
  if(!code) {
    $("#dataList2").empty();
    loadAuctionItem();
    return;
  }
  var url= '/weixin/wxCode/getAuctionItemSecondType.do?code='+code;
  $.ajax({
    url: url,
    type: 'post',
    data: {},
    dataType: 'JSON',
    contentType : "application/json;charset=utf-8",
    cache: false,
    success:function(data){
      var dataList2 = data.rows;
      if(dataList2.length> 0)
      {
        var str2 = '';
        $.each(dataList2,function(i,obj){
          str2+='<p class="scrollItem2 secondType" id="'+obj.code+'" onclick="loadAuctionItem(\''+obj.code+'\', this,true)">' + '<span>'+obj.name+'</span>';
          str2+='</p>';
        });
        $("#dataList2").empty();
        $("#dataList2").append(str2);
        $(".pro-item").empty();
        if(dataList2[0].id)
        {
          type = dataList2[0].code;
          loadAuctionItem(type, $(".scrollItem2").get(0));
        }
      }
    }
  })
}

function searchCallback() {
  var type1 = $("#dataList2 .scrollItemChecked").data("id");
  var type2 = $("#dataList2 .scrollItemChecked");
  loadAuctionItem(type1, type2, true);
}

//根据类型加载拍品数据
function loadAuctionItem(type1, type2,isRefByType){
  if(isRefByType){
    isRef = isRefByType;
  }
  if(isRef){
    $(".pro-item").empty();
    pageId = 1;
  }
  isRef = false;
  $('#loadingToast').show();
  $(".secondType").removeClass("scrollItemChecked");
  $(type2).addClass("scrollItemChecked");
  var AuctionItemModel = {};
  AuctionItemModel.type = type1;
  AuctionItemModel.name =  $("#shoppingName").val();
  AuctionItemModel.status = "3";
  AuctionItemModel.page = pageId;
  AuctionItemModel.attributes = ['0', '1'];
  AuctionItemModel.isOnsale = "1";
  var url= '/weixin/auctionItem/ajaxDataList.do';
  $.ajax({
    url: url,
    type: 'post',
    data: JSON.stringify(AuctionItemModel) ,
    dataType: 'JSON',
    contentType : "application/json;charset=utf-8",
    cache: false,
    success:function(data){
      $('#loadingToast').hide();
      var dataList = data.rows;
      var datalength = data.total;
      if(dataList.length> 0)
      {
        var str = '';
        $.each(dataList,function(i,obj){
          var coverImage = '/weixin/foreground/images/no-image.jpg';
          if(obj.resList != null && obj.resList.length > 0) {
            coverImage = hostPath + obj.resList[0].path;
          }

          var price = obj.curPrice;
          if(price <= 0) {
            price = obj.startPrice;
          }

          var endTime = obj.endTime;
          if(obj.attribute != '0') {
            endTime = '';
          }
          str += '<a href="javascript:toAuctionItemDetail('+obj.id+','+obj.attribute+');" class="weui-grid1" style="position: initial;padding: 10px 0px;">'
              +       '<div class="mystore-auctionitem-div">'
              +           '<div class="weui-grid__icon" style="width: 90%;height: 150px;">'
              +               '<img src="' + coverImage + '" alt="">'
              +           '</div>'
              +           '<p class="weui-grid__label auctionitem-auctionitem-label">名称:' + obj.name + '</p>'
              +           '<p class="weui-grid__label auctionitem-auctionitem-label">作者:' + obj.uploader + '</p>'
              +           '<p class="weui-grid__label auctionitem-auctionitem-label">价格:' + price + '</p>'
              +           '<p class="weui-grid__label auctionitem-auctionitem-label">结束时间:' + endTime + '</p>'
              +       '</div>'
              +   '</a>';

        });
      }
      $(".pro-item").append(str);
      if(datalength <= (pageId * 10)){
        $("#loadMore").hide();
      }else{
        $("#loadMore").show();
      }
      pageId++;
    }
  })
}

