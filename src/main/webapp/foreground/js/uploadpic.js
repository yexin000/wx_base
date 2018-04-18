var browser={ 
	versions:function(){ 
		var u = navigator.userAgent, app = navigator.appVersion; 
			return { //移动终端浏览器版本信息 
				trident: u.indexOf('Trident') > -1, //IE内核 
				presto: u.indexOf('Presto') > -1, //opera内核 
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核 
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核 
				mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端 
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器 
				iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器 
				iPad: u.indexOf('iPad') > -1, //是否iPad 
				webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部 
		}; 
	}(), 
	language:(navigator.browserLanguage || navigator.language).toLowerCase() 
} 

function isApple(browser) {
	if(browser.versions.ios == true
		|| browser.versions.iPhone == true
		|| browser.versions.iPad == true) {
		return true;
	} else {
		return false;
	}
}

function UploadPic() {
  this.sw = 0;
  this.sh = 0;
  this.tw = 0;
  this.th = 0;
  this.scale = 0;
  this.maxWidth = 0;
  this.maxHeight = 0;
  this.maxSize = 0;
  this.fileSize = 0;
  this.fileDate = null;
  this.fileType = '';
  this.fileName = '';
  this.input = null;
  this.canvas = null;
  this.mime = {};
  this.type = '';
  this.callback = function () {};
  this.loading = function () {};
}

UploadPic.prototype.init = function (options) {
  this.maxWidth = options.maxWidth || 800;
  this.maxHeight = options.maxHeight || 600;
  this.maxSize = options.maxSize || 3 * 1024 * 1024;
  this.input = options.input;
  this.mime = {'png': 'image/png', 'jpg': 'image/jpeg', 'jpeg': 'image/jpeg', 'bmp': 'image/bmp'};
  this.callback = options.callback || function () {};
  this.loading = options.loading || function () {};
    
  this._addEvent();
};

/**
 * @description 绑定事件
 * @param {Object} elm 元素
 * @param {Function} fn 绑定函数
 */
UploadPic.prototype._addEvent = function () {
  var _this = this;
    
  function tmpSelectFile(ev) {
    _this._handelSelectFile(ev);
  }
    
  this.input.addEventListener('change', tmpSelectFile, false);
};

/**
 * @description 绑定事件
 * @param {Object} elm 元素
 * @param {Function} fn 绑定函数
 */
UploadPic.prototype._handelSelectFile = function (ev) {
  var file = ev.target.files[0];
  
  this.type = file.type
  
  // 如果没有文件类型，则通过后缀名判断（解决微信及360浏览器无法获取图片类型问题）
  if (!this.type) {
    this.type = this.mime[file.name.match(/\.([^\.]+)$/i)[1]];
  }
  
  if (!/image.(png|jpg|jpeg|bmp)/.test(this.type)) {
    alert('选择的文件类型不是图片');
    return;
  }
    
  //if (file.size > this.maxSize) {
  //  alert('选择文件大于' + this.maxSize / 1024 / 1024 + 'M，请重新选择');
  //  return;
  //}

  this.fileName = file.name;
  this.fileSize = file.size;
  this.fileType = this.type;
  this.fileDate = file.lastModifiedDate;
    
  this._readImage(file);
};

/**
 * @description 读取图片文件
 * @param {Object} image 图片文件
 */
UploadPic.prototype._readImage = function (file) {
  var _this = this;
    
  function tmpCreateImage(uri) {
    _this._createImage(uri);
  }
  
  this.loading();
    
  this._getURI(file, tmpCreateImage);
};

/**
 * @description 通过文件获得URI
 * @param {Object} file 文件
 * @param {Function} callback 回调函数，返回文件对应URI
 * return {Bool} 返回false
 */
UploadPic.prototype._getURI = function (file, callback) {
  var reader = new FileReader();
  var _this = this;

  function tmpLoad() {
    // 头不带图片格式，需填写格式
    var re = /^data:base64,/;
    var ret = this.result + '';
        
    if (re.test(ret)) ret = ret.replace(re, 'data:' + _this.mime[_this.fileType] + ';base64,');
        
    callback && callback(ret);
  }
    
  reader.onload = tmpLoad;
    
  reader.readAsDataURL(file);
    
  return false;
};

/**
 * @description 创建图片
 * @param {Object} image 图片文件
 */
