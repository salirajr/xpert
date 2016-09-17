$(document).ready(function() {
	var rows_dt_lookuparchomp = 0;
	var dt_lookuparchomp = $('#tbl-lookup-archcomp').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 40,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2, 3 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/lookuparchcomp-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = fnLookupArchompCompound();
			rows_dt_lookuparchomp = oSettings._iDisplayStart;
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               }, {
		            	   mData : "name"
		               }, {
		            	   mData : "key"
		               }, {
		            	   mData : "value"
		               } ]
		
	});
	
	var rows_dt_lookupdata = 0;
	var dt_lookupdata = $('#tbl-lookup-data').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 40,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2, 3 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/lookupdata-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = fnLookupDataCompound();
			rows_dt_lookupdata = oSettings._iDisplayStart;
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               }, {
		            	   mData : "name"
		               }, {
		            	   mData : "key"
		               }, {
		            	   mData : "value"
		               } ]
		
	});
	
	var rows_dt_cipher = 0;
	var dt_cipher = $('#tbl-cipher').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 25,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/cipher-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = fnCipherCompound();
			rows_dt_cipher = oSettings._iDisplayStart;
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               }, {
		            	   mData : "xeai_cipher_key"
		               }, {
		            	   mData : "xeai_cipher_type"
		               } ]
	});
	var rows_dt_cipherrule = 0;
	var dt_cipherrule = $('#tbl-cipher-rule').DataTable({
		"bPaginate" : true,
		"pagingType" : "simple",
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 25,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/cipherrule-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var chiperKey = $('#cipherKey').val();
			if(chiperKey==='')chiperKey=0;
			
			var qparam = JSON.stringify([ chiperKey,"",""]);
			
			rows_dt_cipherrule = oSettings._iDisplayStart;
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               },
		               {
		            	   mData : "xeai_cipher"
		               },
		               {
		            	   mData : "xeai_cipher_parameters"
		               } ]
		
	});
	
	var rows_dt_context = 0;
	var dt_context = $('#tbl-context').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 25,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/context-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = fnContextCompound();
			rows_dt_context = oSettings._iDisplayStart;
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               }, {
		            	   mData : "context_site"
		               }, {
		            	   mData : "context_alias"
		               } ]
		
	});
	
	var dt_group = $('#tbl-group').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 25,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/group-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = fnLookupGroupCompound();
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               }, {
		            	   mData : "group_key"
		               }, {
		            	   mData : "group_identifier"
		               } ]
		
	});
	
	var dt_owner = $('#tbl-owner').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 25,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/owner-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = fnOwnerCompound();
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               }, {
		            	   mData : "name"
		               }, {
		            	   mData : "alias"
		               }, {
		            	   mData : "baseuri"
		               }, {
		            	   mData : "domain"
		               } ]
		
	});
	var dt_xeai_message_versioning = $('#tbl-message-versioning').DataTable({
		"bPaginate" : true,
		"bInfo" : true,
		"bLengthChange" : false,
		"bAutoWidth" : false,
		"processing" : true,
		"bServerSide" : false,
		"bInfo" : false,
		"bFilter" : false,
		"order" : [],
		"pageLength" : 25,
		"columnDefs" : [ {
			"bSortable" : false,
			"aTargets" : [ 0, 1, 2 ]
		} ],
		"sAjaxSource" : "../dautl/extract/rows/message-versioning-nonl.do",
		"fnServerData" : function(sSource,
				aoData, fnCallback, oSettings) {
			
			var qparam = fnMessageVersioningCompound() ;
			
			$.ajax({
				"type" : "POST",
				"url" : sSource,
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
				}
			});
		},
		"aoColumns" : [
		               {
		            	   mData : "no",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return "<a class='details-control'>"
		            		   + data + "</a>";
		            	   }
		               }, {
		            	   mData : "message_name"
		               }, {
		            	   mData : "message_group"
		               }, {
		            	   mData : "message_type"
		               }, {
		            	   mData : "message_template",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return fnTextAreaRendering(
		            				   data, 15, 30, 2);
		            	   }
		               }, {
		            	   mData : "message_sample",
		            	   mRender : function(data,
		            			   type, full) {
		            		   return fnTextAreaRendering(
		            				   data, 15, 30, 2);
		            	   }
		               }, {
		            	   mData : "version"
		               }, {
		            	   mData : "description"
		               } ]
		
	});
	
	$('.nav-tabs a[href="#tab-4"]').tab('show')
	$('#lbl-reference-header').html('-'+ $('.nav-tabs .active').text().trim().replace(/\s\s+/g, ' '));
	$('a[data-toggle="tab"]').on('shown.bs.tab',function(e) {
		$('#lbl-reference-header').html('-'+ $('.nav-tabs .active').text().trim().replace(/\s\s+/g,' '));
	});
					
