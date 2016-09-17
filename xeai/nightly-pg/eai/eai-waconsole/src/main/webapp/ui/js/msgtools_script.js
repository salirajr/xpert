$(document)
		.ready(
				function() {

					var editorContext = ace.edit("context-editor");
					editorContext.setTheme("ace/theme/tomorrow");
					editorContext.setAutoScrollEditorIntoView(true);
					editorContext.setOption("maxLines", 100);
					editorContext.session.setMode("ace/mode/xml");

					var editorRqo = ace.edit("rqo-editor");
					editorRqo.setTheme("ace/theme/tomorrow");
					editorRqo.setAutoScrollEditorIntoView(true);
					editorRqo.setOption("maxLines", 100);
					editorRqo.session.setMode("ace/mode/text");

					var editorRpi = ace.edit("rpi-editor");
					editorRpi.setTheme("ace/theme/tomorrow");
					editorRpi.setAutoScrollEditorIntoView(true);
					editorRpi.setOption("maxLines", 100);
					editorRpi.session.setMode("ace/mode/text");

					$
							.ajax({
								type : "POST",
								url : "../dautl/retrieve/json/cbxstubtscenario.do",
								data : JSON.stringify([]),
								contentType : "application/json",
								dataType : "json",
								async : true,
								success : function(respond) {
									var objSelect = document
											.getElementById("context-source");
									$.each(respond.payload, function(i, row) {
										opt = document.createElement("option");
										opt.value = row.key;
										opt.text = row.key;
										opt.description = row.key;
										opt.setAttribute("description",
												row.description);
										objSelect.appendChild(opt);
										if (i == 0) {
											setContextEditor(row.key,
													row.description);
										}
										i++;
									});

								},
								error : function(msg) {
									toastr
											.error("Context failed to Load, no reference available");
									console.log(msg);
								}
							});

					$('#context-source').change(
							function() {
								setContextEditor(this.value, $(
										'option:selected', this).attr(
										'description'));
							});

					$('#rqo-payload').change(function() {
						fnAssignRqo(this.value);

					});
					$('#rqo-payload-type').change(function() {
						var value = $('#rqo-payload').val();
						fnAssignRqo(value);

					});
					$('#rpi-content').change(function() {
						fnAssignRpi();

					});
					$('#rpi-type').change(function() {
						payloadType = this.value;
						if (payloadType == 'json') {
							editorRpi.session.setMode("ace/mode/json");
						} else if (payloadType == 'xml') {
							editorRpi.session.setMode("ace/mode/xml");
						} else {
							editorRpi.session.setMode("ace/mode/text");
						}

					});
					$('#mock-send').on(
							'click',
							function(e) {
								var mock = {
									context : editorContext.getSession()
											.getValue(),
									requestPayload : editorRqo.getSession()
											.getValue(),
									integrationType : $('#integration-type')
											.val()
								};
								var mockData = JSON.stringify(mock);
								$.ajax({
									type : "POST",
									url : "../appliance/async/mockup.do",
									data : mockData,
									contentType : "application/json",
									dataType : "json",
									async : true,
									success : function(respond) {
										editorRpi.getSession().setValue(
												respond.payload);
										$('#rpi-content').val('payload');
										$('#rpi-type').val('text');
										$('#rpi-result-payload').val(respond.payload);
										$('#rpi-result-headers').val(respond.headers);
										toastr.info(respond.message);

									},
									error : function(err) {
										toastr.error(err.message);
									}
								});
							});
					function setContextEditor(value, description) {
						$('#context-desription').html(description);
						var parameters = new Array();
						parameters.push(value);
						var requestBody = JSON.stringify(parameters);
						$
								.ajax({
									type : "POST",
									url : "../dautl/retrieve/json/contextstubtscenariobyname.do",
									data : requestBody,
									contentType : "application/json",
									dataType : "json",
									async : true,
									success : function(respond) {
										console.log(respond);
										editorContext.getSession().setValue(
												respond.payload.context);
										editorRqo.getSession().setValue(
												respond.payload.rqo);
										editorRpi.getSession().setValue("");
										$('#rqo-payload').val('default');
										$('#rqo-payload-type').val(
												(respond.payload.rqotype)
														.toLowerCase());
										fnAssignRqo('default');

									},
									error : function(err) {
									}
								});
					}
					function fnAssignRqo(rqoMode) {
						console.log(rqoMode);
						var payloadType = $('#rqo-payload-type').val();
						if (rqoMode == 'custom') {
							console.log(payloadType);
							if (payloadType == 'json') {
								editorRqo.getSession().setValue("[{}]");
							} else if (payloadType == 'xml') {
								editorRqo
										.getSession()
										.setValue(
												"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
							} else {
								editorRqo
										.getSession()
										.setValue(
												"This is sample fixed length of 39 chars");
							}
						}

						if (payloadType == 'json') {
							editorRqo.session.setMode("ace/mode/json");
						} else if (payloadType == 'xml') {
							editorRqo.session.setMode("ace/mode/xml");
						} else {
							editorRqo.session.setMode("ace/mode/text");
						}

					}
					
					function fnAssignRpi(){
						var content = $('#rpi-content').val();
						if(content == 'payload'){
							editorRpi.session.setValue($('#rpi-result-payload').val());
							$('#rpi-type').attr('disabled', false);
						}else if(content == 'headers'){
							editorRpi.session.setValue($('#rpi-result-headers').val());
							editorRpi.session.setMode("ace/mode/text");
							$('#rpi-type').val('text');
							$('#rpi-type').attr('disabled', true);
						}
					}

				});
// END OF READY DOCUMENTS

