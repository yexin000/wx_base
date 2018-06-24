$(function(){
    // 判断用户是否有上传商品权限
    var wxid = localStorage.getItem("openId");
    var url= '/weixin/business/ajaxGetJoinBusiness.do?wxid='+ wxid;
    $.ajax({
        url: url,
        type: 'post',
        data: {} ,
        dataType: 'JSON',
        contentType : "application/json;charset=utf-8",
        cache: false,
        success:function(result){
            if(result.code == "0") {
                var data = result.data;
                $("#businessName").val(data.name);
                $("#businessTelNum").val(data.telNum);
                $("#businessWxNum").val(data.wxAccount);
                $("#businessAddress").val(data.address);
                var businessOptions = '';
                businessOptions += '<option value="' + data.id + '" selected="selected">' + data.name + '</option>';
                $("#businesses").empty();
                $("#businesses").append(businessOptions);
                var businessId = data.id;
                // 查询商户下的拍卖会
                $.ajax({
                    url: '/weixin/auction/ajaxGetJoinAuctions.do?businessId='+ businessId,
                    type: 'post',
                    data: {} ,
                    dataType: 'JSON',
                    contentType : "application/json;charset=utf-8",
                    cache: false,
                    success:function(result){
                        if(result.code == "0") {
                            var auctionList = result.data;
                            if(auctionList.length > 0) {
                                var options = '';
                                for(var i = 0; i < auctionList.length; i ++) {
                                    if(i == 0) {
                                        options += '<option value="' + auctionList[i].id + '" startAttr="' + auctionList[i].starttime + '" endAttr="' + auctionList[i].endtime + '"  selected="selected">' + auctionList[i].name + '</option>';
                                        $("#auctionTimeShow").html(auctionList[i].starttime + " 至 " + auctionList[i].endtime);
                                    } else {
                                        options += '<option value="' + auctionList[i].id + '" startAttr="' + auctionList[i].starttime + '" endAttr="' + auctionList[i].endtime + '" >' + auctionList[i].name + '</option>';
                                    }
                                }
                                $("#auctions").empty();
                                $("#auctions").append(options);
                            }
                        }
                    }
                })
            } else {
                $("#tipLabel").html("未找到商家信息，请申请商家加入后上传商品");
                $('#tipDialog').show();
                setTimeout(function () {
                    history.back(-1);
                }, 3000);
            }
        }
    })

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


    $("#auctions").on("change", function () {
        var auctionStartTime = $(this).find("option:selected").attr("startAttr");
        var auctionEndTime = $(this).find("option:selected").attr("endAttr");
        $("#auctionTimeShow").html(auctionStartTime + " 至 " + auctionEndTime);
    });

    $("#attribute").on("change", function () {
        var attr = $(this).val();
        showInputs(attr);
    });

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
        //$("#uploaderFiles").empty();
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

    showInputs($("#attribute").val());
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

    if(params.startPrice <= 0) {
        isPamramsRight = false;
        $("#tipLabel").html("请输入正确的价格");
        $('#tipDialog').show();
    } else if (params.addPrice <= 0) {
        isPamramsRight = false;
        $("#tipLabel").html("请输入正确的加价幅度");
        $('#tipDialog').show();
    }

    if($("#attribute").val() == 0) {
        var auctionStartTime = $("#auctions").find("option:selected").attr("startAttr");
        auctionStartTime = new Date(auctionStartTime).getTime();
        var auctionEndTime = $("#auctions").find("option:selected").attr("endAttr");
        auctionEndTime = new Date(auctionEndTime).getTime();
        var startTime = new Date($("#startTimeInput").val()).getTime();
        var endTime = new Date($("#endTimeInput").val()).getTime();
        if(!startTime || !endTime) {
            $("#tipLabel").html("请输入拍卖开始和结束时间");
            $('#tipDialog').show();
            return
        }

        if(startTime >= endTime) {
            $("#tipLabel").html("拍卖开始时间必须小于结束时间");
            $('#tipDialog').show();
            return
        }

        if(startTime < auctionStartTime || startTime >= auctionEndTime
            || endTime < auctionStartTime || endTime >= auctionEndTime ) {
            $("#tipLabel").html("拍卖开始和结束时间必须在拍卖专场时间内");
            $('#tipDialog').show();
            return
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

    $("#startTime").val(new Date($("#startTimeInput").val()).format("yyyy-MM-dd hh:mm:ss"));
    $("#endTime").val(new Date($("#endTimeInput").val()).format("yyyy-MM-dd hh:mm:ss"));

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

function clearPics() {
    $("#uploaderFiles").empty();
    $("#uploaderFiles").append('<li class="weui-uploader__file" style="background-image:url(/weixin/foreground/images/imgBg.png)"></li>')
    $('#itemImages').val('')
}

function showInputs(attr) {
    if(attr == "0") {
        $(".auctionAttr").show();
        $(".businessAttr").hide();
    } else if(attr == "1") {
        $(".auctionAttr").hide();
        $(".businessAttr").show();
    }
}



