<#include "../import/userTop.ftl">
<div class="container panel"
     style="box-shadow: 2px 2px 5px #212024;">
    <div class="log-sign-box">
        <div class="form-box">
            <div class="register-box hidden">
                <h1>注册</h1>
                <input type="text" id="userName" name="userName" placeholder="用户名">
                <input type="password" id="password" name="password" placeholder="密码">
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="确认密码">
                <div style="margin-left: 47px">
                    <input type="text" style="width: 50%;float: left" name="verifyCode" id="verifyCode"
                           placeholder="验证码"
                           class="input-item">
                    <img src="/getCaptcha" id="check_code_img"
                         style="height: 50px;float: left;border:solid;border-radius: 10px"
                         onclick="javascript:this.src='/getCaptcha?'+new Date().getTime();"
                         title="点击刷新"></div>
                <button type="button" onclick="signIn()">注册</button>
            </div>
            <div class="login-box">
                <h1>登录</h1>
                <input type="text" id="log_userName" name="log_userName" placeholder="用户名">
                <input type="password" id="log_password" name="log_password" placeholder="密码">
                <div style="margin-left: 47px">
                    <input type="text" style="width: 50%;float: left" name="log_verifyCode" id="log_verifyCode"
                           placeholder="验证码"
                           class="input-item">
                    <img src="/getCaptcha" id="log_check_code_img"
                         style="height: 50px;float: left;border:solid;border-radius: 10px"
                         onclick="javascript:this.src='/getCaptcha?'+new Date().getTime();"
                         title="点击刷新"></div>

                <button type="button" onclick="logIn()">登录</button>
            </div>
        </div>
        <div class="con-box left"><h2>欢迎来到<span>博客园</span></h2>
            <p>来创建你的专属<span>账号</span>吧</p>
            <img src="../imgs/null_logo.png" alt="">
            <p>已有账号</p>
            <button id="login">去登录</button>
        </div>
        <div class="con-box right">
            <h2>欢迎进入<span>博客园</span></h2>
            <p>来学习他人<span>技术</span>吧</p>
            <img src="../imgs/null_logo.png" alt="">
            <p>没有账号？</p>
            <button id="register">去注册</button>
        </div>

    </div>
</div>
<script>
    // 登录注册动画
    //要操作到的元素
    let login = document.getElementById('login');
    let register = document.getElementById('register');
    let form_box = document.getElementsByClassName('form-box')[0];
    let register_box = document.getElementsByClassName('register-box')[0];
    let login_box = document.getElementsByClassName('login-box')[0]
    //去注册按钮点击事件
    register.addEventListener('click', () => {
        form_box.style.transform = 'translateX(80%)';
        login_box.classList.add('hidden');
        register_box.classList.remove('hidden');
        $("#check_code_img").click();
    })
    //去登录按钮点击事件
    login.addEventListener('click', () => {
        form_box.style.transform = 'translateX(0%)';
        register_box.classList.add('hidden');
        login_box.classList.remove('hidden');
        $("#log_check_code_img").click();
    })

    // 登录
    function logIn() {
        let userName = $("#log_userName").val();
        let password = $("#log_password").val();
        let verifyCode = $("#log_verifyCode").val();

        if (!checkNotNull(userName) || !checkNotNull(password)) {
            warningZuiMsg("请输入用户名和密码")
            return;
        }
        if (!checkNotNull(verifyCode) || verifyCode.length != 4) {
            warningZuiMsg("验证码不正确")
            return;
        }

        $.post("/user/login", {
                userName: userName,
                password: password,
                verifyCode: verifyCode
            },
            function (data) {
                if (data.code == 200) {
                    successZuiMsg("登录成功")
                    sleep(1000)
                    window.location.href = '${referer!'/'}';
                    return;
                }
                warningZuiMsg(data.message);
                $("#log_check_code_img").click();
            });
    }

    // 注册
    function signIn() {
        let userName = $("#userName").val();
        let password = $("#password").val();
        let confirmPassword = $("#confirmPassword").val();
        let verifyCode = $("#verifyCode").val();

        if (!checkNotNull(userName) || !checkNotNull(password) || !checkNotNull(confirmPassword)) {
            warningZuiMsg("请输入用户名和密码")
            return;
        }
        if (password != confirmPassword) {
            warningZuiMsg("两次输入的密码不一致")
            return;
        }
        if (!checkNotNull(verifyCode) || verifyCode.length != 4) {
            warningZuiMsg("验证码不正确")
            return;
        }

        $.post("/user/signIn", {
                userName: userName,
                password: password,
                verifyCode: verifyCode
            },
            function (data) {
                if (data.code == 200) {
                    successZuiMsg("注册成功")
                    form_box.style.transform = 'translateX(0%)';
                    register_box.classList.add('hidden');
                    login_box.classList.remove('hidden');
                    $("#log_check_code_img").click();
                    return;
                }
                warningZuiMsg(data.message);
                $("#check_code_img").click();
            });
    }


