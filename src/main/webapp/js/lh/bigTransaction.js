var pageId = 1;
$(function(){
    loadTransaction();
})

//加载大额支付
function loadTransaction(){
    $('#loadingToast').show();
    var url= '/weixin/transaction/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
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
                    str+='<tr onclick="toAuctionItemDetail('+obj.auctionitemid+','+obj.attribute+')">';
                    str+='  <td class="pro-item-M">' ;
                    str+='  <div class="itemDiv">' ;

                    var loadClass = '';
                    if(parseInt(coverimgHeight) > parseInt( coverimgWidth)){
                        loadClass = 'height';
                    }else{
                        loadClass = 'width';
                    }
                    str+='      <img src="' + hostPath + coverimg +  '"  style="border-radius: 5px;"  class="'+loadClass+'" >'  ;
                    str+='  </div>' ;
                    str+='  </td>';

                    str+='  <td class="pro-item-H">';
                    str+='      <p ><span>金额:</span>  <span> '+parseInt(obj.finalprice)+' </span></p>';
                    var banNo = obj.bankNo;
                    str+='      <p><span>尾号: </span><span style="overflow:hidden;  "> '+banNo.substr(banNo.length-4) +'<span></p>';
                    str+='      <p><span>明细: </span><span style="overflow:hidden;  "> '+obj.name +'<span></p>';
                    str+='      <p><span>时间: </span><span style="overflow:hidden;  "> '+obj.createtime +'<span></p>';
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
