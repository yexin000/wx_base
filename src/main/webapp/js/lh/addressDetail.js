$(function(){
    var addrId = getParam("addrId");
    if(addrId != null) {
        loadAddress(addrId);
    }
})

//加载收货地址
function loadAddress(addrId){
    var url= '/weixin/userAddr/getAddrById.do?addrId=' + addrId;
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            if(data && data.success){
                $("#id").val(data.data.id);
                $("#name").val(data.data.receiver);
                $("#telNum").val(data.data.phoneNum);
                $("#address").val(data.data.address);
            }
        }
    })
}

//新增地址
function addAddress(){
    var userAddr = {};
    userAddr.id = $("#id").val();
    userAddr.wxid = localStorage.getItem("openId");
    userAddr.address = $("#address").val();
    userAddr.phoneNum = $("#telNum").val();
    userAddr.receiver = $("#name").val();
    userAddr.isDefault = "1";
    var url= '/weixin/userAddr/ajaxAddAddr.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(userAddr) ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            if(data.code == '0'){
                window.location.href='../../html/lh/address.html';
            }
        }
    })
}
