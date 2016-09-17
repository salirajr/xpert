$(document).ready(function() {	
	var editorContext = ace.edit("context-editor");
	editorContext.setTheme("ace/theme/tomorrow");
	editorContext.setAutoScrollEditorIntoView(true);
	editorContext.setOption("maxLines", 100);
	editorContext.session.setMode("ace/mode/xml");
	
	var editorRqo = ace.edit("msg-out-editor");
	editorRqo.setTheme("ace/theme/tomorrow");
	editorRqo.setAutoScrollEditorIntoView(true);
	editorRqo.setOption("maxLines", 100);
	editorRqo.session.setMode("ace/mode/json");

	var editorRpi = ace.edit("msg-in-editor");
	editorRpi.setTheme("ace/theme/tomorrow");
	editorRpi.setAutoScrollEditorIntoView(true);
	editorRpi.setOption("maxLines", 100);
	editorRpi.session.setMode("ace/mode/json");

	var dt_transform_rule = $('#dt-transform-rule').DataTable({
		"bPaginate" : false,
		"sPaginationType": "full_numbers",
		"bFilter": false,
		"bSearchable":false,
		"bInfo":false,
		"pageLength" : 1000,					
		"sAjaxSource" : "../dautl/extract/rows/transform-rule.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			var transform_id = $("#transform-id").val(); //by id
			if(transform_id==''||transform_id===''||
					transform_id==null||transform_id===null)transform_id=0;						
			var qparam = JSON.stringify([transform_id]);
			
			$.ajax({
				"type" : "POST",
				"url" : "../dautl/extract/rows/transform-rule.do",
				"contentType" : "application/json",
				"dataType" : "json",
				"data" : qparam,
				"sAjaxDataProp" : "payload",
				"success" : function(
						result) {
					result.iTotalDisplayRecords = result.nRow;
					result.aaData = result.payload;
					result.payload = null;
					result.iDisplayLength = oSettings._iDisplayLength;
					result.iDisplayStart = oSettings._iDisplayStart;
					fnCallback(result);
					// set template by transform id
					qparam = JSON.stringify([transform_id,"",1000,0]);
					$.ajax({
						"type" : "POST",
						"url" : "../dautl/extract/rows/transform.do",
						"contentType" : "application/json",
						"dataType" : "json",
						"data" : qparam,
						"sAjaxDataProp" : "payload",
						"success" : function(result) {
							if(result.payload.length>0){													
								editorRqo.getSession().setValue(result.payload[0].transform_template);
								document.getElementById("setTemplate").value = result.payload[0].transform_template;
								document.getElementById("setTemplate").innerText = result.payload[0].transform_template
								editorRqo.setReadOnly(true);
								var splitFromTo = result.payload[0].transform_type.split('/');
								$('#from').val(splitFromTo[0]);
								$('#to').val(splitFromTo[1]);
								setEditorType($('#from').val(),$('#to').val()
										,editorRpi,editorRqo);
							}
						}
					});
					
				},
				error : function(msg) {
					var data = {
							"iTotalRecords" : 0,
							"iTotalDisplayRecords" : 0,
							"aData" : [ {
								"transform_rule_id" : "",
								"transform_rule_source" : "",
								"transform_rule_target" : "",
								"transform_rule_config" : "",
								"transform_rule_matrix" : ""
							} ]
					};
					return JSON.stringify(data);
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "transform_rule_id",
		            	   mRender : function(data,
		            			   type, full) {
										return "<a class='details-control'>"
										+ data + "</a>";
								}	
							},
							{
								mData : "transform_rule_source"
							},
							{
								mData : "transform_rule_target"
							},
							{
								mData : "transform_rule_config"
							},
							{
								mData : "transform_rule_matrix"
							} 
						]
		});
	
	$('#dt-transform-rule tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_transform_rule.row(tr);
		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRender(rowIdx)).show();
			tr.addClass('shown');
			$('#transform_rule_id-' + rowIdx).val(
							data.transform_rule_id);
			$('#transform_rule_source-' + rowIdx).val(
							data.transform_rule_source);
			$('#transform_rule_target-' + rowIdx).val(
							data.transform_rule_target);
			$('#transform_rule_config-' + rowIdx).val(
							data.transform_rule_config);
			$('#transform_rule_matrix-' + rowIdx).val(
							data.transform_rule_matrix);			            
		}
	});	
	
	$('#dt-transform-rule tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		var data={
				"transform_rule_id" : Number($('#transform_rule_id-' + rowIdx).val()),
				"transform_rule_source" : $('#transform_rule_source-' + rowIdx).val(),
				"transform_rule_target" : $('#transform_rule_target-' + rowIdx).val(),
				"transform_rule_config" : Number($('#transform_rule_config-' + rowIdx).val()),
				"transform_rule_matrix" : $('#transform_rule_matrix-' + rowIdx).val()
		}
		
		dt_transform_rule.row(rowIdx).data(data);
		var row = dt_transform_rule.row(rowIdx);
		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
		}
	});
	
	$('#dt-transform-rule tbody').on('click', 'button.act-delete',function() {
		var rowIdx = this.value;
		var tempDataRule = dt_transform_rule.rows().data();
		
		var tempTransformId = $("#transform-id").val(); //by id;
		var action = $("#actionTemp").val();
		var dataParam;
				
		if(tempDataRule[rowIdx].is_exist!=1){
			dt_transform_rule.row(rowIdx).remove().draw( false );
			for ( i = 0 ; i < tempDataRule.length ; i++){
				var row = dt_transform_rule.row(i);
				if (row.child.isShown()) {
					// This row is already open - close it
					row.child.hide();
				}
			}
		}else if(action===tempTransformId){
			action = 'delete';
			dataParam = fnCompoundDelete("transform-rule",tempTransformId,tempDataRule[rowIdx].transform_rule_id,'','','','');
			var answer = confirm("Do you want to Delete This Record "+tempDataRule[rowIdx].transform_rule_id+" ?")
			if (answer){
				deleteTransformRule(dataParam,action,dt_transform_rule);
			}
		}
	});
	
	$('#addNew').click(function() {
		if(cekNewRule(dt_transform_rule)){
			var answer = confirm("Do you want to Save This Data ?");
			if (answer){
				insertUpdate();
			}else{
				window.location.href = '../transform/home.do';
			}
		}else{
			window.location.href = '../transform/home.do';
		}
	});
	
	$('#btn-detail-transform-rule').click(function() {
		if(cekNewRule(dt_transform_rule)){
			var answer = confirm("Do you want to Save This Data ?");
			if (answer){
				insertUpdate();
			}else{
				window.location.href = '../transform/detail_transform_rule.do';
			}
		}else{
			window.location.href = '../transform/detail_transform_rule.do';
		}
	});

	var counter =1;
	$('#addData').click(function() {
		var currentPage = dt_transform_rule.page();
		var currentRow = dt_transform_rule.page.info();
		var lastRows = $('table#dt-transform-rule tr:last td:first').text();
		if(lastRows!='No data available in table'){
			lastRows++;
			counter = lastRows;
		}
	    //insert a test row
		dt_transform_rule.row.add({
			"transform_rule_id" : counter,
			"transform_rule_source" : null,
			"transform_rule_target" : null,
			"transform_rule_config" : 0,
			"transform_rule_matrix" : null
		}).draw();
		
		//move added row to desired index (here the row we clicked on)
		var index = dt_transform_rule.row(this).index(),
		rowCount = dt_transform_rule.data().length-1,
		insertedRow = dt_transform_rule.row(rowCount).data(),
		tempRow;

		for (var i=rowCount;i>index;i--) {
			tempRow = dt_transform_rule.row(i-1).data();
			dt_transform_rule.row(i).data(tempRow);
			dt_transform_rule.row(i-1).data(insertedRow);
		}
		//refresh the page
		dt_transform_rule.page(currentRow.pages).draw();
		var oTable = $('#dt-transform-rule').dataTable();
		oTable.fnPageChange( 'last' );
	});	
	
	$('#setTemplate').click(function() {
		
		if($('#to').val()==='json'){				
			try{
				JSON.parse(editorRqo.getSession().getValue());
		    }catch(e){
		    	alert("Please check format template json");
		        return;
		    }
		}else if($('#to').val()==='xml'){
			var xmlString = editorRqo.getSession().getValue();
			if(!parseXml(xmlString)){
				alert("Please check format template xml");
		        return;
			}
		}
		
		if(document.getElementById("setTemplate").value==='SET'){
			if(editorRqo.getSession().getValue()===null||editorRqo.getSession().getValue()==null||
							editorRqo.getSession().getValue()===''||editorRqo.getSession().getValue()==='')return ;
			document.getElementById("setTemplate").value = editorRqo.getSession().getValue();
			document.getElementById("setTemplate").innerText = editorRqo.getSession().getValue();
			editorRqo.setReadOnly(true);
		}else{
			editorRqo.getSession().setValue(document.getElementById("setTemplate").value);
			document.getElementById("setTemplate").value = 'SET';
			document.getElementById("setTemplate").innerText = 'SET';
			editorRqo.setReadOnly(false);
		}
	});
	
	$('#send').click(function() {
		var data = dt_transform_rule.rows().data();
		var fromID = $('#from').val();
		var toID = $('#to').val();
		if(document.getElementById("setTemplate").value==='SET'){
			alert("Please SET Message-Out");
			return;
		}else if(data.length==0){
			alert("Please insert rule ");
			return;
		}else if(editorRpi.getSession().getValue()===null||editorRpi.getSession().getValue()==null
						|| editorRpi.getSession().getValue()===''||editorRpi.getSession().getValue()==''){
			alert("Please insert Message-In ");
			return;
		}else if($('#from').val()==='json'){			
			
			try{
				JSON.parse(editorRpi.getSession().getValue());
		    }catch(e){
		    	editorRqo.getSession().setValue('Failed to transform Message; nested exception is org.springframework.messaging.MessageHandlingException: nested exception is com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException: "Invalid json format source" captured with [ERRCD: 1100], ; Content is not allowed in prolog.'); //error in the above string(in this case,yes)!
		        return;
		    }
		}
		
		
		$('#bntlabelTemplate').innerText = editorRqo.getSession().getValue();
		
		var dataTemp = {
				context:editorContext.getSession().getValue(),
				payload:editorRpi.getSession().getValue(),
				transformTemplate:document.getElementById("setTemplate").value,
				transformType:$('#to').val(),
				transformData:[],
				from:fromID,
				to:toID
		};
		for ( i = 0 ; i < data.length ; i++){
			var temp={
					"transformRuleId" : data[i].transform_rule_id,
					"transformRuleSource" : data[i].transform_rule_source,
					"transformRuleTarget" : data[i].transform_rule_target,
					"transformRuleConfig" : data[i].transform_rule_config,
					"transformRuleMatrix" : data[i].transform_rule_matrix
			};
			dataTemp.transformData.push(temp);
					
		}
		var qparam = JSON.stringify(dataTemp);
		
		$.ajax({
			type : "POST",
			url : "../transform/"+fromID+"/"+toID+"/context.do",				
			data : qparam,
			contentType : "application/json",
			dataType : "json",
			async : true,
			success : function(respond) {
				editorRqo.getSession().setValue(respond.payload);
			},
			error : function(err) {
				editorRqo.getSession().setValue(respond.message);
			}
		});
	});
	
	
	$('#from').change(function() {
		payloadType = this.value;
		editorRpi.session.setMode("ace/mode/"+payloadType);
	});
	$('#to').change(function() {
		payloadType = this.value;
		editorRqo.session.setMode("ace/mode/"+payloadType);
	});
	
	document.getElementById("setTemplate").innerText = 'SET';
	document.getElementById("setTemplate").value = 'SET';	
	
	function setContextEditor() {
		var parameters = new Array();
		parameters.push("212");
		var requestBody = JSON.stringify(parameters);
		$.ajax({
			type : "POST",
			url : "../dautl/retrieve/json/contextTransform.do",
			data : requestBody,
			contentType : "application/json",
			dataType : "json",
			async : true,
			success : function(respond) {
				editorContext.getSession().setValue(
				respond.payload.context);
			},
			error : function(err) {
			}
		});
	}
	
	 setContextEditor();
	 $('#panel-context').hide();
	 
	 $('#btn-save').click(function() {
		 insertUpdate();
	 });
	 
	 function insertUpdate() {
		 var tempDataRule = dt_transform_rule.rows().data();
		 if(document.getElementById("setTemplate").value==='SET'){
			 alert("Please SET Message-Out");
			 return null;
		 }else if(tempDataRule.length==0){
			alert("Please insert rule ");
			return null;
		 }
		 var fromID = $('#from').val();
		 var toID = $('#to').val();
		 var tempTemplate = $('#setTemplate').val();
		 var tempTransformId = $("#transform-id").val(); //by id;
		 var tempType = fromID+"/"+toID;
		 var action = $("#actionTemp").val();
		 var mainAction ='';
		 var dataParam;
		 
		 if(action===''){
			 action = 'insert';
			 mainAction = 'insert';
			 var retVal = prompt("Enter your transform id : ");
			 tempTransformId = retVal;						
			 dataParam = fnCompoundInsert("transform",tempTransformId,tempTemplate,tempType,'','','');
		 }else if(action===tempTransformId){
			 action = 'update';
			 mainAction = 'update';
			 dataParam = fnCompoundUpdate("transform",tempTransformId,tempTemplate,tempType,'','','');
		 }
		 
		 $.ajax({
			 type : "POST",
			 url : "../dml/execute/"+action+"/transform-"+action+".do",
			 data : dataParam,
			 contentType : "application/json",
			 dataType : "json",
			 async : true,
			 success : function(respond) {
				 
				 if(respond.message==='Succes '+action){					 
					 for ( i = 0 ; i < tempDataRule.length ; i++){						 
						 if(tempDataRule[i].is_exist===1){
							 action = 'update';
							 dataParam = fnCompoundUpdate("transform-rule",
									 tempTransformId,
									 tempDataRule[i].transform_rule_id,
									 tempDataRule[i].transform_rule_source,
									 tempDataRule[i].transform_rule_target,
									 tempDataRule[i].transform_rule_config,
									 tempDataRule[i].transform_rule_matrix);
									
							}else {
								action = 'insert';
								dataParam = fnCompoundInsert("transform-rule",
										tempTransformId,
										tempDataRule[i].transform_rule_id,
										tempDataRule[i].transform_rule_source,
										tempDataRule[i].transform_rule_target,
										tempDataRule[i].transform_rule_config,
										tempDataRule[i].transform_rule_matrix);
							}
							updateTransformRule(dataParam,action,i,tempDataRule.length,mainAction);
						}
					}
				},
				error : function(err) {
					alert(err.statusText+" "+err.status+" [table transform]");
				}
			});
	 }
	 
	 $('#btn-refresh').click(function() {
		 editorRpi.getSession().setValue('');
		dt_transform_rule.ajax.reload();
	 });
	
});

