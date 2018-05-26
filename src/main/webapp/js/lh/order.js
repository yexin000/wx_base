
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){	
	


	$(window).scroll(function(){
        var scrolltop=$(document).scrollTop();
        var Vheight=$(window).height();
        if(scrolltop > 0){
            $("#backtop").show(); 
            }else{
                 $("#backtop").hide();
                }      
    });
	$("#backtop").click(function(){
		$("html,body").animate({scrollTop:0},"fast");
	})
    $("#order-tit li").click(function(){
       var selectStatus = $(this).attr("status");
        loadOrder(selectStatus);
    })

    $('li').eq(0).click();
})

    //加载个人订单数据
    function loadOrder(status){
        $(".pro-item").empty();
        var OrderModel = {};
        OrderModel.status = status;
        OrderModel.wxid = sessionStorage.getItem("openId");
        var url= '/weixin/order/ajaxDataList.do';
        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(OrderModel) ,
            dataType: 'JSON',
            contentType : "application/json;charset=utf-8",
            cache: false,
            success:function(data){
                var dataList = data.rows;
                if(dataList.length> 0)
                {
                    var str = '';
                    $.each(dataList,function(i,obj){
                        str+='<tr onclick="toAuctionItemDetail('+obj.id+')">';
                        str+='  <td class="pro-item-M"><img src="../../../images/lh/wshop_indexbanner1.jpg"  alt=""></td>';
                        str+='  <td class="pro-item-H">';
                        str+='      <h2>'+obj.itemName+'</h2>';
                        str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                        str+='      <p><span>订单价格: </span><span style="overflow:hidden;  "> '+obj.orderMoney +'<span></p>';
                        str+='  </td>';
                        str+='</tr>';
                    });
                }
                $(".pro-item").append(str);
            }
        })
    }