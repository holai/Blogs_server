<%@ page import="java.net.URLDecoder" %><%--
  Created by IntelliJ IDEA.
  User: lu
  Date: 2019/12/16
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆成功</title>
</head>
<body>
    <%
        String login= request.getParameter("login");
        String id= request.getParameter("id");
        String type= request.getParameter("type");
        String name = URLDecoder.decode(request.getParameter("name"),"utf-8");
        String avatar = request.getParameter("avatar");
        // 创建Cookie
        Cookie cookie = new Cookie("login", login);
        cookie.setPath("/");
        // 有效期,秒为单位
        cookie.setMaxAge(10*60*60*60);
        // 设置cookie
        response.addCookie(cookie);
        // 创建Cookie
        Cookie cookie1 = new Cookie(login, id);
        cookie1.setPath("/");
        // 有效期,秒为单位
        cookie1.setMaxAge(10*24*60*60);
        // 设置cookie
        response.addCookie(cookie1);
        // 创建Cookie
        Cookie cookie2 = new Cookie("name", name);
        cookie2.setPath("/");
        // 有效期,秒为单位
        cookie2.setMaxAge(10*24*60*60);
        // 设置cookie
        response.addCookie(cookie2);
        // 创建Cookie
        Cookie cookie4 = new Cookie("avatar", avatar);
        cookie4.setPath("/");
        // 有效期,秒为单位
        cookie4.setMaxAge(10*24*60*60);
        // 设置cookie
        response.addCookie(cookie4);
    %>
    <script>
        var isyd= navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i);
        if(isyd){
            window.history.back(-1);
        }

    </script>
    <div style="font-size: 20px;width: 100%;text-align: center">
        登陆成功
    </div>
    <div style="font-size: 20px;width: 100%;text-align: center">
        如果没有自动返回请返回主页
        <br/>
        <a href="https://houlai.fun/">回到主页</a>
    </div>


</body>
</html>
