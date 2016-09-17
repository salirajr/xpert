$(document).ready(function() {
	$('#jt-buscontext').jstree({
		'core' : {
			"data" : [ {
				"text" : "DOMAIN",
				'icon' : 'fa fa-folder',
				"attr" : {
					"type" : "DIR",
					"base" : "",
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
	
	$('#jt-buscontext').on("changed.jstree",function(e, data) {

		var selectedId = data.selected[0];
		var type = data.node.original.attr['type'];
		var base = data.node.original.attr['base'];
		var path = data.node.original.attr['path'];
		var text = data.node.original.text;
		console.log("path=====>"+path.substring(1));
		var childs = data.node.children;
		
		if (type == 'DIR') {
			if (childs.length == 0)
				treed('jt-buscontext', selectedId,
						base, path, text);
		} else if (type == "xml") {
			$('#if-buscontext').val(path + '/' + text);
		}
	}).on("ready.jstree", function(e, data) {
	});
	
});

function insertUpdateDeleteCtx(rowIdx,dt_context,actionParam){
	
	var ctxCode = $('#context-code-' + rowIdx).val();
	var ctxSite = $('#context-site-' + rowIdx).val();
	var ctxAlis = $('#context-alias-'+ rowIdx).val();
	var ctxDesc = $('#context-description-'+ rowIdx).val();
	
	
	var row = dt_context.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnContextCompoundInsert(ctxAlis, ctxCode, ctxDesc, ctxSite);
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnContextCompoundDelete(ctxAlis, ctxCode, ctxDesc, ctxSite);
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnContextCompoundUpdate(ctxAlis, ctxCode, ctxDesc, ctxSite);
		}
		
	}
	
	console.log('dataparam ' + dataParam);								 
	
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/context-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert((respond.message));
			if(respond.message==='Succes '+action)dt_context.ajax.reload();

		},
		error : function(err) {
			alert(err.message);
		}
	});	
}

function fnContextCompoundUpdate(contextAlias, contextCode, contextDescription, contextSite) {
	
	var qparam = JSON.stringify([contextAlias, contextDescription, contextSite,contextCode]);
  	console.log(qparam);
  	return qparam;
}

function fnContextCompoundInsert(contextAlias, contextCode, contextDescription, contextSite) {
	
	var qparam = JSON.stringify([ contextAlias, contextCode, contextDescription, contextSite]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnContextCompoundDelete(contextAlias, contextCode, contextDescription, contextSite) {
	
	var qparam = JSON.stringify([ contextCode ]);
	console.log(qparam);
	return qparam;
}

function addNewContextForm(dt_context){
	
	var currentPage = dt_context.page();
	var currentRow = dt_context.page.info();
	
	
	//insert a test row
	var contextSite = $('#if-buscontext').val();
	if (contextSite != null && contextSite != ''
			&& contextSite.endsWith('.xml')) {
		dt_context.row.add({
			"no":999,
			"context_site" : contextSite.substring(1),
			"context_alias" : "NEW ALIAS"
		}).draw();
		
		//move added row to desired index (here the row we clicked on)
		var index = dt_context.row(this).index(),
		rowCount = dt_context.data().length-1,
		insertedRow = dt_context.row(rowCount).data(),
		tempRow;

		for (var i=rowCount;i>index;i--) {
			tempRow = dt_context.row(i-1).data();
			dt_context.row(i).data(tempRow);
			dt_context.row(i-1).data(insertedRow);
		}     
		//refresh the page
		dt_context.page(currentRow.pages).draw();
		var oTable = $('#tbl-context').dataTable();
		oTable.fnPageChange( 'last' );
		
	} else {
		toastr.warning('Context[' + contextSite
				+ '] is unable to be added!');
	}	
}


function fnUpdateFormRenderContext(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Context Code</td>'
			+ '	<td><input type="text" id="context-code-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true"></input></td>'
			
			+ '	<td>Context Site</td>'
			+ '	<td><input type="text" id="context-site-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"  readonly="true" ></input></td>'
			
			+ '	<td>Context Alias</td>'
			+ '	<td><input type="text" id="context-alias-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			+ '</tr>'
			+ '<tr>'		
			
			+ '	<td>Context Desc</td>'
			+ '	<td><input type="text" id="context-description-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
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

function getContextSequence(key,idTxt){
	var param = JSON.stringify([]);
	var context_sequence= '0';
	$.ajax({
		type : "POST",
		url : "../dautl/retrieve/json/" + key + ".do",
		data : param,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			context_sequence =  respond.payload[0].context_sequence;
			$('#'+idTxt).val(context_sequence);
		},
		error : function(msg) {
			console.log("msg:"+msg);
			
		}
	});
	return context_sequence;
}

function treed(jtree, selectedId, base, path, dirName) {
	if (dirName == "DOMAIN")
		dirName = "/";
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
				tSelectorId = jQuery("#" + jtree).jstree(true).create_node(
						$('#' + selectedId),
						{
							"text" : arr[i].name,
							"icon" : icon,
							"attr" : {
								"type" : type,
								"path" : (path + '/' + dirName).replace('//',
										'/'),
								"base" : base
							},
							id : true
						}, "last", false, true);
			}
			$("#" + jtree).jstree("open_node", "#" + selectedId);
		}
	});
}