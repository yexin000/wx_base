var pageId = 1;
$(function(){
    var id = getParam("id");
    loadMyMessage(id);
})

//加载消息
function loadMyMessage(id){
    $('#loadingToast').show();
    var url= '/weixin/message/getId.do?id='+id;
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
            var data1 = data.data;
            var firstMessage = data1.messagenote;
            //加载留言消息，处理第一条

            var str = '';
            str+='  <div style="width: 99%;">';
            str+='      <p style="text-align: center">';
            str+='          <span style="color: #BBBBBB">'+data1.createtime+'</span>';
            str+='      </p>';
            str+='      <div>';
            if(localStorage.getItem("openId")  == data1.toWxid){
                str+='          <p style="display: inline-block;vertical-align: top;margin-left: 0.1rem;">';
                str+='          <img src="'+   data1.path +'" style="width: 0.3rem;height: 0.3rem;">';
                str+='          </p>';
                str+='          <p style=" width:2.35rem; display: inline-block;word-wrap:break-word;vertical-align: top;">';
                str+=               firstMessage;
                str+='          </p>';
            }else{
                //发给别人的
                str+='          <p style=" width:2.35rem; display: inline-block;margin-left: 0.28rem;vertical-align: top;">';
                str+=               '<span style="float: right;width:2.4rem; word-wrap:break-word;">'+firstMessage +'</span>';
                str+='          </p>';
                str+='           <p style=" display: inline-block;float: right;vertical-align: top;margin-right: 0.1rem;">';
                str+='           <img src="'+  data1.toPath+'" style="width: 0.3rem;height: 0.3rem; ">';
                str+='           </p>';
            }
            str+='      </div>';
            str+='  </div>';

            var dataList = data.messageList;
            if(dataList.length> 0)
            {
                $.each(dataList,function(i,obj){
                    str+='  <div style="width: 99%;">';
                    str+='      <p style="text-align: center">';
                    str+='          <span style="color: #BBBBBB">'+obj.createtime+'</span>';
                    str+='      </p>';
                    str+='      <div>';
                    if(localStorage.getItem("openId")  == obj.toWxid){
                        //发给自己的
                        str+='          <p style="display: inline-block;vertical-align: top;margin-right: 0.1rem">';
                        str+='          <img src="'+   obj.path+'" style="width: 0.3rem;height: 0.3rem;">';
                        str+='          </p>';
                        str+='          <p style=" width:2.35rem; display: inline-block;word-wrap:break-word;vertical-align: top;">';
                        str+=               obj.messagenote;
                        str+='          </p>';
                    }else{
                        //发给别人的
                        str+='          <p style=" width:2.35rem; display: inline-block;margin-left: 0.28rem;vertical-align: top;">';
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
            $("#dataDiv").append(str);
            pageId++;
        }
    })
}
