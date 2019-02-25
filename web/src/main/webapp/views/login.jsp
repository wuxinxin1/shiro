<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/2/24/024
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>login</h1>
<form action="/shiro/test/login" method="post">
    username:<input type="text" name="username"/>
    <br><br>
    password:<input type="text" name="password"/>
    <br><br>
    <input type="submit" value="登录"/>
    <br><br>
</form>
</body>
</html>
