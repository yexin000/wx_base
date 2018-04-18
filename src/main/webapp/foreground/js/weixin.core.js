// 全局命名空间
var ningboWeixin = {
	author : 'xw',
	version : '1.0',
	website : ''
};
// 工具包
ningboWeixin.utils = {
	setParam : function(name, value) {
		localStorage.setItem(name, value);
	},
	getParam : function(name) {
		return localStorage.getItem(name);
	}
};

// 业务控制中心，需实现业务
ningboWeixin.controllers = {};

// 事件注册
ningboWeixin.run = function(pages) {
	var pages = pages, count = pages.length;
	for ( var i = 0; i < count; i++) {
		var page = pages[i], id = page.id;
		e_array = page.event.split(',');
		for ( var j = 0; j < e_array.length; j++) {
			var e = e_array[j];
			if ($.trim(e).length == 0)
				continue;
			$('#' + id).live(e, ningboWeixin.controllers[id][e]);
		}
	}
};