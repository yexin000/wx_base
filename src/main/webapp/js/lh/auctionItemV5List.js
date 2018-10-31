$(function(){
    //判断用户等级TODO
    loadAcutionItemV5Banner();
    loadAcutionItemV5();
})

//加载v5轮播图数据
function loadAcutionItemV5Banner(){
    $('#loadingToast').show();
    var params = {};
    params.isV5Show = '1';
    var url= '/weixin/auctionItemV5/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(params) ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                for(var i = 0; i < dataList.length; i ++) {
                    var obj = dataList[i];
                    var coverimg = '';
                    var coverimgWidth = '';
                    var coverimgHeight = '';
                    if(obj.resList && obj.resList.length > 0) {
                        coverimg = obj.resList[0].path;
                        coverimgWidth = obj.resList[0].width;
                        coverimgHeight = obj.resList[0].height;
                    }
                    str+='<li class="bannerItem" onclick="toAuctionItemDetail('+obj.id+',3)" style="background-color: #363636">';
                    str+='	    <div style="position: relative; width: 3.2rem;height: 1.75rem;"><a>';
                    var loadClass = '';
                    if(parseInt(coverimgWidth) > parseInt(coverimgHeight * 1.8)){
                        loadClass = 'width';
                    }else{
                        loadClass = 'length';
                    }
                    str+='          <img src="' + hostPath + coverimg +  '" alt="" class="'+loadClass+'" >';
                    str+='      </a></div> ';
                    str+='  </li>';

                }

                $(".bannerList").append(str);
                bannerDW("banner1",3000,true,"red");
            }
        }
    })
}


//加载普通v5特区商品数据
function loadAcutionItemV5(){
    $('#loadingToast').show();
    var params = {};
    var url= '/weixin/auctionItemV5/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(params) ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows;
            var str = '';
            $.each(dataList,function(i,obj){
                str+='    <a href="javascript:toAuctionItemDetail('+obj.id+',3);" style="position: initial;background-color:#fff;border-radius:2px;float: left;padding: 6px 4px 4px 6px;width: 46%;box-sizing: border-box;margin-left: 0.07rem;margin-top: 0.1rem;">';
                str+='    <div style="height: 160px;width: 99%;">';
                str+='    <div style="background-image: url('+hostPath + obj.resList[0].path+');background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;width: 100%;height: 90%;">';
                str+='       <img style="float:left;margin-left: -0.25rem;margin-top:-0.15rem;width: 0.7rem;height:0.7rem;" src="../../../images/lh/v5.png"/></div>';
                str+='    </div>';
                str+='    <p class="weui-grid__label" style="float: left;font-size: 12px;">名称:'+obj.name+'</p>';

                str+='    <p class="weui-grid__label" style="float: left;font-size: 12px;color: #d0a642;width: 1.3rem;margin-left: -0.01rem;">介绍:'+obj.description+'</p>';


                str+='  </div>';
                str+=' </a>';
            });

            $("#dataDiv").append(str);
        }
    })
}

