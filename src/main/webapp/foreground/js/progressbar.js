
			  var ProgressBar = {};
			  var spinner;
			  
			  ProgressBar.opts = {
					  lines: 11, // The number of lines to draw
					  length: 9, // The length of each line
					  width: 4, // The line thickness
					  radius: 12, // The radius of the inner circle
					  corners: 1, // Corner roundness (0..1)
					  rotate: 0, // The rotation offset
					  color: '#fff', // #rgb or #rrggbb
					  speed: 1, // Rounds per second
					  trail: 60, // Afterglow percentage
					  shadow: true, // Whether to render a shadow
					  hwaccel: false, // Whether to use hardware acceleration
					  className: 'spinner', // The CSS class to assign to the spinner
					  zIndex: 2e9, // The z-index (defaults to 2000000000)
					  top: 'auto', // Top position relative to parent in px
					  left: 'auto' // Left position relative to parent in px
			};
			  
			  ProgressBar.init = function(){
				  $("body").append("<div id='mask'></div>");
				  $("body").append("<div id='probar'></div>");
				   spinner = new Spinner(ProgressBar.opts);
			  };
					  
			  ProgressBar.show = function(){
				  $("#mask").addClass("mask").fadeIn("slow");
				  $("#probar").addClass("probar").fadeIn("slow");
				  spinner.spin($("#probar").get(0));
				  
			  };
			  
			  ProgressBar.hide = function(){
				  $("#mask").css({ display: 'none' });
				  $("#probar").css({ display: 'none' });
				  spinner.spin();
				  
			  };
					  
			           
						