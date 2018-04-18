$package('WeiXin.wxMenu');

WeiXin.wxMenu = function(){
	var _box = null;
	var _this = {
		toList:function(parentId){
				_box.form.search.resetForm();
				if(parentId){
					$('#search_parentId').val(parentId);
					$('#btnback').linkbutton('enable');
					_box.grid.datagrid('hideColumn','childMenus');
				}else{
					$('#btnback').linkbutton('disable');
					$('#btnback').linkbutton('disable');
					_box.grid.datagrid('showColumn','childMenus');
				}
				_box.handler.refresh();
		},
		syncClient:function(){
			$.ajax('sync.do',{
				type:'post',
				 	dataType:'json',
				 	data:null,
				 	success:function(data){
						WeiXin.alert('提示','同步成功.','warning');  
				 	}
			});
		},
		//设置默认按钮数据
		addDefBtns:function(){
			var defaultBtns= [
				{"btnName":"添加","menuid":2,"actionUrls":"save.do","btnType":"add"},
				{"btnName":"修改","menuid":2,"actionUrls":"getId.do|save.do","btnType":"edit"},
				{"btnName":"删除","menuid":2,"actionUrls":"delete.do","btnType":"remove"}
			];
			var tbline = $(".tb-line:visible");
			var btnType = $("input[name='btnType']",tbline);
			$.each(defaultBtns,function(i,btn){
				var isExists = false;
				//判断按钮类型是否存在
				if(btnType.length > 0){
					for(var i =0; i < btnType.length; i++){
						if(btnType.eq(i).val() == btn.btnType){
							isExists = true;
							break;
						}
					}
				}
				if(!isExists){
					_this.addLine(btn);
				}
			});
		},
		addLine: function(data){
			var table = $("#btn-tb");
			var	html = "<tr class='tb-line'>";
			html+=	   "	<td align='center'><span  class='newFlag red'>*</span></td>";
			html+=	   "	<td><input name=\"btnName\" class=\"easyui-validatebox text-name\" style=\"width:100%\" data-options=\"required:true\"></td>";
			html+=	   "	<td><input name=\"btnType\" class=\"easyui-validatebox text-name\" style=\"width:100%\" data-options=\"required:true\"></td>";
			html+=	   "	<td><input name=\"actionUrls\" class=\"easyui-validatebox text-desc\" style=\"width:100%\"  ></td>";
			html+=	   "	<td align='center'><a class=\"easyui-linkbutton remove-btn\"  iconCls=\"icon-remove\" plain=\"true\"></a>";
			html+=	   "	<input class=\"hidden\" name=\"btnId\">";
			html+=	   "	<input class=\"hidden\" name=\"deleteFlag\">";
			html+=	   "	</td>";
			html+=	   "</tr>";
			var line = $(html);
			//版定删除按钮事件
			$(".remove-btn",line).click(function(){
				WeiXin.confirm('提示','你确定想删除?',function(r){
					if(r){
						_this.delLine(line);
					}
				})
			});
			if(data){
				if(data.id){
					$(".newFlag",line).html(''); //清空新增标记
				}
				$("input[name='btnId']",line).val(data.id);
				$("input[name='btnName']",line).val(data.btnName);
				$("input[name='btnType']",line).val(data.btnType);
				$("input[name='actionUrls']",line).val(data.actionUrls);
			}
			$.parser.parse(line);//解析esayui标签
			table.append(line);
			
		},
		//删除全部
		delAllLine:function(b){
			if(b){
				$(".tb-line").remove();
			}else{
				$(".tb-line").each(function(i,line){
					_this.delLine($(line));
				});
			}
		},
		//删除单行
		delLine:function(line){
			if(line){
				var btnId = $("input[name='btnId']",line).val();
				if(btnId){
					$("input[name='deleteFlag']",line).val(1); //设置删除状态
					line.fadeOut();
				}else{
					line.fadeOut("fast",function(){
						 $(this).remove();
					});
				}
			}
		},
		config:{
  			action:{
  				save:'', //新增&修改 保存Action  
  				getId:'',//编辑获取的Action
  				delele:''//删除数据的Action
  			},
  			event:{
  				add : function(){
  					_this.delAllLine(true);//清空已有的数据
  					_box.handler.add();//调用add方法
					var parentId =$('#search_parentId').val();
					if(parentId){
						$("#edit_parentId").val(parentId);
					}
				},
				edit:function(){
					_this.delAllLine(true);
					_box.handler.edit(function(result){
						$.each(result.data.btns,function(i,btn){
							_this.addLine(btn);
						});
					});
				}
  			},
  			dataGrid:{
  				title:'参数列表',
	   			url:'dataList.do',
	   			columns:[[
					{field:'id',checkbox:true},
					{field:'name',title:'菜单名称',width:220,sortable:true},
					{field:'type',title:'菜单类型',width:220,sortable:true},
					{field:'rank',title:'顺序',width:220,sortable:true},
					{field:'url',title:'链接',width:220,sortable:true},
					{field:'childMenus',title:'子菜单',width:220,align:'center',formatter:function(value,row,index){
						var html ="<a href='#' onclick='WeiXin.wxMenu.toList("+row.id+")'>子菜单管理("+row.subCount+")</a>";
						return html;
					}}
				]],
				toolbar:[
					{id:'btnadd',text:'添加',btnType:'add'},
					{id:'btnedit',text:'编辑',btnType:'edit'},
					{id:'btndelete',text:'删除',btnType:'remove'},
					{
						id:'btnback',
						text:'返回',
						disabled: true,
						iconCls:'icon-back',
						handler:function(){
							_this.toList();
						}
					},
					{
						id:'btSyncClient',
						text:'同步到微信客户端',
						iconCls:'icon-edit',
						handler:function(){
							WeiXin.confirm('提示','您确定要同步到微信客户端?',function(r){
								if(r){
									_this.syncClient();
								}
							});
						}
					}
				]
			}
		},
		init:function(){
			_box = new YDataGrid(_this.config); 
			_box.init();
			$('#addLine_btn').click(_this.addLine);
			$('#addDefLine_btn').click(_this.addDefBtns);
			$('#delAllLine_btn').click(function(){
				WeiXin.confirm('提示','你确定要删除?',function(r){
					_this.delAllLine(false);
				});
			});
			$('#type').combobox({
				onChange:function(n,o){
					if("click" == n){
						$("#msgType").show();
						$("#url").hide();
					}else if("view" == n){
						$("#msgType").hide();
						$("#url").show();
					}else if("first" == n){
						$("#msgType").hide();
						$("#url").hide();
					}
				}
			});
			
		},
	}
	return _this;
}();

$(function(){
	WeiXin.wxMenu.init();
});		