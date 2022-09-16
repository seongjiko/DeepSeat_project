<%--
  Created by IntelliJ IDEA.
  User: soc06212
  Date: 2022/09/16
  Time: 17:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Room 생성</title>
</head>
<body>
<h2>Room 생성</h2>
<form action="/api/room/create" method="post" onsubmit="return checkValid();">
    <input type="text" name="apiKey" placeholder="API Key" id="apiKey">
    <input type="text" name="roomName" placeholder="방 이름" id="roomName">
    <input type="number" name="longitude" placeholder="경도 (longitude)" id="longitude">
    <input type="number" name="latitude" placeholder="위도 (latitude)" id="latitude">
    <input type="submit">
</form>
</body>
<script type="text/javascript">
    function checkValid() {
        const apiKey = document.getElementById("apiKey").value;
        const roomName = document.getElementById("roomName").value;
        const longitude = document.getElementById("longitude").value;
        const latitude = document.getElementById("latitude").value;

        if (apiKey !== "" && roomName !== "" && longitude !== "" && latitude !== "") {
            return true;
        } else {
            alert("모든 값을 입력해주세요.")
            return false;
        }
    }
</script>
</html>
