function insertUpdateDeleteLd(rowIdx,dt_lookupdata,actionParam){
	
	var ldKey = $('#lookupdata-key-' + rowIdx).val();
	var ldName = $('#lookupdata-name-'+ rowIdx).val();
	var ldValue = $('#lookupdata-value-'+ rowIdx).val();
	var ldRemarks = $('#lookupdata-remarks-'+ rowIdx).val();
	
	
	var row = dt_lookupdata.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnLookupDataCompoundInsert(ldKey, ldName, ldValue, ldRemarks);
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnLookupDataCompoundDelete(ldKey, ldName, ldValue, ldRemarks);
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnLookupDataCompoundUpdate(ldKey, ldName, ldValue, ldRemarks);
		}
		
	}
	
	console.log('dataparam ' + dataParam);								 
	
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/lookup-data-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert((respond.message));
			if(respond.message==='Succes '+action)dt_lookupdata.ajax.reload();

		},
		error : function(err) {
			alert(err.message);
		}
	});	
}

function fnLookupDataCompoundUpdate(ldKey, ldName, ldValue, ldRemarks) {
	
	var qparam = JSON.stringify([ldRemarks , ldValue,ldKey , ldName]);
  	console.log(qparam);
  	return qparam;
}

function fnLookupDataCompoundInsert(ldKey, ldName, ldValue, ldRemarks) {
	
	var qparam = JSON.stringify([ ldKey, ldName, ldRemarks , ldValue]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnLookupDataCompoundDelete(ldKey, ldName, ldValue, ldRemarks) {
	
	var qparam = JSON.stringify([  ldKey, ldName ]);
	console.log(qparam);
	return qparam;
}

function addNewLookupDataForm(dt_lookupdata){
	
	var currentPage = dt_lookupdata.page();
	var currentRow = dt_lookupdata.page.info();
	
	//insert a test row
	dt_lookupdata.row.add({
			"name" : null,
			"key" : null,
			"value" : null
	}).draw();
	    
	    //move added row to desired index (here the row we clicked on)
	var index = dt_lookupdata.row(this).index(),
	rowCount = dt_lookupdata.data().length-1,
	insertedRow = dt_lookupdata.row(rowCount).data(),
	tempRow;

	for (var i=rowCount;i>index;i--) {
		tempRow = dt_lookupdata.row(i-1).data();
		dt_lookupdata.row(i).data(tempRow);
		dt_lookupdata.row(i-1).data(insertedRow);
	}     
	//refresh the page
	dt_lookupdata.page(currentRow.pages).draw();
	var oTable = $('#tbl-lookup-data').dataTable();
	oTable.fnPageChange( 'last' );
	
}


function fnUpdateFormRenderLookupData(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Data Name</td>'
			+ '	<td><input type="text" id="lookupdata-name-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'
			
			+ '	<td>Data Key</td>'
			+ '	<td><input type="text" id="lookupdata-key-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'

			+ '	<td>Data Value</td>'
			+ '	<td><input type="text" id="lookupdata-value-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			+ '	<td>Data Remarks</td>'
			+ '	<td><input type="text" id="lookupdata-remarks-'
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