<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/27/027
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>

<!--
   ajax与服务器用xml进行交互
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script
        src="https://code.jquery.com/jquery-3.3.1.js"
        integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
        crossorigin="anonymous"></script>

<!---jquery的ajax的使用get方法
    1.load方法有四个参数 url args  callfunction，type
-->
<script>
    $(function () {

        $("a").click(function () {
            var url=this.href;
            var args={"time":new Date()}
            //发送get请求
            $.get(url,args,function (date) {
                //获取到的是一个xmlObject

            })
            return false;
        })
    })
</script>
<body>

   <ul>
       <li><a href="a.xml">a.xmll</a></li>
       <li><a href="b.xml">b.xml</a></li>
   </ul>

   <ul id="aa"></ul>
</body>
</html>
