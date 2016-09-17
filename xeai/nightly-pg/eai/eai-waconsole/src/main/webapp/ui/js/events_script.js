$(document).ready(function() {

	// js for selected combobox in html
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
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	$('.chosen-container.chosen-container-multi').css('width','396px');
	// end of js for selected combobox in html
	
	var dt_event = $('#dt-event').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : false,
		"bServerSide" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 25,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2, 3, 4, 5, 6,7, 8 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/event.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
				
			var qparam = fnCriteriasCompound();
			
			$.ajax({
				"type" : "POST",
				"url" : "../dautl/extract/rows/event.do",
				"contentType" : "application/json",
				"dataType" : "json",
				"data" : qparam,
				"sAjaxDataProp" : "payload",
				"success" : function(result) {
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
		            	   mData : "event_name",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "";
		            	   }
		               },
		               {
		            	   mData : "event_name",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               },
		               {
		            	   mData : "event_base"
		               },
		               {
		            	   mData : "integration_type",
		            	   mRender : function(data,type, full) {
		            		   return fnRenderIntegrationType(data);
		            	   }
		               },
		               {
		            	   mData : "event_owner"
		               },
		               {
		            	   mData : "event_status",
		            	   mRender : function(data,type, full) {
		            		   return fnRenderState(data);
		            	   }
		               }, {
		            	   mData : "event_group"
		               }, {
		            	   mData : "system_source"
		               }, {
		            	   mData : "system_target"
		               } ]
		
	});
	
	// Add event listener for opening and closing details
	
	$('#dt-event tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_event.row(tr);
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRender(rowIdx)).show();
			tr.addClass('shown');
			
			fnInitiateCbxReferenceForm("owner","owner-" + rowIdx,data.event_owner);
			fnInitiateCbxReferenceForm("group","group-" + rowIdx,data.event_group);
			fnInitiateCbxReferenceFormEvent("cbxevents_name","event-base-" + rowIdx,data.event_base,data.event_name);

			$('#event-name-' + rowIdx).val(data.event_name);
			$('#event-base-' + rowIdx).val(data.event_base);
			$('#i-type-' + rowIdx).val(data.integration_type);
			$('#group-' + rowIdx).val(data.event_group);
			$('#sys-source-' + rowIdx).val(data.system_source);
			$('#sys-target-' + rowIdx).val(data.system_target);
			$('#owner-' + rowIdx).val(data.event_owner);
			$('#msg-ty-source-' + rowIdx).val(data.system_source_message_type);
			$('#msg-ty-target-' + rowIdx).val(data.system_target_message_type);
			$('#state-' + rowIdx).val(data.event_status);
			
			if(data.is_existing!=1){
				$('#event-name-' + rowIdx).attr('readonly', false);
			}
		}
	});
	
	$('#dt-event tbody').on('click','button.act-update',function() {
		var rowIdx = this.value;
		$('#event-name-' + rowIdx).val();
		$('#event-base-' + rowIdx).val();
		$('#i-type-' + rowIdx).val();
		$('#group-' + rowIdx).val();
		$('#sys-source-' + rowIdx).val();
		$('#sys-target-' + rowIdx).val();
		$('#owner-' + rowIdx).val();
		$('#msg-ty-source-' + rowIdx).val();
		$('#msg-ty-target-' + rowIdx).val();
		$('#state-' + rowIdx).val();
		
		var row = dt_event.row(this.value);
		var data = row.data();
		var action;
		var dataParam;
		
		if (data.event_name === null
				|| data.event_name === ''
					|| data.event_name == ''
						|| data.event_name == null) {
			action = 'insert';
			dataParam = fnEventCompoundInsert($('#event-base-' + rowIdx).val(), 
					$('#group-' + rowIdx).val(), 
					$('#event-name-'+ rowIdx).val(), 
					$('#owner-' + rowIdx).val(), 
					$('#state-' + rowIdx).val(),
					$('#i-type-' + rowIdx).val(), 
					$('#sys-source-'+ rowIdx).val(), 
					$('#msg-ty-source-'+ rowIdx).val(), 
					$('#sys-target-'+ rowIdx).val(), 
					$('#msg-ty-target-'+ rowIdx).val());
		} else {
			action = 'update';
			dataParam = fnEventCompoundUpdate(
					$('#event-base-' + rowIdx).val(), 
					$('#group-' + rowIdx).val(), 
					$('#event-name-'+ rowIdx).val(), 
					$('#owner-' + rowIdx).val(), 
					$('#state-' + rowIdx).val(),
					$('#i-type-' + rowIdx).val(), 
					$('#sys-source-'+ rowIdx).val(), 
					$('#msg-ty-source-'+ rowIdx).val(), 
					$('#sys-target-'+ rowIdx).val(), 
					$('#msg-ty-target-'+ rowIdx).val());
		}
		$.ajax({
			type : "POST",
			url : "../dml/execute/" + action+ "/event-" + action+ ".do",
			data : dataParam,
			contentType : "application/json",
			dataType : "json",
			async : true,
			success : function(respond) {
				alert((respond.message));
				if(respond.message==='Succes '+action)dt_event.ajax.reload();
				
			},
			error : function(err) {
				alert(err.message);
			}
		});
		
	});
	
	$('#dt-event tbody').on('click','button.act-delete',function() {

		var rowIdx = this.value;
		$('#event-name-' + rowIdx).val();
		$('#event-base-' + rowIdx).val();
		$('#i-type-' + rowIdx).val();
		$('#group-' + rowIdx).val();
		$('#sys-source-' + rowIdx).val();
		$('#sys-target-' + rowIdx).val();
		$('#owner-' + rowIdx).val();
		$('#msg-ty-source-' + rowIdx).val();
		$('#msg-ty-target-' + rowIdx).val();
		$('#state-' + rowIdx).val();
		
		var data = fnEventCompoundDelete($('#event-base-' + rowIdx).val(),
				$('#group-' + rowIdx).val(),
				$('#event-name-' + rowIdx).val(), 
				$('#owner-' + rowIdx).val(), 
				$('#state-' + rowIdx).val(), 
				$('#i-type-' + rowIdx).val(),
				$('#sys-source-' + rowIdx).val(),
				$('#msg-ty-source-' + rowIdx).val(),
				$('#sys-target-' + rowIdx).val(), 
				$('#msg-ty-target-'+ rowIdx).val()
		);

		var answer = confirm("Do you want to Delete This Record "+ $('#event-name-' + rowIdx).val() + " ?")
		if (answer) {
			$.ajax({
				type : "POST",
				url : "../dml/execute/delete/event-delete.do",
				data : data,
				contentType : "application/json",
				dataType : "json",
				async : true,
				success : function(respond) {
					alert((respond.message));
					if(respond.message==='Succes delete')dt_event.ajax.reload();
				},
				error : function(err) {
					alert(err.message);
				}
			});
		}
	});
	
	$('#addData').click(function() {
		var currentPage = dt_event.page();
		var currentRow = dt_event.page.info();
		
		// insert a test row
		dt_event.row.add({
			"event_name" : null,
			"event_base" : null,
			"event_group" : null,
			"event_owner" : null,
			"event_status" : 0,
			"integration_type" : 0,
			"system_source" : null,
			"system_source_message_type" : null,
			"system_target" : null,
			"system_target_message_type" : null
		}).draw();
		// move added row to desired index (here
		// the row we clicked on)
		var index = dt_event.row(this).index(), rowCount = dt_event.data().length - 1, 
		insertedRow = dt_event.row(rowCount).data(), tempRow;

		for (var i = rowCount; i > index; i--) {
			tempRow = dt_event.row(i - 1).data();
			dt_event.row(i).data(tempRow);
			dt_event.row(i - 1).data(insertedRow);
		}
		// refresh the page
		dt_event.page(currentRow.pages).draw();
		var oTable = $('#dt-event').dataTable();
		oTable.fnPageChange('last');
	});
	
	$('#btnSearchEvent').on('click', function(e) {
		dt_event.ajax.reload();
		toastr.info("Queried Done!");
	});
	
	$('#re-query').on('click', function(e) {
		$('#eventName').val("");
		$('#eventBase').val("");
		$('#owner-name').val('').trigger('chosen:updated');
		$('#group-name').val('').trigger('chosen:updated');
		$('#sys-source').val("");
		$('#sys-target').val("");
		dt_event.ajax.reload();
		toastr.info("Refresh cleared!");
	});
	
	fnInitiateCbxReference("owner", "owner-name");
	fnInitiateCbxReference("group", "group-name");
	
});
/* END OF READY DOCUMENTS */

