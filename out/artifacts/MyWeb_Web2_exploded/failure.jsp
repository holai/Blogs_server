<%--
  Created by IntelliJ IDEA.
  User: lu
  Date: 2019/12/18
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆失败</title>
</head>
<body>

<div style="font-size: 20px;width: 100%;text-align: center">
    登陆失败
</div>
<div style="font-size: 20px;width: 100%;text-align: center">
<%
    String login= request.getParameter("token");
    out.print(login);
%>
</div>
</body>
</html>
