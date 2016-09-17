$(document)
		.ready(
				function() {

					/* */

					var dt_owner = $('#tbl-owner')
							.DataTable(
									{
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
										"sAjaxSource" : "../dautl/extract/rows/owner.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var qparam = JSON.stringify([ "",
													oSettings._iDisplayLength,
													oSettings._iDisplayStart ]);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/owner.do",
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
													mData : "name",
													mRender : function(data,
															type, full) {
														return '<a class="owner-detail-flcontainer">'
																+ data + '</a>';
													}
												},
												{
													mData : "name",
													mRender : function(data,
															type, full) {
														return 'Active';
													}
												} ],
										"fnInitComplete" : function(oSettings,
												json) {
											$(
													'#tbl-owner tbody a.owner-detail-flcontainer:eq(0)')
													.click();
											console
													.log('end of fnInitComplete owner');
										}

									});

					var dt_flcontainer = $('#tbl-flcontainer')
							.DataTable(
									{
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
										"deferLoading" : 0,
										"sAjaxSource" : "../dautl/extract/rows/flcontainer.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											console
													.log("dt_flcontainer reload");

											var tempOwner = $('#tempif-owner')
													.val();
											var qparam = JSON.stringify([
													tempOwner, "", "",
													oSettings._iDisplayLength,
													oSettings._iDisplayStart ]);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/flcontainer.do",
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
													mData : "container_flname",
													mRender : function(data,
															type, full) {
														return '<a class="flcontainer-detail-buscontext">'
																+ data + '</a>';
													}
												}, {
													mData : "container_flgroup"
												} ],
										"fnInitComplete" : function(oSettings,
												json) {

											$(
													'#tbl-flcontainer tbody a.flcontainer-detail-buscontext:eq(0)')
													.click();
											console
													.log('end of fnInitComplete flcontainer');
										}

									});

					var dt_buscontext = $('#tbl-buscontext')
							.DataTable(
									{
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
										"pageLength" : 25,
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2 ]
										} ],
										"deferLoading" : 0,
										"sAjaxSource" : "../dautl/extract/rows/buscontext.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var params = '-';
											if ($('#tempif-flcontainer').val() != '-')
												params = JSON.parse($(
														'#tempif-flcontainer')
														.val()).container_bus;

											var qparam = JSON.stringify([
													params, '',
													oSettings._iDisplayLength,
													oSettings._iDisplayStart ]);
											console.log(qparam);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/buscontext.do",
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
													mData : "context_alias",
													mRender : function(data,
															type, full) {
														return '<a class="buscontext-detail-bus">'
																+ data + '</a>';
													}
												}, {
													mData : "context_sequence"
												}, {
													mData : "context_level"
												} ],
										"fnInitComplete" : function(oSettings,
												json) {
											console
													.log('end of fnInitComplete buscontext');
										}

									});

					var rows_dt_component = 0;
					var dt_component = $('#tbl-component')
							.DataTable(
									{

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
										"pageLength" : 20,
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2 ]
										} ],
										"sAjaxSource" : "../dautl/extract/rows/buses.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var qparam = JSON.stringify([ '-',
													'-',
													oSettings._iDisplayLength,
													oSettings._iDisplayStart ]), key = 'buscontext';

											if ($('#tempif-context').val() != '-') {
												key = "buses";
												var params = JSON.parse($(
														'#tempif-context')
														.val());
												var tempLv = params.context_level;
												var tempContext = params.context_code;

												qparam = JSON
														.stringify([
																'',
																tempContext,
																"",
																oSettings._iDisplayLength,
																oSettings._iDisplayStart ]);
											} else if ($('#tempif-flcontainer')
													.val() != '-') {

												key = "buscontext";

												var params = JSON.parse($(
														'#tempif-flcontainer')
														.val());

												qparam = JSON
														.stringify([
																params.container_bus,
																'BINDERKEY',
																oSettings._iDisplayLength,
																oSettings._iDisplayStart ]);

											} else if ($('#tempif-flcontainer')
													.val() == '-') {

												key = "buses-olayer";

												var params = $('#tempif-owner')
														.val();

												qparam = JSON
														.stringify([
																params,
																params,
																oSettings._iDisplayLength,
																oSettings._iDisplayStart ]);

											}
											rows_dt_component = oSettings._iDisplayStart;

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/"
																+ key + ".do",
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
															console.log(result);

														}
													});
										},
										"aoColumns" : [
												{
													mData : "identifier",
													mRender : function(data,
															type, full) {
														return '';
													}
												},
												{
													mData : "identifier",
													mRender : function(data,
															type, full) {
														return '<a class="bus-act-control">'
																+ data + '</a>';
													}
												}, {
													mData : "referral"
												} ],
										"fnInitComplete" : function(oSettings,
												json) {
											console
													.log('end of fnInitComplete buses');
										},
										"fnRowCallback" : function(nRow, aData,
												iDisplayIndex,
												iDisplayIndexFull) {
											var index = rows_dt_component
													+ iDisplayIndexFull + 1;
											$('td:eq(0)', nRow).html(index);
											return nRow;
										}

									});

					var dt_responsebucontrol = $('#tbl-response-buscontrol')
							.DataTable({
								"bPaginate" : true,
								"pagingType" : "simple",
								"bInfo" : false,
								"bLengthChange" : false,
								"bAutoWidth" : false,
								"bInfo" : false,
								"bFilter" : false,
								"order" : [],
								"pageLength" : 5,
								"aoColumns" : [ {
									mData : "name"
								}, {
									mData : "value"
								} ]
							});

					/* DataTable definition END! */

					$('#tbl-owner tbody').on(
							'click',
							'a.owner-detail-flcontainer',
							function() {
								console.log("owner-detail-owner clicked! ");
								var tr = $(this).closest('tr');
								var row = dt_owner.row(tr);

								// Open this row
								var rowIdx = row.index();
								var data = row.data();

								$('#tempif-owner').val(data.name);
								dt_flcontainer.ajax.reload();

								if (!$(tr).hasClass('active')) {
									dt_owner.$('tr.active').removeClass(
											'active');
									$(tr).addClass('active');

									$('#tempif-flcontainer').val("-");
									dt_buscontext.ajax.reload();
									$('#tempif-context').val("-");

								}
							});

					$('#tbl-flcontainer tbody')
							.on(
									'click',
									'a.flcontainer-detail-buscontext',
									function() {

										console
												.log("flcontainer-detail-flcontainer clicked! ");
										var tr = $(this).closest('tr');
										var row = dt_flcontainer.row(tr);

										// Open this row
										var rowIdx = row.index();
										var data = row.data();

										if ($(tr).hasClass('active')) {
											dt_flcontainer.$('tr.active')
													.removeClass('active');
											$('#tempif-flcontainer').val("-");
										} else {
											dt_flcontainer.$('tr.active')
													.removeClass('active');
											$(tr).addClass('active');

											$('#tempif-flcontainer').val(
													JSON.stringify(data));
										}
										dt_buscontext.ajax.reload();
										$('#tempif-context').val("-");

									});

					$('#tbl-buscontext tbody').on(
							'click',
							'a.buscontext-detail-bus',
							function() {
								var tr = $(this).closest('tr');
								var row = dt_buscontext.row(tr);

								// Open this row
								var rowIdx = row.index();
								var data = row.data();

								if ($(tr).hasClass('active')) {
									dt_buscontext.$('tr.active').removeClass(
											'active');
									$('#tempif-context').val("-");
								} else {
									dt_buscontext.$('tr.active').removeClass(
											'active');
									$(tr).addClass('active');
									$('#tempif-context').val(
											JSON.stringify(data));
								}

							});

					$('#tbl-component tbody')
							.on(
									'click',
									'a.bus-act-control',
									function() {
										var tr = $(this).closest('tr');
										var row = dt_component.row(tr);

										// Open this row
										var rowIdx = row.index();
										var data = row.data();

										$('#tempif-bus').val(
												JSON.stringify(data));

										if (!$(tr).hasClass('active')) {
											dt_component.$('tr.active')
													.removeClass('active');
											$(tr).addClass('active');
											if ($('#tempif-context').val() != '-'
													|| ($('#tempif-context')
															.val() == '-' && ($(
															'#tempif-flcontainer')
															.val() == '-'))) {
												fnInitiateCbxReference(
														'cipherrule-nonl',
														'if-selected-rule',
														JSON
																.stringify([
																		data.xeai_bus_cipher,
																		'',
																		'stance-opt' ]));

												$('#if-selected-bus').html(
														data.xeai_bus_name);
												$('#if-selected-cipher').html(
														data.xeai_bus_cipher);
												$('#if-selected-type').html(
														data.xeai_bus_group);

											} else if ($('#tempif-flcontainer')
													.val() != '-') {

												if ($('#if-selected-rule')
														.val().includes(
																'binderValue')) {
													$('#if-selected-operand')
															.val(
																	fnRenderParameter(
																			$(
																					'#if-selected-rule')
																					.val())
																			.replace(
																					'binderValue',
																					data.context_alias));
												}

											}

										}

									});

					/* DataTable action END! */

					$('#act-bus-load')
							.click(
									function() {
										fnResetDialog();
										if ($('#tempif-context').val() != '-'
												|| ($('#tempif-context').val() == '-' && ($(
														'#tempif-flcontainer')
														.val() == '-'))) {
											fnAdjustDialog(true);
											fnReinitializedCbxReference('if-selected-rule');
										} else if ($('#tempif-flcontainer')
												.val() != '-') {

											var data = JSON.parse($(
													'#tempif-flcontainer')
													.val());
											if (data.xeai_bus_cipher == 'archcomp.RepressorImpl'
													|| data.xeai_bus_cipher == 'archcomp.ActivatorImpl') {
												$('#tempif-bus').val(
														JSON.stringify(data));
												fnAdjustDialog(false);
											} else {
												fnAdjustDialog(true);
											}

											$('#if-selected-bus').html(
													data.xeai_bus_name);
											$('#if-selected-cipher').html(
													data.xeai_bus_cipher);
											$('#if-selected-type').html(
													data.xeai_bus_group);

											fnInitiateCbxReference(
													'cipherrule-nonl',
													'if-selected-rule',
													JSON
															.stringify([
																	data.xeai_bus_cipher,
																	'domain-opt,stance-opt',
																	'' ]));
										}

									});

					$("#if-selected-rule")
							.change(
									function() {
										if (this.value == null
												|| this.value == "null")
											$("#if-selected-operand").val("");
										else {

											if ($('#tempif-context').val() != '-') {

											} else if ($('#tempif-flcontainer')
													.val() != '-') {
												if (this.value != '') {
													$('#if-selected-operand')
															.val(
																	fnRenderParameter(this.value));
												} else {
													$('#if-selected-operand')
															.val('');
												}

											}

										}

									});

					$("#act-callcommand")
							.click(
									function() {
										var databus, datacontext, dataflcontainer, buscontrol, module = "", referral = "", container = "", action = "", aparameter = "", instance = "", command = "", cparameters = ""

										databus = JSON.parse($('#tempif-bus')
												.val());

										module = $('#tempif-owner').val();

										if ($('#tempif-flcontainer').val() == '-'
												&& $('#tempif-context').val() == '-') {
											referral = "LAYER";
											instance = databus.xeai_bus_name;
											command = $(
													'#if-selected-rule  option:selected')
													.text();
											cparameters = $(
													'#if-selected-operand')
													.val();
										} else if ($('#tempif-flcontainer')
												.val() != '-'
												&& $('#tempif-context').val() == '-') {
											referral = "LAYER";
											instance = $('#if-selected-bus')
													.html();
											command = $(
													'#if-selected-rule  option:selected')
													.text();
											cparameters = $(
													'#if-selected-operand')
													.val();
										} else if ($('#tempif-flcontainer')
												.val() != '-'
												&& $('#tempif-context').val() != '-') {

											datacontext = JSON.parse($(
													'#tempif-context').val());

											dataflcontainer = JSON.parse($(
													'#tempif-flcontainer')
													.val());

											if (datacontext.context_level == 'BASE') {
												action = "inject('?')";
												aparameter = "?=?";
											} else if (datacontext.context_level == 'BINDERKEY') {
												action = "inject('?','?')";
												aparameter = "?="
														+ datacontext.context_alias
														+ ";?=?";
											}
											instance = $('#if-selected-bus')
													.html();
											referral = datacontext.context_level;
											container = dataflcontainer.xeai_bus_name;
											command = $(
													'#if-selected-rule option:selected')
													.text();
											cparameters = $(
													'#if-selected-operand')
													.val();

										}

										if (command == 'CUSTOM') {
											command = '';
										} else {
											buscontrol = fnGetControls(module,
													referral, container,
													action, aparameter,
													instance, command,
													cparameters);

											console.log(buscontrol);
										}

										$
												.ajax({
													type : "POST",
													url : "../default/control/receiver.do",
													data : buscontrol,
													contentType: "text/plain",
													dataType : "text",
													async : true,
													success : function(respond) {
														console.log(respond);
														dt_responsebucontrol.clear().draw();
														dt_responsebucontrol.rows
																.add(JSON.parse(respond))
																.draw();
													},
													error : function(msg) {
														console.log(msg);
													}
												});

									});

					function fnAdjustDialog(withDetailTable) {
						if (withDetailTable) {
							$('#cnt-controlcomponent').removeClass('col-sm-12');
							$('#cnt-controlcomponent').addClass('col-sm-8');
							$('#cnt-dialogcomponent').css('width', '');
							$('#cnt-dialogcomponent').css('width', '80%');
							$('#cnt-defcomponent').show();
							$('#mdl-act-component').modal('show');
							dt_component.ajax.reload();
						} else {
							$('#cnt-controlcomponent').removeClass('col-sm-8');
							$('#cnt-controlcomponent').addClass('col-sm-12');
							$('#cnt-dialogcomponent').css('width', '');
							$('#cnt-dialogcomponent').css('width', '50%');
							$('#cnt-defcomponent').hide();

							$('#mdl-act-component').modal('show');
						}
					}

					function fnResetDialog() {
						$('#tempif-bus').val('-');
						$('#if-selected-bus').html('No component selected');
						$('#if-selected-cipher').html(
								'Cipher is not identified');
						$('#if-selected-type').html('Type is not identified');
					}

				});

