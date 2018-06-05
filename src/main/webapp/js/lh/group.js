
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){
    loadGroup();
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

//加载微拍群
function loadGroup(){
    $('#loadingToast').show();
    var url= '/weixin/group/ajaxGetGroupList.do';
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
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    var codeUri = encodeURIComponent(obj.codeUrl);

                    str+='<div class="weui-cell" onclick="toGroupDetail(\''+codeUri+'\')">';
                    str+='  <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">';
                    str+='      <img src="' + hostPath + obj.logoUrl +  '" style="width: 50px;display: block">';
                    str+='  </div>';
                    str+='  <div class="weui-cell__hd" style="margin-left: 0.1rem;">';
                    str+='      <p>'+obj.groupName+'</p>';
                    str+='  </div>';
                    str+='</div>';
                });
            }
            $("#groupList").append(str);
        }
    })
}

function toGroupDetail(codeUri) {
    window.location.href = '../../html/lh/groupDetail.html?codeUri='+codeUri;
}



