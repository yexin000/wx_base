var pageId = 1;
$(function(){
    var id = getParam("id");
    loadMyMessage(id);
})

$("#sendMessage").click(function () {
    sendMessage();
});

//加载留言板
function loadMyMessage(id){
    $('#loadingToast').show();
    var url= '/weixin/message/getByV5Id.do?id='+id;
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            //加载消息主题
            var str = '';
            var dataList = data.messageList;
            if(dataList.length> 0)
            {
                $.each(dataList,function(i,obj){
                    str+='  <div style="width: 99%;">';
                    str+='      <p style="text-align: center">';
                    str+='          <span style="color: #BBBBBB">'+obj.createtime+'</span>';
                    str+='      </p>';
                    str+='      <div>';
                    if(localStorage.getItem("openId")  != obj.wxid){
                        //发给自己的
                        str+='          <p style="display: inline-block;vertical-align: top;margin-left: 0.1rem">';
                        str+='          <img src="'+   obj.path+'" style="width: 0.3rem;height: 0.3rem;">';
                        str+='          </p>';
                        str+='          <p style=" width:2.35rem; display: inline-block;word-wrap:break-word;vertical-align: top; min-height: 0.3rem;text-align:left;">';
                        str+=               obj.messagenote;
                        str+='          </p>';
                    }else{
                        //发给别人的
                        str+='          <p style=" width:2.35rem; display: inline-block;margin-left: 0.28rem;vertical-align: top; min-height: 0.3rem;text-align:right;">';
                        str+=               '<span style="float: right;width:2.4rem; word-wrap:break-word;">'+obj.messagenote +'</span>';
                        str+='          </p>';
                        str+='           <p style=" display: inline-block;float: right;vertical-align: top;margin-right: 0.1rem;">';
                        str+='           <img src="'+   obj.path+'" style="width: 0.3rem;height: 0.3rem; ">';
                        str+='           </p>';
                    }
                    str+='      </div>';
                    str+='  </div>';
                });

            }
            $("#dataDiv").empty();
            $("#dataDiv").append(str);
            pageId++;
        }
    })
}


//发送消息
function sendMessage(){
    var messageNote = $("#messageNote").val();
    if(!messageNote){
        $("#msgLabel").html("请输入消息");
        $("#msgDialog").show();
        return;
    }
    $('#loadingToast').show();
    var id = getParam("id");
    var messageModel = {};
    messageModel.v5Id = id;
    messageModel.messagenote = messageNote;
    messageModel.messagetype = 2;
    messageModel.status = 1;
    messageModel.wxid = localStorage.getItem('openId');
    messageModel.toWxid = '0';
    var url= '/weixin/message/save.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(messageModel) ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            if(data.success == false){
                $("#msgLabel").html("发送失败");
                $("#msgDialog").show();
            }else{
                //跳转订单详情列表
                loadMyMessage(id);
                $("#messageNote").val("");
            }
        }
    })
}