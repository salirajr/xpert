$(document)
		.ready(
				function() {

					var config = {
						'.chosen-select' : {},
						'.chosen-select-deselect' : {
							allow_single_deselect : true
						},
						'.chosen-select-no-single' : {
							disable_search_threshold : 5
						},
						'.chosen-select-no-results' : {
							no_results_text : 'Oops, nothing found!'
						},
						'.chosen-select-width' : {
							width : "95%"
						}
					}
					for ( var selector in config) {
						$(selector).chosen(config[selector]);
					}
					$(".chosen-container.chosen-container-multi").css("width",
							"500px");

					var radarData = {
						labels : [ "FMMxpert", "RMSxpert", "AMSxpert",
								"IICSxpert", "FIDSxpert", "CMSxpert" ],
						datasets : [ {
							label : "Processes Message",
							fillColor : "rgba(28,132,198,0.2)",
							strokeColor : "rgba(28,132,198,1)",
							pointColor : "rgba(28,132,198,1)",
							pointStrokeColor : "#fff",
							pointHighlightFill : "#fff",
							pointHighlightStroke : "rgba(151,187,205,1)",
							data : [ 85, 90, 80, 90, 40, 60 ]
						}, {
							label : "Failed Message",
							fillColor : "rgba(248,172,89,0.2)",
							strokeColor : "rgba(248,172,89,1)",
							pointColor : "rgba(248,172,89,1)",
							pointStrokeColor : "#fff",
							pointHighlightFill : "#fff",
							pointHighlightStroke : "rgba(151,187,205,1)",
							data : [ 10, 5, 7, 3, 30, 8 ]
						}, {
							label : "Invalid Message",
							fillColor : "rgba(237,85,101,0.2)",
							strokeColor : "rgba(237,85,101,1)",
							pointColor : "rgba(237,85,101,1)",
							pointStrokeColor : "#fff",
							pointHighlightFill : "#fff",
							pointHighlightStroke : "rgba(151,187,205,1)",
							data : [ 5, 5, 13, 7, 30, 2 ]
						} ]
					};

					var radarOptions = {
						scaleShowLine : true,
						angleShowLineOut : true,
						scaleShowLabels : true,
						scaleBeginAtZero : true,
						angleLineColor : "rgba(0,0,0,.1)",
						angleLineWidth : 1,
						pointLabelFontFamily : "'Arial'",
						pointLabelFontStyle : "normal",
						pointLabelFontSize : 10,
						pointLabelFontColor : "#666",
						pointDot : true,
						pointDotRadius : 3,
						pointDotStrokeWidth : 1,
						pointHitDetectionRadius : 20,
						datasetStroke : true,
						datasetStrokeWidth : 2,
						datasetFill : true,
						responsive : true,
					}

					var ctx = document.getElementById("radarChart").getContext(
							"2d");
					var myNewChart = new Chart(ctx).Radar(radarData,
							radarOptions);
					var now = Date.parse('Thu, 17 Dec 2015 00:00:00');
					console.log(new Date(now));
					$(function() {
						var processedData = [ [ now, 90 ],
								[ (now + (5 * 60000)), 80 ],
								[ (now + (10 * 60000)), 81 ],
								[ (now + (15 * 60000)), 70 ],
								[ (now + (20 * 60000)), 40 ],
								[ (now + (25 * 60000)), 20 ],
								[ (now + (30 * 60000)), 60 ],
								[ (now + (35 * 60000)), 90 ],
								[ (now + (40 * 60000)), 40 ],
								[ (now + (45 * 60000)), 100 ],
								[ (now + (50 * 60000)), 21 ],
								[ (now + (55 * 60000)), 10 ],
								[ (now + (60 * 60000)), 16 ],
								[ (now + (65 * 60000)), 90 ],
								[ (now + (70 * 60000)), 17 ] ];
						var invalidData = [ [ now, 0 ],
								[ (now + (5 * 60000)), 10 ],
								[ (now + (10 * 60000)), 30 ],
								[ (now + (15 * 60000)), 70 ],
								[ (now + (20 * 60000)), 60 ],
								[ (now + (25 * 60000)), 10 ],
								[ (now + (30 * 60000)), 40 ],
								[ (now + (35 * 60000)), 70 ],
								[ (now + (40 * 60000)), 60 ],
								[ (now + (45 * 60000)), 60 ],
								[ (now + (50 * 60000)), 10 ],
								[ (now + (55 * 60000)), 0 ],
								[ (now + (60 * 60000)), 0 ],
								[ (now + (65 * 60000)), 0 ],
								[ (now + (70 * 60000)), 0 ] ];
						var failedData = [ [ now, 40 ],
								[ (now + (5 * 60000)), 10 ],
								[ (now + (10 * 60000)), 60 ],
								[ (now + (15 * 60000)), 40 ],
								[ (now + (20 * 60000)), 10 ],
								[ (now + (25 * 60000)), 10 ],
								[ (now + (30 * 60000)), 60 ],
								[ (now + (35 * 60000)), 40 ],
								[ (now + (40 * 60000)), 10 ],
								[ (now + (45 * 60000)), 40 ],
								[ (now + (50 * 60000)), 20 ],
								[ (now + (55 * 60000)), 40 ],
								[ (now + (60 * 60000)), 90 ],
								[ (now + (65 * 60000)), 30 ],
								[ (now + (70 * 60000)), 0 ] ];
						var queueStateData = [ [ now, 4 ],
								[ (now + (5 * 60000)), 0 ],
								[ (now + (10 * 60000)), 1 ],
								[ (now + (15 * 60000)), 2 ],
								[ (now + (20 * 60000)), 3 ],
								[ (now + (25 * 60000)), 0 ],
								[ (now + (30 * 60000)), 4 ],
								[ (now + (35 * 60000)), 0 ],
								[ (now + (40 * 60000)), 100 ],
								[ (now + (45 * 60000)), 130 ],
								[ (now + (50 * 60000)), 180 ],
								[ (now + (55 * 60000)), 200 ],
								[ (now + (60 * 60000)), 400 ],
								[ (now + (65 * 60000)), 410 ],
								[ (now + (70 * 60000)), 590 ] ];

						function euroFormatter(v, axis) {
							return v.toFixed(axis.tickDecimals);
						}

						function doPlot(position) {
							$
									.plot(
											$("#flot-line-chart-multi"),
											[ {
												data : processedData,
												label : "Processed Message",
												color : "#1c84c6"
											}, {
												data : invalidData,
												label : "Invalid Message",
												color : "#f8ac59"
											}, {
												data : failedData,
												label : "Failed Message",
												color : "#ed5565"

											}, {
												data : queueStateData,
												label : "Queued Message",
												color : "#e0e0e0"

											} ],
											{
												xaxes : [ {
													mode : 'time',
													minTickSize : [ 5, "minute" ]
												} ],
												yaxes : [
														{
															min : 0
														},
														{
															// align if we are
															// to the right
															alignTicksWithAxis : position == "right" ? 1
																	: null,
															position : position,
															tickFormatter : euroFormatter
														} ],
												legend : {
													position : 'sw'
												},
												// 1ab394
												colors : [ "#1ab394" ],
												grid : {
													color : "#999999",
													hoverable : true,
													clickable : true,
													tickColor : "#D4D4D4",
													borderWidth : 0,
													hoverable : true
												// IMPORTANT! this is needed for
												// tooltip to work,

												},
												tooltip : true,
												tooltipOpts : {
													content : "%s for %x was %y",
													xDateFormat : "%m-%d-%y %h:%M",

													onHover : function(
															flotItem,
															$tooltipEl) {
														// console.log(flotItem,
														// $tooltipEl);
													}
												}

											});
						}

						doPlot("right");

						$("button").click(function() {
							doPlot($(this).text());
						});
					});

				});
