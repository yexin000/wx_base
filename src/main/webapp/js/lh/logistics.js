
$(function(){
    logistics();
})

//加载消息
function logistics(){
    $('#loadingToast').show();
    var  params = {};
    params.id = 266;
    var url= '/weixin/order/ajaxGetLogistics.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(params)  ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataItem = data.auctionItem; //商品信息
            $("#itemImg").attr("src" , hostPath + dataItem.resList[0].path);

            var dataList = data.dataList;
            var str = '';
            var myObject = JSON.parse(dataList);

            //0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
            var status = myObject.state;
            if(status == "0"){
                $("#logisticsStatus").html("在途中");
            }else if(status == "1"){
                $("#logisticsStatus").html("1已揽收");
            }else if(status == "2"){
                $("#logisticsStatus").html("疑难");
            }else if(status == "3"){
                $("#logisticsStatus").html("已签收");
            }else{
                //问题状态处理中
                $("#logisticsStatus").html("问题状态处理中");
            }

            if(myObject.com == "shunfeng"){
                $("#logisticsCom").html("顺丰");
            }else if(myObject.com == "shentong"){
                 $("#logisticsCom").html("申通");
            }else if(myObject.com == "yuantong"){
                $("#logisticsCom").html("圆通");
            }else if(myObject.com == "zhongtong"){
                $("#logisticsCom").html("中通");
            }else if(myObject.com == "huitongkuaidi"){
                $("#logisticsCom").html("百世汇通");
            }else if(myObject.com == "yunda"){
                $("#logisticsCom").html("韵达");
            }else if(myObject.com == "tiantian"){
                $("#logisticsCom").html("天天");
            }else if(myObject.com == "debangwuliu"){
                $("#logisticsCom").html("德邦");
            }else if(myObject.com == "jd"){
                $("#logisticsCom").html("京东");
            }else if(myObject.com == "youzhengguonei"){
                $("#logisticsCom").html("邮政包裹");
            }else if(myObject.com == "ems"){
                $("#logisticsCom").html("EMS");
            }else {
                $("#logisticsCom").html("未登记的快递公司");
            }

            //address
            var address = data.useraddr.address;
            $("#address").html(address);
            var ldataList = myObject.data;
            $.each(ldataList,function(i,obj){
                str += '<p style="margin-left:0.2rem;"> <span>'+obj.time+'</span>  <span style="margin-left:0.1rem;">'+obj.context+'</span> </p>';
            });
            $("#dataDiv").append(str);
        }
    })
}
