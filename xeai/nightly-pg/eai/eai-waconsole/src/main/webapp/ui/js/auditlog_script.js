$(document)
		.ready(
				function() {

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
					$('.chosen-container.chosen-container-multi').css('width',
							'396px');

					var dt_logs = $('#audit-log')
							.DataTable(
									{
										"bAutoWidth" : false,
										"bProcessing" : false,
										"bServerSide" : false,
										"sAjaxDataProp" : "payload",
										"bPaginate" : false,
										"bInfo" : false,
										"bFilter" : false,
										"bLengthChange" : false,
										"order" : [ [ 0, "desc" ],
												[ 4, "desc" ], [ 2, "desc" ] ],
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 3, 4, 5, 6, 7 ]
										} ],
										"sAjaxSource" : "../dautl/extract/rows/auditlog.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var key = "auditlog";

											var qparam = fnCriteriasCompound();

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/"
																+ key + ".do",
														"data" : qparam,
														"contentType" : "application/json",
														"dataType" : "json",
														"success" : fnCallback,
														error : function(msg) {
															var data = {
																"iTotalRecords" : 0,
																"iTotalDisplayRecords" : 0,
																"aData" : [ {
																	"xeai_id" : "",
																	"correlation_id" : "",
																	"audit_type" : "",
																	"event_name" : "",
																	"audit_time" : "",
																	"payload" : ",",
																	"audit_param" : "",
																	"severity" : "",
																	"endpoint" : ""
																} ]
															};
															return JSON
																	.stringify(data);
														}
													});
										},
										"aoColumns" : [
												{
													mData : "xeai_id"
												},
												{
													sClass : "bold-hlight",
													mData : "correlation_id"
												},
												{
													sClass : "payload-detail",
													mData : "audit_type"
												},
												{
													mData : "event_name"
												},
												{
													mData : "audit_time",
													sClass : "absolute"
												},
												{
													mData : "audit_param",
													mRender : function(data,
															type, full) {
														return fnTextAreaRendering(
																data, 15, 15, 2);
													}
												},
												{
													mData : "severity",
													mRender : function(data,
															type, full) {
														return fnTextAreaRendering(
																data, 15, 15, 2);
													}
												},
												{
													mData : "endpoint",
													mRender : function(data,
															type, full) {
														return fnTextAreaRendering(
																data, 15, 15, 2);
													}
												} ],
										"fnRowCallback" : function(nRow, aData,
												iDisplayIndex) {
											/*
											 * numbers less than or equal to 0
											 * should be in red text
											 */

											if (aData.correlation_id != null
													&& aData.correlation_id != ''
													&& aData.iexeai_id != null
													&& aData.iexeai_id != '') {
												jQuery('td', nRow).addClass(
														'danger-hlight');
												$('td:eq(0)', nRow)
														.html(
																fnAsLink(
																		aData.iexeai_id,
																		'danger-detail'));

											} else if (aData.correlation_id != null
													&& aData.correlation_id != ''
													&& aData.severity != null
													&& aData.severity != '')
												jQuery('td', nRow).addClass(
														'warning-hlight');
											return nRow;
										}

									});

					$('#audit-log tbody')
							.on(
									'click',
									'td.payload-detail',
									function() {
										var cell = dt_logs.cell(this);
										var iRow = cell.index().row;
										var iColumn = cell.index().column;
										var data = cell.data();

										var rowData = dt_logs.row(
												cell.index().row).data();

										console.log("column-index: [" + iRow
												+ "," + iColumn + "], value: "
												+ cell.data());
										console.log(rowData);

										var temp;
										if (data.indexOf("</textarea>") == -1) {

											var qparam = JSON.stringify([
													rowData.xeai_id,
													rowData.event_name,
													rowData.audit_type ]);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/retrieve/json/audilogpayload.do",
														"data" : qparam,
														"contentType" : "application/json",
														"dataType" : "json",
														"success" : function(
																respond) {
															console
																	.log(respond);
															temp = data
																	+ "<br><textarea cols='15' rows='2' class='normal-hlight' >"
																	+ respond.payload.payload
																	+ "</textarea>";
															cell.data(temp)
																	.draw();
														},
														"error" : function(msg) {
															toastr
																	.error("Retrive payload FAILED!");
														}
													});

										}

									});

					$('#audit-log tbody')
							.on(
									'click',
									'a.danger-detail',
									function() {
										var tr = $(this).closest('tr');
										var row = dt_logs.row(tr);

										if (row.child.isShown()) {
											// This row is already open - close
											// it
											row.child.hide();
											tr.removeClass('shown');
										} else {
											// Open this row
											var rowIdx = row.index();
											var rowData = row.data();

											var qparam = JSON.stringify([
													rowData.xeai_id,
													rowData.event_name ]);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/retrieve/json/auditerrorlogcause.do",
														"data" : qparam,
														"contentType" : "application/json",
														"dataType" : "json",
														"success" : function(
																respond) {
															row
																	.child(
																			fnDangerDetailRender(respond.payload.error_detail))
																	.show();
															tr
																	.addClass('shown');
														},
														"error" : function(msg) {
															toastr
																	.error("Retrive payload FAILED!");
														}
													});

										}
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
						$('#correlation-id').val("");
						$('#audit-type').val('').trigger('chosen:updated');
						$('#event-name').val('').trigger('chosen:updated');
						$('#audit-date-start').val("");
						$('#audit-time-start').val("");
						$('#audit-date-end').val("");
						$('#audit-time-end').val("");
						$('#endpoint').val("");
						$('#payload').val("");
						$('#onnull-correlation').prop('checked', false);
						dt_logs.ajax.reload();
						toastr.info("Query cleared!");
					});

					// cbxevents_name
					fnInitiateCbxReference("cbxevents_name", "event-name");
					fnInitiateCbxReference("cbxauditlogconfig_name",
							"audit-type");
					
					$('#jump-to-error').on('click',function(e){
						jumpToErrorLog();
					});

				});