//My function that parses a string into an XML DOM, throwing an Error if XML parsing fails
function parseXml(xmlString) {
    var parser = new DOMParser();
    // attempt to parse the passed-in xml
    var dom = parser.parseFromString(xmlString, 'text/xml');
    var flag;
    if(isParseError(dom)) {
    	flag = false;
    }else{
    	flag = true;
    }
    return flag;
}

function isParseError(parsedDocument) {
    // parser and parsererrorNS could be cached on startup for efficiency
    var parser = new DOMParser(),
        errorneousParse = parser.parseFromString('<', 'text/xml'),
        parsererrorNS = errorneousParse.getElementsByTagName("parsererror")[0].namespaceURI;

    if (parsererrorNS === 'http://www.w3.org/1999/xhtml') {
        // In PhantomJS the parseerror element doesn't seem to have a special namespace, so we are just guessing here :(
        return parsedDocument.getElementsByTagName("parsererror").length > 0;
    }

    return parsedDocument.getElementsByTagNameNS(parsererrorNS, 'parsererror').length > 0;
};

function setEditorType(payloadTypeFrom,payloadTypeTo,editorRpi,editorRqo){
	editorRpi.session.setMode("ace/mode/"+payloadTypeFrom);
	editorRqo.session.setMode("ace/mode/"+payloadTypeTo);
}

