<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initail-scale=1">
<link rel="stylesheet" href="https://unpkg.com/onsenui/css/onsenui.css">
<link rel="stylesheet" href="https://unpkg.com/onsenui/css/onsen-css-components.min.css">
<script src="https://unpkg.com/onsenui/js/onsenui.min.js"></script>
<script src="https://unpkg.com/jquery/dist/jquery.min.js"></script>
<title>Board - Mobile ver</title>
<style>
a:link{ color:black;text-decoration:none; }
a:visited{ color:black;text-decoration:none; }
a:hover{ color:black;text-decoration:none; }
</style>
<script>

	// 보고있는 글의 번호
	var pnum = ${textNum};
	
	// 페이지메이커(임시)
	var ppm;	
	
	// 댓글을 표시하는 함수(매개변수: 페이지 번호)
	function getRepList(rpage){	
		$.getJSON("/board/replies/"+pnum+"/"+rpage, function(data){
			
			// 댓글 출력 틀이 될 변수
			var str = "";		
			
			// JSON형태로 만들어진 데이터를 받아와서 댓글 출력 틀에 뿌려준다
			// data.list = 컨트롤러의 맵에 list로 담긴 댓글 데이터
			$(data.list).each(
					function() {
						str += "<li class='list-item' data-rno='"+ this.rno +"'>"								
									+"<div class='list-item__center'>"
										+"<div class='list-item__title'>" + this.id + "</div>"
										+"<div class='list-item__title' style='padding-top:7px;'>" + this.text + "</div>"
										+"<div class='list-item__subtitle' style='text-align:right;'>" + this.wdate + "</div>"
									+"</div>"																
							+ "</li>"
					});
			// 만들어진 댓글 틀을 표시
			$("#replies").html(str);
			
			// 댓글의 페이지 출력
			// data.pageMaker = 컨트롤러에서 pageMaker로 담긴 페이지에 대한 데이터
			printPaging(data.pageMaker);
			ppm = data.pageMaker;
		});	
	}
	
	// 총 댓글 페이지 수를 담을 변수 - 댓글 추가에서 활용
	var totalRepPage;
	
	// 총 댓글 수를 센다
	function getRepCount(){
		$.getJSON("/board/replies/"+pnum+"/1", function(data){
			
			// 총 댓글 수 
			var cnt = data.pageMaker.totalCount;
			
			// 총 댓글 페이지 수 변수에 저장
			totalRepPage = Math.ceil(cnt / 10.0);
			//alert(totalRepPage); 
			
			// 총 댓글 수를 jsp에 표시
			$("#ccount").html("댓글 "+cnt);
			
		})
	}	
	
	// '등록' 버튼을 눌렀을 때 - 댓글 추가
	$(document).ready(function() {
		$("#repAdd").on("click", function() {
		
			// 입력값이 없을때 경고문 출력
			if ( $("#wid").val().length == 0 || $("#wtext").val().length == 0 ){
				alert("남긴이와 댓글을 입력해 주세요");
				return;
			}
			
			// 입력된 값 변수에 저장
			var wid = $("#wid").val();
			var wtext = $("#wtext").val();
	
			// ajax로 JSON형태의 데이터 전송 (전송 메소드를 컨트롤러 설정과 맞춰야 원하는 동작이 가능)
			$.ajax({
				type : 'post',
				url : '/board/replies/',
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : 'text',
				data : JSON.stringify({
					pnum : pnum,
					id : wid,
					text : wtext
				}),
				success : function(result) {
					
					// 전송완료 후 페이지를 다시 불러오고, 댓글 숫자를 다시 세고, input창 데이터를 지워준다
					if (result == 'SUCCESS') {
						getRepCount();
						printPaging(ppm);
						getRepList(1);
						$("#wid").val("");
						$("#wtext").val("");
	
					}
				}
			});
		});
	});
	
	// '삭제' 버튼을 눌렀을 때
	$(document).ready(function() {
		$("#replies").on("click", ".repDel", function(){			
			
			var chk = confirm("삭제 call?");
			
			if ( chk == true ){				
				
				// 설정해놓은 class에서 해당 댓글 번호를 찾아온다
				var reply = $(this).parent().parent();
				var rno = reply.attr("data-rno");	
				
				$.ajax({
					type : 'delete',
					url : '/board/replies/' + rno,
					headers : {
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "DELETE"					
					},
					dataType : 'text',
					success : function(result){
						
						if ( result == 'SUCCESS' ){
							getRepCount();							
							printPaging(ppm);
							getRepList(replyPage);							
							
						}
					}
				});				
				
			}else{
				
			}
			
		});
		
	});	
		
	// '수정' 버튼 클릭했을 때
	$(document).ready(function() {
		$("#replies").on("click", ".repMod", function(){			
		
			var reply = $(this).parent().parent();
			var rno = reply.attr("data-rno");			
			var reptext = reply.find('.repText').text();
			
			// 댓글을 수정할 수 있는 창을 표시
			$("#text"+rno).val(reptext);
			$("#modDiv"+rno).show();
		
		});
		
	});
	
	// 수정을 누른 후에 뜬 창에서 등록버튼을 눌렀을 때
	$(document).ready(function() {
		$("#replies").on("click", ".repmodGo", function(){			
			
			var reply = $(this).parent().parent().parent().parent();
			var rno = reply.attr("data-rno");
			var reptext = $("#text"+rno).val();
			
			// 수정한 데이터값을 가지고 전송
			$.ajax({
				type : 'put',
				url : '/board/replies/' + rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "PUT"					
				},
				dataType : 'text',
				data : JSON.stringify({text : reptext}),
				success : function(result){
					
					if( result == 'SUCCESS' ){
						$("#modDiv"+rno).hide();
						getRepList(replyPage);
					}
				}
			});			
		
		});
		
	});	
	
	// 수정을 누른 후에 뜬 창에서 취소버튼을 눌렀을 때
	$(document).ready(function() {
		$("#replies").on("click", ".repCancel", function(){			
			
			var reply = $(this).parent().parent().parent().parent();
			var rno = reply.attr("data-rno");
		
			// 표시된 창만 숨겨준다
			$("#modDiv"+rno).hide();
		
		});
		
	});
			  
	// 댓글 페이지 출력
	function printPaging(pageMaker){
		
		var str = "";
		
		if ( totalRepPage <= 1 ){
			str = "";
			$("#repPage").html(str);
			return;
		}else{
			
			if(pageMaker.prev){
				str += "<a href='"+(pageMaker.startPage-1)+"'>◀</a> ";
			}else{
				str += "　";
			}
			
			for(var i=pageMaker.startPage, len = pageMaker.endPage; i <= len; i++){	
				  str += i;
			}
			
			if(pageMaker.next){
				str += " <a href='"+(pageMaker.endPage + 1)+"'>▶</a>";
			}		
			$("#repPage").html(str);
		
		}
	}
	
	// 현재 댓글 페이지 (최초 1)
	var replyPage = 1;
	
	$(document).ready(function() {
		$("#repPage").on("click", "a", function(event){
			
			// a태그 진행을 막아준다
			event.preventDefault();
			
			// a태그에서 계산된 pageMaker의 start, end 페이지를 받아서 현재 페이지 변수에 저장
			replyPage = $(this).attr("href");
			
			// 페이지 정보로 댓글 목록을 다시 불러온다
			getRepList(replyPage);
			
		});	
	});
	
	// 최초 실행시 댓글 수와 댓글 표시
	getRepCount();
	getRepList(1);
