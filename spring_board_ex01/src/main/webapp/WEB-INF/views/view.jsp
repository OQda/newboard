<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- jQuery 2.1.4 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<style>
table.type09 {
	width:600px;
	max-width: 600px;
	border: 2px solid #ccc;
   	border-collapse: collapse;
	text-align: left;
	line-height: 1;
	font-size: 12px;
}
table.type09 thead th {
	text-align:left;
    padding: 10px;
    font-weight: bold;
    color: black;
    border-bottom: 3px solid black;
}
table.type09 tbody th {
	width:50px;
	padding: 10px;
	text-align: center;
    border-bottom: 1px solid #ccc;
    background: #f3f6f7;
}
table.type09 td {
	width:510px;
	max-width:510px;	
    padding: 5px 10px 5px 10px;    	
    line-height: 1.7;
    border-bottom: 1px solid #ccc;
    
}
.btn, .type09{
	text-align:right;
}
#input{
	white-space:nowrap;
	width:100px;
	text-align:left;
}
#nowrap{
	width:500px;
	max-width:500px;
	text-overflow:ellipsis;
	white-space:nowrap;
	overflow:hidden;
}
#modDiv{
	border: 1px solid gray;
	width: 575px;
	height: 70%;
	background-color: white;
	position: absolute;
	top: 30%;
}
#modForm{	
	position: relative;
}
#textline{
	width:500px;
	max-width:500px;
	word-break:break-word;
	word-break:break-all;
}

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
						str += "<tr data-rno='"+ this.rno +"'>"
								+"<td>"
									+"<div id=modForm>"
									+ "<div>" + this.id+ "</div>"
									+ "<div id=textline class='repText'>" + this.text + "</div>"
									+ "<div>" + this.wdate + "</div>"							
										+"<div id=modDiv"+this.rno+" style='display:none;border: 1px solid gray;"
												+"width: 575px;height: 70%;background-color: white;position: absolute;"
												+"top: 30%;'>"
											+"<input type=text id=text"+this.rno+" style='width:450px; height:85%;'>"										
											+"　 <input type=button class='repmodGo' value='등록'>"
											+"　<input type=button class='repCancel' value='취소'>"										
										+"</div>"	
									+"</div>"
								+"</td>"
								+"<td class=btn>"
									+"<input type=button class=repMod value='수정'><br><br>"
									+"<input type=button class=repDel value='삭제'>"
								+"</td>"								
							+ "</tr>"
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
<center>
	<table class=type09>
        <thead>
			<tr>
				<th scope=cols align=center>제목</th>
				<th scope=cols id=nowrap>${textList.title}</th>
			</tr>
		</thead>
        <tbody>		
		<tr><th scope=row>글쓴이</th><td>${textList.id}</td></tr>
		<tr><th scope=row>작성일</th><td>${textList.indate}</td></tr>
		<tr><th scope=row>조회수</th><td>${textList.count}</td></tr>
		<tr><th scope=row>내용</th><td>${textList.context}</td></tr>
		</tbody>        
    </table>
    <table cellpadding=5>
    	<tr align=center>
    		<td>
    			<input type=button value="목록" onclick=window.location='/board/list?page=${cri.page}'>    			
    		</td>
    		<td>
    			<input type=button value="수정" onclick=window.location='/board/update/${textNum}?page=${cri.page}'>
    		</td>    		
    		<td>
    			<input type=button value="삭제" onclick=window.location='/board/delete/${textNum}?page=${cri.page}'>
    		</td>
    	</tr>
    </table>
    
    <table class=type09>
        <thead>
            <tr>
            	<!-- 댓글 수 출력 -->
                <th><div id=ccount></div></th>
                <!-- 페이지 출력 -->
                <th><div id=repPage></div></th>                            
            </tr>
        </thead>
        <tbody>
    	<tr>
    		<td id=input>
    			<input type=text size=10 name=id id=wid placeholder="남긴이">　　
    			<input type=text placeholder="댓글 입력" size=45 name=text id=wtext>
    		</td>
    		<td class=btn><button id=repAdd>등록</button></td>
    	</tr>
    	</tbody>
    	<!-- 댓글 틀 출력 -->        
        <tbody id=replies>        
        </tbody>
    </table>
</center>
</body>
</html>