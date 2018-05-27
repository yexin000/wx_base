
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
    var model = {};
    model.isShow = '1';
    var url= '/weixin/userAddr/dataList.do?wxid='+ sessionStorage.getItem("openId");
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            //var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='  <span style="padding-left: 0.1rem;"> '+obj.receiver+' </span>';
                    str+='  <span style="padding-left: 0.5rem;"> '+obj.phoneNum+' </span> <br>';
                    str+='  <span style="padding-left: 0.3rem;"> '+obj.address+' </span>';
                    str+='  <span style="padding-left: 0.3rem;"> 是否默认'+obj.isDefault+' </span>';

                });
            }
            $(".pro-item").append(str);
        }
    })
}



