<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>API Key Generate</title>
</head>
<body>
<h2>API Key 생성</h2>
<hr>
<form action="/api/generate-api-key" method="post">
    <input type="text" placeholder="Admin ID" name="adminId">
    <input type="text" placeholder="Admin PW" name="adminPw">
    <input type="submit">
</form>
</body>
</html>