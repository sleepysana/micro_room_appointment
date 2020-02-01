<%--suppress JSUnresolvedVariable --%>
<%--
  Created by IntelliJ IDEA.
  User: akira
  Date: 2019/8/17
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>登录</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--图标-->
    <link rel="stylesheet" type="text/css" href="${path}/static/css/login/font-awesome.min.css">

    <!--布局框架-->
    <link rel="stylesheet" type="text/css" href="${path}/static/css/login/login-util.css">

    <!--主要样式-->
    <link rel="stylesheet" type="text/css" href="${path}/static/css/login/login.css">
</head>

<body>

<div class="login">
    <div class="container-login100">
        <div class="wrap-login100">
            <div class="login100-pic js-tilt" data-tilt>
                <img src="${path}/static/img/login_bg.png" alt="啥都没...">
            </div>

            <form class="login100-form validate-form">
				<span class="login100-form-title">
					用户登录
				</span>

                <div class="wrap-input100 validate-input">
                    <input class="input100" type="text" id="credential" placeholder="邮箱">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
						<i class="fa fa-envelope" aria-hidden="true"></i>
					</span>
                </div>

                <div class="wrap-input100 validate-input">
                    <input class="input100" type="password" id="password" placeholder="密码">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
						<i class="fa fa-lock" aria-hidden="true"></i>
					</span>
                </div>

                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" id="login">
                        登录
                    </button>
                </div>

                <div class="text-center p-t-12">
                    <a class="txt2" href="javascript:">
                        忘记密码？
                    </a>
                </div>

                <div class="text-center p-t-136">
                    <a class="txt2" href="${path}/register">
                        还没有账号？立即注册
                        <i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" type="text/javascript"></script>
<script src="https://www.layuicdn.com/layui-v2.5.4/layui.js" type="text/javascript"></script>
<script type="text/javascript">

    layui.use('form', function () {
        let layer = layui.layer;
        $("#login").click(function () {
            $(this).attr("disabled", "");
            let _this = this;
            let credential = $("#credential").val(),
                password = $("#password").val();
            $.ajax({
                type: "POST",
                url: "${path}/doLogin",
                data: {
                    "credential": credential,
                    "password": password
                },
                dataType: "json",
                success: function (data) {
                    console.log('响应的JSON:',data);
                    if (data.flag) {
                        console.log(data.resource);
                        window.location.href = data.resource;
                    } else {
                        layer.alert(data.message);
                        $(_this).removeAttr("disabled");
                    }
                },
                error: function (e) {
                    console.log(e);
                    layer.alert('哦欧~ 登录发生错误了');
                    $(_this).removeAttr("disabled");
                }
            })
        })
    });
</script>
</html>