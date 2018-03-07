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
<center>
<form name="newinsert" action="insertgo" method="post">

<!-- Prerequisite=This example use ionicons(http://ionicons.com) to display icons. -->
<div class="toolbar">
  <div class="toolbar__left">
    <span class="toolbar-button toolbar-button--quiet" onclick=window.location='list?page=${cri.page}'>
      <i class="ion-android-arrow-back" style="font-size:32px; vertical-align:-6px;"></i>
    </span>
  </div>

  <div class="toolbar__center">
  	  글쓰기
  </div>

  <div class="toolbar__right" style="margin-top:5px;">
		<input type=submit value=쓰기 class="toolbar-button toolbar-button--outline">	
  </div>
</div>

<ul class="list">
  <li class="list-item">
    <div class="list-item__center">
      <input type="text" class="text-input" size=100% placeholder="글쓴이" name=id required>
    </div>
  </li>
  <li class="list-item">
    <div class="list-item__center">
      <input type="text" class="text-input" size=100% placeholder="제목" name=title required>
    </div>
  </li>
  <li class="list-item">
    <div class="list-item__center">
      <textarea class="textarea textarea--transparent" cols=100% rows="10" placeholder="내용" name=context></textarea>
    </div>
  </li>
</ul>			

</form>
</center>
</body>
</html>