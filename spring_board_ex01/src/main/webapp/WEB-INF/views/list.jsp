<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    color: black;
    border-bottom: 3px solid black;
}
table.type09 tbody th {
    padding: 10px;
	text-align: center;
    border-bottom: 1px solid #ccc;
    background: #f3f6f7;
}
/* table.type09 tbody tr:hover{background-color:silver;} */
table.type09 td {
	max-width: 400px;
	text-overflow:ellipsis;
	white-space:nowrap;
	overflow:hidden;
    padding: 10px;
    border-bottom: 1px solid #ccc;
}
.num, .type09 tr td:nth-child(1){
	width: 30px;
	padding: 10px;
    border-bottom: 1px solid #ccc;
}
.title, .type09 tr td:nth-child(2){
	text-overflow:ellipsis;
	white-space:nowrap;
	overflow:hidden;
	text-align: left;
	width: 400px;
	max-width: 400px;
	padding: 10px;
    border-bottom: 1px solid #ccc;
}
.id, .type09 tr td:nth-child(3){
	width: 70px;
	padding: 10px;
    border-bottom: 1px solid #ccc;
}
.rcnt, .type09 tr td:nth-child(4){
	width: 40px;
	padding: 10px;
    border-bottom: 1px solid #ccc;
}
.wdate, .type09 tr td:nth-child(5){
	width: 120px;
	padding: 10px;
    border-bottom: 1px solid #ccc;
}

a:link{ color:black;text-decoration:none; }
.page:link { color:red;text-decoration:underline; }
a:visited{ color:black;text-decoration:none; }
.page:visited { color:red;text-decoration:underline; }
a:hover{ color:black;text-decoration:none; }
.list:hover { color:black;text-decoration:underline; }
</style>
</head>
<body>
<center>
	<table class=type09>
        <thead>
            <tr>
                <th class="num">번호</th>
                <th class="title">제목</th>
                <th class="id">글쓴이</th>
                <th class="rcnt">조회수</th>
                <th class="wdate">작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${textList}" var="text">
                <tr>
                    <td>${text.num}</td>
                    <td>
                    	<a href='view/${text.num}?page=${cri.page}' class=list>${text.title}</a>
                    	<c:if test="${text.rep != 0 }">[${text.rep}]</c:if>
                    </td>
                    <td>${text.id}</td>
                    <td>${text.count}</td>
                    <td>${text.wdate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <table>
    	<tr>
    		<td><input type=button value="글쓰기" onclick=window.location='insert'></td>
    	</tr>
    </table>
    <table>    
    <tr>
	    <td align=left><c:if test="${pageMaker.prev}"><a href="list?page=1">[처음]</a></c:if></td>
	    <td align=center>
		    <table cellpadding=5>
		    	<tr>     	
		    	<td><c:if test="${pageMaker.prev}"><a href="list?page=${pageMaker.startPage - 1}">[◀ 이전]</a></c:if></td>    		    		
		    		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="num">
		    		<td>  			    			
		    		<b><a href="list?page=${num}" <c:out value="${pageMaker.cri.page == num? 'class=page' : ''}"></c:out>>${num}</a></b>		
		    		</td>
		    		</c:forEach>
		    	<td>		
		    		<c:if test="${pageMaker.next && pageMaker.endPage > 0}"><a href="list?page=${pageMaker.endPage + 1}">[다음 ▶]</a></c:if>
		    	</td>
		    </tr>
		    </table>
		</td>
		<td align=right><c:if test="${pageMaker.next && pageMaker.endPage > 0}"><a href="list?page=${pageMaker.totalCount}">[끝]</a></c:if></td>
    </tr>
    </table>
</center>
</body>
</html>