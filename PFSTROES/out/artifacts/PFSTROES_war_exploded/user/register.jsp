<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css'/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--webfonts-->
    <!--//webfonts-->
    <script src="js/setDate.js" type="text/javascript"></script>
    <script type="text/javascript">
        function jiancha() {
            if (/^[a-z0-9_-]{3,16}$/.test(document.getElementsByName("username")[0].value)) {
                isUserUsernameAvailable();
            } else {
                var message = document.getElementById("message");
                message.innerText = "用户名非法";
                message.style.color = 'red';
            }
        }

        function jianchaemail() {
            if (/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(document.getElementsByName("email")[0].value)) {
                var message = document.getElementById("message2");
                message.innerText = "邮箱地址可用";
                message.style.color = 'blue';
            } else {
                var message = document.getElementById("message2");
                message.innerText = "邮箱地址非法";
                message.style.color = 'red';
            }
        }
        function jianchanbirthday() {
            if (/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/.test(document.getElementsByName("birthday")[0].value)) {
                var message = document.getElementById("message3");
                message.innerText = "格式正确";
                message.style.color = 'blue';
            } else {
                var message = document.getElementById("message3");
                message.innerText = "格式不正确，示例：2018-03-16";
                message.style.color = 'red';
            }
        }
        function isUserUsernameAvailable() {
            var username = document.getElementsByName("username")[0];
            var message = document.getElementById("message");
            if (username.value === "") {
                message.innerText = "";
                return;
            }
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200) {
                    var responseText = request.responseText;
                    if (responseText === "true") {
                        message.innerText = "用户名可用";
                        message.style.color = 'blue';
                    } else {
                        message.innerText = "用户名重复";
                        message.style.color = 'red';
                    }
                }
            };
            var url = "/admin/AjaxServlet?ajax=isUserUsernameAvailable&username=" + username.value;
            request.open("GET", url, true);
            request.send(null);
        }
    </script>
</head>

<body>
<div class="main" align="center">
    <div class="header">
        <h1>创建一个免费的新帐户！</h1>
    </div>
    <p></p>
    <form method="post" action="${pageContext.request.contextPath }/user/UserServlet">
        <input type="hidden" name="op" value="register"/>
        <ul class="left-form">
            <li>
                <span id="message">${msg.error.username }</span><br/>
                <input type="text" name="username" placeholder="用户名" value="${msg.username}" required="required"
                       onblur="jiancha()"/>
                <a href="#" class="icon ticker"> </a>
                <div class="clear"></div>
            </li>
            <li>
                ${msg.error.nickname }<br/>
                <input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
                <a href="#" class="icon ticker"> </a>
                <div class="clear"></div>
            </li>
            <li>
                <span id="message2">${msg.error.email }</span><br/>
                <input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required"
                       onblur="jianchaemail()"/>
                <a href="#" class="icon ticker"> </a>
                <div class="clear"></div>
            </li>
            <li>
                ${msg.error.password }<br/>
                <input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
                <a href="#" class="icon into"> </a>
                <div class="clear"></div>
            </li>
            <li>
                <span id="message3">${msg.error.birthday }</span><br/>
                <input type="text" placeholder="出生日期 2018-03-16" name="birthday" value="${msg.birthday}" size="15"
                       onblur="jianchanbirthday()"/>
                <div class="clear"></div>
            </li>
            <li style="height: 10px;"></li>
            <li>
                <input type="submit" value="创建账户">
                <div class="clear"></div>
            </li>
        </ul>
        <p class="submit">
            <a href="${pageContext.request.contextPath }/user/login.jsp">登录</a>
            <a href="${pageContext.request.contextPath }/index.jsp">返回首页</a>
        </p>

        <div class="clear"></div>

    </form>

</div>
<!-----start-copyright---->

<!-----//end-copyright---->

</body>

</html>