//					=================================================== tbl-contex =======================================================
	$('#tbl-context tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteCtx(rowIdx,dt_context,'update');
	});
	$('#tbl-context tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#context-site-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteCtx(rowIdx,dt_context,'delete');
		}
	});
//					=================================================== tbl-lookup-archcomp =======================================================					
	$('#tbl-lookup-archcomp tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteLa(rowIdx,dt_lookuparchomp,'update');
	});
	$('#tbl-lookup-archcomp tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#lookuparchomp-name-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteLa(rowIdx,dt_lookuparchomp,'delete');
		}
	});
//					=================================================== tbl-lookup-data =======================================================					
	$('#tbl-lookup-data tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteLd(rowIdx,dt_lookupdata,'update');
	});
	$('#tbl-lookup-data tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#lookupdata-name-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteLd(rowIdx,dt_lookupdata,'delete');
		}
	});		
//					=================================================== tbl-cipher =======================================================					
	$('#tbl-cipher tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteCipher(rowIdx,dt_cipher,dt_cipherrule,'update');
	});
	$('#tbl-cipher tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#xeai-cipher-key-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteCipher(rowIdx,dt_cipher,dt_cipherrule,'delete');
		}
	});		
//					=================================================== tbl-cipher-rule =======================================================					
	$('#tbl-cipher-rule tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteCipherRule(rowIdx,dt_cipherrule,'update');
	});
	$('#tbl-cipher-rule tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#xeai_cipher-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteCipherRule(rowIdx,dt_cipherrule,'delete');
		}
	});	
//					=================================================== tbl-group =======================================================					
	$('#tbl-group tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteGroup(rowIdx,dt_group,'update');
	});
	$('#tbl-group tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#group-key-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteGroup(rowIdx,dt_group,'delete');
		}
	});	
//					=================================================== tbl-owner =======================================================					
	$('#tbl-owner tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteOwner(rowIdx,dt_owner,'update');
	});
	$('#tbl-owner tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#owner-name-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteOwner(rowIdx,dt_owner,'delete');
		}
	});
