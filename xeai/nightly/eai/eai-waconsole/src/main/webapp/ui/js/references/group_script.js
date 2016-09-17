function insertUpdateDeleteGroup(rowIdx,dt_group,actionParam){
	
	var gkey = $('#group-key-' + rowIdx).val();
	var gidentifier = $('#group-identifier-' + rowIdx).val();
	
	
	var row = dt_group.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnGroupCompoundInsert(gkey, gidentifier);
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnGroupCompoundDelete(gkey, gidentifier);
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnGroupCompoundUpdate(gkey, gidentifier);
		}
		
	}
	
	console.log('dataparam ' + dataParam);	
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/group-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert((respond.message));
			if(respond.message==='Succes '+action)dt_group.ajax.reload();

		},
		error : function(err) {
			alert(err.message);
		}
	});
	
	
	
		
}

function fnGroupCompoundUpdate(gkey, gidentifier) {
	
	var qparam = JSON.stringify([gidentifier,gkey]);
  	console.log(qparam);
  	return qparam;
}

function fnGroupCompoundInsert(gkey, gidentifier) {
	
	var qparam = JSON.stringify([gkey, gidentifier]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnGroupCompoundDelete(gkey, gidentifier){
	
	var qparam = JSON.stringify([  gkey ]);
	console.log(qparam);
	return qparam;
}

function addNewGroupForm(dt_group){
	
	var currentPage = dt_group.page();
	var currentRow = dt_group.page.info();
	
	//insert a test row
	dt_group.row.add({
			"group_key" : null,
			"group_identifier" : null
	}).draw();
	    
	    //move added row to desired index (here the row we clicked on)
	var index = dt_group.row(this).index(),
	rowCount = dt_group.data().length-1,
	insertedRow = dt_group.row(rowCount).data(),
	tempRow;

	for (var i=rowCount;i>index;i--) {
		tempRow = dt_group.row(i-1).data();
		dt_group.row(i).data(tempRow);
		dt_group.row(i-1).data(insertedRow);
	}     
	//refresh the page
	dt_group.page(currentRow.pages).draw();
	var oTable = $('#tbl-group').dataTable();
	oTable.fnPageChange( 'last' );
	
}


function fnUpdateFormRenderGroup(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Key</td>'
			+ '	<td><input type="text" id="group-key-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'
			
			+ '	<td>Identifier</td>'
			+ '	<td><input type="text" id="group-identifier-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" ></input></td>'
			
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