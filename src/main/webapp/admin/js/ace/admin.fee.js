jQuery.adminFee = {
		feeDataTable:null,
		initSearchDataTable : function() {
			if (this.feeDataTable == null) {
				this.feeDataTable = $('#dt_table_view').dataTable({
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
					"sAjaxSource" : $.ace.getContextPath() + "/admin/fee/list",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var name = $("#_name").val();
						if (!!name) {
							aoData.push({
								"name" : "feename",
								"value" : name
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
						"mDataProp" : "user.name"
					}, {
						"mDataProp" : "year"
					}, {
						"mDataProp" : "needa"
					}, {
						"mDataProp" : "reala"
					},{
						"mDataProp" : "resta"
					}, {
						"mDataProp" : "needb"
					}, {
						"mDataProp" : "realb"
					},{
						"mDataProp" : "restb"
					}, {
						"mDataProp" : "needc"
					}, {
						"mDataProp" : "realc"
					},{
						"mDataProp" : "restc"
					},{
						"mDataProp" : "state"
					},{
						"mDataProp" : ""
					}],
					"aoColumnDefs" : [
						{
							'aTargets' : [3],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [4],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [5],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [6],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [7],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [8],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [9],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [10],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [11],
							'fnRender' : function(oObj, sVal) {
								 return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
						{
							'aTargets' : [13],
							'fnRender' : function(oObj, sVal) {
								return"  <button class=\"btn2 btn-info\" onclick=\"$.adminFee.deleteFee("+oObj.aData.id+")\"><i class=\"icon-trash\"></i> 删除</button>" ;
							}
						},
						{
							'aTargets' : [12],
							'fnRender' : function(oObj, sVal) {
								if(sVal=='未缴')
								 return "<span class='label label-important'>"+sVal+"</span>";
								else return "<span class='label label-success'>"+sVal+"</span>"
							}
						},
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.feeDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.feeDataTable.fnDraw(oSettings);
			}

		},
		deleteFee :function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ace.getContextPath() + "/admin/fee/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.state=='success'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.adminFee.initSearchDataTable();
	        				}else{
	        					noty({"text":""+ json.resultMap.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		},
		showFeeAddModal: function(id){
			$("#id").val(id);
			$('#_modal').modal({
			});
			$("#_modal").modal('show');
		},
		showEdit: function (id){
			$("#id").val(id);
			$.ajax({
    			type : "get",
    			url : $.ace.getContextPath() + "/admin/fee/get/"+id,
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$("#name").val(json.object.name);
    					$("#context").val(json.object.context);
    				}else{
    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
    				}
    			}
    		});
			$("#_modal").modal('show');
		},
		
		saveFee: function(id){
			$.ajax({
    			type : "post",
    			url : $.ace.getContextPath() + "/admin/fee/save",
    			data:$("form").serialize(),
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$("#_modal").modal('hide');
    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
    					$.adminFee.initSearchDataTable();
    				}else{
    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
    				}
    			}
    		});
		}
		
};