
//图片访问路径
var imagePath = "http://127.0.toTypeAuction0.1:8080/";


$(function(){
    loadData();
})

function loadData()
{
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
            var dataList2 = data.rows2;

            if(dataList1.length> 0)
            {
                var str1 = '';
                $.each(dataList1,function(i,obj){
                    str1+='<p class="scrollItem" id="'+obj.id+'" onclick="loadType2Date(\''+obj.code+'\')">' + obj.name;
                    str1+='</p>';
                });
                $("#dataList1").append(str1);
            }

            if(dataList2.length> 0)
            {
                var str2 = '';
                $.each(dataList2,function(i,obj){
                    str2+='<p class="scrollItem2"  onclick="loadAuctionItem(\''+obj.code+'\')" >' + '<span>'+obj.name+'</span>';
                    str2+='</p>';
                });
                $("#dataList2").append(str2);
            }
            if(dataList2[0].id)
            {
                loadAuctionItem(dataList2[0].code);
            }

        }
    })
}

    function loadType2Date(code)
    {
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
                        str2+='<p class="scrollItem2" onclick="loadAuctionItem(\''+obj.code+'\')">' + '<span>'+obj.name+'</span>';
                        str2+='</p>';
                    });
                    $("#dataList2").empty();
                    $("#dataList2").append(str2);

                    if(dataList2[0].id)
                    {
                        loadAuctionItem(dataList2[0].code);
                    }
                }
            }
        })
    }


    //根据类型加载拍品数据
    function loadAuctionItem(type){
        var AuctionItemModel = {};
        AuctionItemModel.type = type;
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
                $(".pro-item").empty();
                $(".pro-item").append(str);
            }
        })
    }

