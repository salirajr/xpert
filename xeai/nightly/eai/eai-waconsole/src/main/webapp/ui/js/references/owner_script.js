function insertUpdateDeleteOwner(rowIdx,dt_owner,actionParam){
	
	var ownerName = $('#owner-name-' + rowIdx).val();
	var ownerAlias = $('#owner-alias-' + rowIdx).val();
	var ownerBaseuri = $('#owner-baseuri-'+ rowIdx).val();
	var ownerDomain = $('#owner-domain-'+ rowIdx).val();
	
	
	var row = dt_owner.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnOwnerCompoundInsert(ownerName , ownerAlias , ownerBaseuri, ownerDomain );
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnOwnerCompoundDelete(ownerName , ownerAlias , ownerBaseuri, ownerDomain );
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnOwnerCompoundUpdate(ownerName , ownerAlias , ownerBaseuri, ownerDomain );
		}
		
	}
	
	console.log('dataparam ' + dataParam);								 
	
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/owner-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert((respond.message));
			if(respond.message==='Succes '+action)dt_owner.ajax.reload();

		},
		error : function(err) {
			alert(err.message);
		}
	});	
}

function fnOwnerCompoundUpdate (ownerName , ownerAlias , ownerBaseuri, ownerDomain ){
	
	var qparam = JSON.stringify([ownerAlias, ownerBaseuri, ownerDomain,ownerName]);
  	console.log(qparam);
  	return qparam;
}

function fnOwnerCompoundInsert(ownerName , ownerAlias , ownerBaseuri, ownerDomain ) {
	
	var qparam = JSON.stringify([ ownerName, ownerAlias, ownerBaseuri, ownerDomain]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnOwnerCompoundDelete(ownerName , ownerAlias , ownerBaseuri, ownerDomain ) {
	
	var qparam = JSON.stringify([ ownerName ]);
	console.log(qparam);
	return qparam;
}

function addNewOwnerForm(dt_owner){
	
	var currentPage = dt_owner.page();
	var currentRow = dt_owner.page.info();
	
	//insert a test row
	dt_owner.row.add({
			"name" : null,
			"alias" : null,
			"baseuri" : null,
			"domain" : null
	}).draw();
	    
	    //move added row to desired index (here the row we clicked on)
	var index = dt_owner.row(this).index(),
	rowCount = dt_owner.data().length-1,
	insertedRow = dt_owner.row(rowCount).data(),
	tempRow;

	for (var i=rowCount;i>index;i--) {
		tempRow = dt_owner.row(i-1).data();
		dt_owner.row(i).data(tempRow);
		dt_owner.row(i-1).data(insertedRow);
	}     
	//refresh the page
	dt_owner.page(currentRow.pages).draw();
	var oTable = $('#tbl-owner').dataTable();
	oTable.fnPageChange( 'last' );
	
}


function fnUpdateFormRenderOwner(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Name</td>'
			+ '	<td><input type="text" id="owner-name-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true"></input></td>'
			
			+ '	<td>Alias</td>'
			+ '	<td><input type="text" id="owner-alias-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'

			+ '	<td>Baser Url</td>'
			+ '	<td><input type="text" id="owner-baseuri-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			
			+ '	<td>Domain</td>'
			+ '	<td><input type="text" id="owner-domain-'
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