<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="http://image.lgeshop.com/css/style_2005.css">
<html>
  <head>
	<title>content update</title>
	<script language="javascript">
	  function checkValue()
	  {
		if(document.input.subject.value == "")
		{
		  alert("제목을 입력해주세요");
		  return false;
		}
		if(document.input.content.value == "")
		{
		  alert("내용을 입력해주세요");
		  return false;
		}
		if(document.input.writer.value == "")
		{
		  alert("이름을 입력해주세요");
		  return false;
		}
		if(document.input.email.value == "")
		{
		  alert("이메일을 입력해주세요");
		  return false;
		}
		document.input.submit();
	  }
	</script>
  </head>
  <body>
	<center>
	  <hr width="600" color="Maroon" size="2" noshade>
		<font size="5" color="Navy"><b>글 편 집</b></font>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <a href="board.do">목록</a>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <a href='../'>메인</a>
	  <hr width="600" color="Maroon" size="2" noshade>
	  <!------------------------ re 변화 1 --------------------------->
	  <form name="input" action="board.do?m=updateOk&seq=${board.seq}" method="post"
	  enctype="multipart/form-data">
	  <!------------------------------------------------------------->
	   <!-- 히든을 이용하여 글 번호를 넘긴다. -->
	   <!-- <input type="hidden" name="idx" value="14"> --> 

		<table align="center" width="600" cellspacing="1" 
										  cellpadding="3" border="1">
		  <tr>
			<td align="center" width="20%">제목</td>
			<td align="center" width="80%">
			  <input type="text" name="subject" size="60" value="${board.subject}">
			</td>
		  </tr>
		  <tr>
			<td align="center" width="20%">내용</td>
			<td align="center" width="80%">

			  <textarea name="content" rows="10" cols="60">${board.content}</textarea>
			</td>
		  </tr>
		  <tr>
			<td align="center" width="20%">작성자</td>
			<td align="center" width="80%">
			  <input type="text" name="writer" size="60" value="${board.writer}" disabled>
			</td>
		  </tr>
		  <tr>
			<td align="center" width="20%">이메일</td>
			<td align="center" width="80%">
			  <input type="text" name="email" size="60" value="${board.email}" disabled>
			</td>
		  </tr>
		  <tr>
			<td align="center" width="20%">홈페이지</td>
			<td align="center" width="80%">
			  <input type="text" name="homepage" size="60" value="null">
			</td>
		  </tr>
		  <tr>
			<td align="center" width="20%">파일첨부</td>
			<td align="center" width="80%">
			  <input type="text" id="fname" name="filename" size="53" value="${board.fname}" disabled>
			  <input type="button" value="삭제" onclick="fileCheck()">
			  <input type="file" name="newfile" value="파일변경">
			  
			</td>
		  </tr>
		  <tr>
			<td align="center" width="20%">패스워드</td>
			<td align="center" width="80%">
			  <input type="text" name="pwd" size="60">
			</td>
		  </tr>
		  <tr>
			<td align="center" colspan="2">
			  <input type="button" value="수정하기" onclick="checkValue()">
			  <input type="reset" value="다시쓰기">
			</td>
		  </tr>
		</table>
	  </form>
	  <hr width="600" color="Maroon" size="2" noshade>
	</center>
	
	<script language="javascript">
	  function fileCheck(){
	    if(document.input.filename.value == "")
		{
		  alert("파일이 없습니다");
		  return false;
		}else if(document.input.filename.value != ""){
			var filetrue = confirm("파일을 삭제하시겠습니까?");
			if(filetrue==true) document.getElementById("fname").value='';
		}
	  }
	</script>
  </body>
</html>







