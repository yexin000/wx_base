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
    loadBusinessJoin();

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
})

function loadBusinessJoin() {
    // 判断用户是否有上传商品权限
    $('#loadingToast').show();
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
            $('#loadingToast').hide();
            if(result.code == "0") {
                var data = result.data;
                $("#name").val(data.name);
                $("#telNum").val(data.telNum);
                $("#wxAccount").val(data.wxAccount);
                $("#address").val(data.address);
                $("#id").val(data.id);
                $("#uploadBtn").html("修改信息");
                $(".page__title").text("您已成功加入");
            } else {
                $(".page__title").text("加入我们");
            }
        }
    })
}

var propertyName = {
    'name' : "商家名称",
    'telNum' : "商家电话",
    'wxAccount' : "微信号",
    'address' : "商家地址"
};

function submitUpload() {
    var params = {};
    params.name = $("#name").val();
    params.telNum = $("#telNum").val();
    params.wxAccount = $("#wxAccount").val();
    params.address = $("#address").val();
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
        $("#tipLabel").html("请选择上传商家图片");
        $('#tipDialog').show();
        return;
    }
    if(!isPamramsRight) {
        return;
    }

    $('#loadingToast').show();
    var url=  $("#businessform").attr("action");
    var formData = new FormData($("#businessform")[0]);
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
            if($("#id").val() != null && $("#id").val() != '') {
                $("#tipLabel").html("修改成功");
            } else {
                $("#tipLabel").html(result.msg);
            }

            $('#tipDialog').show();
            loadBusinessJoin();
            //window.location.href = "../../html/lh/uploadList.html";
        }
    });
}

function clearPics() {
    $("#uploaderFiles").empty();
    $("#uploaderFiles").append('<li class="weui-uploader__file" style="background-image:url(/weixin/foreground/images/imgBg.png)"></li>')
    $('#itemImages').val('')
}


