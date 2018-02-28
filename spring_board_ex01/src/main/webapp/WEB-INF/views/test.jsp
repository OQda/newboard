<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Test Page</h2>
	
	<div>
		<div>
			남긴이 <input type=text name=id id=wid>
		</div>
		<div>
			남길말 <input type=text name=text id=wtext>
		</div>
		<button id=repAdd>등록</button>
	</div>
	
	<ul id="replies">
	</ul>
	
	<!-- jQuery 2.1.4 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script>
	var pnum = 32756;
	function getRepCount(){
		$.getJSON("replies/32756/1", function(data){
			var cnt = data.pageMaker.totalCount;
			alert(cnt);
		})
	}	
	function getRepList(){
		$.getJSON("replies/32756/1", function(data){
			
			console.log(data.list.length);
			
			var str = "";		
			
			$(data.list).each(
					function() {
						str += "<li data-rno="+this.rno+"'>"
							+ this.rno + ":" + this.id + ":" + this.text
							+ "</li>"
					});
			$("#replies").html(str);
		});	
	}
	
	$("#repAdd").on("click", function() {

		var wid = $("#wid").val();
		var wtext = $("#wtext").val();

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

				if (result == 'SUCCESS') {
					getRepList();
					getRepCount();

				}
			}
		});
	});
	</script>
	안녕 <div id="ccount">얍</div> 안녕
</body>
</html>