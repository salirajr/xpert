$(document).ready(function() {
	
	$.ajax({url: "../../reference/get/rms/calibrate.htm", 
		success: function(result){
			var jsonResult = "[";
			var arr = JSON.parse(result);
			for(var i = 0; i < arr.length; i++) {
				jsonResult += "[\""+arr[i].parameter_id+"\", \""+arr[i].parameter_desc+"\", \""+arr[i].parameter_value+"\"]";
				if(i < arr.length - 1 )
					jsonResult += ",";
			}
			jsonResult += "]";
			
			$('#rms-parameters').dataTable({
				"bFilter" : false,
				"autoWidth": false,
				"bInfo" : false,
				"scrollX": true,
				"bLengthChange":false,
				"sRowSelect": "single",
				"aaData" : JSON.parse(jsonResult)
			});
    }});
	
});
