var pageId = 1;

$(function(){	
    loadbusinessData();
})

//加载商户数据
function loadbusinessData(){
    $('#loadingToast').show();
    var bisinessModel = {};
    bisinessModel.auditStatus = '1';
    bisinessModel.status = '1';
    bisinessModel.isExcellent = '2';
    bisinessModel.page = pageId;
    bisinessModel.rows = 1000;
    var url= '/weixin/business/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(bisinessModel) ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows;
            var datalength = data.total;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    if(obj.logoPath == null || obj.logoPath == '') {
                        obj.logoPath = "foreground/images/no-image.jpg";
                    }
                    var coverImage = hostPath+ obj.logoPath;
                    str += '<a href="javascript:toBusinessDetail('+obj.id+')";" class="weui-grid" style="position: initial;padding: 10px 0px;">'
                    str += '<div class="mystore-auctionitem-div1">'
                    str +=   '<div class="weui-grid__icon" style="width: 90%;height: 150px;">'
                    str +=      '<img src="' + coverImage + '" alt="">'
                    str +=    '</div>'
                    str +=    '<p class="weui-grid__label auctionitem-auctionitem-label">昵称:' + dataList[i].name + '</p>'
                    str +=  '</div>'
                    str +='</a>';
                    $("#auctionItemData").append(str);
                    str = '';
                });
                //加载上拉加载按钮
                $("#loadMore").remove();
                str +='<a href="javascript:loadbusinessData();" id="loadMore" class="weui-btn weui-btn_default weui-btn_loading"> 点击加载更多</a>';
            }
            $("#dataDiv").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").remove();
            }
            pageId++;
        }
    })
}