//					=================================================== tbl-message-versioning =======================================================					
	$('#tbl-message-versioning tbody').on('click', 'button.act-update',function() {
		var rowIdx = this.value;
		insertUpdateDeleteMessageVersioning(rowIdx,dt_xeai_message_versioning,'update');
	});
	$('#tbl-message-versioning tbody').on('click', 'button.act-delete',function() {								
		var rowIdx = this.value;
		var answer = confirm("Do you want to Delete This Record "+$('#message-name-' + rowIdx).val()+" ?")
		if (answer){
			insertUpdateDeleteMessageVersioning(rowIdx,dt_xeai_message_versioning,'delete');
		}
	});						
	
	$('#addNew').on('click', function(e) {
		var tabActive = $('.nav-tabs .active').text();
		if(tabActive.trim()==='LOOKUP ARCHCOMP'){
			addNewLookupArchompForm(dt_lookuparchomp);
		}else if(tabActive.trim()==='LOOKUP DATA'){
			addNewLookupDataForm(dt_lookupdata);
		}else if(tabActive.trim()==='CIPHER'){
			addNewCipherForm(dt_cipher);
		}else if(tabActive.trim()==='GROUP'){
			addNewGroupForm(dt_group);
		}else if(tabActive.trim()==='MESSAGE VERSION'){
			addNewMessageVersioningForm(dt_xeai_message_versioning);
		}else if(tabActive.trim()==='OWNER'){
			addNewOwnerForm(dt_owner);
		}
	});
	
	$('#addCipherRule').on('click', function(e) {
		addNewCipherRuleForm(dt_cipherrule);
	});
	
	$('#ln-context-addcontext').click(function() {
		addNewContextForm(dt_context);								
	});
	
	$('#search-dialog').on('click', function(e) {
		var tabActive = $('.nav-tabs .active').text();
		console.log(tabActive.trim());
		if(tabActive.trim()==='LOOKUP ARCHCOMP'){
			fnAdjustDialog("LOOKUP ARCHCOMP SEARCH","cnt-lookup-archomp",
					"cnt-context","cnt-lookup-data",
					"cnt-cipher","cnt-group",
					"cnt-message-versioning","cnt-owner");
		}else if(tabActive.trim()==='LOOKUP DATA'){
			fnAdjustDialog("LOOKUP DATA SEARCH","cnt-lookup-data",
					"cnt-context","cnt-lookup-archomp",
					"cnt-cipher","cnt-group",
					"cnt-message-versioning","cnt-owner");
		}else if(tabActive.trim()==='CIPHER'){
			fnAdjustDialog("CIPHER SEARCH","cnt-cipher",
					"cnt-context","cnt-lookup-archomp",
					"cnt-lookup-data","cnt-group",
					"cnt-message-versioning","cnt-owner");
		}else if(tabActive.trim()==='GROUP'){
			fnAdjustDialog("GROUP SEARCH","cnt-group",
					"cnt-context","cnt-lookup-archomp",
					"cnt-lookup-data","cnt-cipher",
					"cnt-message-versioning","cnt-owner");
		}else if(tabActive.trim()==='MESSAGE VERSION'){
			fnAdjustDialog("MESSAGE VERSION SEARCH","cnt-message-versioning",
					"cnt-context","cnt-lookup-archomp",
					"cnt-lookup-data","cnt-cipher",
					"cnt-group","cnt-owner");
		}else if(tabActive.trim()==='OWNER'){
			fnAdjustDialog("OWNER SEARCH","cnt-owner",
					"cnt-context","cnt-lookup-archomp",
					"cnt-lookup-data","cnt-cipher",
					"cnt-group","cnt-message-versioning");
		}else if(tabActive.trim()==='CONTEXT'){
			fnAdjustDialog("CONTEXT SEARCH","cnt-context",
					"cnt-lookup-archomp","cnt-lookup-data",
					"cnt-cipher","cnt-group",
					"cnt-message-versioning","cnt-owner");
		}
		
	});
	
	$('#btnSearchEvent').on('click', function(e) {
		var tabActive = $('.nav-tabs .active').text();
		console.log(tabActive.trim());
		if(tabActive.trim()==='LOOKUP ARCHCOMP'){
			dt_lookuparchomp.ajax.reload();
		}else if(tabActive.trim()==='LOOKUP DATA'){
			dt_lookupdata.ajax.reload();
		}else if(tabActive.trim()==='CIPHER'){
			dt_cipher.ajax.reload();
		}else if(tabActive.trim()==='GROUP'){
			dt_group.ajax.reload();
		}else if(tabActive.trim()==='MESSAGE VERSION'){
			dt_xeai_message_versioning.ajax.reload();
		}else if(tabActive.trim()==='OWNER'){
			dt_owner.ajax.reload();
		}else if(tabActive.trim()==='CONTEXT'){
			dt_context.ajax.reload();
		}
		toastr.info("Queried Done!");
	});
	
	$('#clear-all').on('click', function(e) {
		clearFormSearch();
		
		dt_lookuparchomp.ajax.reload();
		dt_lookupdata.ajax.reload();
		dt_cipher.ajax.reload();
		dt_group.ajax.reload();
		dt_xeai_message_versioning.ajax.reload();
		dt_owner.ajax.reload();
		dt_context.ajax.reload();
		
		toastr.info("Refresh Done!");
	});
	
	$("a[href='#tab-1']").on('shown.bs.tab', function(e) {
		$('#addNew').show();
	});
	$("a[href='#tab-2']").on('shown.bs.tab', function(e) {
		$('#addNew').show();
	});
	$("a[href='#tab-3']").on('shown.bs.tab', function(e) {
		$('#addNew').show();
	});
	$("a[href='#tab-4']").on('shown.bs.tab', function(e) {
		$('#addNew').hide();
	});
	$("a[href='#tab-6']").on('shown.bs.tab', function(e) {
		$('#addNew').show();
	});
	$("a[href='#tab-7']").on('shown.bs.tab', function(e) {
		$('#addNew').show();
	});
	$("a[href='#tab-8']").on('shown.bs.tab', function(e) {
		$('#addNew').show();
	});
	showHideButtonAdd();
	
	context(dt_context);
	lookuparchomp(dt_lookuparchomp);
	lookupdata(dt_lookupdata);
	chiper(dt_cipher,dt_cipherrule);
	cipherRule(dt_cipherrule);
	group(dt_group);
	owner(dt_owner);
	messageVersioning(dt_xeai_message_versioning);
	
});

function showHideButtonAdd(){
	var tabActive = $('.nav-tabs .active').text();
	console.log(tabActive.trim());
	if(tabActive.trim()==='CONTEXT'){
		$('#addNew').hide();
	}else{
		$('#addNew').show();
	}
};

