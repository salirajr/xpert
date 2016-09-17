function insertUpdateDeleteCipherRule(rowIdx,dt_cipherrule,actionParam){
	
	var crId = $('#xeai_cipher-' + rowIdx).val();
	var cKey = $('#cipher-key').val();
	var crParameter = $('#xeai_cipher_parameters-' + rowIdx).val();
	var crGroup = $('#xeai_cipher_group-' + rowIdx).val();
	
	var row = dt_cipherrule.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnCipherRuleCompoundInsert(cKey, crId, crParameter, crGroup);
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnCipherRuleCompoundDelete(cKey, crId, crParameter, crGroup);
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnCipherRuleCompoundUpdate(cKey, crId, crParameter, crGroup);
		}
		
	}
	
	console.log('dataparam ' + dataParam);								 
	
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/cipher-rule-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert((respond.message));
			if(respond.message==='Succes '+action)dt_cipherrule.ajax.reload();

		},
		error : function(err) {
			alert(err.message);
		}
	});	
}

function fnCipherRuleCompoundUpdate(cKey, crId, crParameter, crGroup) {
	
	var qparam = JSON.stringify([ crParameter, crGroup ,crGroup, cKey, crId]);
  	console.log(qparam);
  	return qparam;
}

function fnCipherRuleCompoundInsert(cKey, crId, crParameter, crGroup) {
	
	var qparam = JSON.stringify([ cKey, crId, crParameter, crGroup , crGroup]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnCipherRuleCompoundDelete(cKey, crId, crParameter, crGroup) {
	
	var qparam = JSON.stringify([  cKey, crId]);
	console.log(qparam);
	return qparam;
}

function addNewCipherRuleForm(dt_cipherrule){
	
	var currentPage = dt_cipherrule.page();
	var currentRow = dt_cipherrule.page.info();
	
	//insert a test row
	dt_cipherrule.row.add({
			"xeai_cipher" : null,
			"xeai_cipher_parameters" : null
	}).draw();
	    
	    //move added row to desired index (here the row we clicked on)
	var index = dt_cipherrule.row(this).index(),
	rowCount = dt_cipherrule.data().length-1,
	insertedRow = dt_cipherrule.row(rowCount).data(),
	tempRow;

	for (var i=rowCount;i>index;i--) {
		tempRow = dt_cipherrule.row(i-1).data();
		dt_cipherrule.row(i).data(tempRow);
		dt_cipherrule.row(i-1).data(insertedRow);
	}     
	//refresh the page
	dt_cipherrule.page(currentRow.pages).draw();
	var oTable = $('#tbl-cipher-rule').dataTable();
	oTable.fnPageChange( 'last' );
	
}


function fnUpdateFormRenderCipherRule(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Rule</td>'
			+ '	<td><input type="text" id="xeai_cipher-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'
			
			+ '	<td>Parameters</td>'
			+ '	<td><input type="text" id="xeai_cipher_parameters-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" ></input></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '	<td>Group</td>'			
			+ '	<td><select class="form-control input-sm" id="xeai_cipher_group-'
			+ rowId
			+ '">'
			+ '			<option value="null"></option>'
			+ '	</select></td>'
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