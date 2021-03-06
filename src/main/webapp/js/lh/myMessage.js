var pageId = 1;
$(function(){

    $("#business-tit li").click(function(){
        $("#dataDiv").empty();
        pageId = 1;
        $(this).addClass("cur").siblings().removeClass("cur");
        selectType = $(this).attr("selectType");
        if(selectType == '1'){
            loadMessage( );
        }else{
            loadMyMessage( );
        }
    })

    $('li').eq(0).click();

})

//加载消息
function loadMessage(){
    $('#loadingToast').show();
    var messageModel = {}
    messageModel.messagetype = 1;
    messageModel.toWxid = localStorage.getItem("openId");
    var url= '/weixin/message/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(messageModel) ,
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
                    str+='<div style="width: 99%;height: 0.62rem;border-bottom: 1px solid #EFEFEF" onclick="toMessageDetail(\''+ obj.id + '\')">';
                    str+='<p style="font-size: 0.14rem;margin-left: 0.1rem">官方通知：</p>  <p>';
                    str+=' <span class="messageList" style="margin-left: 0.2rem;"> '+obj.messagenote+'</span>';
                    str+=' </p>';

                    str+='<span style="margin-left: 0.1rem">'+obj.createtime+'</span>';
                    str+='<span style="float: right">查看详情></span>';
                    str+='</div>';
                });
                //加载上拉加载按钮
                 $("#loadMore").remove();
                str +='<a href="javascript:loadActivity();" id="loadMore" class="weui-btn weui-btn_default weui-btn_loading"> 点击加载更多</a>';
            }
            $("#dataDiv").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").remove();
            }
            pageId++;
        }
    })
}



//加载消息
function loadMyMessage(){
    $('#loadingToast').show();
    var messageModel = {}
    messageModel.toWxid = localStorage.getItem("openId");
    messageModel.messagetype = 2;
    var url= '/weixin/message/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(messageModel) ,
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
                    // 如果当前聊天的发送对象是我，那么接收对象还是toWxid
                    if(obj.wxid == localStorage.getItem("openId")){
                        str+='<div style="width: 99%;height: 0.62rem;border-bottom: 1px solid #EFEFEF" onclick="toMessageDetail(\''+ obj.id + '\' ,\''+ obj.toWxid +'\')">';
                    }else{
                        //如果我是被接收对象，那么我的回复对象是该条消息的发送者 wxid
                        str+='<div style="width: 99%;height: 0.62rem;border-bottom: 1px solid #EFEFEF" onclick="toMessageDetail(\''+ obj.id + '\' ,\''+ obj.wxid +'\')">';
                    }
                    //用户消息
                    if(obj.wxid == localStorage.getItem('openId')){
                        str+='<p style="font-size: 0.14rem;margin-left: 0.1rem">我发送的：</p>  <p>';
                    }else{
                        str+='<p style="font-size: 0.14rem;margin-left: 0.1rem">留言给我：</p>  <p>';
                    }

                    str+=' <span class="messageList" style="margin-left: 0.2rem;"> '+obj.messagenote+'</span>';
                    str+=' </p>';
                    str+='<span style="margin-left: 0.1rem">'+obj.createtime+'</span>';
                    str+='<span style="float: right">查看详情></span>';
                    str+='</div>';
                });
                //加载上拉加载按钮
                $("#loadMore").remove();
                str +='<a href="javascript:loadMyMessage();" id="loadMore" class="weui-btn weui-btn_default weui-btn_loading"> 点击加载更多</a>';
            }
            $("#dataDiv").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").remove();
            }
            pageId++;
        }
    })
}
