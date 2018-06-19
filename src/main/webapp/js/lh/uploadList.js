var pageId = 1;
$(function() {
    loadMyUpload();
})

//加载我的上传
function loadMyUpload(){
    $('#loadingToast').show();
    var AuctionItemModel = {};
    AuctionItemModel.uploadWxid = localStorage.getItem("openId");
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
                    if(obj.resList && obj.resList.length > 0) {
                        coverimg = obj.resList[0].path;
                    }
                    var checkStatus = '未审核';
                    if(obj.status == '1') {
                        checkStatus = '未审核';
                    } else if (obj.status == '2') {
                        checkStatus = '审核未通过';
                    } else if (obj.status == '3') {
                        checkStatus = '审核通过';
                    }

                    if(obj.status == '3') {
                        str+='<tr onclick="toAuctionItemDetail('+obj.id+','+obj.attribute+')" style="border-bottom: 1px solid #808080;">';
                    } else {
                        str+='<tr onclick="$(\'#tipDialog\').show();" style="border-bottom: 1px solid #808080;">';
                    }
                    str+='  <td class="pro-item-M"><img src="' + hostPath + coverimg +  '"  alt=""></td>';
                    str+='  <td class="pro-item-H">';
                    str+='      <h2>'+obj.name+'</h2>';
                    str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                    str+='      <p><span>商品价格: </span><span style="overflow:hidden;  "> '+obj.startPrice +'<span> <span style="float: right; padding: 0.03rem;border-radius:6px; ">' + checkStatus+ '</span></p>';
                    str+='  </td>';
                    str+='</tr>';
                });
            }
            $(".pro-item").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").remove();
            }
            pageId++;
        }
    })
}

function closeTip(){
    $("#tipDialog").hide();
}