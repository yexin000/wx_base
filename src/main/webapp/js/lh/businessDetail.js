
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){	
	
	$("#backtop").hide()
    var id = getParam("id");
    loadfindData(id);
    loadBusinessAuctionItem(id);
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
	
})

//加载商家数据
function loadfindData(id){

    var url= '/weixin/business/ajaxGetId.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(id),
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var dataObj = data.data;
            $("#businessImg").attr("src", hostPath + dataObj.logoPath);
            $("#businessName").html( dataObj.name);
            $("#shoppingCount").html( dataObj.itemCount);

            $("#dataDiv").append(dataObj);
        }
    })
}


//加载商家拍品数据
function loadBusinessAuctionItem(id){
    var AuctionItemModel = {};
    AuctionItemModel.businessId = id;
    var url= '/weixin/auctionItem/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(AuctionItemModel) ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){

                    str+='<tr>';
                    str+='  <td class="pro-item-M"><img src="../../../images/lh/wshop_indexbanner1.jpg"  alt=""></td>';
                    str+='  <td class="pro-item-H">';
                    str+='      <h2>'+obj.name+'</h2>';
                    str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                    str+='      <p><span>商品价格: </span><span style="overflow:hidden;  "> '+obj.startPrice +'<span></p>';
                    str+='      <p><span>商品销量: </span><span style="overflow:hidden;  "> '+obj.startPrice +'<span></p>';
                    str+='  </td>';
                    str+='</tr>';

                });
            }
            $(".pro-item").append(str);

        }
    })
}
