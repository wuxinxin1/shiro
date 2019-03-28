<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/27/027
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>

<!--
   ajax与服务器用json进行交互
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script>
    window.onload=function () {
        var elementsByTagName = document.getElementsByTagName("a");
        var ul = document.getElementById("aa");
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
                            var responseText = xmlHttpRequest.responseText;
                            //将字符串转换为json
                            var object = eval("("+responseText+")");
                            //创建子节点
                            var nameTar = document.createElement("li");
                            var ageTar = document.createElement("li");

                            //创建子节点的文本节点
                            var nameText = document.createTextNode(object.name);
                            var ageText = document.createTextNode(object.age);
                            nameTar.appendChild(nameText)
                            ageTar.appendChild(ageText)

                            //清理之前创建的节点
                            ul.innerHTML=""
                            //将创建的子节点放入到ul中
                            ul.appendChild(nameTar)
                            ul.appendChild(ageTar)
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
       <li><a href="a.json">a.json</a></li>
       <li><a href="b.json">b.json</a></li>
   </ul>

   <ul id="aa"></ul>
</body>
</html>