function updateTransformRule(dataParam,action,i,max,mainAction){
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/transform-rule-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			if(i=== (max)-1){
				alert(respond.message);
				if(mainAction==='update'){
					window.location.href = '../transform/home.do?transform-id='+$('#transform-id').val();
				}else{
					window.location.href = '../transform/home.do';
				}
				
			}
			
		},
		error : function(err) {
			if(i=== (max)-1){
				alert(err.statusText+" "+err.status+" [table transform_rule]");
				if(mainAction==='update'){
					window.location.href = '../transform/home.do?transform-id='+$('#transform-id').val();
				}else{
					window.location.href = '../transform/home.do';
				}
			}
		}
	});
}

function deleteTransformRule(dataParam,action,dt_transform_rule){
	$.ajax({
		type : "POST",
		url : "../dml/execute/"+action+"/transform-rule-"+action+".do",
		data : dataParam,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			alert(respond.message);
			dt_transform_rule.ajax.reload();
			
		},
		error : function(err) {
			alert(err.statusText+" "+err.status+" [table transform_rule]");
		}
	});
}

function fnCompoundUpdate(tempTbaleName,tempTransformId,transform_rule_id,transform_rule_source,transform_rule_target,transform_rule_config,transform_rule_matrix) {
	if(tempTbaleName==='transform'){
		var qparam = JSON.stringify([transform_rule_id,transform_rule_source,tempTransformId]);
      	console.log(qparam);
      	return qparam;
	}else if(tempTbaleName==='transform-rule'){
		var qparam = JSON.stringify([ transform_rule_source,transform_rule_target,transform_rule_config,transform_rule_matrix,tempTransformId,transform_rule_id]);
      	console.log(qparam);
      	return qparam;
	};
	
}

