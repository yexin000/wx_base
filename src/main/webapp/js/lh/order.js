
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
                        str+='<tr style="padding-top: 1rem;">  ';
                        str+=' <td> <span style="padding-left: 0.14rem;">'+obj.businessName+'</span>  </td> ';
                        var statuName = '';
                        if(obj.status == '1'){
                            statuName = '订单已失效';
                        }else if(obj.status == '2'){
                            statuName = '订单待支付';
                        }else if(obj.status == '3'){
                            statuName = '订单已支付';
                        }else if(obj.status == '4'){
                            statuName = '订单已发货';
                        }else{
                            //0
                            statuName = '订单已删除';
                        }
                        str+=' <td ><span style="float:right;padding-right: 0.2rem;color: #ff7936">'+statuName+'</span>  </td> </tr>';
                        str+='</tr>';
                        str+='<tr  >';
                        str+='  <td class="pro-item-M"><img src="../../../images/lh/wshop_indexbanner1.jpg"  alt=""></td>';
                        str+='  <td class="pro-item-H">';
                        str+='      <h2>'+obj.itemName+'</h2>';
                        str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                        str+='      <p><span>订单价格: </span><span style="overflow:hidden;  "> '+obj.orderMoney +'<span> <span  onclick="goDetail(\'+obj.id+\')" style="float: right;border: 1px solid #808080;padding: 0.03rem;border-radius:6px; ">查看详情</span></p>';
                        str+='  </td>';
                        str+='</tr>';
                    });
                }
                $(".pro-item").append(str);
            }
        })
    }