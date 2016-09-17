$(document)
		.ready(
				function() {

					var editor = ace.edit("main_editor");
					editor.setTheme("ace/theme/tomorrow");
					editor.setAutoScrollEditorIntoView(true);
					editor.setOption("maxLines", 100);
					editor.textInput.getElement().disabled = true;

					$('#locks')
							.click(
									function() {
										var arrClass = $('#lock-state').attr(
												'class');
										console.log(arrClass);
										if (arrClass == 'fa fa-unlock') {
											$("#locks").empty();
											$("#locks")
													.append(
															$("<i id='lock-state' class='fa fa-lock' ></i>"));
											editor.textInput.getElement().disabled = false;
										} else if (arrClass == 'fa fa-lock') {
											$("#locks").empty();
											$("#locks")
													.append(
															$("<i id='lock-state' class='fa fa-unlock' ></i>"));
											editor.textInput.getElement().disabled = true;
										}
									});

					$('#download').on('click', function(e) {
						var file = $('#file-lable').html();
						location.href = "../get/wiring/file.htm?path=/" + file;
					});

					$.ajax({
						url : "../fabricates/files/RuntimeConfiguration.htm",
						success : function(result) {
							var jsonResult = "[";
							var arr = JSON.parse(result);
							var initFile = null;
							for (var i = 0; i < arr.length; i++) {
								if (i == 0) {
									initFile = arr[i].file_location
											+ arr[i].file_name;
								}
								jsonResult += "[\"" + arr[i].file_location
										+ arr[i].file_name + "\"]";
								if (i < arr.length - 1)
									jsonResult += ",";
							}
							jsonResult += "]";

							$('#runtime-configuration').dataTable({
								"bPaginate" : false,
								"bFilter" : false,
								"autoWidth" : false,
								"bInfo" : false,
								"scrollX" : true,
								"bLengthChange" : false,
								"sRowSelect" : "single",
								"aaData" : JSON.parse(jsonResult),
							});

							if (initFile != null) {
								fileView(initFile);
							}

							$('#runtime-configuration tbody').on('click', 'tr',
									function() {
										var file = $('td', this).eq(0).text();
										fileView(file);
									});

							$('#link').on('click', function(e) {
								var file = "/" + $('#file-lable').html();
								$("#base").val("wiring");
								$("#file").val(file);

								$("#aflinkage").attr("method", "post");
								$("#aflinkage").submit();
							});

							$('#upload').on('click', function(e) {
								$("#fileUpload").click();
							});

							$("#fileUpload").change(function() {
								$("#view-upload-group").removeClass("invisible");
								$("#file-upload-title").val($("#fileUpload").val());
							});

						}
					});

					Dropzone.options.myAwesomeDropzone = {
						autoProcessQueue : false,
						dictDefaultMessage : "Put your custom message here",
						uploadMultiple : false,
						// Dropzone settings
						accept : function(file, done) {
							console.log("uploaded");
							done();
						},
						init : function() {
							this.on("addedfile", function() {
								if (this.files[1] != null) {
									this.removeFile(this.files[0]);
								}
							});
						}
					};

					function fileView(file) {
						var type = null;
						$('#file-lable').html(file);
						editor.textInput.getElement().disabled = true;
						if (file != null && file.length > 3) {
							console.log(file.substring(file.length - 3));
							if (file.substring(file.length - 3) == 'drl') {
								type = 'Drl';
							} else if (file.substring(file.length - 3) == 'xml') {
								type = 'Xml';
							}
						}
						if (type != null) {
							$
									.ajax({
										url : "../extracts/wire.htm?path=/"
												+ file,
										success : function(result) {
											if (type == "Drl") {
												editor.session
														.setMode("ace/mode/java");
												editor.getSession().setValue(
														result);
											} else if (type == "Xml") {
												editor.session
														.setMode("ace/mode/xml");
												editor.getSession().setValue(
														result);
											}

										}
									});
						}
					}

				});