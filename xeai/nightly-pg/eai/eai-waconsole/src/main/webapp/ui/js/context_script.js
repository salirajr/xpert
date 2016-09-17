function insertUpdateDelete(rowIdx,dt_context,actionParam){
	
	var ctxCode = $('#context-code-' + + rowIdx).val();
	var ctxSite = $('#context-site-' + + rowIdx).val();
	var ctxAlis = $('#context-alias-' + + rowIdx).val();
	var ctxDesc = $('#context-description-' + + rowIdx).val();
	
	
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
			dt_context.ajax.reload();

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
	dt_context.row.add({
			"context_site" : null,
			"context_alias" : null
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
			+ '		class="form-control input-sm"></input></td>'
			+ '</tr>'
			+ '<tr>'
			+ '	<td>Context Alias</td>'
			+ '	<td><input type="text" id="context-alias-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			+ '	<td>Context Desc</td>'
			+ '	<td><input type="text" id="context-description-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			+ '</tr>'
			+ '<tr>'
			+ '	<td></td><td></td>'
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