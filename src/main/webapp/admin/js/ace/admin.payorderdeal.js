jQuery.adminPayorderdeal = {
		payorderdealDataTable:null,
		initSearchDataTable : function() {
			if (this.payorderdealDataTable == null) {
				this.payorderdealDataTable = $('#dt_table_view').dataTable({
					"sDom" : "<'row-fluid'<'span6'l>r>t<'row-fluid'<'span6'i><'span6'p>>",
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
					"aoColumns" : [ {
						"mDataProp" : "id"
					}, {
						"mDataProp" : "order.id"
					}, {
						"mDataProp" : "order.c4"
					}, {
						"mDataProp" : "order.c9"
					}, {
						"mDataProp" : "order.c12"
					}, {
						"mDataProp" : "submitDate"
					},{
						"mDataProp" : "user.name"
					}, {
						"mDataProp" : "state"
					}, {
						"mDataProp" : ""
					}],
					"aoColumnDefs" : [
					{
						'aTargets' : [7],
						'fnRender' : function(oObj, sVal) {
							 return "<span class='label label-success'>"+sVal+"</span>"
						}
					},
					{
						'aTargets' : [8],
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
					},
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
	}
};