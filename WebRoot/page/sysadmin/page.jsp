<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.component.common.spring.util.*"%>
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

<title>测试页</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type='text/javascript' src='<%=path%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=path%>/dwr/util.js'>
</script>
<script type='text/javascript'
	src='<%=path%>/dwr/interface/sysCodeValueService.js'>
</script>
<script type="text/javascript" src="<%=path%>/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.pagination.js"></script>
<script type="text/javascript">

	function find() {
		dwr.engine._execute("dwr", 'sysCodevalueService', 'findAllCodeValueEntitys', {
			callback : function(results) {
				$("tr:gt(0)").remove();
				for ( var i = 0; i < results.length; i++) {
					var line = "";
					line += "<tr><td>" + results[i].codeValueId + "</td>";
					line += "<td>" + results[i].parentId + "</td>";
					line += "<td>" + results[i].codeId + "</td>";
					line += "<td>" + results[i].value + "</td>";
					line += "<td>" + results[i].orderId + "</td>";
					line += "<td>" + results[i].codeValueDesc + "</td></tr>";
					$("table:last-child").append(line);
				}
				$("tr:even").addClass("odd_table");
			},
			errorHandler : function(e) {
				alert("查询失败:" + e);
			}
		});
	}
</script>
<style type="text/css">
td {
	border: 1px solid #750037;
	width: 100px;
	font-size: 12px;
	text-align: center;
	margin: 0px;
}

.userTable {
	border-collapse: collapse;
}

.userTable .title {
	font-weight: bold;
	font-size: 14px;
	background-color: #ffcdfc;
	height: 25px;
}

.odd_table {
	background-color: #00cd00;
}

.toggleClass {
	background-color: #0fcd00;
}
}
</style>
</head>

<body>
	<p>
		<a href="javascript:find();">所有字典</a>
	</p>
	<table id="allUsers" class="userTable" cellspacing="0">
		<tr>
			<td class="title">代码值ID</td>
			<td class="title">上级代码值ID</td>
			<td class="title">代码ID</td>
			<td class="title">代码值</td>
			<td class="title">排序号</td>
			<td class="title">描述</td>
		</tr>
	</table>
</body>
</html>
