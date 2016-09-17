$(document).ready(function() {
	var dt_transform = $('#tbl-transform').DataTable({
		"bPaginate" : true,
		"pagingType" : "simple",
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : true,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"bServerSide" : true,
		"pageLength" : 15,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/transform.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = JSON.stringify([ "","",
			                              oSettings._iDisplayLength,
			                              oSettings._iDisplayStart ]);
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
				"contentType" : "application/json",
				"dataType" : "json",
				"data" : qparam,
				"sAjaxDataProp" : "payload",
				"success" : function(
						result) {
					result.iTotalDisplayRecords = result.nRow;
					result.aaData = result.payload;
					result.payload = null;
					result.iDisplayLength = oSettings._iDisplayLength;
					result.iDisplayStart = oSettings._iDisplayStart;
					fnCallback(result);
					
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "transform_id",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return '<a class="transform-rule">'
		            		   + data + '</a>';
		            	   }
		               },
		               {
		            	   mData : "transform_template",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return fnTextAreaRendering(
		            				   data, 15, 15, 2);
		            	   }
		               },
		               {
		            	   mData : "transform_type"
		               }
		               ],
		               "fnInitComplete" : function(oSettings,
		            		   json) {
		            	   $('#tbl-transform tbody a.transform-rule:eq(0)').click();
		               }
	});
					
	var dt_transform_rule = $('#tbl-transform-rule').DataTable({
		"bPaginate" : true,
		"pagingType" : "simple",
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : true,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"bServerSide" : true,
		"pageLength" : 15,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/transform-rule.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var tempTransformId = $('#transform-id').val();
			var qparam = JSON.stringify([tempTransformId]);
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
				"contentType" : "application/json",
				"dataType" : "json",
				"data" : qparam,
				"sAjaxDataProp" : "payload",
				"success" : function(
						result) {
					result.iTotalDisplayRecords = result.nRow;
					result.aaData = result.payload;
					result.payload = null;
					result.iDisplayLength = oSettings._iDisplayLength;
					result.iDisplayStart = oSettings._iDisplayStart;
					fnCallback(result);
					
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "transform_rule_id"
		               },
		               {
		            	   mData : "transform_rule_source",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return fnTextAreaRendering(
		            				   data, 15, 15, 2);
		            	   }
		               },
		               {
		            	   mData : "transform_rule_target",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return fnTextAreaRendering(
		            				   data, 15, 15, 2);
		            	   }
		               },
		               {
		            	   mData : "transform_rule_config"
		               },
		               {
		            	   mData : "transform_rule_matrix"
		               }
		               ]
		
	});
	/* DataTable definition END! */
	
	$('#tbl-transform tbody').on('click','a.transform-rule',function() {
		var tr = $(this).closest('tr');
		var row = dt_transform.row(tr);
		
		// Open this row
		var rowIdx = row.index();
		var data = row.data();
		
		$('#transform-id').val(data.transform_id);
		dt_transform_rule.ajax.reload();
		
		if (!$(tr).hasClass('active')) {
			dt_transform.$('tr.active').removeClass('active');
			$(tr).addClass('active');
		}
	});
	$('#btn-link-transform-template').click(function() {
		window.location.href = '../transform/home.do?transform-id='+$('#transform-id').val();
	});	
	
	$('#btn-delete').click(function() {
		var answer = confirm("Do you want to Delete This Data "+$('#transform-id').val()+" ?");
		if (answer){
			deleteTransformRule($('#transform-id').val(),'delete',dt_transform_rule)
		}
	});
	
});
	
function fnTextAreaRendering(data, minLength, cols, rows) {
	if (data == null || data == '')
		return data;
	else if (data.length <= minLength)
		return data;
	else {
		var result = "<textarea cols='" + cols + "' rows='" + rows
				+ "' class='normal-hlight' readonly >" + data + "</textarea>";
		return result;
	}

}

function deleteTransformRule(tempTransformId,action,dt_transform_rule){
	dataParam =  fnCompoundDelete("transform-rule",tempTransformId,'') ;
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/transform-rule-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			
			if(respond.message==='Succes '+action){
				dataParam =  fnCompoundDelete("transform",tempTransformId,'') ;
				deleteTransform(dataParam,action);
			}else{
				var tempDataRule = dt_transform_rule.rows().data();	
				if(tempDataRule.length<1){
					dataParam =  fnCompoundDelete("transform",tempTransformId,'') ;
					deleteTransform(dataParam,action);
				}
			}
			
		},
		error : function(err) {
			alert(err.statusText+" "+err.status+" [table transform_rule]");
			window.location.href = '../transform/detail_transform_rule.do';
		}
	});
}

function deleteTransform(dataParam,action){

	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/transform-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert(respond.message);
			window.location.href = '../transform/detail_transform_rule.do';
		},
		error : function(err) {
			alert(err.statusText+" "+err.status+" [table transform]");
			window.location.href = '../transform/detail_transform_rule.do';
		}
	});

}

function fnCompoundDelete(tempTbaleName,tempTransformId,transform_rule_id) {
	if(tempTbaleName==='transform'){
		var qparam = JSON.stringify([tempTransformId]);
      	console.log(qparam);
      	return qparam;
	}else if(tempTbaleName==='transform-rule'){
		var qparam = JSON.stringify([ tempTransformId,transform_rule_id]);
      	console.log(qparam);
      	return qparam;
	};
}