// END OF DOCUMENT READY
function fnTextAreaRendering(data, minLength, cols, rows) {
	if (data == null || data == '')
		return data;
	else if (data.length <= minLength)
		return data;
	else {
		var result = "<textarea style='color: inherit;' cols='" + cols
				+ "' rows='" + rows + "' class='normal-hlight' >" + data
				+ "</textarea>";
		return result;
	}

}

function fnCriteriasCompound() {
	var xeaiId = '';
	var correlationId = '';
	var eventName = '';
	var auditType = '';
	var auditTimeStart = '';
	var auditTimeEnd = '';
	var endpoint = '';
	var payload = '';
	var nullCorrelation = '';

	var arr, i, temp;

	$('#lbl-xeai-id').html("");
	if ($('#xeai-id').val() != '') {
		xeaiId = $('#xeai-id').val();
		$('#lbl-xeai-id').html("XEAI-ID eq {" + xeaiId + "}");
	}

	if ($('#correlation-id').val() != '') {
		correlationId = $('#correlation-id').val();
		temp = "CORRELATION-ID eq {" + correlationId + "}";
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

	$('#lbl-audit-type').html("");
	if ($('#audit-type > :selected').length > 0) {
		i = 0;
		arr = $('#audit-type').val();
		$('#audit-type > :selected').each(function() {
			auditType += arr[i];
			if (i < arr.length - 1)
				auditType += ",";
			i++;
		});
		$('#lbl-audit-type').html("AUDIT-TYPE in {" + auditType + "}");
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
		auditTimeEnd = moment().add(1, 'minutes').format("DD-MM-YYYY HH:mm");
		temp = " AUDIT-TIME is { before " + auditTimeEnd + "}"
		$('#lbl-audit-time').html(temp);
	}

	$('#lbl-endpoint').html("");
	if ($('#endpoint').val() != '') {
		endpoint = $('#endpoint').val();
		$('#lbl-endpoint').html("ENDPOINT like {" + endpoint + "}");
	}

	$('#lbl-payload').html("");
	if ($('#payload').val() != '') {
		payload = $('#payload').val();
		$('#lbl-payload').html("PAYLOAD like {" + payload + "}");
	}

	$('#lbl-correlation-id').html("");
	if ($('#onnull-correlation').prop('checked')) {
		nullCorrelation = true;

		if (correlationId != '') {
			temp = "CORRELATION-ID with {" + correlationId
					+ " and null incorrelation}";
		} else {
			temp = "CORRELATION-ID with {null incorrelation}";
		}
		$('#lbl-correlation-id').html(temp);
	}

	var qparam = JSON.stringify([ xeaiId, correlationId, eventName, auditType,
			auditTimeStart, auditTimeEnd, endpoint, payload, nullCorrelation ]);
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

function fnDangerDetailRender(description) {
	return '<textarea rows="10" class="danger-hlight form-control" style="width: 100%; font-size: 90%; margin:3px; ">'
			+ description + '</textarea>';

}

function jumpToErrorLog(){
	
	var xeaiIdTemp = $('#xeai-id').val();
	var eventNameTemp = '';
	$('#event-name').trigger('chosen:updated');
	if ($('#event-name > :selected').length > 0) {
		i = 0;
		arr = $('#event-name').val();
		$('#event-name > :selected').each(function() {
			eventNameTemp += arr[i];
			if (i < arr.length - 1)
				eventNameTemp += ",";
			i++;
		});
	}
	
	var dateStartTemp = $('#audit-date-start').val();
	var timeStartTemp = $('#audit-time-start').val();
	var dateEndTemp = $('#audit-date-end').val();
	var timeEndTemp = $('#audit-time-end').val();
	
	window.location.href = '../auditerrorlog/home.do?xeaiIdTemp='+xeaiIdTemp
	+'&eventNameTemp='+eventNameTemp
	+'&dateStartTemp='+dateStartTemp
	+'&timeStartTemp='+timeStartTemp
	+'&dateEndTemp='+dateEndTemp
	+'&timeEndTemp='+timeEndTemp;
}