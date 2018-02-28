<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
table.type09 {
    border-collapse: collapse;
    text-align: center;
    line-height: 1;
	font-size: 12px;

}
table.type09 thead th {
	text-align:center;
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    color: black;
    border-bottom: 3px solid black;
}
table.type09 tbody th {
    padding: 10px;
	text-align: center;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
    background: #f3f6f7;
}
table.type09 td {
    padding: 10px;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
}
</style>
</head>
<body>
<center>
	<form action="insertgo" method="post">
		<table class=type09>
			<thead>
				<th colspan=2>새글 작성</th>
			</thead>
			<tbody>
				<tr>
					<th>글쓴이 </th>
					<td align=left><input type=text name=id required></td>
				</tr>
				<tr>
					<th>글 제목 </th>
					<td align=left><input type=text name=title size=50 maxlength=50 required></td>
				</tr>
				<tr>
					<th>글 내용 </th>
					<td><textarea cols=52 rows=8 name=context></textarea></td>
				</tr>
			</tbody>
		</table>
		<table cellpadding=5 align=center>
			<tr>
				<td><input type=button value=취소 onclick=window.location='list'></td>
				<td><input type=submit value=쓰기></td>
			</tr>
		</table>
	</form>
</center>
</body>
</html>