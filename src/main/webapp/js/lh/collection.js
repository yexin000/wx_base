var pageId = 1;
    $(function(){
        loadCollection();
    });

    //取消收藏
    function cancelCollection(id){
        var params={};
        params.favId=id;
        params.wxid=localStorage.getItem("openId");
        $.post("/weixin/favorite/ajaxDelFavorite.do",params,function(data){
            alert(data.msg);
            loadCollection();
        });
    }

    //加载个人收藏数据
    function loadCollection(){
        $('#loadingToast').show();
        var url= '/weixin/favorite/ajaxDataList.do?wxid='+localStorage.getItem("openId")+'&page='+pageId;
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
                        str+='<tr  >';
                        str+='  <td class="pro-item-M" onclick="toAuctionItemDetail('+obj.favId+','+obj.favType+')"> <div class="itemDiv">';
                        var loadClass = '';
                        if(parseInt(obj.height) > parseInt(obj.width)){
                            loadClass = 'length';
                        }else{
                            loadClass = 'width';
                        }
                        str+='<img src="' + hostPath + obj.logoPath + '"  alt=""  class="'+loadClass+'"></td>';
                        str+=' </div> <td class="pro-item-H">';
                        str+='      <h2>'+obj.favName+'</h2>';
                        str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                        str+='      <p><span>商品库存: </span><span style="overflow:hidden;  "> '+obj.stock +'<span><span onclick="cancelCollection('+obj.favId+');" style="float: right;border: 1px solid #808080;padding: 0.03rem;border-radius:6px; ">取消收藏</span></p>';
                        str+='  </td>';
                        str+='</tr>';
                    });
                }
                $(".pro-item").append(str);
                if(datalength <= (pageId * 10)){
                    $("#loadMore").remove();
                }
                pageId++;
            }
        })
    }



