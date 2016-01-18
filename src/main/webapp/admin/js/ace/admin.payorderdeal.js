jQuery.adminPayorderdeal = {
		payorderdealDataTable:null,
		initSearchDataTable : function() {
			if (this.payorderdealDataTable == null) {
				this.payorderdealDataTable = $('#dt_table_view').dataTable({
					"sDom" : "<'row-fluid'<'span5'l><'span3'r><'span4'T>t<'row-fluid'<'span6'i><'span6'p>>",
					 "oTableTools": {
				        	 "aButtons": [
				             "copy",
				             "xls"
				             ],
				            "sSwfPath": "media/swf/copy_csv_xls_pdf.swf"
				        },
					"sPaginationType" : "bootstrap",
					"oLanguage" : {
						"sLengthMenu" : "每页显示 _MENU_ 条记录",
						"sZeroRecords" : "抱歉， 暂时没有记录",
						"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
						"sSearch" : "",
						"sInfoEmpty" : "没有数据",
						"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
						"oPaginate" : {
							"sFirst" : "首页",
							"sPrevious" : "前一页",
							"sNext" : "后一页",
							"sLast" : "尾页"
						}
					},
					"bAutoWidth" : false,
					"iDisplayLength" : 10,
					"aLengthMenu" : [ 5, 10, 25, 50],
					"bServerSide" : true,
					"sServerMethod" : "POST",
					"bProcessing" : true,
					"bSort" : false,
					"sAjaxSource" : $.ace.getContextPath() + "/admin/orderdeal/list",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var user = $("#_user").val();
						if (!!user) {
							aoData.push({
								"name" : "user",
								"value" : user
							});
						}
						var id = $("#_id").val();
						if (!!id) {
							aoData.push({
								"name" : "id",
								"value" : id
							});
						}
						var state = $("#_state").val();
						if (!!state) {
							aoData.push({
								"name" : "state",
								"value" : state
							});
						}
						$.ajax({
							"dataType" : 'json',
							"type" : "POST",
							"url" : sSource,
							"data" : aoData,
							"success" : function(data){
								fnCallback(data);
							}
						});
					},
					"aoColumns" : [ 
					                {
						"mDataProp" : "id"
					},{
						"mDataProp" : "oid"
					}, {
						"mDataProp" : "user.username"
					}, {
						"mDataProp" : "order.c4"
					}, {
						"mDataProp" : "order.c9"
					},{
						"mDataProp" : "user.p1"
					},{
						"mDataProp" : "user.p2"
					},{
						"mDataProp" : "pay"
					}, {
						"mDataProp" : "order.c12"
					}, {
						"mDataProp" : "submitDate"
					},{
						"mDataProp" : "user.name"
					}, {
						"mDataProp" : "state"
					}],
					"aoColumnDefs" : [
					{
						'aTargets' : [0],
						'fnRender' : function(oObj, sVal) {
							return "<input type='checkbox' name='checkbox' value='"+sVal+"'>";
						}
					},
					{
						'aTargets' : [11],
						'fnRender' : function(oObj, sVal) {
							if(sVal=='商家未确认')
								return "<span class='label label-info'>"+sVal+"</span>";
							else if(sVal=='已确认订单')
								 return "<span class='label label-success'>"+sVal+"</span>";
							else if(sVal=='已付款')
								 return "<span class='label label-important'>"+sVal+"</span>";
							 return "<span class='label'>"+sVal+"</span>";
						}
					},
					/*{
						'aTargets' : [10],
						'fnRender' : function(oObj, sVal) {
							var operate = '<div class="btn-group">';
							operate += '<button class="btn btn-info dropdown-toggle" data-toggle="dropdown"> 操作 <span class="caret"></span></button>';
							operate += ' <ul class="dropdown-menu">';
							operate += '<li><a onclick="$.adminPayorderdeal.ordersend('+oObj.aData.id+')"><i class="icon-edit"></i>发货</a></li>';
							operate += '<li><a onclick="$.adminPayorderdeal.orderpay('+oObj.aData.id+')"><i class="icon-wrench"></i>付款 </a></li>';
							operate += '</ul>';
							operate += '</div>';
							return operate;
						}
					},*/
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.payorderdealDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.payorderdealDataTable.fnDraw(oSettings);
			}

		},
		ordersend :function(id){
			bootbox.confirm( "是否确认发货？", function (result) {
		        if(result){
		        	$.ajax({
		    			type : "get",
		    			url : $.ace.getContextPath() + "/admin/orderdeal/send/"+id,
		    			dataType : "json",
		    			success : function(json) {
		    				if(json.state=='success'){
		    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
		    					$.adminPayorderdeal.initSearchDataTable();
		    				}else{
		    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
		    				}
		    			}
		    		});
		        }
		    });
		},
		
	orderpay :function(id){
		bootbox.confirm( "是否确认付款？", function (result) {
	        if(result){
	        	$.ajax({
	    			type : "get",
	    			url : $.ace.getContextPath() + "/admin/orderdeal/pay/"+id,
	    			dataType : "json",
	    			success : function(json) {
	    				if(json.state=='success'){
	    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.adminPayorderdeal.initSearchDataTable();
	    				}else{
	    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	    				}
	    			}
	    		});
	        }
	    });
	},
	deleteall :function(id){
		bootbox.confirm( "是否确认删除？", function (result) {
	        if(result){
	        	$.ajax({
	    			type : "post",
	    			url : $.ace.getContextPath() + "/admin/orderdeal/deleteall",
	    			dataType : "json",
	    			data:"id="+$.adminPayorderdeal.getcheckedvalue(),
	    			success : function(json) {
	    				if(json.state=='success'){
	    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.adminPayorderdeal.initSearchDataTable();
	    				}else{
	    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	    				}
	    			}
	    		});
	        }
	    });
	},
	sendall :function(id){
		bootbox.confirm( "是否确认发货？", function (result) {
	        if(result){
	        	$.ajax({
	    			type : "post",
	    			url : $.ace.getContextPath() + "/admin/orderdeal/sendall",
	    			dataType : "json",
	    			data:"id="+$.adminPayorderdeal.getcheckedvalue(),
	    			success : function(json) {
	    				if(json.state=='success'){
	    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.adminPayorderdeal.initSearchDataTable();
	    				}else{
	    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	    				}
	    			}
	    		});
	        }
	    });
	},
	payall :function(id){
		bootbox.confirm( "是否确认付款？", function (result) {
	        if(result){
	        	$.ajax({
	    			type : "post",
	    			url : $.ace.getContextPath() + "/admin/orderdeal/payall",
	    			dataType : "json",
	    			data:"id="+$.adminPayorderdeal.getcheckedvalue(),
	    			success : function(json) {
	    				if(json.state=='success'){
	    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	    					$.adminPayorderdeal.initSearchDataTable();
	    				}else{
	    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	    				}
	    			}
	    		});
	        }
	    });
	},
	getcheckedvalue:function(){
		var v="";
		var checks=$("input[name='checkbox']:checked");
		for(var i=0;i<checks.size();i++){
				if(v=='')
					v=$(checks[i]).val();
				else v+=","+$(checks[i]).val();
		}
		return v;
	}

};