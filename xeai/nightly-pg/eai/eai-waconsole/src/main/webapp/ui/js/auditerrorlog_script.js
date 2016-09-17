$(document).ready(function() {
	var config = {
			'.chosen-select' : {},
			'.chosen-select-deselect' : {
				allow_single_deselect : true
			},
			'.chosen-select-no-single' : {
				disable_search_threshold : 5
			},
			'.chosen-select-no-results' : {
				no_results_text : 'Oops, nothing found!'
			},
			'.chosen-select-width' : {
				width : "95%"
			}
	}
	$('.i-checks').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green',
	});
	
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	$('.chosen-container.chosen-container-multi').css('width','396px');
	
	var dt_logs = $('#audit-error-log').DataTable({
		"bAutoWidth" : false,
		"bProcessing" : false,
		"bServerSide" : false,
		"sAjaxDataProp" : "payload",
		"bPaginate" : false,
		"bInfo" : false,
		"bFilter" : false,
		"bLengthChange" : false,
		"order" : [ [ 0, "desc" ],
		            [ 3, "desc" ], [ 1, "desc" ] ],
		            "columnDefs" : [ {
		            	"bSortable" : false,
		            	"aTargets" : [ 0, 1, 3, 4, 5, 6 ]
		            } ],
		            "sAjaxSource" : "../dautl/extract/rows/auditerrorlog.do",
		            "fnServerData" : function(sSource,
		            		aoData, fnCallback, oSettings) {
		            	
		            	var qparam = fnCriteriasCompound();
		            	
		            	$.ajax({
		            		"type" : "POST",
		            		"url" : "../dautl/extract/rows/auditerrorlog.do",
		            		"data" : qparam,
		            		"contentType" : "application/json",
		            		"dataType" : "json",
		            		"success" : fnCallback,
		            		error : function(msg) {
		            			var data = {
		            					"iTotalRecords" : 0,
		            					"iTotalDisplayRecords" : 0,
		            					"aData" : [ {
		            						"xeai_error_id" : "",
		            						"xeai_id" : "",
		            						"event_name" : "",
		            						"audit_time" : "",
		            						"cause" : ",",
		            						"severity" : "",
		            						"endpoint" : ""
		            					} ]
		            			};
		            			return JSON.stringify(data);
		            		}
		            	});
		            },
		            "aoColumns" : [
		                           {
		                        	   mData : "xeai_error_id"
		                           },
		                           {
		                        	   sClass : "bold-hlight",
		                        	   mData : "xeai_id",
		                        	   mRender : function(data,
		                        			   type, full) {
		                        		   return "<a href='../auditlog/home.do?xeai-id="+data+"'>"
		                        		   + data + "</a>";
		                        	   }
		                           },
		                           {
		                        	   mData : "event_name"
		                           },
		                           {
		                        	   mData : "audit_time",
		                        	   sClass : "absolute"
		                           },
		                           {
		                        	   mData : "cause",
		                        	   mRender : function(data,
		                        			   type, full) {
		                        		   return fnTextAreaRendering(data, 15, 15, 2);
		                        	   }
		                           },
		                           {
		                        	   mData : "severity",
		                        	   mRender : function(data,
		                        			   type, full) {
		                        		   return fnTextAreaRendering(data, 15, 15, 2);
		                        	   }
		                           },
		                           {
		                        	   mData : "endpoint",
		                        	   mRender : function(data,
		                        			   type, full) {
		                        		   return fnTextAreaRendering(data, 15, 15, 2);
		                        	   }
		                           } ]	
	});

	$('#searchWithCriteria').on('click', function(e) {
		dt_logs.ajax.reload();
		toastr.info("Queried Done!");
	});
	$('#re-query').on('click', function(e) {
		dt_logs.ajax.reload();
		toastr.success("Re-query success!");
	});
	$('#clear-query').on('click', function(e) {
		$('#xeai-id').val("");
		$('#xeai-error-id').val("");
		$('#event-name').val('').trigger('chosen:updated');
		$('#eventNameTempId').val("");
		$('#audit-date-start').val("");
		$('#audit-time-start').val("");
		$('#audit-date-end').val("");
		$('#audit-time-end').val("");
		$('#endpoint').val("");
		$('#cause').val("");
		dt_logs.ajax.reload();
		toastr.info("Query cleared!");
	});
	
	// cbxevents_name
	fnInitiateCbxReference("cbxevents_name", "event-name");
	
});
	// END OF DOCUMENT READY
function fnTextAreaRendering(data, minLength, cols, rows) {
	if (data == null || data == '')
		return data;
	else if (data.length <= minLength)
		return data;
	else {
		var result = "<textarea cols='" + cols + "' rows='" + rows
		+ "' class='normal-hlight' >" + data + "</textarea>";
		return result;
	}
}

