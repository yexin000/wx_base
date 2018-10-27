var pageId = 1;
$(function(){
    loadMyExchange();
})

//加载我的兑换数据
function loadMyExchange(){
    $('#loadingToast').show();
    $("#loadMore").hide();
    var ExchangeRecodeModel = {};
    ExchangeRecodeModel.wxid = localStorage.getItem("openId");
    var url= '/weixin/exchangeRecode/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(ExchangeRecodeModel) ,
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
                    str+='      <h2>'+obj.icName+'</h2>';
                    str+='      <p><span>兑换数量: </span><span style="overflow:hidden;  "> '+obj.num +'<span></p>';
                    str+='      <p><span>兑换时间: </span><span style="overflow:hidden;  "> '+obj.createtime +'<span></p>';
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

