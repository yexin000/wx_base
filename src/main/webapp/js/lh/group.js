
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
    var url= '/weixin/group/ajaxGetGroupList.do';
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
                    var codeUri = encodeURIComponent(obj.codeUrl);
                    str+='<tr onclick="toGroupDetail(\''+codeUri+'\')" style="border-bottom: 1px solid #808080;">';
                    str+='  <td style="padding: 0 0.1rem;"> ';
                    str+='    <img src="' + hostPath + obj.logoUrl +  '" style="width: 0.4rem;height: 0.4rem;">' ;
                    str+='  <span style="padding-left: 0.3rem;font-size: 20px;"> '+obj.groupName+' </span>';
                    str+='  </td>';
                    str+='</tr>';
                });
            }
            $(".pro-item").append(str);
        }
    })
}

function toGroupDetail(codeUri) {
    window.location.href = '../../html/lh/groupDetail.html?codeUri='+codeUri;
}



