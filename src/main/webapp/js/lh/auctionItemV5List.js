$(function(){
    var id = getParam("id");
    loadAcutionItemV5(id);
})

//加载活动数据
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
                str+='    <a href="javascript:;" class="weui-grid" style="position: initial;">';
                str+='    <div style="height: 160px;width: 80%;padding-left: 10%;">';
                str+='    <div class="weui-grid__icon" style="width: 90%;height: 80%;">';
                str+='    <img src="https://mlhdkj.com/weixin/sdFile/uploadFiles/userappheadimg/20180628/807e71dddf4b4e6eb04f4f6b8b8e458ejpg" alt="">';
                str+='    </div>';
                str+='    <p class="weui-grid__label" style="float: left;font-size: 12px;">名称:'+obj.name+'</p>';
                str+='  <p class="weui-grid__label" style="float: left;font-size: 12px;color: #d0a642;width: 1.1rem;">介绍:'+obj.description+'</p>';
                str+='  <p class="weui-grid__label" style="float: left;font-size: 12px;">库存:'+obj.stock+'件</p>';
                str+='  </div>';
                str+=' </a>';
            });
            $("#dataDiv").append(str);
        }
    })
}
