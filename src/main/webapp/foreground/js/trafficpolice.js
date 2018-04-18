(function($){
	jQuery.fn.extend({
		"xx":function(){
			$(this).hide();
			var $x = $(this);
			var $xi = $(this).find('li');
			var $xform = $x.prev('form');
			var $xf = $xform.find('input');
			$xf.focus(function(){
				var $cul = $(this).parent().parent().next('ul');
				$('ul').not($cul).hide();
				$cul.show();
				if($xf.val()=="" && $cul.hasClass('vv')){
					$cul.hide();
				}
			});
			$xf.keyup(function(){
				var $cul = $(this).parent().parent().next('ul');
				if($xf.val() !== "")
					$cul.show();
			});
			$xi.click(function(e){
				$xf.val($(e.target).text());
				$x.hide();
			});
			return $(this).parent().parent().next('tr').find('ul');
		},
		"getInput":function(){
			return $(this).prev('form').find('input');
		}
	});
})(jQuery);
$(function(){
	var ID = {id:0};
	ID.next = function(){
		this.id ++;
		return this.id;
	}
	var GPS = {geoPos:0};
	window.GPS = GPS;
	GPS.position = function(conf){
		var self = this;
		if(self.geoPos == 1){
			//���ڶ�λ��...
		}else if (navigator.geolocation && this.geoPos == 0){
			self.geoPos = 1;
			if($.isFunction(conf.onSearch))conf.onSearch();
			navigator.geolocation.getCurrentPosition(function(pos){
				var geoPos = {};
				self.geoPos = geoPos;
				geoPos['latitude'] = pos.coords.latitude;
				geoPos['longitude'] = pos.coords.longitude;
				conf.success(geoPos);
			},function(){
				self.geoPos = 0;
				if($.isFunction(conf.failure))conf.failure();
			},{ enableHighAccuracy: true, timeout: 12000, maximumAge: 12000 });
		}else if($.isPlainObject(this.geoPos)){
			conf.success(this.geoPos);
		}else{
			if($.isFunction(conf.failure))conf.failure();
			self.geoPos = -1;
		}
	}
	
	if($.validator &&　$.validator.messages){
		$.extend($.validator.messages,{
			required: "必填项,请您输入",
			remote: "Please fix this field.",
			email: "请输入正确的电子邮箱",
			url: "请输入正确的URL.",
			date: "请输入正确的日期",
			dateISO: "Please enter a valid date (ISO).",
			number: "必须是数字",
			digits: "请输入数字",
			creditcard: "请输入正确的卡号",
			equalTo: "两次输入不一致",
			maxlength: $.validator.format("最多能输入{0}位."),
			minlength: $.validator.format("至少输入{0}位."),
			rangelength: $.validator.format("长度限制为{1}位"),
			range: $.validator.format("数字必须在 {0}到{1}."),
			max: $.validator.format("数字必须小于等于{0}."),
			min: $.validator.format("数字必须大于等于{0}.")
		});
		jQuery.validator.addMethod("mobile", function(value, element) {
		    var length = value.length;
		    var mobile =  /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
		    return this.optional(element) || (length == 11 && mobile.test(value));
		}, "�ֻ�����ʽ����");
		// �绰������֤   
		jQuery.validator.addMethod("phone", function(value, element) {
		    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
		    return this.optional(element) || (tel.test(value));
		}, "�绰�����ʽ����");
		
		// ��ϵ�绰(�ֻ�/�绰�Կ�)��֤   
		jQuery.validator.addMethod("isPhone", function(value,element) {
		  var length = value.length;   
		  var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
		  var tel = /^\d{3,4}-?\d{7,9}$/;   
		  return this.optional(element) || (tel.test(value) || mobile.test(value));   
		   
		}, "����ȷ��д�����ϵ�绰");
	};
	$.fn.scrollBottom = function(callback){
		var that = $(this);
		var loadWidget = $('<div class="paging-loading ui-btn-up-d ui-shadow">������...</div>');
		that.data('loadWidget',loadWidget);
		var loadId = ID.next();
		that.data('loadId',loadId);
		$(window).bind('scroll.'+loadId,function(){
			var pos = $(document).height() - ($(window).scrollTop()) - $(window).height()
			if(pos <= 20){
				if(that.data('loading')) return ;
		     	that.data('loading',true);
				that.append(that.data('loadWidget'));
				callback.apply(that[0]);
			}
		});
	};
	$.fn.scrollEnd = function(finished){
		var that = $(this);
		if(that.data('loadWidget')) that.data('loadWidget').remove();
		that.data('loading',false);
		if(finished){
			var loadId = that.data('loadId');
			$(window).unbind('scroll.'+loadId);
		}
	}
	$.fn.isShow = function(){
		return $(this).css('display') != 'none';
	}

    $.fn.serializeJson = function(jsonData){
    	if(this.length > 0){
    		if(jsonData){
    			$('[name]',this).each(function(){
    				var that = $(this);
    				var name = that.attr('name');
    				var value = name ? (jsonData[name] || '') : '';
    				if(value != ''){
	    				if(that[0].tagName == 'INPUT'){
	    					if(jsonData[name] != undefined) that.val(value);
	    				}else{
	    					that.text(value);
	    				}
    				}
    			});
    			
    			return this;
    		}
			var res = {};
			$('input,select,textarea',this).each(function(){
				var that = $(this);
				var name = that.attr('name');
				var val = that.attr('val');
				val = val? val : that.val();
				if(val != undefined && val != ''){
					res[name] = val;
				}
			});
			return res;
    	}else{
    		return {};
    	}
    };
    
    $.postJSON = function(e,r,success,error){
    	$.ajax({url:e,type:'post',dataType:'json',data:r,success:success,error:error});
    }

    $.syncPostJSON = function(url,params,callback){
    	$.ajax({type: 'post',async:false, url: url, data: params, success: callback, dataType: 'json'});
    };
    
    $.getJSON = function(e,r,success,error){
    	$.ajax({url:e,type:'get',timeout:10000,dataType:'json',data:r,success:success,error:error});
    }
    
    $.urlParam = function(name){
        var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
        return (results && results.length > 0) ? results[1] : 0;
    }
    Date.prototype.format = function (format){ 
	    var o = { 
		    "M+" : this.getMonth()+1, //month 
		    "d+" : this.getDate(), //day 
		    "h+" : this.getHours(), //hour 
		    "m+" : this.getMinutes(), //minute 
		    "s+" : this.getSeconds(), //second 
		    "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		    "S" : this.getMilliseconds() //millisecond 
	    } 
	    if(/(y+)/.test(format)) { 
	    	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	    } 
	    for(var k in o) { 
		    if(new RegExp("("+ k +")").test(format)) { 
		    	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		    } 
	    } 
	    return format; 
    }
    //JqueryMobile
	if($.mobile && $.mobile.loading){
		$.mobile.showLoading = function(callback,msg){
			if($.isFunction(callback) && $.mobile.loaderWidget.isShow()) return false;
			$.mobile.loading('show', {  
    	        text:(msg||'������...'),
    	        textVisible: true,
    	        theme: 'a',
    	        textonly: false,
    	        html: ""
    	    });
			if($.isFunction(callback)){
				callback.call();
			}
		};
		$.mobile.hideLoading = function(){
			$.mobile.loading('hide');
		}
	}
    //###########################
	//���ڽ���
    //###########################
    var TrafficPolice = {};
    //�����ľٱ�,��д�ٱ���Ϣ
    TrafficPolice.IllegalReportPage = function(){
        var geocoder = new qq.maps.Geocoder({
            complete : function(result){
            }
        });
    	var lon = $('#lon').val().split(',');
    	var latLng = new qq.maps.LatLng(lon[0], lon[1]);
    	geocoder.getAddress(latLng);
    	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    		WeixinJSBridge.call('hideOptionMenu');
		});
    	$('#mydate').on('change',function(){
    		var time = $('#mydate').val();
    		time = time.replace(/-/g,"/");
    		time = new Date(Date.parse(time));
    		var now = new Date();
    		var lastThreeDay = new Date();
    		lastThreeDay.setDate(now.getDate()-3);
    		if(time <= lastThreeDay || time > now){
 				$('body').simpledialog2({
 					mode: 'button',
 					headerText: '���ڽ���',
 					buttonPrompt: '�ٱ���Υ��ʱ��ֻ��ѡ��72Сʱ֮��',
 					buttons : {
 					 'ȷ��': {
 						click: function () {
 							$("#mydate").val("");
 							$('#mydate').next('a').trigger('click');
 						}
 					 }
 					}
 				});
 				return false;
    		}
    	});
    	//alert($('input[name=mydate]').val());
    	if($('input[name=countNum]').val()>0){
    		$('.counttr').show();
    		if($('input[name=countNum]').val()<=3){
    			$('.counta').append('������������Υ���ٱ����������޸�'+(4-$('input[name=countNum]').val())+'��.');
    		}else{
    			$('.counta').append('���Ѿ��޸Ķ���ˣ��������������Ϣ��Ҫ���ƣ��������ύΥ���ٱ���лл��');
    			$('.btnSubmit').hide();
    		}
    	}
    	function saveInfo(){
    		var data1={};
    		data1['qianming'] = $('#iname').val();
			data1['captcha']=$('input[name=captcha]').val();
			data1['number']=$('input[name=number]').val();
			data1['mydate']=$('input[name=mydate]').val();
			data1['informer']=$('input[name=informer]').val();
			data1['carno'] = $('#prefix-select').val()+$('#aprefix-select').val()+$('#carNum').val();
			data1['carno1'] = $('#carNum').val();
			data1['tel']=$('input[name=tel]').val();
			data1['area']=$('#area').val();
			data1['road']=$('#road').getInput().val();
			data1['roaddetail']=$('#roaddetail').getInput().val();
			data1['kind']=$('#kind').getInput().val();
			data1['dzqmdetail'] = $('.dzqm').text() + 'ǩ��' +  data1['qianming'];
			var z = $('#kind').prev('form').find('input').val();
			if($('#kind li.jiang').filter(':contains('+z+')').size() > 0){
				youjiang = true;
			}else{
				youjiang = false;
			}
			data1['youjiang'] = youjiang;
			if($.mobile.loaderWidget.isShow()) return false;
			$.mobile.loading('show', {  
    	        text: '������...',
    	        textVisible: true,
    	        theme: 'a',  
    	        textonly: false,
    	        html: ""
    	    });
			$.postJSON('/weixin/trafficpolice/doIllegalReport',data1,function(rest){
				if(rest.status){
					$.mobile.changePage('#success');
				}else{
					$.mobile.loading('hide');
					$('img.captcha-img').trigger('click',true);
					alert(rest.message); //�ύ�Ʊ�
				}
			},function(){
				$.mobile.loading('hide');
				$('img.captcha-img').trigger('click',true);
			});
    	}
    	$('#icancal').click(function(){
    		$('body').simpledialog2({
					mode: 'button',
					headerText: '���ڽ���',
					buttonPrompt: 'ȷ��ȡ��֮ǰ���в�����',
					buttons : {
					 'ȷ��': {
						click: function () {
							//saveInfo();
							$.mobile.changePage('#showResult');
						}
					 },
					 'ȡ��':{
						 icon : "delete",
						 theme: "c",
						 click:function(){}
					 }
					}
				});
    	});
    	$('#iok').click(function(){
    		var re = /[^\u4e00-\u9fa5]/;
    		if($('#iname').val() === '' || re.test($('#iname').val())){
    			$('body').simpledialog2({
 					mode: 'button',
 					headerText: '���ڽ���',
 					buttonPrompt: '����ȷ������ĵ���ǩ��',
 					buttons : {
 					 'ȷ��': {
 						click: function () {}
 					 }
 					}
 				});
    			return false;
    		}else{
    			saveInfo();
    		}
    	});
    	var form = $('#xform');
    	var logonForm = form;
    	if(form.length > 0){
    		$('form.ui-listview-filter').each(function(){
    			var iform = $(this);
    			iform.validate();
    			iform.find('input').attr('required','required');//('add',{required:true});
    		});
    		form.submit(false);
    		form.validate({
    			rules: {
    				carNum:{
    					required:true,
    					rangelength:[5,5]
    				},
    				informer:{
    					required:true
    				},
    				tel:{
    					required:true,
    					isPhone:true
    				}
				},
				invalidHandler:function(e,form){
					if(form.errorList.length == 1){
						var element = form.errorList[0].element;
						if($(element).attr('name') == 'captcha'){
							//$('img.captcha-img').listview('refresh').trigger('create');
						}
					}
				},
				submitHandler:function(form){
					var d = new Date();
					var iyear = d.getFullYear();  
					var imonth = d.getMonth()+1;
					imonth = imonth < 10 ? "0"+imonth : imonth;
					var iday = d.getDate();
					iday = iday < 10 ? "0"+iday : iday;
					var ihour = d.getHours();
					ihour = ihour < 10 ? "0"+ihour : ihour;
					var imin = d.getMinutes();
					imin = imin < 10 ? "0"+imin : imin;
					var icarNum = $('#prefix-select').val()+$('#aprefix-select').val()+$('#carNum').val();
					var i2year = $('#mydate').val().substring(0,4);
					var i2month = $('#mydate').val().substring(5,7);
					var i2day = $('#mydate').val().substring(8,10);
					var i2hour = $('#mydate').val().substring(11,13);
					var i2min = $('#mydate').val().substring(14,16);
					var iroad = $('#area').val()+$('#road').getInput().val();
					if($('#roaddetail').getInput().val() !== $('#road').getInput().val()){
						iroad += $('#roaddetail').getInput().val();
					}
					var itype = $('#kind').getInput().val();
					$('#iyear').text(iyear);
					$('#imonth').text(imonth);
					$('#iday').text(iday);
					$('#ihour').text(ihour);
					$('#imin').text(imin);
					$('#icarNum').text(icarNum);
					$('#i2year').text(i2year);
					$('#i2month').text(i2month);
					$('#i2day').text(i2day);
					$('#i2hour').text(i2hour);
					$('#i2min').text(i2min);
					$('#iroad').text(iroad);
					$('#itype').text(itype);
					$.mobile.changePage('#elecsign');
				},
    			showErrors:function(){
    			this.errorList = this.errorList.slice(0,1);
    			this.defaultShowErrors();
    		}});
    		
    		$('.btnSubmit').click(function(){
    			if($('input[name=countNum]').val()<=3){
//    	    		$('.btnSubmit').hide();
    				var allOK = false;
    				$('form.ui-listview-filter').each(function(){
        				var iform = $(this);
        				allOK = iform.valid();
    		});
        			if(logonForm.valid()){
        				if(allOK) logonForm.submit();
    	}
    	    	}
    		});
    	}
		$('img.captcha-img').click(function(e,onclick){
			if(!onclick && $.mobile.loaderWidget.isShow()) return false;
			$.mobile.showLoading();
			
			var token = new Date().getTime();
			var that = $(this);
			var src = that.data('src');
			if(!src){
				src = that.attr('src');
				that.data('src',src);
			}
			src += '?'+token;
			that.attr('src',src);
		}).load(function(){
			$.mobile.loading('hide');
		});
		$('#kind').xx();
		function toOption(arr){
			var temp =  '';
			for(var i = 0 ;i < arr.length ; i++){
				temp += '<option value="' + arr[i] + '">' + arr[i] + '</option>';
			}
			return temp;
		};
		$.getJSON('/weixin/trafficpolice/illegalReport/getallarea','',function(data){
			$('#area').empty();
			var optionlist = '';
			/*$.each(data,function(index,item){
				optionlist += '<option value='+data[index]['name']+'>' + data[index]['name'] +'</option>';
			});*/
			var arr = ['������','�޺���','��ɽ��','������','������','�����','ƺɽ����','��������','������','��������','���ٹ�·'];
			optionlist += toOption(arr);
			$('#area').append(optionlist).selectmenu("refresh", true);
			//$('#area').val('������').attr('selected', true).siblings('option').removeAttr('selected');
			$('#area').change(function(e){
				$('#road').getInput().val('');
				$('#roaddetail').getInput().val('');
				$.mobile.showLoading();
				var area = $('#area :selected').text();
				$.getJSON('/weixin/trafficpolice/illegalReport/getarearoad',{'areaName':area},function(data){
					$('#road').empty();
					var html = '';
					$.each(data,function(index,comment){
						html += '<li>'+comment.name+'</li>';
					});
					$.mobile.hideLoading();
					$('#road').append(html).listview('refresh');
					$('#road').xx();
					$('#road').click(function(e){
						$('#roaddetail').getInput().val('');
						var carea = $('#area :selected').text();
						var road = $(e.target).text();
						$.mobile.showLoading();
						$.getJSON('/weixin/trafficpolice/illegalReport/getareadetailroad',{'areaName':carea,'areaRoad':road},function(data){
							$('#roaddetail').empty();
							var html = '';
							$.each(data,function(index,comment){
								html += '<li>'+comment.name+'</li>';
							});
							$.mobile.hideLoading();
							$('#roaddetail').append(html).listview('refresh');
							$('#roaddetail').xx();
						});
					});
				});
			});
			$('#area').trigger('change');
			if($('input[name=mydate]').val()==null || $('input[name=mydate]').val().trim().length<=0){
	    		$('input[name=mydate]').val(new Date().format("yyyy-MM-dd hh:mm:ss"));
	    	}
	    	var loadPlace=$('input[name=loadPlace]').val();
	    	if(loadPlace!=null || loadPlace.trim().length>0){
	    		var loads=loadPlace.split("-");
	    		$('#area option:contains("'+loads[0]+'")').prop('selected',true);
	    		$('#area').selectmenu('refresh');
	    		$('#road').prev('form').children('div').children('input').val(loads[1]);
				$('#roaddetail').prev('form').children('div').children('input').val(loads[2]);
				$('#kind').prev('form').children('div').children('input').val(loads[3]);
				$('#carNum').val(loads[4]);
	    	}
		});
		this.renderCartLicenseSelect();
    };
    TrafficPolice.IllegalReportPage.prototype = {
        	cartLicensePrifix:function(){
        		if(this.text) return this.text;
        		else{
        			var text = $('#cart-license').text();
            		text = text.split(',');
            		this.text = text;
        		}
        		return this.text;
        	},
        	getSelectValue:function($parent){
        		var $prefixSelect = $parent.data('$prefixSelect');
    			var $aPrefixSelect = $parent.data('$aPrefixSelect');
    			return [$prefixSelect.val(),$aPrefixSelect.val()];
        	},
        	selectCartLicense:function($parent,prefix,carLicens){
        		var $prefixSelect = $parent.data('$prefixSelect');
    			var $aPrefixSelect = $parent.data('$aPrefixSelect');
    			
    			$prefixSelect.val(prefix);
    			$aPrefixSelect.val(carLicens);
    			
    			$prefixSelect.selectmenu("refresh");
    			$aPrefixSelect.selectmenu("refresh");
        	},
        	renderCartLicenseSelect:function($parent){
        		var text = this.cartLicensePrifix();
        		var $prefixSelect = $parent ? $parent.find('#prefix-select') : $('#prefix-select');
        		var $aPrefixSelect = $parent ? $parent.find('#aprefix-select'):$('#aprefix-select');
        		if($parent && $parent.length > 0){
        			$parent.data('$prefixSelect',$prefixSelect);
        			$parent.data('$aPrefixSelect',$aPrefixSelect);
        		}
        		var prefixSelect = $prefixSelect[0];
        		var aPrefixSelect = $aPrefixSelect[0];
        		this.prefixSelect = prefixSelect;
        		this.aPrefixSelect = aPrefixSelect;
        		
        		var part,tmpListMap,tmpList=[];
        		var prefixMap = {};
        		for(var i=0;i<text.length;i++){
        			part = $.trim(text[i]);
        			if(part.length > 1){
        				tmpListMap = prefixMap[part.charAt(0)];
        				tmpListMap = tmpListMap || {};
        				tmpListMap[part.charAt(1)] = '1';
        				prefixMap[part.charAt(0)] = tmpListMap;
        			}
        		}
        		var prefixList = [],_prefixList=['��','��','��'];
        		for(var key in prefixMap){
        			prefixList.push(key)
        		}
        		
        		for(var i=0;i<prefixList.length;i++){
        			if($.inArray(prefixList[i],_prefixList) == -1){
        				_prefixList.push(prefixList[i]);
        			}
        		}
        		prefixList = _prefixList;
        		//���������� ��ǰ��
        		for(var i=0;i<prefixList.length;i++){
        			prefixSelect.options.add(new Option(prefixList[i],prefixList[i]));
        			tmpList = [];
        			for(var k in prefixMap[prefixList[i]]){
        				tmpList.push(k);
        			}
        			tmpList = tmpList.sort();
        			prefixMap[prefixList[i]] = tmpList;
        		}
        		
        		
        		
        		$prefixSelect.change(function(){
        			var val = $(this).val();
        			var aPrefix = prefixMap[val];
    				$(aPrefixSelect.options).remove();
        			for(var i=65;i<=90;i++){
        				var v = String.fromCharCode(i);
        				aPrefixSelect.options.add(new Option(v,v));
        				if(i == 0) {
        					if($.inArray(val,_prefixList) == 0){
        						aPrefixSelect.selectedIndex = 1;
        					}else{
        						aPrefixSelect.selectedIndex = 0;
        					}
        				}
        			}
        			if($.inArray(val,_prefixList) == 0){
    					aPrefixSelect.selectedIndex = 1;
    				}else{
    					aPrefixSelect.selectedIndex = 0;
    				}
        			$aPrefixSelect.selectmenu("refresh");
        		}).selectmenu("refresh").trigger('change');
        	}
        }
    
    
    //�����ľٱ�
    TrafficPolice.IllegalReportResultPage = function(){
    	var form = $('form');
    	var logonForm = form;
    	if(form.length > 0){
    		form.submit(false);
    		form.validate({
    			rules: {
					captcha:{
						required:true,
						rangelength:[4,4],
						 remote: {
						        url: "/weixin/captcha/validato",
						        type: "post"
						 }
					}
				},
				messages:{
					captcha:{
						required:'������֤��',
						rangelength:'��֤�����',
						remote:'��֤�����'
					}
				},
				invalidHandler:function(e,form){
					if(form.errorList.length == 1){
						var element = form.errorList[0].element;
						if($(element).attr('name') == 'captcha'){
							$('img.captcha-img').trigger('click',true);
						}
					}
				},
				submitHandler:function(form){
					if($.mobile.loaderWidget.isShow()) return false;
	    			$.mobile.loading('show', {  
	        	        text: '������...',
	        	        textVisible: true,
	        	        theme: 'a',  
	        	        textonly: false,
	        	        html: ""
	        	    });
					$.postJSON('/weixin/trafficpolice/illegalReportResult',$(form).serializeArray(),function(rest){
						if(rest.status){
							if(rest.message==null){
								$('#result').text("δ�������Ϣ��");	
							}else{
								$('#result').text(rest.message);
							}
							$.mobile.changePage('#success');
						}else{
							$.mobile.loading('hide');
							$('img.captcha-img').trigger('click',true);
							//alert(rest.message);
							$.mobile.changePage('#fail');
						}
						
					},function(){
						$.mobile.loading('hide');
						$('img.captcha-img').trigger('click',true);
					});
				},
    			showErrors:function(){
    			this.errorList = this.errorList.slice(0,1);
    			this.defaultShowErrors();
    		}});
    		
    		$('.btnSubmit').click(function(){
    			logonForm.submit();
    		});
    	}
		$('img.captcha-img').click(function(e,onclick){
			if(!onclick && $.mobile.loaderWidget.isShow()) return false;
			$.mobile.showLoading();
			
			var token = new Date().getTime();
			var that = $(this);
			var src = that.data('src');
			if(!src){
				src = that.attr('src');
				that.data('src',src);
			}
			src += '?'+token;
			that.attr('src',src);
		}).load(function(){
			$.mobile.loading('hide');
		});
    };
    
    /*����Υ����Ϣ��ѯ*/
    TrafficPolice.TrafficPeccancyInquiry = function(){
    	var form = $('form');
    	var logonForm = form;
    	if(form.length > 0){
    		form.submit(false);
    		form.validate({
    			rules: {
    				carNum:{
    					required:true,
    					rangelength:[5,5]
    				},
    				carNumLastFour:{
    					rangelength:[4,4]
    				},
					captcha:{
						required:true,
						rangelength:[4,4],
						 remote:{
						        url: "/weixin/captcha/validato",
						        type: "post"
						 }
					}
				},
				messages:{
					captcha:{
						required:'������֤��',
						rangelength:'��֤�����',
						remote:'��֤�����'
					}
				},
				invalidHandler:function(e,form){
					if(form.errorList.length == 1){
						var element = form.errorList[0].element;
						if($(element).attr('name') == 'captcha'){
							$('img.captcha-img').trigger('click',true);
						}
					}
				},
				submitHandler:function(form){
					if($.mobile.loaderWidget.isShow()) return false;
	    			$.mobile.loading('show', {  
	        	        text: '������...',
	        	        textVisible: true,
	        	        theme: 'a',  
	        	        textonly: false,
	        	        html: ""
	        	    });
	    			var data = $(form).serializeJson();
	    			data['carNum'] = data['prefix'] + data['aprefix'] + data['carNum'];
	    			
					$.getJSON('/weixin/trafficpolice/trafficPeccancyInquiryLogic',data,function(rest){
						if(rest.status){
							$('#cc').text('����Υ����ѯ���');
							$('form').hide();
							$.mobile.loading('hide');
							$('#cresult')[0].innerHTML = rest.message;
							$('#cresult').show();
							$('#dd').show();
							$('#dd').click(function(){
								$('#cc').text('����Υ����Ϣ��ѯ');
								$('#cresult').hide();
								$('form').show();
								$('#dd').hide();
							});
							$('img.captcha-img').trigger('click',true);
							$('#captcha').val('');
						}else{
							$.mobile.loading('hide');
							$('img.captcha-img').trigger('click',true);
							$('#captcha').val('');
							alert(rest.message); //��¼ʧ��
						}
					},function(){
						$.mobile.loading('hide');
						$('img.captcha-img').trigger('click',true);
						$('#captcha').val('');
					});
				},
    			showErrors:function(){
    			this.errorList = this.errorList.slice(0,1);
    			this.defaultShowErrors();
    		}});
    		
    		$('.xsubmit').click(function(){
    			logonForm.submit();
    		});
    		
    		this.renderCartLicenseSelect();
    	}
		$('img.captcha-img').click(function(e,onclick){
			if(!onclick && $.mobile.loaderWidget.isShow()) return false;
			$.mobile.showLoading();
			
			var token = new Date().getTime();
			var that = $(this);
			var src = that.data('src');
			if(!src){
				src = that.attr('src');
				that.data('src',src);
			}
			src += '?'+token;
			that.attr('src',src);
		}).load(function(){
			$.mobile.loading('hide');
		});
    };
    TrafficPolice.TrafficPeccancyInquiry.prototype = TrafficPolice.IllegalReportPage.prototype;
    TrafficPolice.trafficIllegalOrderNew = function(){
    	this.initFormValidate();
    	//ʱ��Ƚ�
    	function bigThanNow(a){
    		a = a.replace(/-/g,"/");
    		var d1 = new Date(Date.parse(a));
    		var d2 = new Date();
    		if(d1>d2){
    			return true;
    		}else{
    			return false;
    		}
    	};
    	var self = this;
    	$('img.captcha-img').click(function(e,onclick){
			if(!onclick && $.mobile.loaderWidget.isShow()) return false;
			$.mobile.showLoading();
			
			var token = new Date().getTime();
			var that = $(this);
			var src = that.data('src');
			if(!src){
				src = that.attr('src');
				that.data('src',src);
			}
			src += '?'+token;
			that.attr('src',src);
		}).load(function(){
			$.mobile.loading('hide');
		});
    	function showPageOne(){
    		$('.page').hide();
    		$('#pageOne').show();
    	}
    	function showPageTwo(){
    		$('.page').hide();
    		$('#pageTwo').show();
    		$('#img1').trigger('click',true);
    		$('#captcha').val('');
    	}
    	function showPageThree(){
    		$('.page').hide();
    		$('#pageThree').show();
    	}
    	function showPageFour(){
    		$('.page').hide();
    		$('#pageFour').show();
    	}
    	function showPageFive(){
    		$('.page').hide();
    		$('#pageFive').show();
    	}
    	function showPageSix(){
    		$('.page').hide();
    		$('#scarNum').val($('#carNum').val());
    		$($('#scarType').prev('span').children('span')[0]).children('span').text($('#carType :selected').text());
    		$('#stelephone').val($('#telephone').val());
    		$('#img2').trigger('click',true);
    		$('#captcha2').val('');
    		
    		var selectedValues = self.getSelectValue(self.stepOneForm);
    		self.selectCartLicense(self.myTrafficform,selectedValues[0],selectedValues[1]);
    		
    		$('#pageSix').show();
    	}
    	function showPageSeven(){
    		$('.page').hide();
    		$('#pageSeven').show();
    	}
    	$('#nbOne').trigger('click');
    	$('#nbThree').click(showPageSeven);
    	$('#sevSearch').click(function(){
    		if(!self.queryTrafficForm.data('validator').form()) return false;
    		var yylsh = $('#sevyyh').val();
    		$.mobile.showLoading();
    		$.getJSON('/weixin/trafficpolice/trafficIllegalOrderCancelLogic',{'yylsh':yylsh},function(rest){
    			$('#sevResult').show();
    			if(rest.status){
    				$('#sevResult').text("ԤԼ��ˮ�ţ�"+$('#sevyyh').val()+"ȡ��ɹ�");
    			}else{
    				$('#sevResult').text("ԤԼ��ˮ�ţ�"+$('#sevyyh').val()+"ȡ��ʧ��");
    			}
    			$.mobile.hideLoading();
    		})
    	});
    	$('#sSearch').click(function(){
    		if(!self.myTrafficform.data('validator').form()) return false;
    		var sdata = $('#myTrafficform').serializeJson();
    		var qdata = {};
    		qdata['hphm'] = sdata['prefix']+sdata['aprefix']+sdata['scarNum'];
    		qdata['hpzl'] = sdata['scarType'];
    		qdata['sjhm'] = sdata['stelephone'];
    		$.mobile.showLoading();
    		$.getJSON("/weixin/trafficpolice/trafficIllegalSearch",qdata,function(data){
    			$('#sDiv').empty();
    			var html = '';
    			if(data.length === 0){
    				$('body').simpledialog2({
        				mode: 'button',
        				headerText: '���ڽ���',
        				buttonPrompt: 'δ��ѯ����Ӧ��¼��',
        				buttons : {
        				 'ȷ��': {
        					click: function () { 
        					}
        				 }
        				}
        			})
    			}else{
	    			$.each(data,function(index,comment){
						html += '<li>'
								+'<p>ԤԼ��ˮ�ţ�<span>' + data[index]['yylsh'] + '</span></p>'
								+'<p>���ƺ��룺' + data[index]['hphm'] + '</p>'
								+'<p>�ֻ���룺' + data[index]['sjhm'] + '</p>'
								+'<p>����㲿�ţ�' + data[index]['cldbmmc'] + '</p>'
								+'<p>ԤԼ���ڣ�' + data[index]['yydate'] + ' ' + data[index]['yydate_sjd']  + '('+ data[index]['yydate_xq']  +')'+'</p>'
								+'<p>¼��ʱ�䣺' + data[index]['lrsj'] + '</p>';
						if(bigThanNow(data[index]['yydate'])){
							html += '<button class="qxyy" data-theme="c">ȡ��ԤԼ</button>';
						}
						html += '</li>';
					});
	    			$('#sDiv').append(html).listview('refresh').trigger('create');
	    			$.mobile.silentScroll(300);
	    			$('.qxyy').click(function(e){
	    	    		//if(!self.queryTrafficForm.data('validator').form()) return false;
	    	    		var yylsh = $($(e.target).parent().parent().find('span')[0]).text();
	    	    		console.log(yylsh);
	    	    		$.mobile.showLoading();
	    	    		$.getJSON('/weixin/trafficpolice/trafficIllegalOrderCancelLogic',{'yylsh':yylsh},function(rest){
	    	    			if(rest.status){
	    	    				$('body').simpledialog2({
	    	    					mode: 'button',
	    	    					headerText: '���ڽ���',
	    	    					buttonPrompt: 'ԤԼ��ˮ�ţ�'+yylsh+'ȡ��ɹ�',
	    	    					buttons : {
	    	    					 'ȷ��': {
	    	    						click: function () { 
	    	    						}
	    	    					 }
	    	    					}
	    	    				})
	    	    				$(e.target).parent().parent().hide();
	    	    				var postData = {'orderNum':yylsh};
	    	    				$.postJSON('/weixin/trafficpolice/changeIllegalStatus',postData,function(d){
	    	    					if(d.status || false){
	    	    						console.log('ȡ��ԤԼ��ˮ�ż�¼���³ɹ�');
	    	    					}else{
	    	    						console.log('ȡ��ԤԼ��ˮ�ż�¼����ʧ��');
	    	    					}
	    	    				});
	    	    			}else{
	    	    				$('body').simpledialog2({
	    	    					mode: 'button',
	    	    					headerText: '���ڽ���',
	    	    					buttonPrompt: 'ԤԼ��ˮ�ţ�'+yylsh+'ȡ��ʧ��',
	    	    					buttons : {
	    	    					 'ȷ��': {
	    	    						click: function () { 
	    	    						}
	    	    					 }
	    	    					}
	    	    				})
	    	    			}
	    	    			$.mobile.hideLoading();
	    	    		});
	    	    	});
    			}
    			$('#img2').trigger('click',true);
        		$('#captcha2').val('');
    			$.mobile.hideLoading();
    		},function(){
    			alert('��ѯʧ��');
    			$.mobile.hideLoading();
    		});
    	});
    	$('#xnext').click(function(){
    		if($("input[type='checkbox']").prop("checked") === true){
    			showPageTwo();
    		}else{
    			$('body').simpledialog2({
    				mode: 'button',
    				headerText: '���ڽ���',
    				buttonPrompt: '��ȷ���������Ķ��������ݣ��������������ϡ�',
    				buttons : {
    				 'ȷ��': {
    					click: function () { 
    					}
    				 }
    				}
    			})
    		}
    	});
    	$('#nbOne').click(function(){
    		showPageOne();
    	});
    	var initPageSix = function(){
    		showPageSix();
    	};
    	$('#fivLast').click(function(){
    		$('#nbTwo').trigger('click');
    	});
    	$('#nbTwo').click(showPageSix);
    	var self = this;
    	$('#xnextTwo').click(function(){
    		if(!self.stepOneForm.data('validator').form()) return false;
    		$.mobile.showLoading();
    		$.getJSON("/weixin/trafficpolice/trafficIllegalOrderPlaceQuery",'',function(data){
    			showPageThree();
    			$('#ulOne').empty();
				var html = '<li data-role="divider" data-theme="b">ѡ��Υ�������</li>';
				$.each(data,function(index,comment){
					html += '<li><a>'
							+'<div style="display:none;">' + data[index]['cldbmid'] + '</div>'
							+'<h3>' + data[index]['cldbmmc'] + '</h4>'
							+'<p>�绰��' + data[index]['cldlxdh'] + '</p>'
							+'<p>��ַ��' + data[index]['cldaddress'] + '</p>'
							+'</a></li>';
				});
				if(data.length === 0){
					$('body').simpledialog2({
	    				mode: 'button',
	    				headerText: '���ڽ���',
	    				buttonPrompt: 'ϵͳ��æ��������һ�������ԡ�',
	    				buttons : {
	    				 'ȷ��': {
	    					click: function () { 
	    					}
	    				 }
	    				}
	    			});
				}
				html += "<button id='tlast'>��һ��</button>"
				$('#ulOne').append(html).listview('refresh').trigger('create');
				$('#tlast').click(showPageTwo);
				$.mobile.hideLoading();
				$('#ulOne li a').click(function(e){
					$.mobile.showLoading();
					var cldbmid = '';
					var xplace = '';
					if(e.target.tagName === 'A'){
						cldbmid = $(e.target).children('div').text();
						xplace = $(e.target).children('h3').text();
					}else{
						cldbmid = $(e.target).parent().children('div').text();
						xplace = $(e.target).parent().children('h3').text();
					}
					html = '';
					$.getJSON("/weixin/trafficpolice/trafficIllegalOrderDateQuery",{'cldbmid':cldbmid},function(data){
						showPageFour();
						$('#choose').empty();
						console.dir(data);
						if(data.length == 0){
							$.mobile.hideLoading();
							var xhtml = '';
							xhtml += "<div><button id='flast'>��һ��</button></div>";
							$('#choose').append(xhtml).trigger('create');
							$('#flast').click(showPageThree);
							$('body').simpledialog2({
			    				mode: 'button',
			    				headerText: '���ڽ���',
			    				buttonPrompt: 'ϵͳ��æ��������һ�������ԡ�',
			    				buttons : {
			    				 'ȷ��': {
			    					click: function () { 
			    					}
			    				 }
			    				}
			    			});
							return false;
						}
    					var snm = data[0]['snm'];
    					var date = data[0]['yydate'];
    					var yydate_xq = data[0]['yydate_xq'];
    					html += "<div data-role='collapsible'><h3>"+date+"("+yydate_xq+")"+"</h3><fieldset data-role='controlgroup'>";
    					for(var i = 0 ; i < data.length ; i++){
    						var tmp = data[i]['yydate'];
    						var cczb_id = data[i]['cczb_id'];
    						yydate_xq = data[i]['yydate_xq'];
    						if(date != tmp){
    							html += "</fieldset></div>";
    							html += "<div data-role='collapsible'><h3>"+tmp+"("+yydate_xq+")"+"</h3><fieldset data-role='controlgroup'>";
    							date = tmp;
    						}
    						var ccsjd = data[i]['ccsjd'];
    						var yy_yysl = data[i]['yy_yysl'];
    						var yy_zs = data[i]['yy_zs'];
    						html += '<input type="radio" name="radio-choice" id="'+cczb_id+'" value="'+cczb_id+'" '+(yy_yysl === yy_zs ? 'disabled':'')+'/><label for="'+cczb_id+'">'+ccsjd+'<span style="color:'+(yy_yysl === yy_zs ? '':'green')+'">('+(yy_yysl === yy_zs ? '��Լ��':'��ԤԼ')+')</span>'+'</label>' ;
    					}
    					html += "</fieldset></div>";
    					html += "<div style='font-size:12px'><p style='color:red'><b>��ܰ��ʾ:</b></p><p>һ������ԤԼ��û��������ģ���3�Σ�ϵͳ�Զ����û������ܽ���ԤԼ��</p><p>�� ������ԤԼʱ�䵽Υ����������ҵ���Ⱥ�ڣ��˴�ԤԼ��Ϊ��Ч���������Ŷӡ�</p></div>"
    					html += "<div class='ui-grid-a'><div class='ui-block-a'><button id='flast'>��һ��</button></div><div class='ui-block-b'><button id='fnext'>��һ��</button></div></div>"
						$('#choose').append(html).trigger('create');
    					$.mobile.hideLoading();
    					$('#flast').click(showPageThree);
    					$('#fnext').click(function(){
    						var cczb_id =  $('input[name=radio-choice]:checked').val(); //����ID
    						var hphm = $('#prefix-select').val()+$('#aprefix-select').val()+$('#carNum').val();
    						var hpzl = $('#carType').val();
    						var jszh = $('#driveLicenseNo').val();
    						var sjhm = $('#telephone').val();
    						var xdata = {};
    						xdata['snm'] = snm;
    						xdata['cldbmid'] = cldbmid;
    						xdata['cczb_id'] = cczb_id;
    						xdata['hphm'] = hphm;
							xdata['hpzl'] = hpzl;
							xdata['jszh'] = jszh;
							xdata['sjhm'] = sjhm;
							$.mobile.showLoading();
							$.getJSON('/weixin/trafficpolice/trafficIllegalOrderLogic',xdata,function(rest){
								showPageFive();
								$("#fresult").empty();
								var ti = $('input[type=radio]:checked').val();
								var edate = '';
								for(var i =0 ;i<data.length;i++) 
								{
									if(data[i]['cczb_id'] === ti) {
										console.log(data[i]['yydate']);
										edate = data[i]['yydate'] + ' ' + data[i]['ccsjd'] + '(' + data[i]['yydate_xq'] + ')';
									
										break;
									}
								}
								if($.isNumeric(rest.message)){
									var result = "<div style='font-size:14px'><p>ԤԼ��ţ�" + rest.message + "</p>";
									result += "<p>���ƺ��룺"+ hphm +"</p>";
									result += "<p>�ֻ���룺"+ sjhm +"<p>";
									result += "<p>����㣺"+ xplace +"</p>";
									result += "<p>����ʱ�䣺" + edate + "</p></div>";
									result += "<hr /><div style='font-size:12px;'>";
									result += "<p><b style='color:red'>��ܰ��ʾ:</b></p>";
									result += "<p>ԤԼ��Ϊ��"+rest.message+"</p>";
									result += "<p>����"+edate+"����ѯ̨��ȡ����š�</p>";
									result += "<p>���ڲ�����ԤԼ����</p>";
									result +="</div>";
									$('#fresult').html(result);
									var vvData = {};
									vvData['weixinToken'] = hphm;
									vvData['orderNum'] = rest.message;
									vvData['orderContact'] = sjhm;
									vvData['orderStatus'] = 1;
									vvData['orderDetail'] = $(result).text();
									$.postJSON('/weixin/trafficpolice/saveIllegalDeal',vvData,function(data){
										if(data.status){
											console.log('����ԤԼ��¼�ɹ�');
										}else{
											console.log('����ԤԼ��¼ʧ��');
										}
									});
								}else{
									$('#fresult').text(rest.message);
								}
								$('#fresult').show();
								$.mobile.hideLoading();
							},function(){
								$('body').simpledialog2({
				    				mode: 'button',
				    				headerText: '���ڽ���',
				    				buttonPrompt: 'ϵͳ��æ�������ԡ�',
				    				buttons : {
				    				 'ȷ��': {
				    					click: function () { 
				    						
				    					}
				    				 }
				    				}
				    			});
								$.mobile.hideLoading();
							});
    					});
    				},function(){
    					html += "<div class='ui-grid-a'><div class='ui-block-a'><button id='flast'>��һ��</button></div></div>"
    					$('#choose').append(html).trigger('create');
    					$('#flast').click(showPageThree);
    					$.mobile.hideLoading();
    				});
				});
			},function(){
				$.mobile.hideLoading();
				alert('ϵͳ��æ�������ԡ�')
    		});
    	});
    	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    		WeixinJSBridge.call('hideToolbar');
    	});
    }
    TrafficPolice.trafficIllegalOrderNew.prototype = TrafficPolice.IllegalReportPage.prototype;
    $.extend(TrafficPolice.trafficIllegalOrderNew.prototype,{
    	initFormValidate:function(){
    		this.stepOneForm = $('#carForm');
    		this.myTrafficform = $('#myTrafficform');
    		this.queryTrafficForm = $('#queryTrafficForm');
    		
    		this.renderCartLicenseSelect(this.stepOneForm);
    		this.renderCartLicenseSelect(this.myTrafficform);
    		
    		var captcha = {
        			rules: {
        				telephone:{
        					number:true,
        					required:true,
        					rangelength:[11,11]
        				},
        				driveLicenseNo:{
        					required:true,
        					rangelength:[18,18]
        				},
        				stelephone:{
        					number:true,
        					required:true,
        					rangelength:[11,11]
        				},
        				scarNum:{
        					required:true,
        					rangelength:[5,5]
        				},
        				carNum:{
        					required:true,
        					rangelength:[5,5]
        				},
    					captcha:{
    						required:true,
    						rangelength:[4,4],
							remote: {
							       url: "/weixin/captcha/validato",
							       type: "post"
							}
    					}
    				},
    				messages:{
    					captcha:{
    						required:'������֤��',
    						rangelength:'��֤�����',
    						remote:'��֤�����'
    					}
    				}
        	};
    		this.stepOneForm.validate(captcha);
    		this.myTrafficform.validate(captcha);
    		this.queryTrafficForm.validate();
	    }
    });
    
    //����Υ��ԤԼ
    TrafficPolice.trafficIllegalOrder = function(){
    	$('#x3submit').click(function(){
    		var yylsh = $('#yylsh').val();
    		if(!$.isNumeric($('#yylsh').val())){
    			$('#tip').text('ԤԼ�ű���Ϊ����');
    			$('#tip').show();
    			return false;
    		}
    		$.mobile.loading('show', {  
    	        text: '������...',
    	        textVisible: true,
    	        theme: 'a',  
    	        textonly: false,
    	        html: ""
    	    });
    		$.getJSON('/weixin/trafficpolice/trafficIllegalOrderCancelLogic',{'yylsh':yylsh},function(rest){
    			if(rest.status){
    				$('#zresult').text("ԤԼ��ˮ�ţ�"+$('#yylsh').val()+"ȡ��ɹ�");
    			}else{
    				$('#zresult').text("ԤԼ��ˮ�ţ�"+$('#yylsh').val()+"ȡ��ʧ��");
    			}
    			$.mobile.loading('hide');
    			$('#fjj').hide();
    			$('#zresult').show();
    		})
		});
    	$('#xnext').click(function(e){
			if($("input[type='checkbox']").prop("checked") === true){
    			$.mobile.changePage('#contact');
    			var xx = {}; /*�������*/
    	    	var xdate = []; /*����*/
    	    	var snm = '';
    	    	var form = $('form');
    	    	var logonForm = form;
    	    	if(form.length > 0){
    	    		form.submit(false);
    	    		form.validate({
    	    			rules: {
    	    				driveLicenseNo:{
    	    					required:true
    	    				},
    	    				telephone:{
    	    					required:true
    	    				},
    						captcha:{
    							required:true,
    							rangelength:[4,4],
    							 remote: {
    							        url: "/weixin/captcha/validato",
    							        type: "post"
    							 }
    						}
    					},
    					messages:{
    						captcha:{
    							required:'������֤��',
    							rangelength:'��֤�����',
    							remote:'��֤�����'
    						}
    					},
    					invalidHandler:function(e,form){
    						if(form.errorList.length == 1){
    							var element = form.errorList[0].element;
    							if($(element).attr('name') == 'captcha'){
    								$('img.captcha-img').trigger('click',true);
    							}
    						}
    					},
    					submitHandler:function(form){
    						if($.mobile.loaderWidget.isShow()) return false;
    		    			$.mobile.loading('show', {  
    		        	        text: '������...',
    		        	        textVisible: true,
    		        	        theme: 'a',  
    		        	        textonly: false,
    		        	        html: ""
    		        	    });
    						$.getJSON('/weixin/trafficpolice/trafficIllegalOrderLogic',$(form).serializeArray(),function(rest){
    							var xd = $(form).serializeArray()
    							console.log(xd);
    							console.log(rest);
    							if(rest.status){
    								$('#x1').hide();
    								$('#x2').show();
    								if($.isNumeric(rest.message)){
    									rest.message = "������"+$('#mydate').val()+" "+ $('#orderTime :selected').text()+"��"+$('#place :selected').text()+"�ɹ�ԤԼ��ԤԼ��ˮ��Ϊ��"+rest.message;
    								}
    								$('#vresult').text(rest.message);
    								$.mobile.loading('hide');
    							}else{
    								$.mobile.loading('hide');
    								$('img.captcha-img').trigger('click',true);
    								alert(rest.message); //��¼ʧ��
    							}
    						},function(){
    							$.mobile.loading('hide');
    							$('img.captcha-img').trigger('click',true);
    						});
    					},
    	    			showErrors:function(){
    	    			this.errorList = this.errorList.slice(0,1);
    	    			this.defaultShowErrors();
    	    		}});
    	    		
    	    		$('.btnSubmit').click(function(){
    	    			logonForm.submit();
    	    		});
    	    	}
    			$('img.captcha-img').click(function(e,onclick){
    				if(!onclick && $.mobile.loaderWidget.isShow()) return false;
    				$.mobile.showLoading();
    				var token = new Date().getTime();
    				var that = $(this);
    				var src = that.data('src');
    				if(!src){
    					src = that.attr('src');
    					that.data('src',src);
    				}
    				src += '?'+token;
    				that.attr('src',src);
    			}).load(function(){
    				$.mobile.loading('hide');
    			});
    			$('#mydate').on('change',function(e,ui){
    				$("#orderTime").empty();
    				var yydate = $(e.target).val();
    				for(var i = 0;i<xx.length;i++){
    					if(xx[i]['yydate'] == yydate){
    						$('#orderTime').append('<option value="'+xx[i]['cczb_id']+'|'+xx[i]['snm']+'">'+xx[i]['ccsjd']+'</option>');
    					}
    				}
    				$("#orderTime").selectmenu('refresh', true);
    			});
    			$('#place').on('change',function(e,ui){
    				$('#mydate').empty();
    				var cldbmid = $(e.target).val();
    				$.mobile.loading('show', {  
    	    	        text: '������...',
    	    	        textVisible: true,
    	    	        theme: 'a',  
    	    	        textonly: false,
    	    	        html: ""
    	    	    });
    				$.getJSON("/weixin/trafficpolice/trafficIllegalOrderDateQuery",{'cldbmid':cldbmid},function(data){
    					xx = data;
    					xdate = [];
    					snm = data[0]['snm'];
    					for(var i = 0 ; i < data.length ; i++){
    						var tmp = data[i]['yydate'];
    						if($.inArray(tmp,xdate) === -1){
    							xdate.push(tmp);
    						}
    					}
    					for(var i = 0 ;i<xdate.length;i++){
    						$('#mydate').append('<option value="'+xdate[i]+'">'+xdate[i]+'</option>');
    					}
    					$("#mydate").selectmenu('refresh', true);
    					$.mobile.loading('hide');
    					$("#mydate").trigger('change');
    					
    				});
    			});
    			function getData(){
	    			$.mobile.loading('show', {  
		    	        text: '������...',
		    	        textVisible: true,
		    	        theme: 'a',  
		    	        textonly: false,
		    	        html: ""
		    	    });
	    			$.getJSON("/weixin/trafficpolice/trafficIllegalOrderPlaceQuery",'',function(data){
	    				$.mobile.loading('hide');
	    				if(data.length === 0){
	    					alert('�ӿڻ�ȡ��ݴ���!');
	    					return ;
	    				}
	    				$('#place').empty();
	    				for(var i = 0 ;i<data.length;i++){
	    					$('#place').append('<option value="'+data[i]['cldbmid']+'">'+data[i]['cldaddress']+'</option>');
	    				}
	    				$.mobile.loading('hide');
	    				$("#place").selectmenu('refresh', true);
	    				$("#place").trigger('change');
	    			},function(){
	    				$.mobile.loading('hide');
	    			});
    			}
    			getData();
    		}
		});
    };
    /*��ʻ��Υ����ѯ*/
    TrafficPolice.TrafficPeccancyInquiryTwo = function(){
    	var form = $('form');
    	var logonForm = form;
    	if(form.length > 0){
    		form.submit(false);
    		form.validate({
    			rules: {
    				driveLicenceNo:{
    					required:true,
    					rangelength:[18,18]
    				},
    				driverRecordNo:{
    					rangelength:[12,12]
    				},
    				captcha:{
						required:true,
						rangelength:[4,4],
						 remote: {
						        url: "/weixin/captcha/validato",
						        type: "post"
						 }
					}
				},
				messages:{
					captcha:{
						required:'������֤��',
						rangelength:'��֤�����',
						remote:'��֤�����'
					}
				},
				invalidHandler:function(e,form){
					if(form.errorList.length == 1){
						var element = form.errorList[0].element;
						if($(element).attr('name') == 'captcha'){
							$('img.captcha-img').trigger('click',true);
						}
					}
				},
				submitHandler:function(form){
					if($.mobile.loaderWidget.isShow()) return false;
	    			$.mobile.loading('show', {  
	        	        text: '������...',
	        	        textVisible: true,
	        	        theme: 'a',  
	        	        textonly: false,
	        	        html: ""
	        	    });
	    			console.log($(form).serializeArray());
					$.getJSON('/weixin/trafficpolice/TrafficPeccancyInquiryTwoLogic',$(form).serializeArray(),function(rest){
						console.dir(rest);
						$('#captcha').val('');
						if(rest.status){
							$('#cc').text('��ʻԱΥ����Ϣ��ѯ���');
							$('form').hide();
							$.mobile.loading('hide');
							$('#cresult')[0].innerHTML = rest.message;
							$('#cresult').show();
							$('#dd').show();
							$('#dd').click(function(){
								$('#cc').text('��ʻԱΥ����Ϣ��ѯ');
								$('#cresult').hide();
								$('form').show();
								$('#dd').hide();
							});							
						}else{
							$('img.captcha-img').trigger('click',true);
							alert(rest.message); //��¼ʧ��
						}
						$.mobile.loading('hide');
					},function(){
						$.mobile.loading('hide');
						$('#captcha').val('');
						$('img.captcha-img').trigger('click',true);
					});
				},
    			showErrors:function(){
    			this.errorList = this.errorList.slice(0,1);
    			this.defaultShowErrors();
    		}});
    		
    		$('.xsubmit').click(function(){
    			logonForm.submit();
    		});
    	}
		$('img.captcha-img').click(function(e,onclick){
			if(!onclick && $.mobile.loaderWidget.isShow()) return false;
			$.mobile.showLoading();
			
			var token = new Date().getTime();
			var that = $(this);
			var src = that.data('src');
			if(!src){
				src = that.attr('src');
				that.data('src',src);
			}
			src += '?'+token;
			that.attr('src',src);
		}).load(function(){
			$.mobile.loading('hide');
		});
    };
    //�û�����Ϣ���
    TrafficPolice.trafficUserInfoChange = function(){
    	var self = this;
    	//��ȡ��һ���?
    	var getForm1 = function(){
    		return $('#myTrafficform1');
    	};
    	var getForm2 = function(){
    		return $('#myTrafficform2');
    	};
    	var getForm3 = function(){
    		return $('#myTrafficform3');
    	}
    	//�ȳ�ʼ��һЩ��ͨ��Ϊ,�?��֤  ����һ���?��
    	var initRules = function(form,submitFunc){
    		if(form.length > 0){
    			form.submit(false);
    			form.validate({
    				rules: {
    					bgjtzz:{
    						required:true
    					},
    					bgsjhm: {
    						required:true,
    						rangelength:[11,11]
    					},
    					scarNum:{
        					required:true,
        					rangelength:[5,5]
        				},
        				cjhs:{
        					required:true,
        					rangelength:[4,4]
        				},
        				captcha:{
    						required:true,
    						remote: {
						        url: "/weixin/captcha/validato",
						        type: "post"
    						}
    					}
    				},
    				messages:{
    					captcha:{
    						required:'������֤��',
    						rangelength:'��֤�����',
    						remote:'��֤�����'
    					}
    				},
    				invalidHandler:function(e,form){
    					if(form.errorList.length == 1){
    						var element = form.errorList[0].element;
    						if($(element).attr('name') == 'captcha'){
    							$(element).parent('div').next('img').trigger('click',true);
    						}
    					}
    				},
    				submitHandler:function(form){
    					if($.mobile.loaderWidget.isShow()) return false;
    					showLoading();
    					submitFunc();
    				},
    				showErrors:function(){
    	    			this.errorList = this.errorList.slice(0,1);
    	    			this.defaultShowErrors();
    	    		}
    			});
    		}
    	}
    	//Form1 �� �ύ����
    	var submitFunc1 = function(){
    		var formData = getForm1().serializeJson();
    		var postData = {};
    		postData['hphm'] = formData['prefix'] + formData['aprefix'] + formData['scarNum'];
    		postData['hpzl'] = formData['scarType'];
    		postData['zhsw'] = formData['cjhs'];
    		$.postJSON('/weixin/trafficpolice/traffic/getVerify',postData,function(e){
    			if(e.status == true){
    				pageTwoShow();
    			}else{
    				showWarn('��֤ʧ��');
    			}
    			hideLoading();
    		},function(){
    			showWarn('�ӿڵ���ʧ��');
    			hideLoading();
    		});
    	}
    	var submitFunc2 = function(){
    		var formData = getForm1().serializeJson();
    		var postData = {};
    		postData['hphm'] = formData['prefix'] + formData['aprefix'] + formData['scarNum'];
    		postData['hpzl'] = formData['scarType'];
    		postData['zhsw'] = formData['cjhs'];
    		formData  = getForm2().serializeArray();
    		$.each(formData,function(d,f){
    			postData[f['name']] = f['value'];
    		});
    		$.postJSON('/weixin/trafficpolice/traffic/getInfoChange',postData,function(e){
    			if(e.status == true){
    				pageThreeShow();
    			}else{
    				showWarn('��֤ʧ��');
    			}
    			hideLoading();
    		},function(){
    			showWarn('�ӿڵ���ʧ��');
    			hideLoading();
    		});
    		
    	}
    	var submitFunc3 = function(){
    		var formData = getForm3().serializeJson();
    		var postData = {};
    		postData['hphm'] = formData['prefix'] + formData['aprefix'] + formData['scarNum'];
    		postData['hpzl'] = formData['scarType'];
    		postData['zhsw'] = formData['cjhs'];
    		postData['sjhm'] = formData['bgsjhm'];
    		$.postJSON('/weixin/trafficpolice/traffic/getInfoChangeResult',postData,function(data){
    			if(data['datalist'] !== undefined){
    				var html = '';
    				$('#sDiv').empty();
    				var data = data['datalist'];
    				$.each(data,function(index,comment){
						html += '<li>'
								+'<p>���ƺ��룺<span>' + data[index]['hphm'] + '</span></p>'
								+'<p>����ֻ���룺' + data[index]['bgsjhm'] + '</p>'
								+'<p>����ͥ��ַ��' + data[index]['bgjtzz'] + '</p>'
								+'<p>�������䣺' + data[index]['dzyx'] + '</p>'
								+'<p>΢�źţ�' + data[index]['wx']  + '</p>'
								+'<p>QQ�ţ�' + data[index]['qq'] + '</p>'
								+'<p>����״̬��' + data[index]['clzt'] + '</p></li>';
					});
	    			$('#sDiv').append(html).listview('refresh').trigger('create');
	    			$.mobile.silentScroll(300);
    			}else{
    				showWarn(data['msg']);
    			}
    			hideLoading();
    		},function(){
    			showWarn('�ӿڵ���ʧ��');
    			hideLoading();
    		})
    	}
    	var pageTwoShow = function(){
    		$('.page').hide();
    		$('#pageTwo').show();
    	}
    	var pageThreeShow = function(){
    		$('.page').hide();
    		$('#pageThree').show();
    	}
    	var pageOneShow = function(){
    		$('.page').hide();
			$('#pageOne').show();
			$('#myTrafficform1 img.captcha-img').trigger('click');
			$('input[name=captcha]').val('')
    	}
    	var pageFourShow = function(){
    		$('.page').hide();
    		$('#pageFour').show();
    		$('#myTrafficform3 img.captcha-img').trigger('click');
			$('input[name=captcha]').val('')
			initFourData();
    	}
    	var initFourData = function(){
    		var form2Data = getForm2().serializeJson();
    		var data  = getForm1().serializeJson();
    		var giveData = {};
    		var tmpData = {};
    		$.each(data,function(d,f){
    			if(d == 'prefix' || d=='aprefix' || d == 'scarType'){
    				tmpData[d] = f;
    			}else{
    				giveData[d] = f;
    			}
    		});
    		giveData['bgsjhm'] = form2Data['bgsjhm'];
    		getForm3().serializeJson(giveData);
    		$('#myTrafficform3 #prefix-select').val(tmpData['prefix']).selectmenu('refresh',true);
    		$('#myTrafficform3 #aprefix-select').val(tmpData['aprefix']).selectmenu('refresh',true);
    		$('#myTrafficform3 #scarType').val(tmpData['scarType']).selectmenu('refresh',true);
    	}
    	//��ʼ��������¼�
    	var initEvent = function(){
    		$('#nbOne').click(function(){
    			pageOneShow();
    		});
    		$('#nbTwo').click(function(){
    			pageFourShow();
    		});
    		$('#loginIn').click(function(){
    			getForm1().submit();
    		});
    		$('#submitInfo').click(function(){
    			getForm2().submit();
    		});
    		$('#backToFir').click(function(){
    			pageOneShow();
    		});
    		$('#SearchResult').click(function(){
    			getForm3().submit();
    		});
    		$('img.captcha-img').click(function(e,onclick){
    			if(!onclick && $.mobile.loaderWidget.isShow()) return false;
    			$.mobile.showLoading();
    			
    			var token = new Date().getTime();
    			var that = $(this);
    			var src = that.data('src');
    			if(!src){
    				src = that.attr('src');
    				that.data('src',src);
    			}
    			src += '?'+token;
    			that.attr('src',src);
    		}).load(function(){
    			$.mobile.loading('hide');
    		});
    	};
    	var initView = function(){
    		self.renderCartLicenseSelect(getForm1());
    		self.renderCartLicenseSelect(getForm3());
			$('#nbOne').trigger('click');
			
    	};
    	initRules(getForm1(),submitFunc1);
    	initRules(getForm2(),submitFunc2);
    	initRules(getForm3(),submitFunc3);
    	initEvent();
    	initView();
    };
    TrafficPolice.trafficUserInfoChange.prototype = TrafficPolice.IllegalReportPage.prototype;
    var showWarn = function(text){
    	$('body').simpledialog2({
			mode: 'button',
			headerText: '���ڽ���',
			buttonPrompt: text,
			buttons : {
			 'ȷ��': {
				click: function () { 
					this.destroy();
				}
			 }
			}
		});
    }
    var showLoading = function(){
    	$.mobile.loading('show', { 
    		text: '������...',
            textVisible: true,
            theme: 'a',  
            textonly: false,
            html: ""
        });
    };
    var hideLoading = function(){
    	$.mobile.loading('hide');
    };
    /*******΢��ͨ��ƽ̨����**************/
    $(function(){
    	$('div[data-role=page]').each(function(){
    		var that = $(this);
    		var id = that.attr('data-page');
    		if(id && TrafficPolice[id] && $.isFunction(TrafficPolice[id])){
    			var func = TrafficPolice[id];
    			new func({el:this});
    		}
    	});
    });
    
});