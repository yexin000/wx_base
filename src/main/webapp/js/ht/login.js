$package('WeiXin.login');
WeiXin.login = function() {
	return {
		toLogin : function() {
			try {
				var form = $("#loginForm");
				WeiXin.progress('Please waiting', 'Loading...');
				WeiXin.submitForm(form, function(data) {
					WeiXin.closeProgress();
					if (data.success) {
						window.location = "main.shtml";
					} else {
						WeiXin.alert('提示', data.msg, 'error');
					}
				});
			} catch (e) {

			}
			return false;
		},
		init : function() {
			if (window.top != window.self) {
				window.top.location = window.self.location;
			}
			var form = $("#loginForm");
			form.submit(WeiXin.login.toLogin);
		}
	}
}();

$(function() {
	WeiXin.login.init();
});