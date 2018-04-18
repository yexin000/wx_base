



function tplJobListItem(jobType,jobName,jobNum) {
	return '<li id="job1" class="job"><a href="#" data-ywdm="'+jobNum+'" data-ywlx="'+jobType+'">'+jobName+'</a></li>';
}


function tplJobAddrListItem(dwmc,lxdz,bldid) {
	return '<li id="addr1" class="addr"><a href="#" data-bldid="'+bldid+'">'+dwmc+'<p style = "text-overflow: ellipsis;">'+lxdz+'</p></a></li>';
}


getJobGuideTypeNameList = function() {
	return WebServiceUtil.getJobGuideTypeName(null,callback);
};

getJobGuideAddrList = function(ywlx,ywdm){
	
	
	var params = "{\"ostype\":\"A\""+",\"ywlx\":\""+ywlx+"\""+",\"ywdm\":\""+ywdm+"\""
	+",\"lat\":\""+"30.278121247584"+"\""+",\"lng\":\""+"120.16809977789"+"\""+",\"distance\":\""+""+"\""
	+",\"flag\":\"1\""+"}";
	return WebServiceUtil.getJobGuideAddrName(params,callBackAddr);
};

function callback(msg){
	var jsonObject = eval("("+msg+")"),li_array = new Array();
	if(jsonObject.head.code!=1){
		return;
	}
	var ja = jsonObject.body;	
	
    for (var i in ja) {
          var jo =  ja[i];
       if(jo.ywlx == jobGuide.jobType){
         var jobName =  jo.ywmc;
         var jobNames= new Array(); 
         jobNames = jobName.split(",");
         
         for(var j in jobNames){
        	 if(jobNames[j]!=null&&jobNames[j].indexOf("=")>0){
        		 var ywmcArray = new Array();
        		 ywmcArray = jobNames[j].split("=");
        		 li_array.push(tplJobListItem(jobGuide.jobType,ywmcArray[1],ywmcArray[0]));
        	 };
        	 
         };
         
         break;
       };
    };
    
    var $listView = $("#jobList");
    $listView.html(li_array.join(''));
    $listView.listview('refresh');
    $listView.show();
    $listView.delegate('li a', 'click', function(e) {
    	jobGuide.jobName = $(this).html();
    	jobGuide.jobType = $(this).data("ywlx");
        jobGuide.jobNum = $(this).data("ywdm");
		getJobGuideAddrList($(this).data("ywlx"),$(this).data("ywdm"));
		
	});
  
	
};

function callBackAddr(msg){
	var jsonObject = eval("("+msg+")"),li_array = new Array();
	if(jsonObject.head.code!=1){
		return;
	}
	var ja = jsonObject.body;
	
	for(var i in ja){
		 li_array.push(tplJobAddrListItem(ja[i].dwmc,ja[i].lxdz,ja[i].id));
		
	};
	
	var $listView = $("#addrList");
    $listView.html(li_array.join(''));
    $listView.listview('refresh');
    $listView.show();
    $listView.delegate('li a', 'click', function(e) {
    	
    	window.location.href="./CarJobGuidDisplay.html?ywlx=" + escape(jobGuide.jobType) + "&ywdm=" + escape(jobGuide.jobNum)+ "&ywmc=" + escape(jobGuide.jobName) + "&bldid=" + escape($(this).data("bldid"));
	});
	
	 $("#mask").addClass("mask").fadeIn("slow");
	$("#LoginBox").fadeIn("slow");
	
};






