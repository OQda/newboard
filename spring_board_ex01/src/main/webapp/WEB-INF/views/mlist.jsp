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
<ons-page>
<div class="toolbar">
  <div class="toolbar__left">    
  </div>

  <div class="toolbar__center">
	게시판
  </div>

  <div class="toolbar__right">
    <span class="toolbar-button toolbar-button--outline" onclick=window.location='insert?page=${cri.page}'>글쓰기</span>
  </div>
</div>

<ons-list>
	<c:forEach items="${textList}" var="text">
		<ons-list-item tappable onclick=location.href='view/${text.num}?page=${cri.page}'>
			<div class="center">
				<div class="list-item__title">
        			${text.title} <c:if test="${text.rep != 0 }"><font color=blue>[${text.rep}]</font></c:if>
				</div>
				
				<div class="list-item__subtitle">
					${text.id} 조회 : ${text.count}
				</div>				
			</div>
			<div class="right">
					${text.pdate}
			</div>
		</ons-list-item>
	</c:forEach>
</ons-list>

<ons-bottom-toolbar>
<div class="segment" style="width: 100%;padding-top:7px;">
  <c:if test="${pageMaker.prev}">
  <div class="segment__item">
    <input type="radio" class="segment__input" onclick=location.href="list?page=${pageMaker.startPage - 1}">
    <div class="segment__button">◀</div>
  </div>
  </c:if>
  <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="num">
  <div class="segment__item">
    <input type="radio" class="segment__input" onclick=location.href="list?page=${num}"
    <c:out value="${pageMaker.cri.page == num? 'checked' : ''}"></c:out>
    >
    <div class="segment__button">${num}</div>
  </div>
  </c:forEach>
  <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
  <div class="segment__item">
    <input type="radio" class="segment__input" onclick=location.href="list?page=${pageMaker.endPage + 1}">
    <div class="segment__button">▶</div>
  </div>
  </c:if>
</div>
</ons-bottom-toolbar>

</ons-page>
</body>
</html>