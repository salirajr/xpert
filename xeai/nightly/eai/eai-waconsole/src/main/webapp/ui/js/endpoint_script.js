$(document).ready(function() {
	$('#endpoint-buses-components').dataTable({
			"sPaginationType" : "full_numbers",
			"bProcessing" : true,
			"bServerSide" : true,
			"sAjaxSource" : "/eai-waconsole/CommonViewAction/getXeaiEndpoint.do",
			"sAjaxDataProp" : "aData",
			"bPaginate" : true,
			"bInfo" : true,
			"bLengthChange" : false,
			"pageLength" : 25,
			"scrollCollapse" : true,
			"fnServerData" : function(sSource,
					aoData, fnCallback, oSettings) {
				var Data = JSON
						.stringify({
							"iDisplayStart" : oSettings._iDisplayStart,
							"iDisplayLength" : oSettings._iDisplayLength,
							"keyData" :"endpoint",
							"keyRowCount" : "endpointRowCount"
						});
				$
						.ajax({
							"type" : "POST",
							"url" : sSource,
							"data" : Data,
							"contentType" : "application/json",
							"dataType" : "json",
							"success" : fnCallback
						})
			},
			"bAutoWidth": false,
			"aoColumns" : [ {
				mData : "endpoint_name" // Instance name
			},
			{
				mData : "endpoint_state", // State
				/*"mRender" : function(data,
						type, full) {
					var colour='';
					var tag='';
					if(data=='STOPED'){
						colour='btn btn-danger';
						tag = 'fa fa-ban';
					}else if(data=='ALIVE'){
						colour='btn btn-primary';
						tag = 'fa fa-check';
					}					
//					var a ='<button type="button" class="'+colour+'  btn-xs" data-toggle="modal" data-target="#exampleModal" data-whatever="'+data+'"><i class="'+tag+'"></i></button>';
					var a = '<a data-toggle="modal" data-target="#exampleModal" >' + data + '</a>';
					return a;
				},*/
				"sClass": "center"
			}, {
				mData : "endpoint_protocols" // protocol
			}/*, {
				mData : "endpoint_type" // type
			}*/,			
			{
				mData : "endpoint_group" // Group Component
			},
			{
				mData : "endpoint_flow" // Busines flow
			},
			{
				mData : "module_group" // module group <- from table xeai_businessflow.flow_owner
			}]
		
	});

});
