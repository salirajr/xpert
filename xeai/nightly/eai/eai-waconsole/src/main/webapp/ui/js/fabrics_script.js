$(document).ready(function() {
	var editorContext = ace.edit("context-editor");
	editorContext.setTheme("ace/theme/tomorrow");
	editorContext.setAutoScrollEditorIntoView(true);
	editorContext.setOption("maxLines", 100);
	editorContext.session.setMode("ace/mode/xml");

	$('#msgtools-jtree').jstree({
		'core' : {
			"data" : [ {
				"text" : "DOMAIN",
				'icon' : 'fa fa-folder',
				"attr" : {
					"type" : "DIR",
					"base" : "",
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

	$('#msgtools-jtree').on("changed.jstree", function(e, data) {

		var selectedId = data.selected[0];
		var type = data.node.original.attr['type'];
		var base = data.node.original.attr['base'];
		var path = data.node.original.attr['path'];
		var text = data.node.original.text;

		var childs = data.node.children;

		if (type == 'DIR') {
			if (childs.length == 0)
				treed(selectedId, base, path, text);

		} else if (type == "xml") {
			viewFile(type, base, path + "/" + text);
		}

	}).on("ready.jstree", function(e, data) {
		initiated("j1_1", "/INV-archcomp/commons/dao.xml", "");
	});

	function viewFile(type, base, absoultePath) {
		$('#context-file').val(absoultePath);
		$('#ifcontext-file').val(base + absoultePath);

		$.ajax({
			url : "../assets/cat.do?asset=" + absoultePath,
			success : function(result) {
				editorContext.getSession().setValue(result);
			}
		});
	}

});
function treed(selectedId, base, path, dirName) {

	if (dirName == "DOMAIN")
		dirName = "/"

	$.ajax({
		url : "../assets/lslrt.do?pwd=" + path + "&name=" + dirName,
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

				tSelectorId = jQuery("#msgtools-jtree").jstree(true)
						.create_node($('#' + selectedId), {
							"text" : arr[i].name,
							"icon" : icon,
							"attr" : {
								"type" : type,
								"path" : path + "/" + dirName
							},
							id : true
						}, "last", false, true);

			}
			$("#msgtools-jtree").jstree("open_node", "#" + selectedId);

		}

	});

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

function initiated(selectedId, prepath, postpath) {

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

					tSelectorId = jQuery("#msgtools-jtree").jstree(true)
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
				$("#msgtools-jtree").jstree("open_node", "#" + selectedId);

				if (prearr.length == 2 && prearr[0] == "") {
					$("#msgtools-jtree").jstree("select_node",
							"#" + nextSelectorId);
				} else if (nextSelectorId != null) {
					prepath = toPath(prearr, 2);

					postpath += "/" + prearr[1];

					initiated(nextSelectorId, prepath, postpath)
				}
			}

		});
	}
}
