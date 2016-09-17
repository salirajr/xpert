$(document)
		.ready(
				function() {

					var tempModule = '-';
					var tempFlow = '-';
					var tempMasterPath = '-';

					// CONTANT NEW CONTEXT NAME
					var CNST_NWCTXNAME = 'newcreate-context.xml';

					// CONTANT NEW CONTEXT NAME
					var CNST_NWALIAS = 'NEW ALIAS';

					var dt_flowlogic = $('#tbl-flowlogic')
							.DataTable(
									{
										"bPaginate" : true,
										"bInfo" : true,
										"bLengthChange" : false,
										"bAutoWidth" : false,
										"processing" : true,
										"bServerSide" : true,
										"bInfo" : false,
										"bFilter" : false,
										"order" : [],
										"bServerSide" : true,
										"pageLength" : 10,
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2 ]
										} ],
										"sAjaxSource" : "../dautl/extract/rows/flowlogics.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var qparam = JSON.stringify([ "",
													oSettings._iDisplayLength,
													oSettings._iDisplayStart ]);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/flowlogics.do",
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
													mData : "container_flgroup",
													mRender : function(data,
															type, full) {
														return fnAsLink(
																"flowlogic-flname",
																data);
													}
												},
												{
													mData : "agg_busgroup",
													mRender : function(data,
															type, full) {
														return fnBusnotationRender(data);
													}
												}, {
													mData : "container_owner"
												} ],
										"fnInitComplete" : function(oSettings,
												json) {

											$(
													'#tbl-flowlogic tbody a.flowlogic-flname:eq(0)')
													.click();

										}

									});

					$('#tbl-flowlogic tbody')
							.on(
									'click',
									'a.flowlogic-flname',
									function() {
										var tr = $(this).closest('tr');

										if (!$(tr).hasClass('active')) {
											dt_flowlogic.$('tr.active')
													.removeClass('active');
											$(tr).addClass('active');

											var row = dt_flowlogic.row(tr);

											// Open this row
											var rowIdx = row.index();
											var data = row.data();

											$('#tempif-flname').val(
													JSON.stringify(data));

											$('#lb-moduleflow')
													.html(
															data.container_owner
																	+ ":"
																	+ data.container_flgroup);

											var ttempModule = tempModule;
											var ttempFlow = tempFlow;
											tempModule = data.container_owner;
											tempFlow = data.container_flgroup;
											
											console.log(data.container_owner+" "+ttempModule+" "+tempModule);

											if (ttempModule != tempModule) {
												$
														.ajax({
															"type" : "POST",
															"url" : "../dautl/retrieve/json/ownerdefinition.do",
															"contentType" : "application/json",
															"dataType" : "json",
															"data" : JSON
																	.stringify([ data.container_owner ]),
															"sAjaxDataProp" : "payload",
															"success" : function(
																	response) {
																console.log(response);
																tempMasterPath = response.payload.domain;
																console.log(tempMasterPath);
																fnInitiateFlow();

															}
														});
											}

											dt_context_flcontainer.ajax
													.reload();

										}
									});

					function fnInitiateFlow() {
						$("#msgtools-jtree").jstree("destroy");
						$('#msgtools-jtree').jstree({
							'core' : {
								"data" : [ {
									"id" : "j1_1",
									"text" : tempMasterPath,
									'icon' : 'fa fa-folder',
									"attr" : {
										"type" : "DIR",
										"path" : "/"
									}
								} ],
								'check_callback' : true
							},
							'plugins' : [ 'types', 'dnd' ],
							'types' : {
								'default' : {
									'icon' : 'fa fa-folder'
								}

							}
						});

						$('#msgtools-jtree')
								.on(
										"changed.jstree",
										function(e, data) {

											if (data.node != undefined) {
												var selectedId = data.selected[0];
												var type = data.node.original.attr['type'];
												var path = data.node.original.attr['path'];
												var text = data.node.original.text;

												var childs = data.node.children;

												if (type == 'DIR') {
													if (childs.length == 0)
														treed(
																'all',
																'msgtools-jtree',
																selectedId,
																path, text);

													$('#if-inf-directory').val(
															fnFormatUri(path
																	+ '/'
																	+ text));

													$('#if-inf-directory')
															.attr('selector',
																	selectedId);

													$('#if-inf-directory')
															.attr('text', text);

												} else if (type == "xml") {

													if (text == CNST_NWCTXNAME) {
														getFile(
																editorDomain,
																'archcomp-asset',
																'newcreate-context');
													} else {
														catFile(editorDomain,
																type, path
																		+ "/"
																		+ text);
													}

													$('#if-inf-contextname')
															.val(text);

													$('#if-inf-contextname')
															.attr('selector',
																	selectedId);
													$('#lbl-inf-contextsite')
															.html(
																	fnFormatUri(path
																			+ '/'));
													$('#if-inf-directory').val(
															fnFormatUri(path));

												}

												$('#if-inf-directory').attr(
														'selectorTy', type);

												if ($("#act-inf-edit").attr(
														'isOn') != '') {
													fnEditMode(
															false,
															editorDomain,
															'act-inf-edit',
															'if-inf-contextname',
															'lblbtn-infcontext');
												}
											}

										}).on(
										"ready.jstree",
										function(e, data) {
											initiated("msgtools-jtree", "j1_1",
													"/local-archs.xml", "/"
															+ tempMasterPath);

										});

						$("#jt-buscontext").jstree("destroy");
						$('#jt-buscontext').jstree({
							'core' : {
								"data" : [ {
									"id" : "j2_1",
									"text" : tempMasterPath,
									'icon' : 'fa fa-folder',
									"attr" : {
										"type" : "DIR",
										"path" : "/"
									}
								} ],
								'check_callback' : true
							},
							'plugins' : [ 'types', 'dnd' ],
							'types' : {
								'default' : {
									'icon' : 'fa fa-folder'
								}

							}
						});

						$('#jt-buscontext')
								.on(
										"changed.jstree",
										function(e, data) {

											if (data.node != undefined) {
												var selectedId = data.selected[0];
												var type = data.node.original.attr['type'];
												var path = data.node.original.attr['path'];
												var text = data.node.original.text;

												var childs = data.node.children;

												if (type == 'DIR') {
													if (childs.length == 0)
														treed(
																"svctx",
																'jt-buscontext',
																selectedId,
																path, text);

												} else if (type == "xml") {
													if (path.charAt(0) == '/')
														path = path.substring(
																1, path.length);

												}
												$('#if-buscontext').val(
														fnFormatUri(path + '/'
																+ text));

												$('#if-buscontext').attr(
														'selector', selectedId);

												$('#if-buscontext').attr(
														'type', type);
											}

										}).on(
										"ready.jstree",
										function(e, data) {
											$("#jt-buscontext").jstree(
													"select_node", "#j2_1");

										});
					}

					/* MAIN DIALOG END! */

					function catFile(editor, type, absoultePath) {
						$.ajax({
							url : "../assets/cat.do?asset=" + absoultePath,
							success : function(result) {
								editor.getSession().setValue(result);
								editor.resize(true)
							}
						});
					}
					function getFile(editor, group, name) {

						var qparam = JSON.stringify([ group, name ]);
						$
								.ajax({
									"type" : "POST",
									"url" : "../dautl/retrieve/json/messageversioning.do",
									"data" : qparam,
									"contentType" : "application/json",
									"dataType" : "json",
									"success" : function(respond) {
										editor
												.getSession()
												.setValue(
														respond.payload.message_template);
										editor.resize(true)
									},
									"error" : function(msg) {
										toastr.error("Retrive payload FAILED!");
									}
								});
					}

					var editorDomain = ace.edit("domain-editor");
					editorDomain.setTheme("ace/theme/tomorrow");
					editorDomain.setAutoScrollEditorIntoView(true);
					editorDomain.setOption("maxLines", 100);
					editorDomain.session.setMode(({
						path : "ace/mode/xml",
						v : Date.now()
					}));

					var editorBuilder = ace.edit("builder-editor");
					editorBuilder.setTheme("ace/theme/tomorrow");
					editorBuilder.setAutoScrollEditorIntoView(true);
					editorBuilder.setOption("maxLines", 100);
					editorBuilder.session.setMode(({
						path : "ace/mode/xml",
						v : Date.now()
					}));
					editorBuilder.setReadOnly(true);

					var dt_context_flcontainer = $('#tbl-context-flcontainer')
							.DataTable(
									{
										"bAutoWidth" : false,
										"bProcessing" : false,
										"bServerSide" : false,
										"sAjaxDataProp" : "payload",
										"bPaginate" : false,
										"bInfo" : false,
										"bFilter" : false,
										"order" : [],
										"bServerSide" : false,
										"sAjaxDataProp" : "payload",
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1 ]
										} ],
										"sAjaxSource" : "../dautl/extract/rows/flcontainer-nonl.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var qparam = JSON.stringify([
													tempModule, tempFlow, '' ]);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/flcontainer-nonl.do",
														"contentType" : "application/json",
														"dataType" : "json",
														"data" : qparam,
														"sAjaxDataProp" : "payload",
														"success" : function(
																result) {
															fnCallback(result);
															$(
																	'#tbl-context-flcontainer tbody tr:eq(0)')
																	.click();
														}
													});
										},
										"aoColumns" : [
												{
													mData : "container_bus",
													mRender : function(data,
															type, full) {
														return "";
													}
												}, {
													mData : "container_flname"
												} ],
										"fnRowCallback" : function(nRow, aData,
												iDisplayIndex,
												iDisplayIndexFull) {
											var index = iDisplayIndexFull + 1;
											$('td:eq(0)', nRow).html(index);
											return nRow;
										}

									});

					var dt_context_buscontext = $('#tbl-context-buscontext')
							.DataTable(
									{
										"bAutoWidth" : false,
										"bProcessing" : false,
										"bServerSide" : false,
										"bDestroy" : true,
										"sAjaxDataProp" : "payload",
										"bPaginate" : false,
										"bInfo" : false,
										"bFilter" : false,
										"order" : [],
										"bServerSide" : false,
										"sAjaxDataProp" : "payload",
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2, 3 ]
										} ],
										"sAjaxSource" : "../dautl/extract/rows/buscontext-nonl.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var qparam;

											if ($('#tempif-context-flcontainer')
													.val() == '-')
												qparam = JSON.stringify([ "-",
														"" ]);
											else {
												var data = JSON
														.parse($(
																'#tempif-context-flcontainer')
																.val());
												qparam = JSON
														.stringify([
																data.container_bus,
																"" ]);
											}

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/buscontext-nonl.do",
														"contentType" : "application/json",
														"dataType" : "json",
														"data" : qparam,
														"sAjaxDataProp" : "payload",
														"success" : function(
																result) {
															fnCallback(result);
															$(
																	'#tbl-context-buscontext tbody a.detail-context-buscontext-alias:eq(0)')
																	.click();
														}
													});

										},
										"aoColumns" : [
												{
													mData : "context_alias",
													mRender : function(data,
															type, full) {
														return "<a class='detail-context-buscontext-alias'>"
																+ data + "</a>";
													}
												},
												{
													mData : "context_level"
												},
												{
													mData : "context_sequence"
												},
												{
													mData : "context_site",
													mRender : function(data,
															type, full) {
														return "<a class='detail-context-buscontext-site'>"
																+ data + "</a>";
													}
												} ]

									});

					$('#tbl-context-flcontainer tbody').on(
							'click',
							'tr',
							function() {
								if (!$(this).hasClass('active')) {
									dt_context_flcontainer.$('tr.active')
											.removeClass('active');
									$(this).addClass('active');

									var data = dt_context_flcontainer.row(this)
											.data();
									if (data != undefined) {
										$('#tempif-context-flcontainer').val(
												JSON.stringify(data));
										dt_context_buscontext.ajax.reload();
									}

								}
							});

					// Add event listener for opening and closing details
					$('#tbl-context-buscontext tbody').on(
							'click',
							'a.detail-context-buscontext-site',
							function() {
								var tr = $(this).closest('tr');
								var row = dt_context_buscontext.row(tr);

								if (row.child.isShown()) {
									// This row is already open - close
									// it
									row.child.hide();
									tr.removeClass('shown');
								} else {
									// Open this row
									var rowIdx = row.index();
									var data = row.data();
									row.child(fnRenderForm(rowIdx)).show();
									tr.addClass('shown');
									$('#if-context-alias-' + rowIdx).val(
											data.context_alias);
									$('#if-context-level-' + rowIdx).val(
											data.context_level);
									$('#if-context-state-' + rowIdx).val(
											data.context_state);
									$('#if-context-sequence-' + rowIdx).val(
											data.context_sequence);
								}
							});
					$('#tbl-context-buscontext tbody')
							.on(
									'click',
									'a.detail-context-buscontext-alias',
									function() {
										var tr = $(this).closest('tr');
										var row = dt_context_buscontext.row(tr);
										if (!$(tr).hasClass('active')) {
											dt_context_buscontext
													.$('tr.active')
													.removeClass('active');
											$(tr).addClass('active');

											$('#tempif-context').val(
													JSON.stringify(row.data()));

											var tcontainer = $(
													'#tempif-context-flcontainer')
													.val();
											if (tcontainer != '-') {
												dt_builderContainer.clear()
														.draw();
												dt_builderContainer.rows
														.add(
																[ JSON
																		.parse(tcontainer) ])
														.draw();

												var data = row.data();
												var tarr = data.context_site
														.split("/");
												$('#actlbl-builder-level')
														.html(
																data.context_level);
												$('#lbl-builder-domainsite')
														.html(
																data.context_site
																		.substring(
																				0,
																				data.context_site.length
																						- tarr[tarr.length - 1].length));
												$('#if-builder-filename').val(
														tarr[tarr.length - 1]);
												$('#if-builder-alias').val(
														data.context_alias);

												dt_builderBaseBuses.ajax
														.reload();
												dt_builderContextBuses.ajax
														.reload();

												if ($('#if-builder-filename')
														.val() == CNST_NWCTXNAME) {

													var templateKey = 'newcreate-context';
													if (tarr[1] == 'repressors') {
														templateKey = 'newcreate-repressors';
													} else if (tarr[1] == 'activators') {
														templateKey = 'newcreate-activators';
													} else if (tarr[1] == 'originators') {
														templateKey = 'newcreate-originators';
													}
													getFile(editorBuilder,
															'archcomp-asset',
															templateKey);
												} else {
													catFile(
															editorBuilder,
															"xml",
															"/"
																	+ row
																			.data().context_site);
												}

											}

										}
									});

					$('#tbl-context-buscontext tbody')
							.on(
									'click',
									'button.btn-context-update',
									function() {
										var dtIdx = this.value;
										var data = dt_context_buscontext.row(
												this.value).data();

										var dtContainer = JSON.parse($(
												'#tempif-context-flcontainer')
												.val());

										var containerBus = dtContainer.container_bus;
										var contextAlias = $(
												'#if-context-alias-' + dtIdx)
												.val();
										var contextLevel = $(
												'#if-context-level-' + dtIdx)
												.val();
										var contextState = $(
												'#if-context-state-' + dtIdx)
												.val();
										var contextSequence = $(
												'#if-context-sequence-' + dtIdx)
												.val();

										var buscontextParam;

										var tSite = data.context_site;
										var tName = tSite.split('/')[tSite
												.split('/').length - 1];
										var tPath = '/'
												+ tSite.substring(0,
														tSite.length
																- tName.length);

										console.log(tName);

										if (tName == CNST_NWCTXNAME) {

											data.context_alias = contextAlias;
											data.context_level = contextLevel;
											data.context_sequence = contextSequence;
											data.context_state = contextState;
											dt_context_buscontext.row(dtIdx)
													.data(data).draw();

											toastr
													.success("Bus Context Relation is saved temporarily!");

											var tr = $(this).closest('tr');
											if (!$(tr).hasClass('active')) {
												console.log('inactive');
											} else {
												console.log('active');
											}
											console.log(tr);

										} else {
											if (data.context_code == null) {
												$
														.ajax({
															type : "POST",
															url : "../dautl/retrieve/json/context-sequence.do",
															data : JSON
																	.stringify([]),
															contentType : "application/json",
															dataType : "json",
															async : true,
															success : function(
																	respond) {
																var contextCode = respond.payload[0].context_sequence;

																var contextParam = JSON
																		.stringify([
																				contextAlias,
																				contextCode,
																				'',
																				data.context_site ]);

																$
																		.ajax({
																			type : "POST",
																			url : "../dml/execute/insert/context-insert.do",
																			data : contextParam,
																			contentType : "application/json",
																			dataType : "json",
																			async : true,
																			success : function(
																					respond) {

																				data.context_alias = contextAlias;
																				dt_context_buscontext
																						.row(
																								dtIdx)
																						.data(
																								data)
																						.draw();
																				console
																						.log(data);

																				buscontextParam = [
																						containerBus,
																						contextLevel,
																						contextState,
																						contextCode,
																						contextSequence,
																						'',
																						'-overflowparam-' ];

																				fnInsertBusContext(
																						dtIdx,
																						buscontextParam)

																			},
																			error : function(
																					err) {
																				console
																						.log(err);
																				toastr
																						.error('creating context['
																								+ contextAlias
																								+ '] failed!');
																			}
																		});

															},
															error : function(
																	msg) {
																toastr
																		.error('creating context sequence['
																				+ contextSite
																				+ '], call your administrator!!');

															}
														});
											} else {
												if (data.context_sequence == -1) {
													if (contextSequence == -1) {
														toastr
																.error('Context sequence can not be set -1');
													} else {
														data.context_alias = contextAlias;
														dt_context_buscontext
																.row(dtIdx)
																.data(data)
																.draw();
														buscontextParam = [
																containerBus,
																contextLevel,
																contextState,
																data.context_code,
																contextSequence,
																'',
																'-overflowparam-' ];

														fnInsertBusContext(
																dtIdx,
																buscontextParam);
													}

												} else {
													if (data.context_alias != contextAlias) {
														$
																.ajax({
																	type : "POST",
																	url : "../dml/execute/update/context-update.do",
																	data : JSON
																			.stringify([
																					contextAlias,
																					'',
																					contextSite,
																					context_code ]),
																	contentType : "application/json",
																	dataType : "json",
																	async : true,
																	success : function(
																			respond) {
																		toastr
																				.success("Context Alias has been changed!");
																	},
																	error : function(
																			err) {
																		toastr
																				.error('creating relation['
																						+ param[0]
																						+ ','
																						+ param[3]
																						+ '] failed!');

																	}
																});
													}

													if (data.context_level != contextLevel
															|| data.context_sequence != contextSequence
															|| data.context_state != contextState) {

														buscontextParam = [
																contextLevel,
																contextState,
																contextSequence,
																'',
																'-overflowparam-',
																data.xeai_bus_code,
																data.context_code ];

														fnUpdateBusContext(
																dtIdx,
																buscontextParam)

													}

												}

											}
										}

									});

					function fnInsertBusContext(dtIdx, param) {
						$.ajax({
							type : "POST",
							url : "../dml/execute/insert/buscontext-insert.do",
							data : JSON.stringify(param),
							contentType : "application/json",
							dataType : "json",
							async : true,
							success : function(respond) {

								var data = dt_context_buscontext.row(dtIdx)
										.data();
								data.context_level = param[1];
								data.context_sequence = param[4];
								data.context_state = param[2];
								dt_context_buscontext.row(dtIdx).data(data)
										.draw();

								toastr.success(respond.message);
							},
							error : function(err) {
								toastr.error('creating relation[' + param[0]
										+ ',' + param[3] + '] failed!');

							}
						});
					}
					function fnUpdateBusContext(dtIdx, param) {
						$.ajax({
							type : "POST",
							url : "../dml/execute/update/buscontext-update.do",
							data : JSON.stringify(param),
							contentType : "application/json",
							dataType : "json",
							async : true,
							success : function(respond) {

								var data = dt_context_buscontext.row(dtIdx)
										.data();
								data.context_level = param[0];
								data.context_sequence = param[2];
								data.context_state = param[1];
								dt_context_buscontext.row(dtIdx).data(data)
										.draw();

								toastr.success(respond.message);
							},
							error : function(err) {
								toastr.error('Update relation[' + param[0]
										+ ',' + param[3] + '] failed!');

							}
						});
					}
					function fnDeleteBusContext(idx, param) {
						$.ajax({
							type : "POST",
							url : "../dml/execute/delete/buscontext-delete.do",
							data : param,
							contentType : "application/json",
							dataType : "json",
							async : true,
							success : function(respond) {
								toastr.success(respond.message);
								dt_context_buscontext.row(idx).remove().draw(
										false);
							},
							error : function(err) {
								toastr.error('creating relation[' + param[1]
										+ ',' + param[0] + '] failed!');

							}
						});
					}

					/* -------------------------------------------------------------------- */

					$('#tbl-context-buscontext tbody').on(
							'click',
							'button.btn-context-delete',
							function() {
								var dtIdx = this.value;
								var data = dt_context_buscontext
										.row(this.value).data();
								var isConfirmed = confirm("Context['"
										+ data.context_alias
										+ "'] Relation will be delete"
										+ this.value);
								if (isConfirmed) {
									fnDeleteBusContext(dtIdx, JSON.stringify([
											data.xeai_bus_code,
											data.context_code ]));
								}
							});

					$("#act-context-refresh")
							.click(
									function() {
										if ($('#if-buscontext').attr('type') == 'DIR')
											fnRefreshJtree('jt-buscontext', $(
													'#if-buscontext').attr(
													'selector'));
									});
					$("#act-context-remove")
							.click(
									function() {
										if ($('#if-buscontext').attr('type') != 'DIR') {

											var site = $('#if-buscontext')
													.val();
											var tName = site.split('/')[site
													.split('/').length - 1];
											var tPath = '/'
													+ site
															.substring(
																	0,
																	site.length
																			- tName.length);

											var param = {
												path : tPath,
												name : tName,
												content : null,
												sourcePath : null,
												sourceName : null

											};

											fnRemoveFile('jt-buscontext', $(
													'#if-buscontext').attr(
													'selector'), param);
										} else {
											toastr
													.warning("'DELETE' opt is not allowed!");
										}
									});
					$("#act-context-createadd")
							.click(
									function() {
										if ($('#if-buscontext').attr('type') == 'DIR') {
											var type = "xml";
											var path = $('#if-buscontext')
													.val();
											var selectedId = $('#if-buscontext')
													.attr('selector');

											var attr = {
												"type" : type,
												"path" : path
											};

											var data = JSON
													.parse($(
															'#tempif-context-flcontainer')
															.val());

											fnAddjTreeChild('jt-buscontext',
													selectedId, attr);

											dt_context_buscontext.row
													.add(
															{
																"context_site" : $(
																		'#if-buscontext')
																		.val(),
																"context_alias" : CNST_NWALIAS,
																"context_level" : "BASE",
																"context_state" : 3,
																"context_sequence" : -1,
																"context_code" : null,
																"container_flname" : data.container_flname,
																"xeai_bus_code" : data.container_bus,
																"xeai_bus_code_binder" : null
															}).draw();
										}

									});

					$('#act-context-add')
							.click(
									function() {
										var contextSite = $('#if-buscontext')
												.val();
										if ($('#if-buscontext').attr('type') == 'xml') {
											var data = JSON
													.parse($(
															'#tempif-context-flcontainer')
															.val());

											$
													.ajax({
														type : "POST",
														url : "../dautl/retrieve/json/context-buscontext-colleteralcheck.do",
														data : JSON
																.stringify([
																		data.container_bus,
																		contextSite, ]),
														contentType : "application/json",
														dataType : "json",
														async : true,
														success : function(
																respond) {

															console
																	.log(respond);

															if (respond.payload.colleteral == 0) {
																var contextAlias = CNST_NWALIAS;
																var contextCode = null;

																if (respond.payload.context_code != undefined) {
																	contextAlias = respond.payload.context_alias;
																	contextCode = respond.payload.context_code;
																}

																dt_context_buscontext.row
																		.add(
																				{
																					"context_site" : contextSite,
																					"context_alias" : contextAlias,
																					"context_level" : "BASE",
																					"context_state" : 3,
																					"context_sequence" : -1,
																					"context_code" : contextCode,
																					"container_flname" : data.container_flname,
																					"xeai_bus_code" : data.container_bus,
																					"xeai_bus_code_binder" : null
																				})
																		.draw();
															} else {
																toastr
																		.warning('Context['
																				+ contextSite
																				+ '] is aleady collaterated with this bus!');
															}

														},
														error : function(msg) {
															toastr.error(msg);

														}
													});

										} else {
											toastr
													.warning('Context['
															+ contextSite
															+ '] is unable to be added!');
										}

									});

					/* END OF CONTEXT SCRIPT */

					var dt_builderContainer = $('#builder-container')
							.DataTable({
								"bAutoWidth" : false,
								"bPaginate" : false,
								"bInfo" : false,
								"bFilter" : false,
								"order" : [],
								"columnDefs" : [ {
									"bSortable" : false,
									"aTargets" : [ 0, 1 ]
								} ],
								"aoColumns" : [ {
									mData : "xeai_bus_name"
								}, {
									mData : "xeai_bus_group",
									mRender : function(data, type, full) {
										return '<a>' + data + '</a>';
									}
								} ]

							});

					var dt_builderBaseBuses = $('#builder-basebus')
							.DataTable(
									{
										"bAutoWidth" : false,
										"bProcessing" : false,
										"bDestroy" : true,
										"bPaginate" : false,
										"bInfo" : false,
										"bFilter" : false,
										"order" : [],
										"bServerSide" : false,
										"sAjaxDataProp" : "payload",
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2 ]
										} ],
										"sAjaxSource" : "../dautl/retrieve/json/contextbus.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var key = "buses-nonl";

											var qparam = JSON.stringify([ '',
													'', '', '' ]);

											if ($('#tempif-context').val() != '-') {
												var data = JSON.parse($(
														'#tempif-context')
														.val());
												qparam = JSON.stringify([ '',
														'', data.xeai_bus_code,
														data.context_code ]);
											}
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
															fnCallback(result);
														},
														"errors" : function(
																result) {
															console.log(result);
														}
													});
										},
										"aoColumns" : [
												{
													mData : "xeai_bus_name",
													mRender : function(data,
															type, full) {
														return "";
													}
												},
												{
													mData : "xeai_bus_name",
													mRender : function(data,
															type, full) {
														return '<a class="actln-builder-basebus"><i class="fa fa-gear"></a>';
													}
												}, {
													mData : "xeai_bus_name"
												} ],
										"fnRowCallback" : function(nRow, aData,
												iDisplayIndex,
												iDisplayIndexFull) {
											var index = iDisplayIndexFull + 1;
											$('td:eq(0)', nRow).html(index);
											return nRow;
										}

									});

					$('#builder-basebus tbody').on(
							'click',
							'a.actln-builder-basebus',
							function() {
								var row = dt_builderBaseBuses.row($(this)
										.closest('tr'));
								var rowIdx = row.index();
								var data = row.data();
								console.log(data);
								fnSetCBusDialog(data.identifier,
										data.xeai_bus_group,
										data.xeai_bus_cipher);
								fnShowCBusDialog('control');
							});

					function fnShowCBusDialog(type) {
						if (type == 'control') {
							$("#if-selected-operand").attr("readonly", true);
							$("#if-selected-cipher").attr('disabled', 'disabled');
							$("#if-selected-group").attr('disabled', 'disabled');
							$('#act-builder-bussave').hide();
							$('#act-builder-busdelete').hide();
							$('#act-builder-buscall').show();
							$('#mdl-controlbus').modal('show');
						} else if (type == 'addnew') {
							$("#if-selected-operand").removeAttr('disabled');
							$("#if-selected-cipher").removeAttr('disabled');
							$("#if-selected-group").removeAttr('disabled');
							$('#act-builder-bussave').show();
							$('#act-builder-busdelete').hide();
							$('#act-builder-buscall').hide();
							$('#mdl-controlbus').modal('show');
						} else if (type == 'control-update') {
							$("#if-selected-operand").attr("readonly", false);
							$('#if-selected-cipher').removeAttr('disabled');
							$('#if-selected-group').removeAttr('disabled');
							$('#act-builder-bussave').show();
							$('#act-builder-busdelete').show();
							$('#act-builder-buscall').show();
							$('#mdl-controlbus').modal('show');
						}
					}
					/** BUILDER TAB */
					$("#act-builder-buscall").click(function() {
						alert('what is buscall?');
					});
					$("#act-builder-bussave")
							.click(
									function() {
										var tdata = JSON.parse($(
												'#tempif-context').val());
										var contextCode = tdata.context_code;
										var busCodeBinder = tdata.xeai_bus_code;

										if (contextCode == null) {
											toastr
													.error("Any of Bus can not be save before context registered!");
										} else {
											$
													.ajax({
														type : "POST",
														url : "../dautl/retrieve/json/bus-sequence.do",
														data : JSON
																.stringify([]),
														contentType : "application/json",
														dataType : "json",
														async : true,
														success : function(
																respond) {
															console
																	.log(respond);
															var busCode = respond.payload.bus_sequence;
															var busCipher = $(
																	'#if-selected-cipher')
																	.val();
															var busGroup = $(
																	'#if-selected-group')
																	.val();
															var busName = $(
																	'#if-selected-operand')
																	.val();
															var busParam = [
																	busCipher,
																	busCode,
																	busGroup,
																	busName ];

															$
																	.ajax({
																		type : "POST",
																		url : "../dml/execute/insert/bus-insert.do",
																		data : JSON
																				.stringify(busParam),
																		contentType : "application/json",
																		dataType : "json",
																		async : true,
																		success : function(
																				respond) {
																			console
																					.log(respond);

																			var bContextParam = [
																					busCode,
																					'BINDBUS',
																					1,
																					contextCode,
																					-1,
																					'-nonullassignation-',
																					busCodeBinder ];
																			$
																					.ajax({
																						type : "POST",
																						url : "../dml/execute/insert/buscontext-insert.do",
																						data : JSON
																								.stringify(bContextParam),
																						contentType : "application/json",
																						dataType : "json",
																						async : true,
																						success : function(
																								respond) {
																							toastr
																									.info(respond.message);

																						},
																						error : function(
																								err) {
																							console
																									.log(err);
																							toastr
																									.error(err);
																						}
																					});
																		},
																		error : function(
																				err) {
																			toastr
																					.error(err);
																			console
																					.log(err);
																		}
																	});
														},
														error : function(err) {
															toastr.error(err);
															console.log(err);
														}
													});
										}

									});
					$("#act-builder-busdelete").click(function() {
						alert('busdelete');
					});

					var dt_builderContextBuses = $('#builder-contextbus')
							.DataTable(
									{
										"bAutoWidth" : false,
										"bProcessing" : false,
										"bDestroy" : true,
										"bPaginate" : false,
										"bInfo" : false,
										"bFilter" : false,
										"order" : [],
										"bServerSide" : false,
										"sAjaxDataProp" : "payload",
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2 ]
										} ],
										"sAjaxSource" : "../dautl/retrieve/json/contextbus.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var key = "buses-nonl";

											var qparam = JSON.stringify([ '',
													'', '', '' ]);

											if ($('#tempif-context').val() != '-') {
												var data = JSON.parse($(
														'#tempif-context')
														.val());
												var tCCode = data.context_code;
												if (tCCode == null)
													tCCode = '-';
												qparam = JSON.stringify([ '',
														tCCode, '', '' ]);
											}
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
															fnCallback(result);
														},
														"errors" : function(
																result) {
															console.log(result);
														}
													});
										},
										"aoColumns" : [
												{
													mData : "xeai_bus_name",
													mRender : function(data,
															type, full) {
														return "";
													}
												},
												{
													mData : "xeai_bus_name",
													mRender : function(data,
															type, full) {
														return '<a class="actln-builder-bus"><i class="fa fa-gear"></a>';
													}
												}, {
													mData : "xeai_bus_name"
												} ],
										"fnRowCallback" : function(nRow, aData,
												iDisplayIndex,
												iDisplayIndexFull) {
											var index = iDisplayIndexFull + 1;
											$('td:eq(0)', nRow).html(index);
											return nRow;
										}

									});

					$('#builder-contextbus tbody').on(
							'click',
							'a.actln-builder-bus',
							function() {
								var row = dt_builderContextBuses.row($(this)
										.closest('tr'));
								var rowIdx = row.index();
								var data = row.data();
								console.log(data);
								fnSetCBusDialog(data.identifier,
										data.xeai_bus_group,
										data.xeai_bus_cipher);
								fnShowCBusDialog('control-update');
							});

					$("#act-flowpattern").click(function() {
						$('#mdl-act-component').modal('show');
					});

					$("#act-inf-edit")
							.click(
									function() {
										if ($("#act-inf-edit").attr('isOn') == '') {

											fnEditMode(true, editorDomain,
													'act-inf-edit',
													'if-inf-contextname',
													'lblbtn-infcontext');

											toastr.info("Editor is enabled!");
										} else {
											toastr
													.warning("Editor is already enabled!");
										}

									});

					$("#act-inf-save")
							.click(
									function() {
										if ($("#act-inf-edit").attr('isOn') != '') {

											var action = 0;
											var context = {};

											if ($("#if-inf-contextname").attr(
													'base') == CNST_NWCTXNAME) {

												if ($('#if-inf-contextname')
														.val() == CNST_NWCTXNAME) {
													toastr
															.error("Name: "
																	+ CNST_NWCTXNAME
																	+ " is not acceptable!");
												} else {
													action = 110;
													context = {
														path : $(
																'#lbl-inf-contextsite')
																.html(),
														name : $(
																'#if-inf-contextname')
																.val(),
														content : editorDomain
																.getSession()
																.getValue()

													}
												}

											} else {
												action = 101;
												context = {
													path : $(
															'#lbl-inf-contextsite')
															.html(),
													name : $(
															'#if-inf-contextname')
															.val(),
													content : editorDomain
															.getSession()
															.getValue(),
													sourcePath : $(
															'#lbl-inf-contextsite')
															.html(),
													sourceName : $(
															"#if-inf-contextname")
															.attr('base')

												}

											}

											if (action != -1) {
												var contextData = JSON
														.stringify(context);
												$
														.ajax({
															type : "POST",
															url : "../assets/"
																	+ action
																	+ "/file.do",
															data : contextData,
															contentType : "application/json",
															dataType : "json",
															async : true,
															success : function(
																	respond) {
																toastr
																		.info(respond.remarks
																				+ " @"
																				+ respond.timestamp);
																fnEditMode(
																		false,
																		editorDomain,
																		'act-inf-edit',
																		'if-inf-contextname',
																		'lblbtn-infcontext');

																$(
																		"#msgtools-jtree")
																		.jstree(
																				'set_text',
																				$(
																						'#if-inf-contextname')
																						.attr(
																								'selector'),
																				context.name);
															},
															error : function(
																	err) {
																toastr
																		.error(err);
															}
														});
											}
										} else {
											toastr
													.error("Context is not on Editing MODE!");
										}
									});

					$("#act-inf-remove")
							.click(
									function() {
										if ($('#if-inf-directory').attr(
												'selectorTy') != 'DIR') {
											var action = 111;
											var param = {
												path : $('#lbl-inf-contextsite')
														.html(),
												name : $('#if-inf-contextname')
														.val(),
												content : null,
												sourcePath : null,
												sourceName : null

											};
											fnRemoveFile('msgtools-jtree', $(
													'#if-inf-contextname')
													.attr('selector'), param);
										} else {
											toastr
													.warning("'DELETE' opt is not allowed on DIR type!");
										}

									});

					$('#act-builder-edit').click(function() {
						alert('edit');
					});
					$('#act-builder-delete').click(function() {
						alert('delete');
					});
					$('#act-builder-addbus').click(function() {
						fnSetCBusDialog('', '', '');
						fnShowCBusDialog('addnew');
					});
					$('#act-builder-controlbus').click(function() {
						fnShowCBusDialog('control');
					});

					$('#if-selected-cipher').change(
							function() {
								fnInitiateCbxReference('cipherrule-nonl',
										'if-selected-cipherrule', JSON
												.stringify([ this.value, '',
														'stance-opt' ]));
							});

					function fnRemoveFile(treeId, nodeId, param) {

						var isConfirmed = confirm("Are you sure deleting context '"
								+ param.path + param.name + "' ?");

						if (isConfirmed) {
							var action = 111;
							$("#" + treeId).jstree("deselect_node", nodeId);
							$.ajax({
								type : "POST",
								url : "../assets/" + action + "/file.do",
								data : JSON.stringify(param),
								contentType : "application/json",
								dataType : "json",
								async : true,
								success : function(respond) {

									toastr.info(respond.remarks + " @"
											+ respond.timestamp);
									$('#' + treeId).jstree("delete_node",
											nodeId);
								},
								error : function(err) {
									toastr.error(err);
								}
							});
						}

					}

					$("#act-inf-refresh").click(
							function() {
								fnRefreshJtree('msgtools-jtree', $(
										'#if-inf-directory').attr('selector'));
							});

					function fnRefreshJtree(jstreeId, nodeId) {
						var childs = $(
								$("#" + jstreeId + " #" + nodeId).jstree(
										"get_children_dom ")).find("li");
						for (var i = 0, l = childs.length; i < l; i++) {
							$('#' + jstreeId).jstree("delete_node",
									$(childs[i]).attr("id"));
						}

						$("#" + jstreeId).jstree("deselect_node", nodeId);
						$("#" + jstreeId).jstree("select_node", nodeId);
					}
					$("#act-inf-createfile").click(
							function() {
								var type = "xml";
								var path = $('#if-inf-directory').val();
								var selectedId = $('#if-inf-directory').attr(
										'selector');

								var attr = {
									"type" : type,
									"path" : path
								};

								fnAddjTreeChild('msgtools-jtree', selectedId,
										attr);
							});

					function fnAddjTreeChild(treeId, nodeId, attr) {
						var icon = "fa fa-file-code-o";
						var tSelectorId = jQuery("#" + treeId).jstree(true)
								.create_node($('#' + nodeId), {
									"text" : CNST_NWCTXNAME,
									"icon" : icon,
									"attr" : attr,
									"id" : true
								}, "last", false, true);

						$("#" + treeId).jstree("deselect_all");
						$("#" + treeId).jstree("select_node", tSelectorId);
					}

					$('#if-inf-directory').keypress(function(event) {
						if (event.keyCode == 10 || event.keyCode == 13)
							event.preventDefault();

					});
					function fnEditMode(mode, editor, actEdit, ifEdit, lblbtn) {
						if (mode) {
							editor.setReadOnly(false);
							$("#" + ifEdit).attr("readonly", false);
							$("#" + actEdit).attr('isOn', true);
							$("#" + ifEdit).attr('base', $('#' + ifEdit).val());
							$("#" + lblbtn).html('IS EDITING');
						} else {
							editor.setReadOnly(true);
							$("#" + ifEdit).attr("readonly", true);
							$("#" + actEdit).attr('isOn', '');
							$("#" + ifEdit).attr('base', '');
							$("#" + lblbtn).html(
									'<i class="fa fa-gear gray-bg"></i>');
						}
					}

					function fnSetCBusDialog(busName, type, cipher) {
						$('#if-selected-operand').val(busName);
						$('#if-selected-group').val(type);
						$('#if-selected-cipher').val(cipher);
						$("#if-selected-cipher").change();
					}
					function fnResetCBusDialog(busName, type, cipher) {
						$('#if-selected-operand').val('');
						$('#if-selected-group').val('');
						$('#if-selected-cipher').val('');
						$("#if-selected-cipher").change();
					}

					function initiation() {
						$('#if-inf-contextname').css('background-color',
								'#ffffff');
						$("#if-inf-directory").attr("readonly", true);
						$("#if-inf-directory").css('background-color',
								'#ffffff');

						$('#if-buscontext').css('background-color', '#ffffff');
						$("#if-buscontext").attr("readonly", true);

						fnInitiateCbxReference('group-nonl',
								'if-selected-group', JSON.stringify([ '',
										'BUS-CAST' ]));

						fnInitiateCbxReference('cipher-nonl',
								'if-selected-cipher', JSON
										.stringify([ '', '' ]));

					}

					initiation();

				});
