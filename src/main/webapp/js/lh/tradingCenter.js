
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


$(function(){
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
    $("#tradingCenter-tit li").click(function(){
        var selectStatus = $(this).attr("streamtype");
        loadOrder(selectStatus);
    })
    $('li').eq(0).click();
})

//加载个人流水数据
function loadOrder(status){
    $('#loadingToast').show();
    $(".content").empty();
    if(status == 5) {
        var OrderModel = {};
        OrderModel.streamtype = status;
        OrderModel.wxid = localStorage.getItem("openId");
        var url= '/weixin/moneyStream/ajaxDataList.do';
        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(OrderModel) ,
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
                        str+='  <li style="padding: 0.1rem 0.05rem 0.1rem 0;width: 3.16rem;border-bottom: 0.2px solid #808080"> ';
                        str+='  <p style="padding-left: 0.1rem"> 流水编号：'+obj.flownumber;

                        str+='  <p style="padding-left: 0.1rem"> ';
                        str+='  <span> 充值金额：'+obj.streammoney;
                        str+='  </span> ';
                        str+='  </p> ';

                        str+='  <p style="padding-left: 0.1rem"> ';
                        str+='  <span> 充值用户：'+obj.wxName;
                        str+='  </span> ';
                        str+='  </p> ';

                        str+='  <p style="padding-left: 0.1rem"> 时间:' + obj.createtime;
                        str+='  </p> ';
                        str+='  </li> ';
                    });
                }
                $(".content").append(str);
            }
        })
    } else {
        var OrderModel = {};
        OrderModel.wxid = localStorage.getItem("openId");
        var url= '/weixin/putForward/ajaxDataList.do';
        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(OrderModel) ,
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
                        str+='  <li style="padding: 0.1rem 0.05rem 0.1rem 0;width: 3.16rem;border-bottom: 0.2px solid #808080"> ';
                        str+='  <p style="padding-left: 0.1rem"> 流水编号：'+obj.putForwardNo;

                        var statusStr = '';
                        if(obj.status == '0'){
                            statusStr = '未受理';
                        }else if(obj.status == '1'){
                            statusStr = '受理成功';
                        }else if(obj.status == '2'){
                            statusStr = '受理失败';
                        }
                        str+='  <span style="float: right;"> '+statusStr+'</span> ';
                        str+='  </p> ';

                        str+='  <p style="padding-left: 0.1rem"> ';
                        str+='  <span> 提现金额：'+obj.money;
                        str+='  </span> ';
                        str+='  </p> ';

                        str+='  <p style="padding-left: 0.1rem"> ';
                        str+='  <span> 提现微信：'+obj.wxAccount;
                        str+='  </span> ';
                        str+='  </p> ';

                        str+='  <p style="padding-left: 0.1rem"> 时间:' + obj.createtime;
                        str+='  </p> ';
                        str+='  </li> ';

                    });
                }
                $(".content").append(str);
            }
        })
    }

}