function fnRenderIntegrationType(type) {
	var result = '';
	if (type == 1) {
		result = 'Async';
	} else if (type == 2) {
		result = 'Sync';
	} else if (type == 3) {
		result = 'Async-Sync';
	} else if (type == 4) {
		result = 'Sync-Async';
	}

	return result;
}
function fnRenderState(type) {
	var result = 'UNIDENTIFIED';
	if (type == 1) {
		result = 'ACTIVE';
	} else if (type == 0) {
		result = 'INACTIVE';
	}
	return result;
}
function fnUpdateFormRender(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Event Names</td>'
			+ '	<td><input type="text" id="event-name-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'
			+ '	<td>Event Base</td>'
			+ '	<td><select class="form-control input-sm" id="event-base-'
			+ rowId
			+ '">'
			+ '	</select></td>'
			+ '	<td>I-Type</td>'
			+ '	<td><select class="form-control input-sm" id="i-type-'
			+ rowId
			+ '">'
			+ '			<option value="1">Async</option>'
			+ '			<option value="2">Sync</option>'
			+ '			<option value="3">Async-Sync</option>'
			+ '			<option value="4">Sync-Async</option>'
			+ '	</select></td>'
			+ '	<td>System Source</td>'
			+ '	<td><input type="text" id="sys-source-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			+ '	<td>System Target</td>'
			+ '	<td><input type="text" id="sys-target-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			+ '</tr>'
			+ '<tr>'
			+ '	<td>State</td>'
			+ '	<td><select class="form-control input-sm" id="state-'
			+ rowId
			+ '">'
			+ '			<option value="1">ACTIVE</option>'
			+ '			<option value="0">INACTIVE</option>'
			+ '	</select></td>'
			+ '	<td>Owner</td>'
			+ '	<td><select class="form-control input-sm" id="owner-'
			+ rowId
			+ '">'
			+ '	</select></td>'
			+ '	<td>Group</td>'

			+ '	<td><select class="form-control input-sm" id="group-'
			+ rowId
			+ '">'
			+ '	</select></td>'

			+ '	<td>Message Type</td>'
			+ '	<td><input type="text" id="msg-ty-source-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			+ '	<td>Message Type</td>'
			+ '	<td><input type="text" id="msg-ty-target-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			+ '</tr>'
			+ '<tr>'
			+ '	<td></td><td></td><td></td><td></td>'
			+ '	<td></td><td></td><td></td><td></td>'
			+ '	<td></td>'
			+ '	<td><button type="button" value="'
			+ rowId
			+ '" class="btn btn-primary btn-sm act-update">'
			+ '			<i class="fa fa-floppy-o"></i>'
			+ '		</button>'
			+ '		<button type="button" value="'
			+ rowId
			+ '" class="btn btn-warning btn-sm act-delete">'
			+ '			<i class="fa fa-times"></i>'
			+ '		</button></td>'
			+ '</tr>'
			+ '</table>';
	return table;
}

