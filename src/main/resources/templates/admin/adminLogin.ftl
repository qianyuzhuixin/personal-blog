<#include "../import/top.ftl">
<div class="container">
    <div class="login-wrapper">
        <div class="header">登录</div>
        <div class="form-wrapper">
            <label for="adminName" style="font-size: large">用户名：</label>
            <input type="text" name="adminName" id="adminName" placeholder="用户名" class="input-item">
            <label for="adminPassword" style="font-size: large">密码：</label>
            <input type="password" name="adminPassword" id="adminPassword" placeholder="密码" class="input-item"
                   required>
            <div>
                <label for="verifyCode" style="font-size: large;display: block">验证码：</label>
                <input type="text" name="verifyCode" id="verifyCode" placeholder="验证码" class="input-item"
                       style="width: 50%;float: left">
                <img src="/getCaptcha" id="check_code_img" style="height: 50px;float: right;border:solid"
                     onclick="javascript:this.src='/getCaptcha?'+new Date().getTime();"
                     title="点击刷新">
            </div>
            <div class="btn" onclick="submit()">登录</div>
        </div>
    </div>
</div>

<script>
    function submit() {
        let adminName = $("#adminName").val();
        let adminPassword = $("#adminPassword").val();
        let verifyCode = $("#verifyCode").val();

        if (!checkNotNull(adminName) || !checkNotNull(adminPassword)) {
            warningZuiMsg("请输入用户名和密码")
            return;
        }

        if (!checkNotNull(verifyCode) || verifyCode.length != 4) {
            warningZuiMsg("验证码不正确")
            return;
        }

        $.post("/xyadmin/adminLogin", {
                adminName: adminName,
                adminPassword: adminPassword,
                verifyCode: verifyCode
            },
            function (data) {
                if (data.code == 200) {
                    window.location.href = "/xyadmin/"
                    return;
                }
                warningZuiMsg(data.message);
                $("#check_code_img").click();
            });
    }
</script>

<style>
    * {
        margin: 0;
        padding: 0;
    }

    html {
        height: 100%;
    }

    body {
        height: 100%;
        font-family: "楷体";
    }

    .container {
        height: 100%;
        background-image: linear-gradient(to right, #d3d1d3, #a6c1ee);
    }

    .login-wrapper {
        background-color: #fff;
        width: 358px;
        height: 588px;
        border-radius: 15px;
        padding: 0 50px;
        position: relative;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }

    .header {
        font-size: 38px;
        font-weight: bold;
        text-align: center;
        line-height: 200px;
    }

    .input-item {
        display: block;
        width: 100%;
        margin-bottom: 20px;
        border: 0;
        padding: 10px;
        border-bottom: 1px solid rgb(128, 125, 125);
        font-size: 15px;
        outline: none;
    }

    .input-item::placeholder {
        text-transform: uppercase;
    }

    .btn {
        text-align: center;
        padding: 10px;
        width: 100%;
        margin-top: 40px;
        background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
        color: #fff;
    }

    .msg {
        text-align: center;
        line-height: 88px;
    }

    a {
        text-decoration-line: none;
        color: #abc1ee;
    }
</style>
<#include "../import/bottom.ftl">