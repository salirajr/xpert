$(document).ready(
		function() {

			var editor = ace.edit("main_editor");
			editor.setTheme("ace/theme/tomorrow");
			editor.setAutoScrollEditorIntoView(true);
			editor.setOption("maxLines", 100);

			$('#jstree1').jstree({
				'core' : {
					"data" : [ {
						"text" : "CONTEXT",
						'icon' : 'fa fa-folder',
						"attr" : {
							"type" : "DIR",
							"base" : "base",
							"path" : ""
						}
					}, {
						"text" : "WIRING",
						'icon' : 'fa fa-folder',
						"attr" : {
							"type" : "DIR",
							"base" : "wiring",
							"path" : ""
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

			$('#jstree1').on("changed.jstree", function(e, data) {

				var selectedId = data.selected[0];
				var type = data.node.original.attr['type'];
				var base = data.node.original.attr['base'];
				var path = data.node.original.attr['path'];
				var text = data.node.original.text;

				var childs = data.node.children;

				if (type == 'DIR') {
					if (text == 'CONTEXT' || text == 'WIRING') {
						text = "";
						path = "";
					}
					if (childs.length == 0)
						treed(selectedId, base, path, text);

				} else if (type == "xml") {
					editor.session.setMode("ace/mode/xml");
					viewFile(type, base, path + "/" + text);
				} else if (type == "drl") {
					editor.session.setMode("ace/mode/java");
					viewFile(type, base, path + "/" + text);
				}

			}).on(
					"ready.jstree",
					function(e, data) {

						var openedFile = "/wireContext-locals.xml";
						var base = "wiring";
						
						if($("#initOFBase").html() != ""){
							base = $("#initOFBase").html();
						}
						
						if($("#initOFFile").html() != ""){
							openedFile = $("#initOFFile").html();
						}

						var selectedId = "j1_1";
						if (base == "wiring")
							selectedId = "j1_2";
						

						// wire-definition/
						setFile(selectedId, base,
								openedFile, "");
						console.log("openedFile:"+openedFile);
					});

			function viewFile(type, base, absoultePath) {

				

				$('#file-lable').html(
						base + " " + absoultePath.replace("\\", "/"));

				$.ajax({
					url : "extracts/" + base + "/" + type + ".htm?path="
							+ absoultePath,
					success : function(result) {
						editor.getSession().setValue(result);
					}
				});
			}

		});

function treed(selectedId, base, path, dirName) {

	// $('#jstree1').jstree().delete_node($('#'+selectedId));
	$.ajax({
		url : "get/" + base + "/ls-lrt.htm?currentDir=" + path + "&dirName="
				+ dirName,
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
				if (extn == "drl" || extn == "xml") {
					icon = "fa fa-file-code-o";
					type = extn;
				} else if (extn == "xls") {
					icon = "fa fa-file-code-o";
					type = extn;
				}

				tSelectorId = jQuery("#jstree1").jstree(true).create_node(
						$('#' + selectedId), {
							"text" : arr[i].name,
							"icon" : icon,
							"attr" : {
								"type" : type,
								"path" : path + "/" + dirName,
								"base" : base
							},
							id : true
						}, "last", false, true);

			}
			$("#jstree1").jstree("open_node", "#" + selectedId);

		}

	});

}

function setFile(selectedId, base, prepath, postpath) {

	console.log("init-prepath :" + prepath + ", postpath:" + postpath);

	if (prepath != "") {
		var prearr = prepath.split("/");
		var path = "/" + prearr[0];
		var dirName = postpath;
		var type;

		$.ajax({
			url : "get/" + base + "/ls-lrt.htm?currentDir=" + path
					+ "&dirName=" + dirName,
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
					if (extn == "drl" || extn == "xml") {
						icon = "fa fa-file-code-o";
						type = extn;
					} else if (extn == "xls") {
						icon = "fa fa-file-code-o";
						type = extn;
					}

					tSelectorId = jQuery("#jstree1").jstree(true).create_node(
							$('#' + selectedId), {
								"text" : arr[i].name,
								"icon" : icon,
								"attr" : {
									"type" : type,
									"path" : path + "/" + dirName,
									"base" : base
								},
								id : true
							}, "last", false, true);

					console.log(tSelectorText);
					if (tSelectorText != null) {
						if (arr[i].name == tSelectorText) {
							nextSelectorId = tSelectorId;
						}
					}
				}
				$("#jstree1").jstree("open_node", "#" + selectedId);

				console.log(prearr.length + " " + prearr[1] + " "
						+ nextSelectorId);
				if (prearr.length == 2 && prearr[0] == "") {
					$("#jstree1").jstree("select_node", "#" + nextSelectorId);
				} else if (nextSelectorId != null) {
					prepath = toPath(prearr, 2);

					postpath += "/" + prearr[1];

					console.log("nextSelectorId: " + nextSelectorId
							+ ", after prepath:" + prepath
							+ ", after postpath:" + postpath);

					setFile(nextSelectorId, base, prepath, postpath)
				}
			}

		});
	}
}

function toPath(arr, start) {
	console.log(arr);
	var result = "";
	for (var i = start; i < arr.length; i++) {
		if (i < arr.length) {
			result = result.concat("/");
		}
		result = result.concat(arr[i]);
	}
	return result;
}