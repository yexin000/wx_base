
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){
    loadAddress();
    $(window).scroll(function(){
        var scrolltop=$(document).scrollTop();
        var Vheight=$(window).height();
        if(scrolltop > 0){
            $("#backtop").show();
        }else{
            $("#backtop").hide();
        }
    });
    $("#backtop").click(function(){
        $("html,body").animate({scrollTop:0},"fast");
    })

})

//加载收货地址
function loadAddress(){
    var url= '/weixin/userAddr/dataList.do?wxid='+ localStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='<div style="padding-top: 0.2rem;">';
                    str+='   <p style="  padding: 0 0.16rem 0.06rem 0.12rem;">';
                    str+='    <span style="font-size: 16px;">'+obj.receiver+'</span>';
                    str+='    <span style="font-size: 16px;float:right;">'+obj.phoneNum+'</span>';
                    str+='   </p>';
                    str+='   <p style="padding: 0 0.12rem 0 0.12rem;">'+obj.address+'</p>';
                    str+='   <p style=" padding: 0.06rem 0.16rem 0 0.12rem;">';
                    str+='    <span>';
                    if(obj.isDefault == '1'){
                        str+=' <img src="../../../images/lh/redio-s.png" style="height: 15px;width: 15px;"> ';
                    }else{
                        str+=' <img src="../../../images/lh/redio-n.png" style="height: 15px;width: 15px;"> ';
                    }
                    str+=' &nbsp;默认地址</span>';
                    str+='    <span style="float: right">修改&nbsp;</span>';
                    str+='    <span style="float: right">删除</span>';
                    str+='   </p>';
                    str+='</div>';
                });
            }
            $("#dataDiv").append(str);
        }
    })
}



