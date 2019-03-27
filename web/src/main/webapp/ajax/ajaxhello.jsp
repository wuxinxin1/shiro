<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/27/027
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>

<!--ajax的一个hello
   下面为XMLHttpRequest，也就是ajxa对象
    onreadystatechange: 一个回调方法，负责和服务器交互，每次请求状态改变都会调用此方法
    readState:  请求状态，0 未初始化(调用open方法前),1 正在加载（调用open,send方法后）,2 已加载,3 交互中,4 完成
    responseText:服务器的响应
    responceXML：服务器响应的xml形式
    state: 状态码
    stateText:状态描述
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ajaxhello</title>
</head>

<script>
    window.onload=function () {

        document.getElementsByTagName("a")[0].onclick=function () {

            //构建对象
            var xmlHttpRequest = new XMLHttpRequest();
            //获取请求路径
            var path = this.href;
            //设置请求方法
            var method="GET";
            //准备发送
            alert("调用open方法前"+xmlHttpRequest.readyState)
            xmlHttpRequest.open(method,path);
            alert("调用open方法后"+xmlHttpRequest.readyState)
            //发送请求
            xmlHttpRequest.send(null);
            alert("调用send方法后"+xmlHttpRequest.readyState)
            //获取响应完成的结果
            xmlHttpRequest.onreadystatechange=function () {
                //获取和服务器交互到哪一步的状态
                var readyState = xmlHttpRequest.readyState;
                alert("readyState="+readyState);
                //如果readState状态为4说明交互完成
                if(readyState==4){
                    //如果响应状态为200，那么可以获取响应结果
                    if(xmlHttpRequest.status==200){
                        var responseText = xmlHttpRequest.responseText;
                        var responseXML =xmlHttpRequest.responseXML;
                        var statusText = xmlHttpRequest.statusText;
                        alert("响应状态描述:"+statusText)
                        alert("响应结果为:"+responseText)
                        alert("响应结果为xml:"+responseXML)
                    }
                }
            }

            //取消默认的请求功能
            return false;
        }

    }
</script>
<body>
 <a href="hello.txt">hello.txt</a>
</body>
</html>