function fnEventCompoundUpdate(eventBase, eventGroup, eventName, eventOwner,
		eventStatus, integrationType, systemSource, systemSourceMessageType,
		systemTarget, systemTargetMessageType) {

	var qparam = JSON.stringify([ eventBase, eventGroup, eventOwner,
	                              eventStatus, integrationType, systemSource,
	                              systemSourceMessageType, systemTarget, systemTargetMessageType,
	                              eventName ]);
	console.log(qparam);
	return qparam;
}

function fnEventCompoundInsert(eventBase, eventGroup, eventName, eventOwner,
		eventStatus, integrationType, systemSource, systemSourceMessageType,
		systemTarget, systemTargetMessageType) {

	var qparam = JSON.stringify([ eventBase, eventGroup, eventName, eventOwner,
	                              eventStatus, integrationType, systemSource,
	                              systemSourceMessageType, systemTarget, systemTargetMessageType ]);
	console.log("fnEventCompoundInsert :" + qparam);
	return qparam;
}

function fnEventCompoundDelete(eventBase, eventGroup, eventName, eventOwner,
		eventStatus, integrationType, systemSource, systemSourceMessageType,
		systemTarget, systemTargetMessageType) {

	var qparam = JSON.stringify([ eventName ]);
	console.log(qparam);
	return qparam;
}

