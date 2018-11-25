var pageId = 1;
$(function(){
  loadActivity();
})

//加活动
function loadActivity(){
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
          str+='<div style="width: 90%;height:1.5rem;margin-left: 5%;  margin-top: 0.2rem;" onclick="toActivityDetail(\''+ obj.id + '\')">';
          str+='   <div style="border-top-left-radius: 3%;border-top-right-radius: 3%;height:1.23rem;  overflow: hidden;background-image:url('+hostPath + obj.activityBg+');background-repeat: no-repeat;background-size: 100% 100%;">';
          str+='      <span style="color: #FFFFFF;float: right">点赞:'+obj.fabulousNum+'</span>';
          str+='   </div>';
          str+='   <div style="text-align: center;background-color: #2D2D2D;overflow: hidden;width: 100%;">';
          str+='       <span style="font-size: 0.16rem; margin-top: 0.03rem; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;color: #C8AE56 ">'+obj.name+'</span>';
          str+='   </div>';
          str+='</div>';
        });
        //加载上拉加载按钮
        $("#loadMore").remove();
        str +='<a href="javascript:loadActivity();" id="loadMore" class="weui-btn weui-btn_default weui-btn_loading"> 点击加载更多</a>';
      }
      $("#dataDiv").append(str);
      if(datalength <= (pageId * 10)){
        $("#loadMore").remove();
      }
      pageId++;
    }
  })
}
