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
<script
        src="https://code.jquery.com/jquery-3.3.1.js"
        integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
        crossorigin="anonymous"></script>

<!---jquery的ajax的使用load方法
    1.load方法有三个参数 url args  callfunction
        a.添加了请求参数会使用post,否则使用get
        b.url路径可以使用选择器，对返回结果的html进行筛选
        c.请求参数设置一个时间，可以起到刷新缓存的作用
        d.回调函数用于接受结果的，以及对结果的处理
-->
<script>
    $(function () {

        $("a").click(function () {
            //var url=this.href;
            //只需要h2标签
            var url=this.href+" h2";
            var args={"time":new Date()}
            $("#aa").load(url,args,function (date) {
                alert(date)
            })
            //$("#aa").load(url)
            return false;
        })
    })
</script>
<body>

   <ul>
       <li><a href="a.xml">a.html</a></li>
       <li><a href="b.html">b.html</a></li>
   </ul>

   <div id="aa"></div>
</body>
</html>
