var orderStatus = 0;
var pageId = 1;
$(function(){
  $("#order-tit li").click(function(){
    orderStatus = $(this).attr("status");
    pageId =1;
    $(".pro-item").empty();
    loadOrder();
  })
  $('li').eq(0).click();
})

//加载个人订单数据
function loadOrder(){
  $('#loadingToast').show();
  $("#loadMore").hide();
  var OrderModel = {};
  OrderModel.status = orderStatus;
  OrderModel.wxid = localStorage.getItem("openId");
  OrderModel.page = pageId;
  var url= '/weixin/order/ajaxDataList.do';
  $.ajax({
    url: url,
    type: 'post',
    data: JSON.stringify(OrderModel) ,
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
          str+='<tr >  ';
          if(obj.businessName){
            str+=' <td  onclick="showBusiness('+obj.businessId+','+obj.itemId+')"> <span style="padding-left: 0.14rem;">'+obj.businessName+'商家 > </span>  </td> ';
          }else{
            str+=' <td  onclick="showBusiness('+obj.businessId+','+obj.itemId+')"> <span style="padding-left: 0.14rem;">未认证商家 > </span>  </td> ';
          }
          var statuName = '';
          if(obj.status == '1'){
            statuName = '订单已失效';
          }else if(obj.status == '2'){
            statuName = '订单待支付';
          }else if(obj.status == '3'){
            statuName = '订单已支付';
          }else if(obj.status == '4'){
            statuName = '订单已发货';
          }else if(obj.status == '5'){
            statuName = '订单已完成';
          }else if(obj.status == '6'){
            statuName = '退货退款中';
          }else if(obj.status == '7'){
            statuName = '已退货退款';
          }else{
            statuName = '订单已删除';
          }
          str+=' <td ><span style="float:right;padding-right: 0.2rem;color: #ff7936">'+statuName+'</span>  </td> </tr>';
          str+='<tr  >';

          str+='  <td class="pro-item-M">' ;
          str+='  <div class="itemDiv">' ;

          var loadClass = '';
          if(parseInt(obj.orderCoverImgHeight) > parseInt( obj.orderCoverImgWidth)){
            loadClass = 'height';
          }else{
            loadClass = 'width';
          }
          str+='      <img src="' + hostPath + obj.orderCoverImg +  '"   class="'+loadClass+'">'  ;
          str+='  </div>' ;
          str+='  </td>';


          str+='  <td class="pro-item-H">';
          str+='      <h2>'+obj.itemName+'</h2>';
          str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
          str+='      <p><span>订单价格: </span><span style="overflow:hidden;  "> '+obj.orderMoney +'<span> <span  onclick="goDetail(\''+obj.id+'\' ,'+obj.isMyUpload+')" style="float: right;border: 1px solid #808080;padding: 0.03rem;border-radius:6px; ">查看详情</span></p>';
          str+='  </td>';
          str+='</tr>';
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

function goDetail(orderId,isMyUpload) {
  window.location.href  = '../../html/lh/orderDetail.html?id='+orderId+'&isMyUpload='+isMyUpload;
}

function showBusiness(id,itemId){
  var url= '/weixin/business/ajaxGetIdByOrder.do?id='+id+'&itemId='+itemId;
  $.ajax({
    url: url,
    type: 'post',
    data:JSON.stringify(id),
    contentType : "application/json;charset=utf-8",
    dataType: 'JSON',
    cache: false,
    success:function(data){
      var dataObj = data.data;
      $("#bussinessname").empty().html(dataObj.bussinessName);
      $("#wxaccount").empty().html(dataObj.wxAccount);
      $("#businessaddress").empty().html(dataObj.businessaddress);
      $("#businessCard").show();
    }
  })

}