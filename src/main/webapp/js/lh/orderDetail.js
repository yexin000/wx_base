$(function(){
    $("#payBtn").click(function(){
        var timestamp = new Date().getTime();
        var nonceStr = "1111aaaa";
        var prepay_id = "prepay_id123";
        var sign = "sign12345"
        var params = "?timestamp=" +timestamp+ "&nonceStr=" + nonceStr
            + "&prepay_id=" +prepay_id+ "&signType=MD5" + "&paySign=" + sign
            + "&orderId=123456789";
        var path = '/pages/wxpay/wxpay' + params;
        wx.miniProgram.navigateTo({ url: path });
    })
})