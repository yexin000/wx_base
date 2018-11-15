$(function(){
    var id = getParam("id");
    loadPriceData(id);
})

//加载出价
function loadPriceData(id){
    $('#loadingToast').show();
    var params = {};
    params.commodityId = id;
    params.wxid = localStorage.getItem("openId");
    var url= '/weixin/expected/ajaxDataList.do?id='+id;
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
            var data = data.data;
            var str = '';
            var isMe = false;
            if(data.uploadWxid == localStorage.getItem("openId")){
                isMe = true;
                $(".footer-com").hide();
            }
            $.each(dataList,function(i,obj){
                str+='<div style="height:0.36rem;margin-left: 1%;  margin-top: 0.06rem;border-bottom: #cccccc solid 1px"  >';
                str+='   <div style=" width: 100%;padding-top: 0.07rem;vertical-align: top">';
                var statusNote = '未处理';
                if(obj.status == '3'){
                    statusNote = '已处理'
                }else if(obj.status =='4'){
                    statusNote = '已完成';
                }
                str+='     <span style="font-size: 0.14rem;position: absolute; ;margin-left: 0.1rem">'+obj.nickName+' 出价:'+obj.price+'元<label>('+statusNote+')</label>'+'</span>';
                //如果是创建者，这里显示确定交易
                if(isMe){
                    if(obj.status == 2){
                        str+='     <span style="float: right;margin-right: 0.1rem;border: 1px solid black;border-radius: 5px;padding: 0.02rem;"  >已同意</span>';
                    }else if(obj.status == 3){
                        str+='     <span style="float: right;margin-right: 0.1rem;border: 1px solid black;border-radius: 5px;padding: 0.02rem;"  >交易完成</span>';
                    }else{
                        str+='     <span style="float: right;margin-right: 0.1rem;border: 1px solid black;border-radius: 5px;padding: 0.02rem;" onclick="toPurchase('+obj.id+')">同意</span>';
                    }

                }
                str+='   </div>';
                str+='</div>';
            });
            $("#dataDiv").append(str);
        }
    })
}



//出价
function toSave(){
    var estimateMoney = $("#myPrice").val();
    if(estimateMoney == null) {
        showToast("请输入预出价金额", function () {
        });
        return;
    }
    if(estimateMoney <= 0) {
        showToast("请输入正确的金额", function () {
        });
        return;
    }
    $('#msgDialog').hide();
    $('#loadingToast').show();
    var ExpectedBean = {};
    ExpectedBean.wxid = localStorage.getItem('openId');
    ExpectedBean.commodityId =  getParam("id");
    ExpectedBean.status = 1;
    ExpectedBean.price = estimateMoney;
    var url= '/weixin/expected/save.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(ExpectedBean),
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            alert("操作成功")
            $('#loadingToast').hide();
            location.reload();
        }
    })
}



//购买
function toPurchase(id){
    debugger
    $('#loadingToast').show();
    var url= '/weixin/expected/purchase.do?id='+id+"&wxid="+localStorage.getItem('openId');
    $.ajax({
        url: url,
        type: 'post',
        data:{},
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            alert("操作成功")
        }
    })
}


function onPrice (){
    $('#msgDialog').show();
}

function closePrice (){
    $('#msgDialog').hide();
}
