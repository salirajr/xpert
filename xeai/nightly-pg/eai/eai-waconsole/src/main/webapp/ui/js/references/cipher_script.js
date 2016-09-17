function insertUpdateDeleteCipher(rowIdx,dt_cipher,dt_cipherrule,actionParam){	
	var ckey = $('#xeai-cipher-key-' + rowIdx).val();
	var ctype = $('#xeai-cipher-type-' + rowIdx).val();	
	var row = dt_cipher.row(rowIdx);
	var data = row.data();
	var action;
	var dataParam;
	
	if(data.is_existing!=1){
		action = 'insert';
		dataParam = fnCipherCompoundInsert(ckey, ctype);
	}else if(data.is_existing===1){
		if(actionParam==='delete'){
			action = 'delete';
			dataParam = fnCipherCompoundDelete(ckey, ctype);
		}else if(actionParam==='update'){
			action = 'update';
			dataParam = fnCipherCompoundUpdate(ckey, ctype);
		}
		
	}
	
	console.log('dataparam ' + dataParam);	
	if(action === 'delete'){
		dataParam = fnCipherCompoundInsert(ckey, '');
		$.ajax({
			type : "POST",
			url : "../dml/execute/"+action+"/cipher-rule-"+action+".do",
			data : dataParam,
			contentType : "application/json",
			dataType : "json",
			async : true,
			success : function(respond) {
				if(respond.message==='Succes '+action){
					dataParam = fnCipherCompoundDelete(ckey, ctype);
					$.ajax({
						type : "POST",
						url : "../dml/execute/"+action+"/cipher-"+action+".do",
						data : dataParam,
						contentType : "application/json",
						dataType : "json",
						async : true,
						success : function(respond) {
							alert((respond.message));
							if(respond.message==='Succes '+action){
								dt_cipher.ajax.reload();
								dt_cipherrule.ajax.reload();
							}

						},
						error : function(err) {
							alert(err.message);
						}
					});
				}else{
					dataParam = fnCipherCompoundDelete(ckey, ctype);
					$.ajax({
						type : "POST",
						url : "../dml/execute/"+action+"/cipher-"+action+".do",
						data : dataParam,
						contentType : "application/json",
						dataType : "json",
						async : true,
						success : function(respond) {
							alert((respond.message));
							if(respond.message==='Succes '+action){
								dt_cipher.ajax.reload();
								dt_cipherrule.ajax.reload();
							}

						},
						error : function(err) {
							alert(err.message);
						}
					});
				}

			},
			error : function(err) {
				alert(err.message);
			}
		});
	}else{
		$.ajax({
			type : "POST",
			url : "../dml/execute/"+action+"/cipher-"+action+".do",
			data : dataParam,
			contentType : "application/json",
			dataType : "json",
			async : true,
			success : function(respond) {
				alert((respond.message));
				if(respond.message==='Succes '+action)dt_cipher.ajax.reload();

			},
			error : function(err) {
				alert(err.message);
			}
		});
	}
	
	
	
		
}

function fnCipherCompoundUpdate(ckey, ctype) {
	
	var qparam = JSON.stringify([ctype,ckey]);
  	console.log(qparam);
  	return qparam;
}

function fnCipherCompoundInsert(ckey, ctype) {
	
	var qparam = JSON.stringify([ckey, ctype]);
  	console.log(qparam);
  	return qparam;
  	
}

function fnCipherCompoundDelete(ckey, ctype){
	
	var qparam = JSON.stringify([  ckey ]);
	console.log(qparam);
	return qparam;
}

function addNewCipherForm(dt_cipher){
	
	var currentPage = dt_cipher.page();
	var currentRow = dt_cipher.page.info();
	
	//insert a test row
	dt_cipher.row.add({
			"xeai_cipher_key" : null,
			"xeai_cipher_type" : null
	}).draw();
	    
	//move added row to desired index (here the row we clicked on)
	var index = dt_cipher.row(this).index(),
	rowCount = dt_cipher.data().length-1,
	insertedRow = dt_cipher.row(rowCount).data(),
	tempRow;

	for (var i=rowCount;i>index;i--) {
		tempRow = dt_cipher.row(i-1).data();
		dt_cipher.row(i).data(tempRow);
		dt_cipher.row(i-1).data(insertedRow);
	}     
	//refresh the page
	dt_cipher.page(currentRow.pages).draw();
	var oTable = $('#tbl-cipher').dataTable();
	oTable.fnPageChange( 'last' );
	
}


function fnUpdateFormRenderCipher(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Key</td>'
			+ '	<td><input type="text" id="xeai-cipher-key-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true" ></input></td>'
			
			+ '	<td>Type</td>'
			+ '	<td><input type="text" id="xeai-cipher-type-'
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