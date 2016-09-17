$(document).ready(function() {

	$.ajax({
		url : "fabricates/files/RuntimeConfiguration.htm",
		success : function(result) {
			var jsonResult = "[";
			var arr = JSON.parse(result);
			for (var i = 0; i < arr.length; i++) {
				jsonResult += "[\""
						+ arr[i].file_name
						+ "\", \"<a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='rcfile-view'>Open</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='rcfile-download'>Replace</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='rcfile-remove'>Remove</a>\"]";
				if (i < arr.length - 1)
					jsonResult += ",";
			}
			jsonResult += "]";

			$('#runtime-configuration').dataTable({
				"bPaginate" : false,
				"bFilter" : false,
				"bInfo" : false,
				"aaData" : JSON.parse(jsonResult)
			});

			$('.rcfile-view').click(function() {
				var file = $(this).attr('file');
				console.log(file);
			});

			$('.rcfile-download').click(function() {
				console.log($(this).attr('file'));
			});
			
			$('.rcfile-remove').click(function() {
				console.log($(this).attr('file'));
				});
			}
		});
	
	$.ajax({
		url : "fabricates/files/ContextWire.htm",
		success : function(result) {
			var jsonResult = "[";
			var arr = JSON.parse(result);
			for (var i = 0; i < arr.length; i++) {
				jsonResult += "[\""
						+ arr[i].file_name
						+ "\", \"<a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='cwfile-view'>Open</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='cwfile-download'>Replace</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='cwfile-remove'>Remove</a>\"]";
				if (i < arr.length - 1)
					jsonResult += ",";
			}
			jsonResult += "]";

			$('#contextwire-configuration').dataTable({
				"bPaginate" : false,
				"bFilter" : false,
				"bInfo" : false,
				"aaData" : JSON.parse(jsonResult)
			});

			$('.cwfile-view').click(function() {
				var file = $(this).attr('file');
				console.log(file);
			});

			$('.cwfile-download').click(function() {
				console.log($(this).attr('file'));
			});
			
			$('.cwfile-remove').click(function() {
				console.log($(this).attr('file'));
				});
			}
		});
	
	$.ajax({
		url : "fabricates/files/RoutingMappings.htm",
		success : function(result) {
			var jsonResult = "[";
			var arr = JSON.parse(result);
			for (var i = 0; i < arr.length; i++) {
				jsonResult += "[\""
						+ arr[i].file_name
						+ "\", \"<a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='rocfile-view'>Open</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='rocfile-download'>Replace</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='rocfile-remove'>Remove</a>\"]";
				if (i < arr.length - 1)
					jsonResult += ",";
			}
			jsonResult += "]";

			$('#routings-configuration').dataTable({
				"bPaginate" : false,
				"bFilter" : false,
				"bInfo" : false,
				"aaData" : JSON.parse(jsonResult)
			});

			$('.rocfile-view').click(function() {
				var file = $(this).attr('file');
				console.log(file);
			});

			$('.rocfile-download').click(function() {
				console.log($(this).attr('file'));
			});
			
			$('.rocfile-remove').click(function() {
				console.log($(this).attr('file'));
				});
			}
		});
	
	$.ajax({
		url : "fabricates/files/ServiceWiringDefinition.htm",
		success : function(result) {
			var jsonResult = "[";
			var arr = JSON.parse(result);
			for (var i = 0; i < arr.length; i++) {
				jsonResult += "[\""
						+ arr[i].file_name
						+ "\", \"<a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='swdfile-view'>Open</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='swdfile-download'>Replace</a>| <a file='"
						+ arr[i].file_location
						+ arr[i].file_name
						+ "' class='swdfile-remove'>Remove</a>\"]";
				if (i < arr.length - 1)
					jsonResult += ",";
			}
			jsonResult += "]";

			$('#servicewire-configuration').dataTable({
				"bPaginate" : false,
				"bFilter" : false,
				"bInfo" : false,
				"aaData" : JSON.parse(jsonResult)
			});

			$('.swdfile-view').click(function() {
				var file = $(this).attr('file');
				console.log(file);
			});

			$('.swdfile-download').click(function() {
				console.log($(this).attr('file'));
			});
			
			$('.swdfile-remove').click(function() {
				console.log($(this).attr('file'));
				});
			}
		});

});