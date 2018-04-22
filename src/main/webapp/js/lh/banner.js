
function bannerDW(bannerBox,bannerTime,autoBanner,color){
//	console.log(color);
	window.onorientationchange=function(){window.location.reload();}
	var oDiv = document.getElementById(bannerBox);
	var oUl = oDiv.getElementsByClassName('bannerList')[0];//document.getElementById('bannerList');
	var aLi = oUl.getElementsByClassName("bannerItem");
	var Img = oUl.getElementsByTagName("img");
//	console.log(oDiv.offsetWidth);
	var w = oDiv.offsetWidth;
	var promptList=oDiv.getElementsByClassName('bannerPrompt');
	var prompt=" ";
	for(var i=0;i<aLi.length;i++){
		prompt+="<span class='promptItem'></span>"
	}
	promptList[0].innerHTML=prompt;
	prompt=oDiv.getElementsByClassName('promptItem');
	if(color!=undefined){
		for(var i=0;i<prompt.length;i++){
		prompt[i].style.background="rgba(31,34,35,.4)";
	};
	};
	if(aLi.length==0){return;};
	prompt[0].style.background=color;
	oUl.style.width=w*aLi.length+"px";
	//console.log(oUl.offsetWidth);
	for(var i=0;i<aLi.length;i++){
		aLi[i].style.width=w+"px"
	};
	//onmousedown onmousemove onmouseup
	//ontouchstart ontouchmove  ontouchend
	//document.ontouchmove = function(ev){
	//	ev.preventDefault();
	//};
	var downX = 0;
	var downLeft = 0;
	var iNow = 0;
	var downTime = 0;
	var timer=null;
	var bannerSwitch=0;
	var timerImg = setInterval(function(){
		for(var i=0;i<Img.length;i++){
			if(Img[i].complete==false){console.log("t退出循环");return;};
		};
		console.log("加载成功");bannerSwitch=1;bannerOn();clearInterval(timerImg) ;
	},50);
	//自动轮播启动定时器	
	function bannerOn(){
		if(autoBanner==true){timer=setInterval(function(){++iNow%aLi.length;startMove( oUl , { left : - iNow%aLi.length * aLi[0].offsetWidth } , 400 , 'easeOut' );},bannerTime);};
	};
	
	oUl.ontouchstart = function(ev){
		if(bannerSwitch!=1){return;};
		if(autoBanner==true){window.clearInterval(timer);}
		//清除定时器
		var touchs = ev.changedTouches[0];
		var bBtn = true;
		downX = touchs.pageX;
		downLeft = this.offsetLeft;
		downTime = Date.now();
		oUl.ontouchmove = function(ev){
			var touchs = ev.changedTouches[0];
			if( this.offsetLeft >= 0 ){
				if(bBtn){
					bBtn = false;
					downX = touchs.pageX;
				}
				this.style.left = (touchs.pageX - downX)/3 + 'px';
			}
			else if( this.offsetLeft <= oDiv.offsetWidth - oUl.offsetWidth ){
				if(bBtn){
					bBtn = false;
					downX = touchs.pageX;
				}
				this.style.left = (touchs.pageX - downX)/3 + ( oDiv.offsetWidth - oUl.offsetWidth ) + 'px';
			}
			else{
				this.style.left = touchs.pageX - downX + downLeft + 'px';
			};
		};
		oUl.ontouchend = function(ev){
				//再次启用定时器
			if(autoBanner==true){
				timer=setInterval(function(){++iNow%aLi.length;startMove( oUl , { left : - iNow%aLi.length * aLi[0].offsetWidth } , 400 , 'easeOut' );},bannerTime);
			}
			var touchs = ev.changedTouches[0];
			if( touchs.pageX < downX ){   //←
				if(iNow %aLi.length != aLi.length-1){
					if( downX - touchs.pageX > aLi[0].offsetWidth/2 || (Date.now() - downTime < 300 && downX - touchs.pageX > 30 ) ){
						iNow++;
					}
				}
				startMove( oUl , { left : - iNow%aLi.length* aLi[0].offsetWidth } , 400 , 'easeOut' );
			}
			else{    //→
				if(iNow %aLi.length!= 0){
					if( touchs.pageX - downX > aLi[0].offsetWidth/2 || (Date.now() - downTime < 300 && touchs.pageX - downX > 30 ) ){
							iNow--;
					}
				}
				startMove( oUl , { left : - iNow%aLi.length * aLi[0].offsetWidth } , 400 , 'easeOut' );
			}
			this.ontouchmove = null;
			this.ontouchend = null;
		};
	};
	
	// JavaScript Document
	function startMove(obj,json,times,fx,fn,fnMove){
//		console.log(prompt.length);
		if(prompt.length!=0){
			for(var i=0;i<prompt.length;i++){
				prompt[i].style.background="rgba(31,34,35,.4)";
			};
			prompt[iNow%aLi.length].style.background=color;
		};
		var iCur = {};
		for(var attr in json){
			if(attr == 'opacity'){
				iCur[attr] = Math.round(getStyle(obj,attr)*100);
			}
			else{
				iCur[attr] = parseInt(getStyle(obj,attr));
			}
		};
		var startTime = now();
		clearInterval(obj.timer);
		obj.timer = setInterval(function(){
			var changeTime = now();
			var scale = 1 - Math.max(0,(startTime - changeTime + times)/times);
			if(fnMove){
				fnMove(scale);
			}
			for(var attr in json){
				                
				var value = Tween[fx](scale*times, iCur[attr] , json[attr] - iCur[attr] , times );
				
				if(attr == 'opacity'){
					obj.style.filter = 'alpha(opacity='+ value +')';
					obj.style.opacity = value/100;
				}
				else{
					obj.style[attr] = value + 'px';
				}
				
				if(scale==1){
					clearInterval(obj.timer);
					if(fn){
						fn.call(obj);
					}
				}	
			}	
		},13);
		function now(){
			return Date.now();
		}
	}
	function getStyle(obj,attr){
		return getComputedStyle(obj,false)[attr];
	}
	var Tween = {
		linear: function (t, b, c, d){
			return c*t/d + b;
		},
		easeIn: function(t, b, c, d){
			return c*(t/=d)*t + b;
		},
		easeOut: function(t, b, c, d){
			return -c *(t/=d)*(t-2) + b;
		},
		easeBoth: function(t, b, c, d){
			if ((t/=d/2) < 1) {
				return c/2*t*t + b;
			}
			return -c/2 * ((--t)*(t-2) - 1) + b;
		},
		easeInStrong: function(t, b, c, d){
			return c*(t/=d)*t*t*t + b;
		},
		easeOutStrong: function(t, b, c, d){
			return -c * ((t=t/d-1)*t*t*t - 1) + b;
		},
		easeBothStrong: function(t, b, c, d){
			if ((t/=d/2) < 1) {
				return c/2*t*t*t*t + b;
			}
			return -c/2 * ((t-=2)*t*t*t - 2) + b;
		}
	}
}