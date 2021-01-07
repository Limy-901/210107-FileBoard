<%@ page language="java" contentType="text/html; charset=UTF-8"
import="java.util.*, mvc.domain.FileBoard, board.mvc.vo.ListResult"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../login/login_check_modul.jsp"/>




<link rel="stylesheet" type="text/css" href="http://image.lgeshop.com/css/style_2005.css">

<html>
  <head> 
    <title>File Board LIST</title>
  </head>
  <body>
    <center>
	  <hr width="600" color="Maroon" size="2" noshade>
	    <font size="5" color="Navy">
		  <b>LYM board</b>
		</font>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="board.do?m=write">글쓰기</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href='../'>메인</a>
		<br/><br/>
			  <hr width="600" color="Maroon" size="2" noshade>
			  <br/>
		<form name="f">
		  <input type="hidden"  name="method" value="list">
		  <select name="ps" onChange="submit()">
		  <c:choose>
	          <c:when test="${listResult.pageSize == 3}">
	         		<option value=3 selected>페이지 SIZE 3</option>
					<option value=5>페이지 SIZE 5</option>
					<option value=10>페이지 SIZE 10</option>
	          </c:when>
	          <c:when test="${listResult.pageSize == 5}">
	         		<option value=3>페이지 SIZE 3</option>
					<option value=5 selected>페이지 SIZE 5</option>
					<option value=10>페이지 SIZE 10</option>
	          </c:when>
	          <c:when test="${listResult.pageSize == 10}">
	         		<option value=3>페이지 SIZE 3</option>
					<option value=5 >페이지 SIZE 5</option>
					<option value=10 selected>페이지 SIZE 10</option>
	          </c:when>
			</c:choose>
		  </select>
		</form>
		

	  <table align="center" cellspacing="1" cellpadding="3" width="800" border="1">
		<tr align="center">
		  <td width="10%">
		    <b>순번</b>
		  </td>
		  <td width="40%"><b>제목</b></td>
		  <td width="15%"><b>글쓴이</b></td>
		  <td width="15%"><b>날짜</b></td>
		  <td width="20%"><b>조회수</b></td>
		</tr>

<c:forEach items="${listResult.list}" var="board">
                <tr align="center">
				  <td width="10%">${board.seq}</td>
				  <td width="40%" align="left"> 
		            <a href="board.do?m=content&seq=${board.seq}&cp=${listResult.currentPage}">
                      ${board.subject}
                    <a>
				  </td>
				  <td width="15%">
					${board.writer}
				  </td>
				  <td width="15%">${board.rdate}</td>
				  <td width="20%">조회수</td>
				</tr>
			</tr>
			<tr>
		</c:forEach>
		  <td colspan="3" align="center">


 <c:forEach begin="1" end="${listResult.totalPageCount}" var="i">
        <a href="board.do?cp=${i}">
           <c:choose>
               <c:when test="${i==listResult.currentPage}">
                  <strong>${i}</strong>
               </c:when>
	           <c:otherwise>
	              ${i}
	           </c:otherwise>
           </c:choose>
        </a>&nbsp;
    </c:forEach>
			 &nbsp;&nbsp;&nbsp; 
			${listResult.currentPage}/${listResult.totalPageCount}
		  </td>
		  <td colspan="2" align="center">
		    총 게시물 수 : ${listResult.totalCount}
		  </td>
		</tr>
	  </table>
	</center>
	<br/><br/>
	  <table align="center" width="60%" cellspacing="1" cellpadding="3" border="1" id="table">
		<tr><td align="center" width="35%">&nbsp;${loginPassUser.name} 님 반갑습니다^^ </td></tr>
  </table>
  
  </body>
</html>

