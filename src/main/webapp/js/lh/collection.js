
//图片访问路径
var imagePath = "http://127.0.0.1:8080/";


    $(function(){


        loadCollection();
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

    })

    //取消收藏
    function cancelCollection(id){
        var url= '/weixin/favorite/ajaxDelFavorite.do?id='+id;
        $.ajax({
            url: url,
            type: 'post',
            data: {} ,
            dataType: 'JSON',
            contentType : "application/json;charset=utf-8",
            cache: false,
            success:function(data){
                loadCollection();
                var dataList = data.rows;
            }
        })
    }


    //加载个人收藏数据
    function loadCollection(){
        $(".pro-item").empty();
        var url= '/weixin/favorite/ajaxDataList.do?wxid='+localStorage.getItem("openId");
        $.ajax({
            url: url,
            type: 'post',
            data: {} ,
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
                        str+='      <h2>'+obj.favName+'</h2>';
                        str+='      <p class="ppp"><span>商品介绍:</span>  <span> '+obj.description+' </span></p>';
                        str+='      <p><span>商品库存: </span><span style="overflow:hidden;  "> '+obj.stock +'<span> <span  onclick="cancelCollection('+obj.id+')" style="float: right;border: 1px solid #808080;padding: 0.03rem;border-radius:6px; ">取消收藏</span></p>';
                        str+='  </td>';
                        str+='</tr>';
                    });
                }
                $(".pro-item").append(str);
            }
        })
    }