function fnCriteriasCompound() {
	var xeaiErrorId = '';
	var xeaiId = '';
	var eventName = '';
	var auditTimeStart = '';
	var auditTimeEnd = '';
	var endpoint = '';
	var causeName = '';

	var arr, i, temp;

	$('#lbl-xeai-id').html("");
	if ($('#xeai-id').val() != '') {
		xeaiId = $('#xeai-id').val();
		$('#lbl-xeai-id').html("XEAI-ID eq {" + xeaiId + "}");
	}

	if ($('#xeai-error-id').val() != '') {
		xeaiErrorId = $('#xeai-error-id').val();
		temp = "ERROR-ID eq {" + xeaiErrorId + "}";
	}

	$('#lbl-event-name').html("");
	$('#event-name').trigger('chosen:updated');
	if ($('#event-name > :selected').length > 0) {
		i = 0;
		arr = $('#event-name').val();
		$('#event-name > :selected').each(function() {
			eventName += arr[i];
			if (i < arr.length - 1)
				eventName += ",";
			i++;
		});
		$('#lbl-event-name').html("EVENT-NAME in {" + eventName + "}");
	}
	if ($('#eventNameTempId').val() != '' && $('#event-name > :selected').length <= 0) {
		var eventNameTempId = $('#eventNameTempId').val() ;
		$('#lbl-event-name').html("EVENT-NAME in {" + eventNameTempId + "}"); 
		eventName = eventNameTempId; 
	}

	if ($('#audit-date-start').val() != '')
		auditTimeStart = $('#audit-date-start').val();

	if ($('#audit-time-start').val() != '') {
		if (auditTimeStart == '') {
			auditTimeStart = moment().format("DD-MM-YYYY") + ' '
			+ $('#audit-time-start').val();
		} else {
			auditTimeStart += ' ' + $('#audit-time-start').val();
		}
	} else if (auditTimeStart != '') {
		auditTimeStart += ' 00:00'
	}

	var auditTimeEnd = '';
	if ($('#audit-date-end').val() != '')
		auditTimeEnd = $('#audit-date-end').val();

	if ($('#audit-time-end').val() != '') {
		if (auditTimeEnd == '') {
			auditTimeEnd = moment().format("DD-MM-YYYY") + ' '
			+ $('#audit-time-end').val();
		} else {
			auditTimeEnd += ' ' + $('#audit-time-end').val();
		}
	} else if (auditTimeEnd != '') {
		auditTimeEnd += ' 23:59'
	}

	$('#lbl-audit-time').html("");
	if (auditTimeStart != "" || auditTimeEnd != "") {
		temp = "AUDIT-TIME is {";
		if (auditTimeStart) {
			temp += " after " + auditTimeStart;
		}
		if (auditTimeEnd) {
			if (auditTimeStart != "")
				temp += " and";
			temp += " before " + auditTimeEnd;
		}
		temp += "}";
		$('#lbl-audit-time').html(temp);
	} else if (auditTimeStart == "" && auditTimeEnd == "") {
		auditTimeEnd = moment().add(1,'minutes').format("DD-MM-YYYY HH:mm");
		temp = " AUDIT-TIME is { before " + auditTimeEnd + "}"
		$('#lbl-audit-time').html(temp);
	}

	$('#lbl-endpoint').html("");
	if ($('#endpoint').val() != '') {
		endpoint = $('#endpoint').val();
		$('#lbl-endpoint').html("ENDPOINT like {" + endpoint + "}");
	}

	$('#lbl-cause').html("");
	if ($('#cause').val() != '') {
		causeName = $('#cause').val();
		$('#lbl-cause').html("cause like {" + causeName + "}");
	}

	var qparam = JSON.stringify([ xeaiErrorId,xeaiId, eventName,
			auditTimeStart, auditTimeEnd, endpoint, causeName ]);
	console.log(qparam);
	return qparam;
}

function fnInitiateCbxReference(key, cbxId) {
	$.ajax({
		type : "POST",
		url : "../dautl/retrieve/json/" + key + ".do",
		data : JSON.stringify([]),
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			var objSelect = document.getElementById(cbxId);
			$.each(respond.payload, function(i, row) {
				opt = document.createElement("option");
				opt.value = row.key;
				opt.text = row.key;
				objSelect.appendChild(opt);
			});
			$('#' + cbxId).chosen().trigger("chosen:updated");
		},
		error : function(msg) {
			toastr.error("Context failed to Load, no reference of " + cbxId
					+ " available");
		}
	});
}

function fnAsLink(data, etcClass) {
	return "<a class='" + etcClass + "'>" + data + "</a>";
}