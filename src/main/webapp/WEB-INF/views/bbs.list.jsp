<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>스프링프레임워크 게시판</title>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script>
	var check = false;
	function CheckAll() {
		var chk = document.getElementsByName("del_idx");
		if (check == false) {
			check = true;
			for (var i = 0; i < chk.length; i++) {
				chk[i].checked = true; //모두 체크
			}
		} else {
			check = false;
			for (var i = 0; i < chk.length; i++) {
				chk[i].checked = false; //모두 해제
			}
		}
	}
</script>
<script>
$(document).ready(function(){
	$("#search").load("search");
});
</script>
</head>
<body>

	<h1>게시판</h1>
	<%@include file="./login.info.jsp"%>
	<div id="search"></div>
	<p>search = ${param.search}</p>

	<h2>${message}</h2>
	<form method=POST action="./delete">
		<table border="1">
			<thead>
				<tr>
					<%-- 이 체크박스를 통해서 전체 선택 해제가 된다 --%>
					<th><input type="checkbox" name="checkall"
						onclick="javascript:CheckAll()"></th>
					<th scope="col">시퀀스 넘버</th>
					<th scope="col">작성자</th>
					<th scope="col">제목</th>
					<th scope="col">내용</th>
					<th scope="col">작성일</th>
					<th scope="col">수정일</th>
				</tr>
			</thead>

			<tbody>
				<!-- 목록이 반복될 영역 -->
				<c:forEach var="item" items="${list}" varStatus="status">
					<tr>
						<%-- 이 체크박스가 for문에 의해서 여러개 생성 --%>
						<td><input type="checkbox" id="del_idx" name="del_idx"
							value="${item.idx}"></td>
						<td><a href="./write?idx=${item.idx}">${item.idx}</a></td>
						<td>${item.user_id}</td>
						<td>${item.subject}</td>
						<td>${item.content}</td>
						<td>${item.reg_datetime}</td>
						<td>${item.mod_datetime}</td>
					</tr>
				</c:forEach>

			</tbody>

		</table>
		<p>
			<c:forEach var="page_i" begin="1" end="${totalPage}" step="1">
				<c:choose>
					<c:when test="${page_i == pageNumber}">
						${page_i}
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${not empty param.search}">
								<a href="./list?search=${param.search}&PageNumber=${page_i}">${page_i}</a>
							</c:when>
							<c:otherwise>
								<a href="./list?PageNumber=${page_i}">${page_i}</a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</p>
		<div><a href="./write">쓰기</a></div>
		<input type="submit" value="삭제">
	</form>
</body>
</html>