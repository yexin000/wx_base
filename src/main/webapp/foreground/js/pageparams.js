Pageparams = {};
Pageparams.postRequest = function(textcode){
	$.post("/weixin/wxCode/queryListByCode.do",{code:textcode},function(data){
		if(window.localStorage){   
			//支持   
			localStorage.setItem("d_title",data.rows[3].value);
			localStorage.setItem("d_floot",data.rows[1].value);
			localStorage.setItem("fpjg",data.rows[2].value);
		}else{
			//不支持
			Cookie.write("d_title", data.rows[3].value);
			Cookie.write("d_floot", data.rows[1].value);	
			Cookie.write("fpjg",data.rows[2].value);	
		}
		
		//alert(JSON.stringify(data));
	});
}