function fnInitiateCbxReference(key, cbxId, params) {
	$.ajax({
		type : "POST",
		url : "../dautl/extract/rows/" + key + ".do",
		data : params,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			document.getElementById(cbxId).innerHTML = '';
			var objSelect = document.getElementById(cbxId);
			fnReinitializedCbxReference(cbxId);
			$.each(respond.payload, function(i, row) {
				opt = document.createElement("option");
				opt.value = row.xeai_cipher_parameters == 'null' ? ''
						: row.xeai_cipher_parameters;
				opt.text = row.xeai_cipher;
				objSelect.appendChild(opt);
			});

		},
		error : function(msg) {
			toastr.error("Context failed to Load, no reference of " + cbxId
					+ " available");
		}
	});

}
function fnReinitializedCbxReference(cbxId) {
	document.getElementById(cbxId).innerHTML = '';
	var objSelect = document.getElementById(cbxId);
	opt = document.createElement("option");
	opt.value = "";
	opt.text = "CUSTOM";
	objSelect.appendChild(opt);
}

function fnRenderParameter(template) {
	if (template != '') {
		var arr = template.split(',');
		var temp = "";
		for (var i = 0; i < arr.length; i++) {
			temp += i + "=" + arr[i].trim() + ";";
		}
		return temp.trim().replace(' ', ', ');
	} else {
		return template;
	}

}