UploadPic.prototype._createImage = function (uri) {
  var img = new Image();
  var _this = this;
    
  function tmpLoad() {
    _this._drawImage(this);
  }
    
  img.onload = tmpLoad;
    
  img.src = uri;
};

/**
 * @description 创建Canvas将图片画至其中，并获得压缩后的文件
 * @param {Object} img 图片文件
 * @param {Number} width 图片最大宽度
 * @param {Number} height 图片最大高度
 * @param {Function} callback 回调函数，参数为图片base64编码
 * return {Object} 返回压缩后的图片
 */
UploadPic.prototype._drawImage = function (img, callback) {
  //if(!isApple(browser)) {
    this.sw = img.width;
    this.sh = img.height;
    this.tw = img.width;
    this.th = img.height;
    
    this.scale = (this.tw / this.th).toFixed(2);

    if (this.sw > this.maxWidth) {
      this.sw = this.maxWidth;
      this.sh = Math.round(this.sw / this.scale);
    }
  
    if (this.sh > this.maxHeight) {
      this.sh = this.maxHeight;
      this.sw = Math.round(this.sh * this.scale);
    }
  
    this.canvas = document.createElement('canvas');
    var ctx = this.canvas.getContext('2d');
    
    this.canvas.width = this.sw;
    this.canvas.height = this.sh;
    if(!isApple(browser)) {
    	ctx.drawImage(img, 0, 0, img.width, img.height, 0, 0, this.sw, this.sh);
    } else {
    	ctx.drawImage(img, img.width, img.height);
    }
  //} else {
  /*  this.sw = img.width;
    this.sh = img.height;
    this.tw = img.width;
    this.th = img.height;
    
    this.scale = (this.tw / this.th).toFixed(2);

    if (this.sw > this.maxWidth) {
      this.sw = this.maxWidth;
      this.sh = Math.round(this.sw / this.scale);
    }
  
    if (this.sh > this.maxHeight) {
      this.sh = this.maxHeight;
      this.sw = Math.round(this.sh * this.scale);
    }
  
    this.canvas = document.createElement('canvas');
    var ctx = this.canvas.getContext('2d');
    
    this.canvas.width = this.sw;
    this.canvas.height = this.sh;
    var tempWidth = img.width;
    img.setAttribute('width',img.height);
    img.setAttribute('height',tempWidth);
    ctx.rotate(90*Math.PI/180); 
    ctx.drawImage(img,-800,-800);
  }*/
    
  this.callback(this.canvas.toDataURL(this.type));
    
  ctx.clearRect(0, 0, this.tw, this.th);
  this.canvas.width = 0;
  this.canvas.height = 0;
  this.canvas = null;
};
function initPhoto() {
	var u = new UploadPic();
	u.init({
		input: document.querySelector('.input'),callback: function (base64) {
			if(picCount == 0) {
				picCount ++;
				base1 = base64.split(",")[1];
				document.getElementById("img1").src = base64;
			} else if(picCount == 1) {
				picCount ++;
				base2 = base64.split(",")[1];
				document.getElementById("img2").src = base64;
			} else if(picCount == 2) {
				picCount ++;
				base3 = base64.split(",")[1];
				document.getElementById("img3").src = base64;
			}
		/*var html = '';
		html = '<div class="itm"><div class="tit">图片名称：</div><div class="cnt">' + this.fileName + '</div></div>'
			+ '<div class="itm"><div class="tit">原始大小：</div><div class="cnt">' + (this.fileSize / 1024).toFixed(2) + 'KB<\/div><\/div>'
			+ '<div class="itm"><div class="tit">编码大小：</div><div class="cnt">' + (base64.length / 1024).toFixed(2) + 'KB<\/div><\/div>'
			+ '<div class="itm"><div class="tit">原始尺寸：</div><div class="cnt">' + this.tw + 'px * ' + this.th + 'px<\/div><\/div>'
			+ '<div class="itm"><div class="tit">编码尺寸：</div><div class="cnt">' + this.sw + 'px * ' + this.sh + 'px<\/div><\/div>'
			+ '<div class="itm"><div class="tit">图片预览：</div><div class="cnt"><img src="' + base64 + '"><\/div><\/div>'
			+ '<div class="itm"><div class="tit">Base64编码：</div><div class="cnt"><textarea>' + base64 + '<\/textarea><\/div><\/div>';

			document.querySelector('.imgzip').innerHTML = html;*/
		},loading: function () {
			//document.querySelector('.imgzip').innerHTML = '读取中，请稍候...';
		}
	});
}