/* END OF READY DOCUMENTS */
function treed(type, jtree, selectedId, path, dirName) {
	

	if (dirName == "DOMAIN")
		dirName = "/"

	$.ajax({
		url : "../assets/lslrt.do?pwd=" + path + "&name=" + dirName
				+ "&processor=" + type,
		success : function(result) {
			var arr = JSON.parse(result);
			var type;
			var icon;
			var extn;
			var tSelectorId;
			for (var i = 0; i < arr.length; i++) {

				type = "DIR";
				icon = 'fa fa-folder';

				extn = arr[i].name.substring(arr[i].name.length - 3);
				if (extn == "xml") {
					icon = "fa fa-file-code-o";
					type = extn;
				}

				tSelectorId = jQuery("#" + jtree).jstree(true).create_node(
						$('#' + selectedId),
						{
							"text" : arr[i].name,
							"icon" : icon,
							"attr" : {
								"type" : type,
								"path" : (path + '/' + dirName).replace('//',
										'/')
							},
							id : true
						}, "last", false, true);

			}
			$("#" + jtree).jstree("open_node", "#" + selectedId);

		}

	});

}

function fnRenderForm(rowId) {// fnRenderForm
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td><input type="text" id="if-context-alias-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			+ '	<td><select class="form-control input-sm" id="if-context-level-'
			+ rowId
			+ '">'
			+ '			<option value="BASE">BASE</option>'
			+ '			<option value="BINDERKEY">BINDERKEY</option>'
			+ '	</select>'
			+ ' <select class="form-control input-sm" id="if-context-state-'
			+ rowId
			+ '">'
			+ '			<option value="1">ACTIVE</option>'
			+ '			<option value="0">INACTIVE</option>'
			+ '			<option value="3">DEACTIVATE</option>'
			+ '	</select> * <input type="text" id="if-context-sequence-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" style="width:30px;"></input> <button type="button" value="'
			+ rowId
			+ '" class="btn btn-primary btn-sm btn-context-update">'
			+ '			<i class="fa fa-floppy-o"></i>'
			+ '		</button>'
			+ '		<button type="button" value="'
			+ rowId
			+ '" class="btn btn-warning btn-sm btn-context-delete">'
			+ '			<i class="fa fa-times"></i>'
			+ '		</button></td>'
			+ '</tr>'
			+ '</table>';
	return table;
}