</script>

<style>
    * {
        /*初始化*/
        margin: 0;
        padding: 0;
    }

    . container {
        /*100%窗口高度*/
        height: 110vh;
        /*弹性布局水平+垂直居中*/
        display: flex;
        justify-content: center;
        align-items: center;
        /*渐变背景*/
        background: linear-gradient(200deg, #e6e8f4, #e3eeff)
    }

    .log-sign-box {
        background-color: #fff;
        width: 650px;
        height: 415px;
        border-radius: 5px;
        /*阴影*/
        box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
        /*相对定位*/
        position: relative;
        display: block;
        margin: 80px auto;
    }

    .form-box {
        /*绝对定位*/
        position: absolute;
        top: -10%;
        left: 5%;
        background-color: #67b1e8;
        width: 320px;
        height: 500px;
        border-radius: 5px;
        box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 2;
        /*动画过渡 加速后减速*/
        transition: 0.5s ease-in-out
    }

    .register-box, .login-box {

        /*弹性布局垂直排列*/
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
    }

    .hidden {
        display: none;
        transition: 0.5s;
    }

    h1 {
        text-align: center;
        margin-bottom: 25px;
        font-family: "楷体";
        color: #fff;
        /*字间距*/
        letter-spacing: 5px;
    }

    input {
        background-color: transparent;
        width: 70%;
        color: #fff;
        border: none;
        /*下边框样式*/
        border-bottom: 1px solid rgba(255, 255, 255, 0.4);
        padding: 10px 0;
        text-indent: 10px;
        margin: 8px 0;
        font-size: 14px;
        letter-spacing: 2px;
    }

    input::placeholder {
        color: #fff;
    }

    input:focus {
        color: #29262a;
        outline: none;
        border-bottom: 1px solid rgba(222, 210, 224, 0.5);
        transition: 0.5s;
    }

    input:focus::placeholder {
        opacity: 0;
    }

    .form-box button {
        width: 70%;
        margin-top: 35px;
        background-color: #f6f6f6;
        outline: none;
        border-radius: 8px;
        padding: 13px;
        color: #266fa6;
        letter-spacing: 2px;
        border: none;
        cursor: pointer;
    }

    .form-box button:hover {
        background-color: #33ecd0;
        color: #f6f6f6;
        transition: background-color 0.5s ease;
    }

    .con-box {
        width: 50%;
        /*弹性布局垂直排列居中*/
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        /*绝对定位居中*/
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
    }

    .con-box.left {
        left: -2%;
    }

    .con-box.right {
        right: -2%;
    }

    .con-box h2 {
        color: #8e9aaf;
        font-size: 25px;
        font-weight: bold;
        letter-spacing: 3px;
        text-align: center;
        margin-bottom: 4px;
    }

    .con-box p {
        font-size: 12px;
        letter-spacing: 2px;
        color: #8e9aaf;
        text-align: center;
    }

    .con-box span {
        color: #266fa6;
    }

    .con-box img {
        width: 150px;
        height: 150px;
        opacity: 0.9;
        margin: 40px 0;
    }

    .con-box button {
        margin-top: 3%;
        background-color: #fff;
        color: #266fa6;
        border: 1px solid #266fa6;
        padding: 6px 10px;
        border-radius: 5px;
        letter-spacing: 1px;
        outline: none;
        cursor: pointer;
    }

    .con-box button:hover {
        background-color: #266fa6;
        color: #fff;
    }
</style>


<#include "../import/userBottom.ftl">