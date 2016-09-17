$(document)
		.ready(
				function() {
					var rows_dt_flowlogic = 0;
					var dt_flowlogic = $('#tbl-flowlogic')
							.DataTable(
									{
										"bPaginate" : true,
										"bInfo" : true,
										"bLengthChange" : false,
										"bAutoWidth" : false,
										"processing" : true,
										"bServerSide" : true,
										"bInfo" : false,
										"bFilter" : false,
										"order" : [],
										"bServerSide" : true,
										"pageLength" : 25,
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2, 3 ]
										} ],
										"sAjaxSource" : "../dautl/extract/rows/flowlogics.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var qparam = JSON.stringify([ "",
													oSettings._iDisplayLength,
													oSettings._iDisplayStart ]);
											rows_dt_flowlogic = oSettings._iDisplayStart;

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/flowlogics.do",
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
													mData : "container_owner",
													mRender : function(data,
															type, full) {
														return "";
													}
												},
												{
													mData : "container_owner",
													mRender : function(data,
															type, full) {
														return "<a class=''>"
																+ data + "</a>";
													}
												},
												{
													mData : "container_flgroup"
												},
												{
													mData : "agg_busgroup",
													mRender : function(data,
															type, full) {
														return fnBusnotationRender(data);
													}
												} ],
										"fnRowCallback" : function(nRow, aData,
												iDisplayIndex,
												iDisplayIndexFull) {
											var index = rows_dt_flowlogic
													+ iDisplayIndexFull + 1;
											$('td:eq(0)', nRow).html(index);
											return nRow;
										},
										"fnInitComplete" : function(oSettings,
												json) {
											$('#tbl-flowlogic tbody tr:eq(0)')
													.click();
										}

									});

					var dt_buscontext = $('#tbl-buscontext')
							.DataTable(
									{
										"bPaginate" : true,
										"bInfo" : true,
										"bLengthChange" : false,
										"bAutoWidth" : false,
										"processing" : true,
										"bServerSide" : true,
										"bInfo" : false,
										"bFilter" : false,
										"order" : [],
										"bServerSide" : true,
										"pageLength" : 25,
										"columnDefs" : [ {
											"bSortable" : false,
											"aTargets" : [ 0, 1, 2, 3 ]
										} ],
										"sAjaxSource" : "../dautl/extract/rows/buscontext.do",
										"fnServerData" : function(sSource,
												aoData, fnCallback, oSettings) {

											var qparam = JSON.stringify([
													$('#tempif-flowlogic')
															.val(), "",
													oSettings._iDisplayLength,
													oSettings._iDisplayStart ]);

											$
													.ajax({
														"type" : "POST",
														"url" : "../dautl/extract/rows/buscontext.do",
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
										"aoColumns" : [ {
											mData : "container_flname"
										}, {
											mData : "context_site"
										}, {
											mData : "context_sequence"
										}, {
											mData : "context_level"
										}, {
											mData : "context_alias"
										} ]

									});

					$('#tbl-flowlogic tbody').on('click', 'a.details-control',
							function() {
								var tr = $(this).closest('tr');
								var row = dt_event.row(tr);
								var rowIdx = row.index();
								var data = row.data();

							});

				});

function fnBusnotationRender(raws) {
	var notations = raws.split(', ');
	var nots;
	var result = "CH-IN";
	for (var i = 0; i < notations.length; i++) {
		nots = notations[i].split('/');
		if (nots[0] == 'ServiceRepressor') {
			result += " >> " + fnAsLink("", nots[1]);
		} else if (nots[0] == 'ServiceBinderRepressor') {
			result += " >>>> " + fnAsLink("", nots[1]);
		} else if (nots[0] == 'ServiceActivator') {
			result += " <> " + nots[1];
		} else if (nots[0] == 'ServiceBinderActivator') {
			result += " <<>> " + fnAsLink("", nots[1]);
		} else if (nots[0] == 'ServiceBinderOriginator') {
			if (result == "CH-IN")
				result = "";
			result += " " + fnAsLink("", nots[1]);
			+" >><<";
		}
	}
	return result.trim();

}

function fnAsLink(className, text) {
	return '<a class="' + className + '">' + text + '</a>';
}
