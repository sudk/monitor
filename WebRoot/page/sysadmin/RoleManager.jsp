<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'RoleManager.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type='text/javascript' src='<%=path%>/dwr/engine.js'>
	
</script>
<script type='text/javascript' src='<%=path%>/dwr/util.js'>
	
</script>
<script type='text/javascript'
	src='<%=path%>/dwr/interface/sysRbacRoleService.js'>
	
</script>
	<link rel="stylesheet" href="<%=path%>/css/jq.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="<%=path%>/themes/blue/style.css" type="text/css" id="" media="print, projection, screen" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
<script type='text/javascript' src='<%=path%>/js/jquery.simplemodal.js'></script>
<link type='text/css' href='<%=path%>/css/demo.css' rel='stylesheet' media='screen' />

<!-- Contact Form CSS files -->
<link type='text/css' href='<%=path%>/css/basic.css' rel='stylesheet' media='screen' />

	<script type="text/javascript" id="js">$(document).ready(function() {
	//$("table").tablesorter();
	$("#append").click(function() {
		
	dwr.engine._execute("dwr", 'sysRbacRoleService', 'findAllRoleEntitys', {
			callback : function(results) {
			    var html="";
				for ( var i = 0; i < results.length; i++) {
					var line = "";
					line += "<tr><td>" + results[i].roleId + "</td>";
					line += "<td>" + results[i].roleName + "</td>";
					line += "<td>" + results[i].roleDesc + "</td>";
					line += "<td>" + results[i].roleStatus + "</td></tr>";
					html+=line;
				}
				$("tr:even").addClass("odd_table");
		 		$("table tbody").append(html);
				$("table").trigger("update");
				// set sorting column and direction, this will sort on the first and third column
				var sorting = [[0,1],[0,0]];
				// sort on the first column
				$("table").trigger("sorton",[sorting]);
			},
			errorHandler : function(e) {
				alert("查询失败:" + e);
			}
		});
		return false;
	});
	
	$("#openA").click(function(e) {
		alert(1)
		//$('#basicmodalcontent').modal();
		//$('#basicModalContent').modal(); // jQuery object; this demo
		alert(1)
		$.modal(document.getElementById('basicmodalcontent')); // DOM
		$.modal('<p><b>HTML</b> elements</p>'); // HTML	
		return false;
	});
		
	
});</script>
</head>

<body>

	<div id="demo">
		<table cellspacing="1" class="tablesorter">
			<thead>
				<tr>
					<th>角色编号</th>
					<th>角色名称</th>
					<th>角色描述</th>
					<th>角色状态</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<a href="#" id="append">Append new table data</a>  <br />  <a href="#"
			id="openA">open winModal</a> <br /> <br />
	</div>


	<div id="basicmodalcontent" style="display:none;">
			<h3>Basic Modal Dialog</h3>
			<p>For this demo, SimpleModal is using this "hidden" data for its content. You can also populate the modal dialog with an AJAX response, standard HTML or DOM element(s).</p>
			<p>Examples:</p>
			<p><code>$('#basicModalContent').modal(); // jQuery object - this demo</code></p>
			<p><code>$.modal(document.getElementById('basicModalContent')); // DOM</code></p>
			<p><code>$.modal('&lt;p&gt;&lt;b&gt;HTML&lt;/b&gt; elements&lt;/p&gt;'); // HTML</code></p>
			<p><code>$('&lt;div&gt;&lt;/div&gt;').load('page.html').modal(); // AJAX</code></p>
		
			<p><a href='http://www.ericmmartin.com/projects/simplemodal/'>More details...</a></p>
		</div>
</body>
</html>
