function check_browser() {
	/*
	 * 智能机浏览器版本信息:
	 * 
	 */
	var browser = {
		versions : function() {
			var u = navigator.userAgent, app = navigator.appVersion;
			return {// 移动终端浏览器版本信息
			trident : u.indexOf('Trident') > -1, // IE内核
			presto : u.indexOf('Presto') > -1, // opera内核
			webKit : u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
			gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
			mobile : !!u.match(/AppleWebKit.*Mobile.*/)
					|| !!u.match(/AppleWebKit/), // 是否为移动终端
			ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
			android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或者uc浏览器
			iPhone : u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, // 是否为iPhone或者QQHD浏览器
			iPad : u.indexOf('iPad') > -1, // 是否iPad
			webApp : u.indexOf('Safari') == -1
		// 是否web应该程序，没有头部与底部
		};
	}(),
	language : (navigator.browserLanguage || navigator.language).toLowerCase()
	}

	if (browser.versions.ios || browser.versions.iPhone
			|| browser.versions.iPad) {
		return 'ios';
	} else if (browser.versions.android) {
		return 'android'
	}
}

function setPosition(){
	if (check_browser() == 'ios') {
		//alert('ios');
		addIosTitle();
		addIosFoot();
		$("#c-ui-header-return").css('position','absolute');
		control();
	}else {
		//alert('android');
		addAndroidHead();
		addAndroidFoot();
		$("#c-ui-header-return").css('position','fixed');
	}
}

//android
function addAndroidHead() {
	document.getElementsByTagName('body')[0].innerHTML += "<div id='pagehead'><h2 id='title' ></h2>"
			+ "<i id=\"c-ui-header-return\" style=\"position:fixed;\" class=\"returnico i_bef\" onClick=\"javascript :history.back(-1);\"></i></div>"
			+ " <div class=\"clear\"></div>";
}

function addAndroidFoot() {
	document.getElementsByTagName('body')[0].innerHTML += "<div class=\"floot\">版权所有：宁波市公安局交通警察局</div>";
}

//IOS
function addIosTitle(){
	document.getElementsByTagName('body')[0].innerHTML += "<div class='next' id='title'>"
		+ "<i id=\"c-ui-header-return\" style=\"position:fixed;\" class=\"returnico i_bef\" onClick=\"javascript :history.back(-1);\"></i></div>"
		+ "<div class=\"clear\"></div>";
}

function addIosFoot(){
	document.getElementsByTagName('body')[0].innerHTML += "<div class='foot'>版权所有：宁波市公安局交通警察局</div>";
}


var isIOS = (/iphone|ipad/gi).test(navigator.appVersion);

function likeFixed() {
    //解绑resize事件  以免进入死循环
    $(window).unbind('resize', likeFixed);

    var t = document.documentElement.scrollTop || document.body.scrollTop, fixed_height = 44, top;

    //网页可见高度加上滚动条高度  - fixed元素的高度
    top = window.innerHeight+ t - fixed_height;
    //设置fixed div的top
    $('.foot').css({'bottom': '0px' });

    //重新绑定resize事件
    setTimeout(function() {
        $(window).bind('resize', likeFixed);
    }, 10);
}

function control() {
    if (isIOS) {
        likeFixed();
        function touchstart(event) {
            $('.next').css('opacity',0);
        }
        function touchend(event) {
            $('.next').css('opacity',1);
        }
        //touch的时候隐藏
        document.addEventListener("touchstart", touchstart, false);
        //滚动后重新设置fixed div的位置
        window.onscroll = likeFixed;
        //touch后显示
        document.addEventListener("touchend", touchend, false);
    }

    //所有input blur时重新计算位置，兼容chrome
    $('body').on('focusout', 'input', likeFixed);
}

