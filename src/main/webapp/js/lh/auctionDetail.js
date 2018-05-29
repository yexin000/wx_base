
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){	
	
	$("#backtop").hide()
    var id = getParam("id");
    loadAuctionData(id);
    loadAuctionItem(id);
})

//加载会展数据
function loadAuctionData(id){
    var url= '/weixin/auction/getId.do?id='+id;
    $.ajax({
        url: url,
        type: 'post',
        data:{},
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            var dataObj = data.data;
            $("#businessName").html( dataObj.name);
            //先处理商品轮播
            var dataList = dataObj.resList;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    var coverimg = obj.path;
                    str+='<li class="bannerItem">';
                    str+='	<a href="javaScipt:void(0)">';
                    str+=' <img src="' + hostPath + coverimg +  '" alt="">';
                    str+=' </a></li>';
                });
                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
        }
    })
}


//加载会展拍品数据
function loadAuctionItem(id){
    var AuctionItemModel = {};
    AuctionItemModel.auctionId = id;
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
                $("#shoppingCount").html(dataList.length);
                var str = '';
                $.each(dataList,function(i,obj){

                    str+='<tr onclick="toAuctionItemDetail('+obj.id+')">';
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
