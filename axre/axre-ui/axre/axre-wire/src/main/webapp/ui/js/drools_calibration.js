$(document).ready(function() {
	
	$.ajax({url: "../../reference/get/drools/calibrate.htm", 
		success: function(result){
			var jsonResult = "[";
			var arr = JSON.parse(result);
			for(var i = 0; i < arr.length; i++) {
				jsonResult += "[\""+arr[i].file_name+"\", \""+arr[i].file_location+"\", \"<a file='"+arr[i].file_location+arr[i].file_name+"' class='drlfile-link co-primary'><i class='fa fa-link'></i></a>\"]";
				if(i < arr.length - 1 )
					jsonResult += ",";
			}
			jsonResult += "]";
			
			$('#drools').dataTable({
				"autoWidth": false,
				"bPaginate" : true,
				"bInfo" : false,
				"scrollX": true,
				"bLengthChange":false,
				"aaData" : JSON.parse(jsonResult)
			});
			
			$('.drlfile-link').click(function(){
				var file=$(this).attr('file');
				console.log(file);
				
				$("#base").val("base");
				$("#file").val(file);
				
				$("#aflinkage").attr("method","post");
				$("#aflinkage").attr("action","../../assetfabrics.htm");
				$("#aflinkage").submit();
			});
			
			
			

    }});
	
});
