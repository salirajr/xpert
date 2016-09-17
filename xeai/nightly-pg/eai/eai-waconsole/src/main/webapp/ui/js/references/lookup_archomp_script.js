function insertUpdateDeleteLa(rowIdx,dt_lookuparchomp,actionParam){
	
	var laKey = $('#lookuparchomp-key-' +rowIdx).val();
	var laName = $('#lookuparchomp-name-'+ rowIdx).val();
	var laValue = $('#lookuparchomp-value-'+ rowIdx).val();
	var laRemarks = $('#lookuparchomp-remarks-'+ rowIdx).val();
	
	
	var row = dt_lookuparchomp.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnLookupArchompCompoundInsert(laKey, laName, laValue, laRemarks);
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnLookupArchompCompoundDelete(laKey, laName, laValue, laRemarks);
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnLookupArchompCompoundUpdate(laKey, laName, laValue, laRemarks);
		}
		
	}
	
	console.log('dataparam ' + dataParam);								 
	
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/lookup-archcomp-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert((respond.message));
			if(respond.message==='Succes '+action)dt_lookuparchomp.ajax.reload();

		},
		error : function(err) {
			alert(err.message);
		}
	});	
}

function fnLookupArchompCompoundUpdate(laKey, laName, laValue, laRemarks) {
	
	var qparam = JSON.stringify([laRemarks , laValue,laKey , laName]);
  	console.log(qparam);
  	return qparam;
}

function fnLookupArchompCompoundInsert(laKey, laName, laValue, laRemarks) {
	
	var qparam = JSON.stringify([ laKey, laName, laRemarks , laValue]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnLookupArchompCompoundDelete(laKey, laName, laValue, laRemarks) {
	
	var qparam = JSON.stringify([  laKey, laName ]);
	console.log(qparam);
	return qparam;
}

function addNewLookupArchompForm(dt_lookuparchomp){
	
	var currentPage = dt_lookuparchomp.page();
	var currentRow = dt_lookuparchomp.page.info();
	
	//insert a test row
	dt_lookuparchomp.row.add({
			"name" : null,
			"key" : null,
			"value" : null
	}).draw();
	    
	    //move added row to desired index (here the row we clicked on)
	var index = dt_lookuparchomp.row(this).index(),
	rowCount = dt_lookuparchomp.data().length-1,
	insertedRow = dt_lookuparchomp.row(rowCount).data(),
	tempRow;

	for (var i=rowCount;i>index;i--) {
		tempRow = dt_lookuparchomp.row(i-1).data();
		dt_lookuparchomp.row(i).data(tempRow);
		dt_lookuparchomp.row(i-1).data(insertedRow);
	}     
	//refresh the page
	dt_lookuparchomp.page(currentRow.pages).draw();
	var oTable = $('#tbl-lookup-archcomp').dataTable();
	oTable.fnPageChange( 'last' );
	
}


function fnUpdateFormRenderLookupArchomp(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Archomp Name</td>'
			+ '	<td><input type="text" id="lookuparchomp-name-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'
			
			+ '	<td>Archomp Key</td>'
			+ '	<td><input type="text" id="lookuparchomp-key-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'

			+ '	<td>Archomp Value</td>'
			+ '	<td><input type="text" id="lookuparchomp-value-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			+ '	<td>Archomp Remarks</td>'
			+ '	<td><input type="text" id="lookuparchomp-remarks-'
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