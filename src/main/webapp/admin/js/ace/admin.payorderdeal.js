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
						var name = $("#_name").val();
						if (!!name) {
							aoData.push({
								"name" : "username",
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
							operate += '<li><a onclick="$.ims.attenceStateCheck.showAbsenteeModal('+oObj.aData.id+')"><i class="icon-edit"></i>发货</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showDayOffModal('+oObj.aData.id+')"><i class="icon-wrench"></i>请假 </a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showTravelModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 出差</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showOkModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 正班</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showLaterModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 迟到</a></li>';
							operate += '<li><a onclick="$.ims.attenceStateCheck.showUnKnownModal('+oObj.aData.id+')"><i class="icon-wrench"></i> 未知</a></li>';
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
				var oSettings = this.userDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.userDataTable.fnDraw(oSettings);
			}

		}
};