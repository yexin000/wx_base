var pageId = 1;
$(function(){
    loadAddress();
})

//加载收货地址
function loadAddress(){
    $('#loadingToast').show();
    var url= '/weixin/activity/ajaxDataList.do';
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
            var datalength = data.total;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    str+='<div style="padding-top: 0.2rem;border-bottom: 1px solid #808080">';
                    str+='   <p style="  padding: 0 0.16rem 0.06rem 0.12rem;">';
                    str+='    <span style="font-size: 16px;">'+obj.receiver+'</span>';
                    str+='    <span style="font-size: 16px;float:right;">'+obj.phoneNum+'</span>';
                    str+='   </p>';
                    str+='   <p style="padding: 0 0.12rem 0 0.12rem;">'+obj.address+'</p>';
                    str+='   <p style=" padding: 0.06rem 0.16rem 0 0.12rem;">';
                    str+='    <span>';
                    if(obj.isDefault == '1'){
                        str+=' <img src="../../../images/lh/redio-s.png" style="height: 15px;width: 15px;"> ';
                    }else{
                        str+=' <img onclick="setDefaultAddr(\'' + obj.id + '\', \'' + localStorage.getItem("openId") + '\')" src="../../../images/lh/redio-n.png" style="height: 15px;width: 15px;"> ';
                    }
                    str+=' &nbsp;默认地址</span>';
                    str+='    <span style="float: right" onclick="editAddr(\'' + obj.id + '\');"> <img src="../../../images/lh/update-lh.png" style="width: 14px;height: 14px;"> 修改&nbsp;</span>';
                    str+='    <span style="float: right;padding-right: 0.12rem;" onclick="deleteAddress('+obj.id+')"> <img src="../../../images/lh/delete-lh.png" style="width: 14px;height: 14px;"> 删除</span>';
                    str+='   </p>';
                    str+='</div>';
                });
                //加载上拉加载按钮
                 $("#loadMore").remove();
                str +='<a href="javascript:loadAddress();" id="loadMore" class="weui-btn weui-btn_default weui-btn_loading"> 点击加载更多</a>';
            }
            $("#dataDiv").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").remove();
            }
            pageId++;
        }
    })
}

function editAddr(addrId) {
   // window.location.href = '../../html/lh/addressDetail.html?addrId='+addrId;
}

