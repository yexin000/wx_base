var pageId = 1;

$(function(){	
    loadbusinessData();
})

//加载商户数据
function loadbusinessData(){
    $('#loadingToast').show();
    var bisinessModel = {};
    bisinessModel.auditStatus = '1';
    bisinessModel.page = pageId;
    var url= '/weixin/business/ajaxDataList.do';
    $.ajax({
        url: url,
        type: 'post',
        data:JSON.stringify(bisinessModel) ,
        contentType : "application/json;charset=utf-8",
        dataType: 'JSON',
        cache: false,
        success:function(data){
            $('#loadingToast').hide();
            var dataList = data.rows;
            var datalength = data.total;
            if(dataList.length> 0)
            {
                var str = '';
                $.each(dataList,function(i,obj){
                    if(obj.logoPath == null || obj.logoPath == '') {
                        obj.logoPath = "foreground/images/no-image.jpg";
                    }
                    str+='<div class="posr-item" style="overflow:hidden;height: 1.7rem;border-bottom: 1px solid #BBBBBB;padding: 0.1rem 0 0.1rem 0;">';
                    str+='	<div class="left" onclick="toBusinessDetail('+obj.id+')">';

                    var loadClass = '';
                    if(parseInt(obj.width) > parseInt(obj.height * 1.8)){
                        loadClass = 'width';
                    }else{
                        loadClass = 'length';
                    }
                    str+=' 		<img src= "'+hostPath + obj.logoPath + '" alt=""  class="'+loadClass+'">';
                    str+='	</div>';
                    str+='</div>';
                });
                //加载上拉加载按钮
                $("#loadMore").remove();
                str +='<a href="javascript:loadbusinessData();" id="loadMore" class="weui-btn weui-btn_default weui-btn_loading"><i class="weui-loading"></i>点击加载更多</a>';
            }
            $("#dataDiv").append(str);
            if(datalength <= (pageId * 10)){
                $("#loadMore").remove();
            }
            pageId++;
        }
    })
}