</script>
</head>
<body>

<!-- Prerequisite=This example use ionicons(http://ionicons.com) to display icons. -->
<div class="toolbar">
  <div class="toolbar__left">
    <span class="toolbar-button toolbar-button--quiet" onclick=window.location='/board/m/list?page=${cri.page}'>
      <i class="ion-android-arrow-back" style="font-size:32px; vertical-align:-6px;"></i>
    </span>
  </div>

  <div class="toolbar__center">
  	 글 제목
  </div>

  <div class="toolbar__right">
		<span class="toolbar-button toolbar-button--outline" 
		onclick=window.location='/board/update/${textNum}?page=${cri.page}'>수정</span>		
		<span class="toolbar-button toolbar-button--outline" 
		onclick=window.location='/board/m/delete/${textNum}?page=${cri.page}'>삭제</span>	
  </div>
</div>

<ul class="list list--noborder">
  <li class="list-item">
    <div class="list-item__center">
    	<div class="list-item__title" style="font-size:20px;">
					${textList.title}
		</div>
    	<div class="list-item__subtitle" style="padding: 7px 5px 0px 3px;">
			<div style="float:left;">${textList.id}</div>
			<div style="float:right;">${textList.indate}</div>
		</div>
		<div class="list-item__subtitle" style="padding: 3px 5px 0px 3px;"> <!-- 위 / 오른쪽 / 아래 / 왼쪽 -->
			조회 : ${textList.count}
		</div>
    </div>
  </li>
  <li class="list-item">
    <div class="list-item__center">
		${textList.context}
    </div>
  </li>
</ul>

<ul class="list">
  <li class="list-header">
    <div style="float:left;" id=ccount></div>
    <div style="float:right;padding-right:20px;" id=repPage></div>
  </li>
  <li class="list-item">
    <div class="list-item__center">
      <input type="text" class="text-input" placeholder="남긴이" name=id id=wid required>      
    </div>    
  </li>
  <li class="list-item">
    <div class="list-item__left">
      <input type="text" class="text-input" placeholder="댓글 입력" name=text id=wtext required>
    </div>
    <div class="list-item__right">
    	<button id=repAdd class="button">등록</button>
    </div>
  </li>
</ul>

<ul class="list" id=replies></ul>

</body>
</html>