function context(dt_context){	
	$('#tbl-context tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_context.row(tr);
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRenderContext(rowIdx)).show();
			tr.addClass('shown');
			
			$('#context-site-' + rowIdx).val(data.context_site);
			$('#context-alias-' + rowIdx).val(data.context_alias);
			$('#context-description-' + rowIdx).val(data.context_description);
			if(data.is_existing!=1){
				getContextSequence("context-sequence",'context-code-' + rowIdx);
			}else{
				$('#context-code-' + rowIdx).val(data.context_code);
			}	
		}	
	});	
}	

function lookuparchomp(dt_lookuparchomp){	
	$('#tbl-lookup-archcomp tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_lookuparchomp.row(tr);
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRenderLookupArchomp(rowIdx)).show();
			tr.addClass('shown');
			$('#lookuparchomp-key-' + rowIdx).val(data.key);
			$('#lookuparchomp-name-' + rowIdx).val(data.name);
			$('#lookuparchomp-value-'+ rowIdx).val(data.value);
			$('#lookuparchomp-remarks-'+ rowIdx).val(data.remarks);
			
			if(data.is_existing!=1){
				$('#lookuparchomp-key-' + rowIdx).attr('readonly', false);
				$('#lookuparchomp-name-' + rowIdx).attr('readonly', false);
			}
		}
	});
}	

function lookupdata(dt_lookupdata){	
	$('#tbl-lookup-data tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_lookupdata.row(tr);
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRenderLookupData(rowIdx)).show();
			tr.addClass('shown');
			
			$('#lookupdata-key-' + rowIdx).val(data.key);
			$('#lookupdata-name-' + rowIdx).val(data.name);
			$('#lookupdata-value-'+ rowIdx).val(data.value);
			$('#lookupdata-remarks-'+ rowIdx).val(data.remarks);
			
			if(data.is_existing!=1){
				$('#lookupdata-key-' + rowIdx).attr('readonly', false);
				$('#lookupdata-name-' + rowIdx).attr('readonly', false);
			}
		}
	});
}

function chiper(dt_cipher,dt_cipherrule){	
	$('#tbl-cipher tbody').on('click','a.details-control',function() {		
		if ($(this).hasClass('active')) {
			$(this).removeClass('active');
		} else {
			var tr = $(this).closest('tr');
			var row = dt_cipher.row(tr);
			
			dt_cipher.$('tr.active').removeClass('active');
			$(tr).addClass('active');
			// Open this row
			var rowIdx = row.index();
			var data = row.data();

			$('#cipherKey').val(data.xeai_cipher_key);
			$('#cipher-key').val(data.xeai_cipher_key);
			
			dt_cipherrule.ajax.reload();
			
			if (row.child.isShown()) {
				// This row is already open - close it
				row.child.hide();
				tr.removeClass('shown');
			} else {
				// Open this row
				var rowIdx = row.index();
				var data = row.data();
				row.child(fnUpdateFormRenderCipher(rowIdx)).show();
				tr.addClass('shown');				
				console.log(data);				
				$('#xeai-cipher-key-' + rowIdx).val(data.xeai_cipher_key);
				$('#xeai-cipher-type-' + rowIdx).val(data.xeai_cipher_type);				
				if(data.is_existing!=1){
					$('#xeai-cipher-key-' + rowIdx).attr('readonly', false);
				}
			}
		}
	});
}

function cipherRule(dt_cipherrule){	
	$('#tbl-cipher-rule tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_cipherrule.row(tr);
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRenderCipherRule(rowIdx)).show();
			tr.addClass('shown');
			
			$('#xeai_cipher-' + rowIdx).val(data.xeai_cipher);
				$('#xeai_cipher_key-' + rowIdx).val(data.xeai_cipher_key);
				$('#xeai_cipher_parameters-' + rowIdx).val(data.xeai_cipher_parameters);
				$('#xeai_cipher_group-' + rowIdx).val(data.xeai_cipher_group);
				
				if(data.is_existing!=1){
					$('#xeai_cipher-' + rowIdx).attr('readonly', false);
				}
				fnInitiateCbxReference("group", "xeai_cipher_group-"+rowIdx,data.xeai_cipher_group,"%CIPHER-SEGMENTS%");
		}
	});
}

