<#include "./top.ftl">

<div class="container">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <!-- 导航头部 -->
            <div class="navbar-header">
                <!-- 移动设备上的导航切换按钮 -->
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target=".navbar-collapse-example">
                    <span class="sr-only">切换导航</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- 品牌名称或logo -->
                <a class="navbar-brand" href="/">首页</a>
            </div>
            <!-- 导航项目 -->
            <div class="collapse navbar-collapse navbar-collapse-example">
                <!-- 一般导航项目 -->
                <ul class="nav navbar-nav">
                    <li><a href="/xyadmin"><i class="icon icon-list-ol"></i>基础数据</a></li>
                    <!-- 导航中的下拉菜单 -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon icon-group"></i>用户管理
                            <b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="/xyadmin/userList">用户列表</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon icon-th-list"></i>文章管理
                            <b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="/xyadmin/articleTypeList">文章类型</a></li>
                            <li><a href="/xyadmin/articleTagList">文章标签</a></li>
                            <li><a href="/xyadmin/articleList">文章管理</a></li>
                        </ul>
                    </li>

                    <li><a href="/xyadmin/adList"><i class="icon icon-dollar"></i>广告管理</a></li>
                    <li><a href="/xyadmin/linkList"><i class="icon icon-link"></i>友情链接</a></li>

                </ul>

                <!-- 右侧的导航项目 -->
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown ">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统
                            <b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu">
                            <li onclick="updatePassword()"><a><i class="icon icon-undo"></i>修改密码</a></li>
                            <li><a href="/xyadmin/logout"><i class="icon icon-signout"></i>退出登陆</a></li>
                        </ul>
                    </li>
                </ul>

            </div><!-- END .navbar-collapse -->
        </div>
    </nav>
    <div class="modal fade" id="passwordUpdateModal">
        <div class="modal-dialog" style="width: 400px">
            <div class="modal-content">
                <form action="/xyadmin/changePassword" method="post">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                                    class="sr-only">关闭</span></button>
                        <h4 class="modal-title">修改密码</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="password">新密码：</label>
                            <input type="text" class="form-control" id="password"
                                   placeholder="新密码" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');">
                        </div>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="newPassword">确认密码：</label>
                            <input type="text" class="form-control" id="newPassword"
                                   placeholder="确认密码" onkeyup="this.value=this.value.replace(/[^\w_]/g,'');">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="changePassword()">确认
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        function updatePassword() {
            $('#passwordUpdateModal').modal('toggle', 'center');
        }


        function changePassword() {
            let password = $('#password').val()
            let newPassword = $('#newPassword').val()
            if (password !== newPassword) {
                warningZuiMsg("确认密码错误！")
                return
            }
            $.post("/xyadmin/changePassword", {
                    newPassword: newPassword,
                },
                function (data) {
                    if (data.code == 200) {
                        successZuiMsg("修改成功！")
                        sleep(500); //暂停0.5秒
                        window.location.href = "/xyadmin/logout"
                        return;
                    }
                    warningZuiMsg(data.message);
                });
        }


    </script>
