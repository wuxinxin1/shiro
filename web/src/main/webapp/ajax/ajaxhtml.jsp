<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/27/027
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>

<!--
   ajax与服务器用html进行交互
   1.使用innerHTML直接将标签数据放在标签中
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script>
    window.onload=function () {
        var elementsByTagName = document.getElementsByTagName("a");
        for (var  i=0;i<elementsByTagName.length;i++){
            elementsByTagName[i].onclick=function () {
                //构建对象
                var xmlHttpRequest = new XMLHttpRequest();
                //获取请求路径
                var path = this.href;
                //设置请求方法
                var method="GET";
                //准备发送
                xmlHttpRequest.open(method,path);
                //发送请求
                xmlHttpRequest.send(null)
                //获取结果
                xmlHttpRequest.onreadystatechange=function () {
                    //响应正常
                    if(xmlHttpRequest.readyState==4){
                        if(xmlHttpRequest.status==200){
                            document.getElementById("aa").innerHTML=xmlHttpRequest.responseText;
                        }
                    }
                }

                return false;
            }
        }
    }
</script>
<body>

   <ul>
       <li><a href="a.html">a.html</a></li>
       <li><a href="b.html">b.html</a></li>
   </ul>

   <div id="aa"></div>
</body>
</html>
