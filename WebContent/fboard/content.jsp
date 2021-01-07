<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${empty board}">
    <c:redirect url="board.do"/>
</c:if>

<link rel="stylesheet" type="text/css" href="http://image.lgeshop.com/css/style_2005.css">
<html>
  <head>
    <title>reboard_content.jsp</title>
  </head>
  <body>
    <center>
	  <hr width="600" color="Maroon" size="2" noshade>
	    <font size="5" color="Navy">
		  <b>LYM board</b>
		</font>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <a href="board.do?m=list&cp=1">목록</a>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <a href='../'>메인</a>
		  
	  <hr width="600" color="Maroon" size="2" noshade>


	  <table align="center" width="60%" cellspacing="1" 
	                          cellpadding="3" border="1" id="table">
		<tr>
		  <td align="center" width="15%"><b>순번</b></td>
		  <td align="center" width="35%">&nbsp;${board.seq}</td>
		  <td align="center" width="15%"><b>날짜</b></td>
		  <td align="center" width="35%">
			&nbsp;${board.rdate}
		  </td>
		</tr>
		<tr>
		  <td align="center" width="15%"><b>글쓴이</b></td>
		  <td align="center" width="35%">
			&nbsp; ${board.writer}
	      </td>
		  <td align="center" width="15%"><b>HomePage</b></td>
		  <td align="center" width="35%">
		    &nbsp;null
		  </td>
		</tr>
		<tr>
		
		
		  <td colspan="2">&nbsp;&nbsp;<b>제목</b> : ${board.subject}</td>
		  <td colspan="2">&nbsp;&nbsp;<b>첨부파일</b>&nbsp;&nbsp;:
		  <c:choose>
		  	<c:when test="${!empty board.fname}">
		  		<a href="board.do?m=download&fname=${board.fname}">${board.fname}</a> / ${board.fsize}KB
		  	</c:when>
		  	<c:otherwise>
		  	&nbsp;파일 없음.
		  	</c:otherwise>
		  	</c:choose>
		  
</td>
		
		  </td>
		</tr>
		<tr>

		  <td colspan="4" height="200">&nbsp;&nbsp;<b>내용</b> : ${board.content}</td>
		</tr>
		<tr>
		  <td colspan="4" align="center">
			<a href="board.do?m=list&cp=1">목록</a> | 
			<a href="board.do?m=update&seq=${board.seq}">편집</a> | 
			<a href="board.do?m=del">삭제</a> | 
			<a href="board.do?m=rewrite&sub=${board.subject}">답변</a>
		  </td>
		</tr>
		<tr align="center" id="ta">
		  <td>
		     리플달기 
		  </td>

          <script language="javascript">
              loginJs = false;
              function check()
              {
                  if(!loginJs)
                  {
                      yesNo = confirm(
                         "먼저 로그인을 하셔야 합니다.");
                      if(yesNo)
                      {
                         location.href="../";
  
                      }
                      
                      return;
                  }
                  else
                  {
                      if(f1.content_reply.value == "" || f1.pwd_reply.value == "")
	                  {
	                      alert("리플 내용과 비밀번호를 모두 입력하셔야 합니다.");
	                      return;
	                  }
                  	  f1.submit();
                  }
              }
          </script>
		  <form name="f1" action="rb-reply-save.do">
			  <td colspan="3">
			     <input type="hidden" name="method" value="replySave">
				 <input type="text" name="content_reply" size="70">
				 &nbsp;&nbsp;비밀번호
				 <input type="text" name="pwd_reply" size="10">
				 <input type="button" value="등록" onclick="check()">
			  </td>
		  </form>
		</tr>
	  </table>
	  
	  
<script language="javascript">     
      function replyDelCheck(idx_reply)
      {
          //alert("loginJs : " + loginJs);
          //alert("idx_reply : " + idx_reply);
          if(!loginJs) //로그인 안함 
          {
             
             alert("로그인 or 리플의 비밀번호를 입력하셔야 합니다.");
             yesNo = confirm("로그인을 하시겠습니까?");
             if(yesNo)
             {
                  location.href="/M2Project/login.do";   
      
             }
             else
             {
                   pwd_reply = prompt("리플 비밀번호를 입력해주세요..");
                   location.href ="/M2Project/rb-reply-del.do?method=replyDel"
                        +"&idx_reply="+idx_reply+"&pwd_reply="+pwd_reply;
             }
          }
          else  // 로그인 함 
          {
               location.href ="/M2Project/rb-reply-del.do?method=replyDel"
                        +"&idx_reply="+idx_reply;
          }
      }
</script>
<br/>
      <table align="center" width="60%" cellspacing="1" 
	                          cellpadding="3" border="1"> 
		 <tr align="center">
		     <td colspan="2">
			    <font color="red"><b>R E P L Y</b></font>
			 </td>
		 </tr>


	  </table>
	</center>
  </body>
</html></html>