function group(dt_group){
	$('#tbl-group tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_group.row(tr);
		
		// Open this row
		var rowIdx = row.index();
		var data = row.data();
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRenderGroup(rowIdx)).show();
			tr.addClass('shown');
			$('#group-key-' + rowIdx).val(data.group_key);
			$('#group-identifier-' + rowIdx).val(data.group_identifier);
			
			if(data.is_existing!=1){
				$('#group-key-' + rowIdx).attr('readonly', false);
			}
		}
	});
}

function owner(dt_owner){
	$('#tbl-owner tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_owner.row(tr);
		
		// Open this row
		var rowIdx = row.index();
		var data = row.data();
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRenderOwner(rowIdx)).show();
			tr.addClass('shown');
			$('#owner-name-' + rowIdx).val(data.name);
			$('#owner-alias-' + rowIdx).val(data.alias);
			$('#owner-baseuri-' + rowIdx).val(data.baseuri);
			$('#owner-domain-' + rowIdx).val(data.domain);
			
			if(data.is_existing!=1){
				$('#owner-name-' + rowIdx).attr('readonly', false);
			}
		}
	});
}

function messageVersioning(dt_xeai_message_versioning){	
	$('#tbl-message-versioning tbody').on('click','a.details-control',function() {
		var tr = $(this).closest('tr');
		var row = dt_xeai_message_versioning.row(tr);
		
		// Open this row
		var rowIdx = row.index();
		var data = row.data();
		
		if (row.child.isShown()) {
			// This row is already open - close
			// it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			var rowIdx = row.index();
			var data = row.data();
			row.child(fnUpdateFormRenderMessageVersioning(rowIdx)).show();
			tr.addClass('shown');
			
			$('#message-name-' + rowIdx).val(data.message_name);
			$('#message-group-' + rowIdx).val(data.message_group);
			$('#message-type-' + rowIdx).val(data.message_type);
			$('#message-template-' + rowIdx).val(data.message_template);
			$('#message-sample-' + rowIdx).val(data.message_sample);
			$('#message-version-' + rowIdx).val(data.version);
			$('#message-description-' + rowIdx).val(data.description);
					
			if(data.is_existing!=1){
				$('#message-name-' + rowIdx).attr('readonly', false);
				$('#message-group-' + rowIdx).attr('readonly', false);
			}
		}
	});
}

function fnTextAreaRendering(data, minLength, cols, rows) {
	if (data == null || data == '')
		return data;
	else if (data.length <= minLength)
		return data;
	else {
		var result = "<textarea cols='" + cols + "' rows='" + rows
		+ "' class='normal-hlight' readonly >" + data + "</textarea>";
		return result;
	}
}

function fnInitiateCbxReference(key, cbxId,value,parameter) {
	var param = JSON.stringify([]);
	if(key==='group') param = JSON.stringify([parameter]);
	$.ajax({
		type : "POST",
		url : "../dautl/retrieve/json/" + key + ".do",
		data : param,
		contentType : "application/json",
		dataType : "json",
		async : true,
		success : function(respond) {
			var objSelect = document.getElementById(cbxId);
			$.each(respond.payload, function(i, row) {
				opt = document.createElement("option");
				opt.value = row.key;
				opt.text = row.key;
				objSelect.appendChild(opt);
			});
			$('#' + cbxId).val(value);
		},
		error : function(msg) {
			toastr.error("Context failed to Load, no reference of " + cbxId
					+ " available");
		}
	});
}

function fnAdjustDialog(headerValue,active,nonactive1,nonactive2,nonactive3,nonactive4,nonactive5,nonactive6) {
	$(".modal-title").text(headerValue);
	$('#'+active).show();
	$('#'+nonactive1).hide();
	$('#'+nonactive2).hide();
	$('#'+nonactive3).hide();
	$('#'+nonactive4).hide();
	$('#'+nonactive5).hide();
	$('#'+nonactive6).hide();
	$('#mdl-act-component').modal('show');
}

