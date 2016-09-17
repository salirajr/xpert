function insertUpdateDeleteMessageVersioning(rowIdx,dt_xeai_message_versioning,actionParam){
	
	var mvName = $('#message-name-' + rowIdx).val();
	var mvGroup = $('#message-group-' + rowIdx).val();
	var mvType = $('#message-type-'+ rowIdx).val();
	var mvTemplate = $('#message-template-'+ rowIdx).val();
	var mvSample = $('#message-sample-' + rowIdx).val();
	var mvVersion = $('#message-version-' + rowIdx).val();
	var mvDescription = $('#message-description-'+ rowIdx).val();
	
	
	var row = dt_xeai_message_versioning.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnMessageVersioningCompoundInsert(mvName , mvGroup , mvType, mvTemplate,mvSample, mvVersion,mvDescription);
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnMessageVersioningCompoundDelete(mvName , mvGroup , mvType, mvTemplate,mvSample, mvVersion,mvDescription);
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnMessageVersioningCompoundUpdate(mvName , mvGroup , mvType, mvTemplate,mvSample, mvVersion,mvDescription);
		}
		
	}
	
	console.log('dataparam ' + dataParam);								 
	
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/message-versioning-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert((respond.message));
			if(respond.message==='Succes '+action)dt_xeai_message_versioning.ajax.reload();

		},
		error : function(err) {
			alert(err.message);
		}
	});	
}

function fnMessageVersioningCompoundUpdate (mvName , mvGroup , mvType, mvTemplate,mvSample, mvVersion,mvDescription){
	
	var qparam = JSON.stringify([ mvType, mvTemplate,mvSample, mvVersion,mvDescription, mvName , mvGroup ]);
  	console.log(qparam);
  	return qparam;
}

function fnMessageVersioningCompoundInsert(mvName , mvGroup , mvType, mvTemplate,mvSample, mvVersion,mvDescription ) {
	
	var qparam = JSON.stringify([ mvName , mvGroup , mvType, mvTemplate,mvSample, mvVersion,mvDescription]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnMessageVersioningCompoundDelete(mvName , mvGroup , mvType, mvTemplate,mvSample, mvVersion,mvDescription) {
	
	var qparam = JSON.stringify([ mvName, mvGroup ]);
	console.log(qparam);
	return qparam;
}

function addNewMessageVersioningForm(dt_xeai_message_versioning){
	
	var currentPage = dt_xeai_message_versioning.page();
	var currentRow = dt_xeai_message_versioning.page.info();
	
	//insert a test row
	dt_xeai_message_versioning.row.add({
		 "message_name" : null,
		 "message_group": null,
		 "message_type": null,
		 "message_template": null,
		 "message_sample": null,
		 "version": null,
		 "description": null
	}).draw();
	    
	    //move added row to desired index (here the row we clicked on)
	var index = dt_xeai_message_versioning.row(this).index(),
	rowCount = dt_xeai_message_versioning.data().length-1,
	insertedRow = dt_xeai_message_versioning.row(rowCount).data(),
	tempRow;

	for (var i=rowCount;i>index;i--) {
		tempRow = dt_xeai_message_versioning.row(i-1).data();
		dt_xeai_message_versioning.row(i).data(tempRow);
		dt_xeai_message_versioning.row(i-1).data(insertedRow);
	}     
	//refresh the page
	dt_xeai_message_versioning.page(currentRow.pages).draw();
	var oTable = $('#tbl-message-versioning').dataTable();
	oTable.fnPageChange( 'last' );
	
}


function fnUpdateFormRenderMessageVersioning(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Name</td>'
			+ '	<td><input type="text" id="message-name-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true"></input></td>'
			
			+ '	<td>Group</td>'
			+ '	<td><input type="text" id="message-group-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'
			+ '	<td>Type</td>'
			+ '	<td><input type="text" id="message-type-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			
			+ '</tr>'
			+ '<tr>'
			
			+ '	<td>Template</td>'
			+ '	<td><textarea rows="4" cols="50" id="message-template-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></textarea></td>'
			
			+ '	<td>Sample</td>'
			+ '	<td><textarea rows="4" cols="50" id="message-sample-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></textarea></td>'
			+ '</tr>'
			+ '<tr>'
			+ '	<td>Version</td>'
			+ '	<td><input type="text" id="message-version-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			+ '	<td>Description</td>'
			+ '	<td><input type="text" id="message-description-'
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