function fnCompoundInsert(tempTbaleName,tempTransformId,transform_rule_id,transform_rule_source,transform_rule_target,transform_rule_config,transform_rule_matrix) {
	if(tempTbaleName==='transform'){
		var qparam = JSON.stringify([ tempTransformId,transform_rule_id,transform_rule_source]);
      	console.log(qparam);
      	return qparam;
	}else if(tempTbaleName==='transform-rule'){
		var qparam = JSON.stringify([ tempTransformId,transform_rule_id,transform_rule_source,transform_rule_target,transform_rule_config,transform_rule_matrix]);
      	console.log(qparam);
      	return qparam;
	}
}

function fnCompoundDelete(tempTbaleName,tempTransformId,transform_rule_id,transform_rule_source,transform_rule_target,transform_rule_config,transform_rule_matrix) {
	if(tempTbaleName==='transform'){
		var qparam = JSON.stringify([tempTransformId]);
      	console.log(qparam);
      	return qparam;
	}else if(tempTbaleName==='transform-rule'){
		var qparam = JSON.stringify([ tempTransformId,transform_rule_id]);
      	console.log(qparam);
      	return qparam;
	};
}

function cekNewRule(dt_transform_rule){
	var flag = false;
	var tempDataRule = dt_transform_rule.rows().data();	
	for ( i = 0 ; i < tempDataRule.length ; i++){
		if(tempDataRule[i].is_exist!=1){
			flag = true;
		}
	}	
	return flag;
}