function fnBusnotationRender(raws) {
	var notations = raws.split(', ');
	var nots;
	var result = "CH-IN";
	for (var i = 0; i < notations.length; i++) {
		nots = notations[i].split('/');
		if (nots[0] == 'ServiceRepressor') {
			result += " >> " + nots[1]
		} else if (nots[0] == 'ServiceBinderRepressor') {
			result += " >>>> " + nots[1]
		} else if (nots[0] == 'ServiceActivator') {
			result += " <> " + nots[1];
		} else if (nots[0] == 'ServiceBinderActivator') {
			result += " <<>> " + nots[1]
		} else if (nots[0] == 'ServiceBinderOriginator') {
			if (result == "CH-IN")
				result = "";
			result += " " + nots[1] + " >><<";
		}
	}
	return result.trim();

}

function fnAsLink(className, text) {
	return '<a class="' + className + '">' + text + '</a>';
}

function toPath(arr, start) {
	var result = "";
	for (var i = start; i < arr.length; i++) {
		if (i < arr.length) {
			result = result.concat("/");
		}
		result = result.concat(arr[i]);
	}
	return result;
}

function initiated(jtreeId, selectedId, prepath, postpath) {
	if (prepath != "") {
		var prearr = prepath.split("/");
		var path = "/" + prearr[0];
		var dirName = postpath;
		var type;

		var lsUrl = "../assets/lslrt.do?pwd=" + path + "&name=" + dirName;
		$.ajax({
			url : lsUrl,
			success : function(result) {
				var arr = JSON.parse(result);
				var type;
				var icon;
				var extn;
				var tSelectorId;
				var nextSelectorId = null;
				var tSelectorText = null;
				if (prearr.length > 1)
					tSelectorText = prearr[1];
				for (var i = 0; i < arr.length; i++) {

					type = "DIR";
					icon = 'fa fa-folder';

					extn = arr[i].name.substring(arr[i].name.length - 3);
					if (extn == "xml") {
						icon = "fa fa-file-code-o";
						type = extn;
					}

					tSelectorId = jQuery("#" + jtreeId).jstree(true)
							.create_node($('#' + selectedId), {
								"text" : arr[i].name,
								"icon" : icon,
								"attr" : {
									"type" : type,
									"path" : path + "/" + dirName
								},
								"id" : true
							}, "last", false, true);

					if (tSelectorText != null) {
						if (arr[i].name == tSelectorText) {
							nextSelectorId = tSelectorId;
						}
					}
				}
				$("#" + jtreeId).jstree("open_node", "#" + selectedId);

				if (prearr.length == 2 && prearr[0] == "") {
					$("#" + jtreeId)
							.jstree("select_node", "#" + nextSelectorId);
				} else if (nextSelectorId != null) {
					prepath = toPath(prearr, 2);

					postpath += "/" + prearr[1];

					initiated(jtreeId, nextSelectorId, prepath, postpath)
				}
			}

		});
	}
}

function fnFormatUri(value) {
	return value.replace(/\/+/g, '/');
}

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
				opt.value = row.tkey;
				opt.text = row.tkey;
				objSelect.appendChild(opt);
			});

		},
		error : function(msg) {
			console.log(msg);
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
	opt.text = "NONE";
	objSelect.appendChild(opt);
}
