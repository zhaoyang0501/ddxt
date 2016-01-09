jQuery.adminOrderstate = {
		userDataTable:null,
		initSearchDataTable : function() {
			if (this.userDataTable == null) {
				this.userDataTable = $('#dt_table_view').dataTable({
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
					"sAjaxSource" : $.ace.getContextPath() + "/admin/orderstate/list",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var state="";
						var begin = $("#_begin").val();
						if (!!begin) {
							aoData.push({
								"name" : "begin",
								"value" : begin
							});
						}
						var end = $("#_end").val();
							if (!!end) {
								aoData.push({
									"name" : "end",
									"value" : end
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
						"mDataProp" : "oid"
					}, {
						"mDataProp" : "user.name"
					}, {
						"mDataProp" : "order.c4"
					}, {
						"mDataProp" : "order.c9"
					},{
						"mDataProp" : "pay"
					},{
						"mDataProp" : "order.c12"
					}, {
						"mDataProp" : "submitDate"
					}, {
						"mDataProp" : "state"
					}, {
						"mDataProp" : "id"
					}],
					"aoColumnDefs" : [
					{
						'aTargets' : [7],
						'fnRender' : function(oObj, sVal) {
							state=sVal;
							if(sVal=='商家未确认')
								return "<span class='label label-info'>"+sVal+"</span>";
							else if(sVal=='已确认订单')
								 return "<span class='label label-success'>"+sVal+"</span>";
							else if(sVal=='已付款')
								 return "<span class='label label-important'>"+sVal+"</span>";
							 return "<span class='label'>"+sVal+"</span>";
						}
					},
					{
						'aTargets' : [8],
						'fnRender' : function(oObj, sVal) {
							if("商家未确认"==state)
								return "  <button class=\"btn2 btn-info\" onclick=\"$.adminOrderstate.deleteorder("+oObj.aData.id+")\"><i class=\"icon-trash\"></i> 删除</button> ";
							if("已付款"==state)
								return " <button class=\"btn2 btn-info\" onclick=\"$.adminOrderstate.confirmorder("+oObj.aData.id+")\"><i class=\"icon-edit\"></i> 确认</button>  ";
							
						}
					},
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.userDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.userDataTable.fnDraw(oSettings);
			}

		},
		deleteorder :function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
		        if(result){
		        	$.ajax({
		    			type : "get",
		    			url : $.ace.getContextPath() + "/admin/orderstate/delete/"+id,
		    			dataType : "json",
		    			success : function(json) {
		    				if(json.state=='success'){
		    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
		    					$.adminOrderstate.initSearchDataTable();
		    				}else{
		    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
		    				}
		    			}
		    		});
		        }
		    });
		},
		confirmorder :function(id){
			bootbox.confirm( "是否确认收款？", function (result) {
		        if(result){
		        	$.ajax({
		    			type : "get",
		    			url : $.ace.getContextPath() + "/admin/orderstate/confirm/"+id,
		    			dataType : "json",
		    			success : function(json) {
		    				if(json.state=='success'){
		    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
		    					$.adminOrderstate.initSearchDataTable();
		    				}else{
		    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
		    				}
		    			}
		    		});
		        }
		    });
		},
};