function fnUpdateFormRender(rowId) {
	// `d` is the original data object for the row
	var table = '<table class="hidden-form">' + '<tr>'
			+ '	<td>Seq</td>'
			+ '	<td><input type="text" id="transform_rule_id-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm" readonly="true"></input></td>'
			+ '	<td></td>'
			
			+ '	<td>Rule Source</td>'
			+ '	<td><input type="text" id="transform_rule_source-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			+ '	<td></td>'
			
			+ '	<td>Rule Target</td>'
			+ '	<td><input type="text" id="transform_rule_target-'
			+ rowId
			+ '" placeholder=""'
			+ '		class="form-control input-sm"></input></td>'
			+ '	<td></td>'
			
			+ '	<td>Config</td>'
			+ '	<td><select class="form-control input-sm" id="transform_rule_config-'
			+ rowId
			+ '">'
			+ '			<option value="0">0</option>'
			+ '			<option value="1">1</option>'
			+ '			<option value="2">2</option>'
			+ '	</select></td>'
			
			+ '	<td>Matrix</td>'
			+ '	<td><select class="form-control input-sm" id="transform_rule_matrix-'
			+ rowId
			+ '">'
			+ '			<option value=""></option>'
			+ '			<option value="1">1</option>'
			+ '			<option value="2">2</option>'
			+ '			<option value="3">3</option>'
			+ '			<option value="4">4</option>'
			+ '	</select></td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '	<td></td><td></td><td></td>'
			+ '	<td><button type="button" value="'
			+ rowId
			+ '" class="btn btn-primary btn-sm act-update">'
			+ '			<i class="fa fa-floppy-o"></i>'
			+ '		</button>'
			+'</td>'
			+'<td>'
			+ '		<button type="button" value="'
			+ rowId
			+ '" class="btn btn-warning btn-sm act-delete">'
			+ '			<i class="fa fa-times"></i>'
			+ '		</button></td>'
			+ '</tr>'
			+ '</table>';
	return table;
}