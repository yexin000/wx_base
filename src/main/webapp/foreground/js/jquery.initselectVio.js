// JavaScript Document
(function($){
	
	var methods = {
		hpzl:function(params){
		return this.each(function(){
				var obj = $(this);	
				
				var defaults = {
					className:'inputSelect'
				};
				
				var settings = $.extend({},defaults,params);
				obj.attr('class',settings.className);
				
				var str_op = "";
				str_op += "<option value='01'>01:大型汽车</option>"
						+"<option value='02' selected='selected'>02:小型汽车</option>"
						+"<option value='03'>03:使馆汽车</option><option value='04'>04:领馆汽车</option>"
						+"<option value='05'>05:境外汽车</option><option value='06'>06:外籍汽车</option>"
						+"<option value='07'>07:普通摩托车</option><option value='08'>08:轻便摩托车</option>"
						+"<option value='09'>09:使馆摩托车</option><option value='10'>10:领馆摩托车</option>"
						+"<option value='11'>11:境外摩托车</option><option value='12'>12:外籍摩托车</option>"
						+"<option value='13'>13:农用运输车</option><option value='14'>14:拖拉机</option>"
						+"<option value='15'>15:挂车</option><option value='16'>教练汽车</option>"
						+"<option value='17'>17:教练摩托车</option><option value='18'>18:试验汽车</option>"
						+"<option value='19'>19:试验摩托车</option><option value='20'>20:临时入境汽车</option>"
						+"<option value='21'>21:临时入境摩托车</option><option value='22'>22:临时行驶车</option>"
						+"<option value='23'>23:警用汽车</option><option value='24'>24:警用摩托车</option>"
						+"<option value='25'>25:原农机号牌</option><option value='26'>26:香港入出境车</option>"
						+"<option value='27'>27:澳门入出境车</option><option value='31'>31:武警号牌</option>"
						+"<option value='32'>32:军队号牌</option><option value='41'>41:无号牌</option>"
						+"<option value='42'>42:假号牌</option><option value='43'>43:挪用号牌</option>"
						+"<option value='99'>99:其他号牌</option>";
				obj.html(str_op);
		});
		},
		
		gcjk : function(params){
		
			return this.each(function(){
			
				var g_obj = $(this);
				
				var defaults = {className:"inputSelect"};
				
				var settings = $.extend({},defaults,params);				
				g_obj.attr('class',settings.className);
			
				
				g_obj.html("<option value='1' selected='selected'>"
											+"	国产"
											+"</option>"
											+"<option value='2'>"
											+"  进口"
											+"</option>");
			});
		
		},
		
		sfjg : function(params){
			
			return this.each(function(){
				var sf_obj = $(this);
				
				var defaults = {className:"inputSelect"};
				
				var settings = $.extend({},defaults,params);
				sf_obj.attr('class',settings.className);
				
				sf_obj.html("<option value='0本地用户'>本地</option><option value='1外地用户'>外地</option>");
			});
		},
		
		sfzm : function(params){
		
			return this.each(function(){
				
				var zm_obj = $(this);
				
				var defaults = {className:"inputSelect"};
				
				var settings = $.extend({},defaults,params);
				zm_obj.attr('class',settings.className);
				
				zm_obj.html("<option value='A' selected='selected'>"
											+"	居民身份证"
											+"</option>"
											+"<option value='B'>"
											+"	组织机构代码"
											+"</option>"
											+"<option value='C'>"
											+"	军官证"
											+"</option>"
											+"<option value='D'>"
											+"	士兵证"
											+"</option>"
											+"<option value='E'>"
											+"	军官离退休证"
											+"</option>"
											+"<option value='F'>"
											+"	境外人员身份证明"
											+"</option>"
											+"<option value='G'>"
											+"	外交人员身份证明"
											+"</option>");
			});
		
		},
	
	};
	
	$.fn.hpzl = function (str) {
        return "<strong>" + str + "</strong>";
    }
	
	$.fn.initselect = function(){
		
		var method = arguments[0];
		if(methods[method]){
		
			method = methods[method];
			arguments = Array.prototype.slice.call(arguments,1);
		
		}else if(typeof(method) == 'object' || !method){
		
			method = methods.init;
		
		}else{
			
			alert('方法不存在');
		
		}
		
		return method.apply(this,arguments);
	
	}
		  
})(jQuery);