function fnLookupArchompCompound() {	
	var archompNameId='';
	var archompKeyId='';	
	if ($('#archompNameId').val() != '') {
		archompNameId = '%'+$('#archompNameId').val()+'%';
	}
	if ($('#archompKeyId').val() != '') {
		archompKeyId = '%'+$('#archompKeyId').val()+'%';
	}	
	var qparam = JSON.stringify([ archompNameId,archompKeyId]);
	console.log(qparam);
	return qparam;
	
}
function fnLookupDataCompound() {	
	var dataNameId='';
	var dataKeyId='';
	var dataValueId='';	
	if ($('#dataNameId').val() != '') {
		dataNameId = '%'+$('#dataNameId').val()+'%';
	}
	if ($('#dataKeyId').val() != '') {
		dataKeyId = '%'+$('#dataKeyId').val()+'%';
	}
	if ($('#dataValueId').val() != '') {
		dataValueId = '%'+$('#dataValueId').val()+'%';
	}	
	var qparam = JSON.stringify([ dataNameId,dataKeyId,dataValueId]);
	console.log(qparam);
	return qparam;
}
function fnCipherCompound() {
	var cipherKeyId='';
	var cipherTypeId='';	
	if ($('#cipherKeyId').val() != '') {
		cipherKeyId = '%'+$('#cipherKeyId').val()+'%';
	}
	if ($('#cipherTypeId').val() != '') {
		cipherTypeId = '%'+$('#cipherTypeId').val()+'%';
	}	
	var qparam = JSON.stringify([ cipherKeyId,cipherTypeId]);
	console.log(qparam);
	return qparam;
}
function fnContextCompound() {	
	var ctxSiteId='';
	var ctxAliasId='';	
	if ($('#ctxSiteId').val() != '') {
		ctxSiteId = '%'+$('#ctxSiteId').val()+'%';
	}
	if ($('#ctxAliasId').val() != '') {
		ctxAliasId = '%'+$('#ctxAliasId').val()+'%';
	}	
	var qparam = JSON.stringify([ ctxSiteId,ctxAliasId]);
	console.log('fnContextCompound=> '+qparam);
	return qparam;
}
function fnLookupGroupCompound() {	
	var groupKeyId='';
	var groupIdentifierId='';	
	if ($('#groupKeyId').val() != '') {
		groupKeyId = '%'+$('#groupKeyId').val()+'%';
	}
	if ($('#groupIdentifierId').val() != '') {
		groupIdentifierId = '%'+$('groupIdentifierId').val()+'%';
	}	
	var qparam = JSON.stringify([ groupKeyId,groupIdentifierId]);
	console.log(qparam);
	return qparam;
}
function fnMessageVersioningCompound() {	
	var messageNameId='';
	var messageGroupId='';
	var messageTypeId='';
	var messageDescriptionId='';	
	if ($('#messageNameId').val() != '') {
		messageNameId = '%'+$('#messageNameId').val()+'%';
	}
	if ($('#messageGroupId').val() != '') {
		messageGroupId = '%'+$('messageGroupId').val()+'%';
	}
	if ($('#messageTypeId').val() != '') {
		messageTypeId = '%'+$('#messageTypeId').val()+'%';
	}
	if ($('#messageDescriptionId').val() != '') {
		messageDescriptionId = '%'+$('messageDescriptionId').val()+'%';
	}	
	var qparam = JSON.stringify([ messageNameId,messageGroupId,messageTypeId,messageDescriptionId]);
	console.log(qparam);
	return qparam;
	
}
function fnOwnerCompound() {	
	var ownerNameId='';
	var ownerAliasId='';
	var ownewBaseUriId='';
	var ownerDoamainId='';	
	if ($('#ownerNameId').val() != '') {
		ownerNameId = '%'+$('#ownerNameId').val()+'%';
	}
	if ($('#ownerAliasId').val() != '') {
		ownerAliasId = '%'+$('ownerAliasId').val()+'%';
	}
	if ($('#ownewBaseUriId').val() != '') {
		ownewBaseUriId = '%'+$('#ownewBaseUriId').val()+'%';
	}
	if ($('#ownerDoamainId').val() != '') {
		ownerDoamainId = '%'+$('ownerDoamainId').val()+'%';
	}	
	var qparam = JSON.stringify([ ownerNameId,ownerAliasId,ownewBaseUriId,ownerDoamainId]);
	console.log(qparam);
	return qparam;	
}

function clearFormSearch(){
	$('#archompNameId').val('');
	$('#archompKeyId').val('');
	$('#dataNameId').val('');
	$('#dataKeyId').val('');
	$('#dataValueId').val('');
	$('#cipherKeyId').val('');
	$('#cipherTypeId').val('');
	$('#ctxSiteId').val('');
	$('#ctxAliasId').val('');
	$('#groupKeyId').val('');
	$('#groupIdentifierId').val('');
	$('#messageNameId').val('');
	$('#messageGroupId').val('');
	$('#messageTypeId').val('');
	$('#messageDescriptionId').val('');
	$('#ownerNameId').val('');
	$('#ownerAliasId').val('');
	$('#ownewBaseUriId').val('');
	$('#ownerDoamainId').val('');
}






