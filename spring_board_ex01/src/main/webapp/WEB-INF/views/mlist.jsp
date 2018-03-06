<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initail-scale=1, sc">
<link rel="stylesheet" href="https://unpkg.com/onsenui/css/onsenui.css">
<link rel="stylesheet" href="https://unpkg.com/onsenui/css/onsen-css-components.min.css">
<script src="https://unpkg.com/onsenui/js/onsenui.min.js"></script>
<script src="https://unpkg.com/jquery/dist/jquery.min.js"></script>
<title>Board - Mobile ver</title>
</head>
<body>

<!-- Prerequisite=This example use ionicons(http://ionicons.com) to display icons. -->
<div class="toolbar">
  <div class="toolbar__left">    
  </div>

  <div class="toolbar__center">
	게시판
  </div>

  <div class="toolbar__right">
    <span class="toolbar-button toolbar-button--outline" onclick=window.location='insert'>글쓰기</span>
  </div>
</div>

<ul class="list">
	<c:forEach items="${textList}" var="text">
		<li class="list-item list-item--tappable" onclick=location.href='view/${text.num}?page=${cri.page}'>
			<div class="list-item__center">
				<div class="list-item__title">
        			${text.title} <c:if test="${text.rep != 0 }">[${text.rep}]</c:if>
				</div>
				
				<div class="list-item__subtitle">
					${text.id} 조회 : ${text.count}
				</div>				
			</div>
			<div class="list-item__right">
					${text.pdate}
			</div>
		</li>
	</c:forEach>
</ul>
    
<script>
	var npage = ${cri.page};
    $(function(){
      // Initialization code
      $('ons-button').on('click', function(e) {
      	if (npage==1){
        	location.href='http://192.168.23.101:8181/board/m/list?page=2';
      	}else if(npage==2){
      		location.href='http://192.168.23.101:8181/board/m/list?page=1';
        }
      })
    });
</script>
<center><ons-button class="button--quiet">페이지 전환</ons-button></center>
  
</body>
</html>