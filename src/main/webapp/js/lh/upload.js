
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){
    loadMyUpload();
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

//加载我的上传
function loadMyUpload(){
    var AuctionItemModel = {};
    AuctionItemModel.uploadWxid = localStorage.getItem("openId");
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
                    str+='<tr onclick="toAuctionItemDetail('+obj.id+')" style="border-bottom: 1px solid #808080;">';
                    str+='  <td class="pro-item-M"><img src="../../../images/lh/wshop_indexbanner1.jpg"  alt=""></td>';
                    str+='  <td class="pro-item-H">';
                    str+='      <h2>'+obj.name+'</h2>';
                    str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                    str+='      <p><span>商品价格: </span><span style="overflow:hidden;  "> '+obj.startPrice +'<span> <span    style="float: right; padding: 0.03rem;border-radius:6px; ">审核通过</span></p>';
                    str+='  </td>';
                    str+='</tr>';
                });
            }
            $(".pro-item").append(str);
        }
    })
}



