$(document).ready(function() {
	
	$.ajax({url: "../../reference/get/querymapper/calibrate.htm", 
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
				viewQmFile(initLoads);
			}
			
			$('#querymapper').dataTable({
				"autoWidth": false,
				"bPaginate":false,
				"bFilter" : false,
				"bInfo" : false,
				"scrollX": true,
				"aaData" : JSON.parse(jsonResult)
			});
			
			
			 $('#querymapper tbody').on('click', 'tr', function () {
				var file = $('td', this).eq(1).text()+$('td', this).eq(0).text();
				viewQmFile(file)
			 });
			 
			 $('#download').on('click', function(e) {
				var file = $('#file-lable').html();
				location.href = "../../get/base/file.htm?path="+file;
			 });
			 
			 $('#link').on('click', function(e) {
				var file = $('#file-lable').html();
				
				$("#base").val("base");
				$("#file").val(file);
				
				$("#aflinkage").attr("method","post");
				$("#aflinkage").submit();
			 });
			
    }});
	
	function viewQmFile(file){
		$( "#querymapper-content-container" ).empty();
		$( "#querymapper-content-container" ).append($("<table id='querymapper-content' class='table table-striped table-bordered table-hover dataTables-example'></table>"));
		$.ajax({url: "../../reference/get/querymapper/calibrate/content.htm?file="+file, 
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
				
				$('#file-lable').html(file);
				
				$('#querymapper-content').dataTable({
					"autoWidth": false,
					"bPaginate" : true,
					"bInfo" : false,
					"scrollX": true,
					"bLengthChange":false,
					"aoColumns" : JSON.parse(arrHead),
					"aaData" : jsonResult.data
				});
				
				$('#querymapper-content_filter').css('float','right');
				$('#querymapper-content_paginate').css('float','right')
	    }});
	}
	
});
