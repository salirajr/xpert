$(document).ready(function() {
	
	$.ajax({url: "../../reference/get/dtsource/calibrate.htm", 
		success: function(result){
			var jsonResult = "[";
			var initLoads = null;
			var arr = JSON.parse(result);
			for(var i = 0; i < arr.length; i++) {
				jsonResult += "[\""+arr[i].file_name+"\", \""+arr[i].file_location+"\"]";
				if(i == 0)
					initLoads = arr[i].file_location+arr[i].file_name;
				if(i < arr.length - 1 )
					jsonResult += ",";
			}
			jsonResult += "]";
			
			if(initLoads != null || initLoads != ''){
				viewDtFile(initLoads);
			}
			
			$('#dtsource').dataTable({
				"autoWidth": false,
				"bPaginate":false,
				"bFilter" : false,
				"bInfo" : false,
				"scrollX": true,
				"aaData" : JSON.parse(jsonResult)
			});
			
			 $('#dtsource tbody').on('click', 'tr', function () {
				var file = $('td', this).eq(1).text()+$('td', this).eq(0).text();
				viewDtFile(file);
			 });
			 
			 $('#download').on('click', function(e) {
				var file = $('#file-lable').html();
				location.href = "../../get/base/file.htm?path="+file;
			 });
			 
			 $('#link').on('click', function(e){
				var file = $('#file-lable').html();
				$("#base").val("base");
				$("#file").val(file);
				
				$("#aflinkage").attr("method","post");
				$("#aflinkage").submit();
			 });
			 
    }});
	
	function viewDtFile(file){
		$('#file-lable').html(file);
		$( "#dtsource-content-container" ).empty();
		$( "#dtsource-content-container" ).append($("<table id='dtsource-content' class='table table-striped table-bordered table-hover dataTables-example'></table>"));
		$.ajax({url: "../../reference/get/dtsource/calibrate/content.htm?file="+file, 
			success: function(result){
				var jsonResult = JSON.parse(result);
				
				var arr = jsonResult.tableKey;
				var arrHead = "[";
				for(var i = 0; i < arr.length; i++) {
					arrHead += "{\"sTitle\":\""+arr[i]+"\"}";
					if(i < arr.length - 1 )
						arrHead += ",";
				}
				arrHead += "]";
				console.log(arrHead);
				
				$('#dtsource-content').dataTable({
					"autoWidth": false,
					"bPaginate" : true,
					"bInfo" : false,
					"scrollX": true,
					"bLengthChange":false,
					"aoColumns" : JSON.parse(arrHead),
					"aaData" : jsonResult.data
				});
				
				$('#dtsource-content_filter').css('float','right');
				$('#dtsource-content_paginate').css('float','right')
	    }});
	}
	
});
