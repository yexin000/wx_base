$(function(){
    //loadMyUpload();
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

    $("#uploadBtn").click(function () {
        submitUpload();
    });
    $("#itemImages").empty();

    $("#itemImages").on("change", function () {
        var obj = document.getElementById("itemImages");
        var length = obj.files.length;

        if(length <= 0 ) {
            return;
        }

        if(length > 5) {
            $("#tipLabel").html("最多上传5张照片");
            $('#tipDialog').show();
            return;
        }
        $("#uploaderFiles").empty();
        if (window.FileReader) {
            for(var i = 0; i < this.files.length; i ++) {
                var reader = new FileReader();
                var file = this.files[i];
                reader.readAsDataURL(file);
                //监听文件读取结束后事件
                reader.onloadend = function (e) {
                    var imageLi = '<li id="uploadImage0" class="weui-uploader__file" style="background-image:url(' + e.target.result + ')"></li>';
                    $("#uploaderFiles").append(imageLi);
                    //$("#uploadImage" + i).css("background-image","url(" + e.target.result + ")");    //e.target.result就是最后的路径地址
                };
            }
        }
    })

    // 加载商品类型
    $.post("/weixin/wxCode/getAllAuctionItemSecondType.do",{rows:100},function(data){
        if(data.total > 0) {
            var options = '';
            for(var i = 0; i < data.rows.length; i ++) {
                if(i == 0) {
                    options += '<option value="' + data.rows[i].code + '" selected="selected">' + data.rows[i].name + '</option>';
                } else {
                    options += '<option value="' + data.rows[i].code + '">' + data.rows[i].name + '</option>';
                }
            }
            $("#type").empty();
            $("#type").append(options);
        }
    });
})

var propertyName = {
    'businessName' : "商家名称",
    'businessTelNum' : "商家电话",
    'businessWxNum' : "微信号",
    'businessAddress' : "商家地址",
    'name' : "商品名称",
    'attribute' : "商品性质",
    'type' : "商品分类",
    'description' : "商品介绍",
    'startPrice' : "起拍价",
    'addPrice' : "加价幅度",
    'standard' : "商品规格",
    'age' : "商品年代",
    'degree' : "商品等级",
    'detail' : "商品详情",
    'stock' : "库存"
};

function submitUpload() {
    var params = {};
    params.businessName = $("#businessName").val();
    params.businessTelNum = $("#businessTelNum").val();
    params.businessWxNum = $("#businessWxNum").val();
    params.businessAddress = $("#businessAddress").val();
    params.name = $("#name").val();
    params.attribute = $("#attribute").val();
    params.type = $("#type").val();
    params.description = $("#description").val();
    params.startPrice = $("#startPrice").val();
    params.addPrice = $("#addPrice").val();
    params.detail = $("#detail").val();
    params.status = "1";
    params.auctionStatus = "0";
    params.standard = $("#standard").val();
    params.age = $("#age").val();
    params.degree = $("#degree").val();
    params.stock = $("#stock").val();
    $("#wxid").val(localStorage.getItem("openId"));

    var isPamramsRight = true;
    for(var key in params) {
        if(!$.trim(params[key])) {
            isPamramsRight = false;
            $("#tipLabel").html("请输入正确的" + propertyName[key]);
            $('#tipDialog').show();
            break;
        }
    }

    if(!$("#itemImages").val()) {
        $("#tipLabel").html("请选择上传商品图片");
        $('#tipDialog').show();
        return;
    }
    if(!isPamramsRight) {
        return;
    }

    //$("#uploadform").submit();
    $('#loadingToast').show();
    var url=  $("#uploadform").attr("action");
    var formData = new FormData($("#uploadform")[0]);
    $.ajax({
        url:url,
        type: 'POST',
        data: formData,
        dataType: 'json',
        cache: false,
        processData: false,
        contentType: false,
        success:function(result){
            $('#loadingToast').hide();
            $("#tipLabel").html(result.msg);
            $('#tipDialog').show();
            window.location.href = "../../html/lh/uploadList.html";
        }
    });
}

//加载我的上传
function loadMyUpload(){
    var AuctionItemModel = {};
    AuctionItemModel.uploadWxid = localStorage.getItem("openId");
    var url= '/weixin/auctionItem/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(AuctionItemModel) ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(data){
            var dataList = data.rows;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='<tr onclick="toAuctionItemDetail('+obj.id+')" style="border-bottom: 1px solid #808080;">';
                    str+='  <td class="pro-item-M"><img src="../../../images/lh/wshop_indexbanner1.jpg"  alt=""></td>';
                    str+='  <td class="pro-item-H">';
                    str+='      <h2>'+obj.name+'</h2>';
                    str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                    str+='      <p><span>商品价格: </span><span style="overflow:hidden;  "> '+obj.startPrice +'<span> <span    style="float: right; padding: 0.03rem;border-radius:6px; ">审核通过</span></p>';
                    str+='  </td>';
                    str+='</tr>';
                });
            }
            $(".pro-item").append(str);
        }
    })
}



