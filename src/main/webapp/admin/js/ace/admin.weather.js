jQuery.adminWeather = {
		weatherDataTable:null,
		initSearchDataTable : function() {
			if (this.weatherDataTable == null) {
				this.weatherDataTable = $('#dt_table_view').dataTable({
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
					"sAjaxSource" : $.ace.getContextPath() + "/admin/weather/list",
					"fnDrawCallback" : function(oSettings) {
						$('[rel="popover"],[data-rel="popover"]').popover();
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var name = $("#_name").val();
						if (!!name) {
							aoData.push({
								"name" : "cityname",
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
						"mDataProp" : "city.name"
					}, {
						"mDataProp" : "nowDate"
					},{
						"mDataProp" : "type"
					},{
						"mDataProp" : "temmax"
					},{
						"mDataProp" : "temmin"
					},{
						"mDataProp" : "aqi"
					},{
						"mDataProp" : "pm25"
					},{
						"mDataProp" : "sd"
					},{
						"mDataProp" : ""
					}],
					"aoColumnDefs" : [
						{
							'aTargets' : [4],
							'fnRender' : function(oObj, sVal) {
								return sVal+"℃";
							}
						},
						{
							'aTargets' : [5],
							'fnRender' : function(oObj, sVal) {
								return sVal+"℃";
							}
						},
						{
							'aTargets' : [8],
							'fnRender' : function(oObj, sVal) {
								return sVal+"%";
							}
						},
						{
							'aTargets' : [9],
							'fnRender' : function(oObj, sVal) {
								return"  <button class=\"btn2 btn-info\" onclick=\"$.adminWeather.deleteWeather("+oObj.aData.id+")\"><i class=\"icon-trash\"></i> 删除</button>";
					
							}
						},
					 {
						'aTargets' : [ '_all' ],
						'bSortable' : false,
						'sClass' : 'center'
					}]

				});
			} else {
				var oSettings = this.weatherDataTable.fnSettings();
				oSettings._iDisplayStart = 0;
				this.weatherDataTable.fnDraw(oSettings);
			}

		},
		showaddModal: function(id){
			$("#id").val("1");
			$('#_modal').modal({
			});
			$("#_modal").modal('show');
		},
		
		updateall :function(id){
			bootbox.confirm( "是否确认更新全部？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ace.getContextPath() + "/admin/weather/updateall/",
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.state=='success'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.adminWeather.initSearchDataTable();
	        				}else{
	        					noty({"text":""+ json.resultMap.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		},
		deleteWeather :function(id){
			bootbox.confirm( "是否确认删除？", function (result) {
	            if(result){
	            	$.ajax({
	        			type : "get",
	        			url : $.ace.getContextPath() + "/admin/weather/delete/"+id,
	        			dataType : "json",
	        			success : function(json) {
	        				if(json.state=='success'){
	        					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
	        					$.adminWeather.initSearchDataTable();
	        				}else{
	        					noty({"text":""+ json.resultMap.msg +"","layout":"top","type":"warning"});
	        				}
	        			}
	        		});
	            }
	        });
		},
		showEdit: function (id){
			$("#id").val(id);
			$.ajax({
    			type : "get",
    			url : $.ace.getContextPath() + "/admin/weather/get/"+id,
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$("#name").val(json.object.name);
    					$("#username").val(json.object.username);
    					$("#password").val(json.object.password);
    					$("#tel").val(json.object.tel);
    					$("#email").val(json.object.email);
    					$("#address").val(json.object.address);
    					$("#level").val(json.object.level);
    					$("#manger").val(json.object.manger);
    					$("#yyzh").val(json.object.yyzh);
    				}else{
    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
    				}
    			}
    		});
			$("#_modal").modal('show');
		},
		saveWeather: function(id){
			$.ajax({
    			type : "post",
    			url : $.ace.getContextPath() + "/admin/weather/save",
    			data:$("form").serialize(),
    			dataType : "json",
    			success : function(json) {
    				if(json.state=='success'){
    					$("#_modal").modal('hide');
    					noty({"text":""+ json.msg +"","layout":"top","type":"success","timeout":"2000"});
    					$.adminWeather.initSearchDataTable();
    				}else{
    					noty({"text":""+ json.msg +"","layout":"top","type":"warning"});
    				}
    			}
    		});
		}
};