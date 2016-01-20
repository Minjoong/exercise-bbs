
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2>로그인 정보</h2>
<c:choose>
	<c:when test="${not empty sessionScope.id}">
		<p>ID : ${sessionScope.id}</p>
		<a href="logout">로그아웃</a>
	</c:when>
	<c:otherwise>
		<a href="login">로그인</a>
		<a href="join">회원가입</a>
	</c:otherwise>
</c:choose>