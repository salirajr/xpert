
SaveDataJson = function(action_name,dataJason,type){	
	 var Data = JSON.stringify(dataJason);
	 $.ajax({
	        type: "POST",
	        url: action_name,
	        data: dataJason,
	        contentType: "application/json",
	        dataType: "json",
			async : true,
	        success: function (msg) {
	        	var alertMsg = '<font color="RED" size="2">'+msg.jsonReturn+'</font>';
	        	if(msg.jsonReturn==='1'){
	        		alertMsg =  '<font color="GREEN" size="3"> Succes </font>';
	        	}
	            $('#dvAlert').empty().html(alertMsg);
	            LoadData();
	            if(type==='insert'){
	            	clear();
	            }else if(type==='delete'){
	            	if(msg.jsonReturn==='1'){
	            		alert('Succes Delete');
		        	}else{
		        		alert('Failed Delete');
		        	}
	            	clear();
	            	$('#modalAddEvent').modal('hide');
	            }
	           
	        },
	        error: function (msg) {    
	        	$('#dvAlert').empty().html('<font color="RED" size="5">Error '+msg+'</font>');
	        }
	    });
	
};