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
                    str1+='<p class="scrollItem firstType" id="'+obj.id+'" onclick="loadType2Date(\''+obj.code+'\', this)">' + obj.name;
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
                        str2+='<p class="scrollItem2 secondType" onclick="loadAuctionItem(\''+obj.code+'\', this,true)">' + '<span>'+obj.name+'</span>';
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
                        var coverimg = '';
                        var coverimgWidth = '';
                        var coverimgHeight = '';
                        if(obj.resList && obj.resList.length > 0) {
                            coverimg = obj.resList[0].path;
                            coverimgWidth = obj.resList[0].width;
                            coverimgHeight = obj.resList[0].height;
                        }
                        str+='<tr onclick="toAuctionItemDetail('+obj.id+','+obj.attribute+')">';
                        str+='  <td class="pro-item-M">' ;
                        str+='  <div class="itemDiv">' ;

                        var loadClass = '';
                        if(parseInt(coverimgHeight) > parseInt( coverimgWidth)){
                            loadClass = 'height';
                        }else{
                            loadClass = 'width';
                        }
                        str+='      <img src="' + hostPath + coverimg +  '"   class="'+loadClass+'">'  ;
                        str+='  </div>' ;
                        str+='  </td>';
                        str+='  <td class="pro-item-H">';
                        str+='      <h2>'+obj.name+'</h2>';
                        str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                        str+='      <p><span>商品价格: </span><span style="overflow:hidden;  "> '+obj.startPrice +'<span></p>';
                        str+='      <p><span>商品库存: </span><span style="overflow:hidden;  "> '+obj.stock +'<span></p>';
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

