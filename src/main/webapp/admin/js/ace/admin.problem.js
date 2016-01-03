jQuery.adminProblem = {
		problemDataTable:null,
		initSearchDataTable : function() {
			if (this.problemDataTable == null) {
				this.problemDataTable = $('#dt_table_view').dataTable({
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
					"sAjaxSource" : $.ace.getContextPath() + "/admin/problem/list",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var name = $("#_name").val();
						if (!!name) {
							aoData.push({
								"name" : "problemname",
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
						"mDataProp" : "name"
					}, {
						"mDataProp" : "hard"
					}, {
						"mDataProp" : "reference"
					}, {
						"mDataProp" : "category.name"
					}, {
						"mDataProp" : "createDate"
					},{
						"mDataProp" : ""
					}],
					"aoColumnDefs" : [
						{
							'aTargets' : [6],
							'fnRender' : function(oObj, sVal) {
								return"  <button class=\"btn2 btn-info\" onclick=\"$.adminProblem.deleteProblem("+oObj.aData.id+")\"><i class=\"icon-trash\"></i> 删除</button>" +
								" <button class=\"btn2 btn-info\" onclick=\"$.adminProblem.showEdit("+oObj.aData.id+")\"><i class=\"icon-pencil\"></i>修改</button>";
							}
						},
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.problemDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.problemDataTable.fnDraw(oSettings);
			}

		},
		deleteProblem :function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ace.getContextPath() + "/admin/problem/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.state=='success'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.adminProblem.initSearchDataTable();
	        				}else{
	        					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		},
		showProblemAddModal: function(id){
			$("#id").val("");
			$('#_modal').modal({
			});
			$("#_modal").modal('show');
		},
		showEdit: function (id){
			$("#id").val(id);
			$.ajax({
    			type : "get",
    			url : $.ace.getContextPath() + "/admin/problem/get/"+id,
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$("*[name='name']").val(json.object.name);
    					$("*[name='cpulimit']").val(json.object.cpulimit);
    					$("*[name='hard']").val(json.object.hard);
    					$("*[name='input']").val(json.object.input);
    					$("*[name='inputemp']").val(json.object.inputemp);
    					$("*[name='inputok']").val(json.object.inputok);
    					$("*[name='output']").val(json.object.output);
    					$("*[name='outputemp']").val(json.object.outputemp);
    					$("*[name='outputok']").val(json.object.outputok);
    					$("*[name='reference']").val(json.object.reference);
    					$("*[name='timelimit']").val(json.object.timelimit);
    					$("*[name='type']").val(json.object.type);
    					$("*[name='remark']").val(json.object.remark);
    				}else{
    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
    				}
    			}
    		});
			$("#_modal").modal('show');
		},
		
		saveProblem: function(id){
			$.ajax({
    			type : "post",
    			url : $.ace.getContextPath() + "/admin/problem/save",
    			data:$("form").serialize(),
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$("#_modal").modal('hide');
    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
    					$.adminProblem.initSearchDataTable();
    				}else{
    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
    				}
    			}
    		});
		}
		
};