function fnInitiateCbxReference(key, cbxId) {
	var param = JSON.stringify([]);
	if (key === 'group')
		param = JSON.stringify([ "%EVENT-BOUNDARY%" ]);
	$.ajax({
		type : "POST",
		url : "../dautl/retrieve/json/" + key + ".do",
		data : param,
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

function fnInitiateCbxReferenceForm(key, cbxId, value) {
	var param = JSON.stringify([]);
	if (key === 'group')
		param = JSON.stringify([ "%EVENT-BOUNDARY%" ]);
	$.ajax({
		type : "POST",
		url : "../dautl/retrieve/json/" + key + ".do",
		data : param,
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
			$('#' + cbxId).val(value);

		},
		error : function(msg) {
			toastr.error("Context failed to Load, no reference of " + cbxId
					+ " available");
		}
	});
}

function fnInitiateCbxReferenceFormEvent(key, cbxId, value, evenName) {
	var param = JSON.stringify([]);
	if (key === 'group')
		param = JSON.stringify([ "%EVENT-BOUNDARY%" ]);
	$.ajax({
		type : "POST",
		url : "../dautl/retrieve/json/" + key + ".do",
		data : param,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			var objSelect = document.getElementById(cbxId);
			$.each(respond.payload, function(i, row) {
				if (evenName != row.key) {
					opt = document.createElement("option");
					opt.value = row.key;
					opt.text = row.key;
					objSelect.appendChild(opt);
				}

			});
			$('#' + cbxId).val(value);

		},
		error : function(msg) {
			toastr.error("Context failed to Load, no reference of " + cbxId
					+ " available");
		}
	});
}

function fnCriteriasCompound() {
	var eventNameId = '';
	var eventBaseId = '';
	var ownerName = '';
	var groupName = '';
	var sysSource = '';
	var sysTarget = '';

	var arr, i, temp;

	if ($('#eventName').val() != '') {
		eventNameId = $('#eventName').val();
	}

	if ($('#eventBase').val() != '') {
		eventBaseId = $('#eventBase').val();
	}

	$('#owner-name').trigger('chosen:updated');
	if ($('#owner-name > :selected').length > 0) {
		i = 0;
		arr = $('#owner-name').val();
		$('#owner-name > :selected').each(function() {
			ownerName += arr[i];
			if (i < arr.length - 1)
				ownerName += ",";
			i++;
		});
	}

	if ($('#group-name > :selected').length > 0) {
		i = 0;
		arr = $('#group-name').val();
		$('#group-name > :selected').each(function() {
			groupName += arr[i];
			if (i < arr.length - 1)
				groupName += ",";
			i++;
		});
	}

	if ($('#sys-source').val() != '') {
		sysSource = $('#sys-source').val();
	}

	if ($('#sys-target').val() != '') {
		sysTarget = $('#sys-target').val();
	}

	var qparam = JSON.stringify([ eventNameId, eventBaseId, ownerName,
			groupName, sysSource, sysTarget ]);
	console.log(qparam);
	return qparam;
}
