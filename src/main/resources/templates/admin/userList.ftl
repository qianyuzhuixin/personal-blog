<#include "../import/adminTop.ftl">

<div class="panel">
    <div class="panel-body">
        <form class="form-horizontal" action="/xyadmin/userList" method="get">
            <div class="form-group">
                <label for="userName" class="col-sm-1">用户名</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="用户名">
                </div>
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-success">查询</button>
                </div>
                <div class="col-sm-1">
                    <a href="/xyadmin/userList" class="btn btn-success">查询全部</a>
                </div>
            </div>
        </form>
    </div>
</div>

<#if userList?? && userList.data?size gt 0 >

    <h3><i class="icon icon-info-sign"></i> 被冻结的用户无法登陆</h3>
    <div class="panel">
        <div class="panel-body">
            <table class="table">
                <thead>
                <tr>
                    <th>注册时间</th>
                    <th>用户名</th>
                    <th>是否冻结</th>
                    <th>是否允许发布文章</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list  userList.data as user>
                    <tr>
                        <td>${(user.userRegisterTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>${(user.userName)!}</td>
                        <td>
                            <#if (user.userFrozen)?? && (user.userFrozen) == 1>
                                <span class="label label-danger">冻结</span>
                            <#else>
                                <span class="label label-success">正常</span>

                            </#if>
                        </td>
                        <td>
                            <#if (user.userWrite)?? && (user.userWrite) == 1>
                                <span class="label label-danger">不允许发布文章</span>
                            <#else>
                                <span class="label label-success">允许发布文章</span>
                            </#if>
                        </td>
                        <td>
                            <button onclick="updateUser('${(user.userId)!}','${(user.userName)!}','${(user.userFrozen)!}','${(user.userWrite)!}')"
                                    type="button"
                                    class="btn btn-mini"><i
                                        class="icon icon-cog"></i> 修改
                            </button>
                            <button onclick="delUser('${user.userId}')" type="button" class="btn btn-mini"><i
                                        class="icon icon-remove"></i>删除
                            </button>
                        </td>
                    </tr>
                </#list>
                </tbody>

            </table>
        </div>
    </div>
    <div class="panel">
        <div class="panel-body" style="text-align: center">
            <ul class="pager pager-pills" style="">
                <li class="previous" onclick="getNewData(1)">
                    <a href="javaScript:void(0)"><i class="icon icon-step-backward"></i></a>
                </li>
                <#if userList.currentPage lte 1>
                    <li class="previous disabled">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                    </li>
                <#else >
                    <li class="previous" onclick="getNewData('${userList.currentPage-1}')">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                    </li>
                </#if>
                <li>
                    <a href="javaScript:void(0)" class="btn">第${userList.currentPage}页/共${userList.getTotalPages()}
                        页</a>
                </li>
                <#if userList.currentPage gte userList.getTotalPages()>
                    <li class="next disabled">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                    </li>
                <#else>
                    <li class="next" onclick="getNewData('${userList.currentPage+1}')">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                    </li>
                </#if>
                <li class="previous" onclick="getNewData('${userList.getTotalPages()}')">
                    <a href="javaScript:void(0)"><i class="icon icon-step-forward"></i></a>
                </li>
                <li class="next">
                    <a href="javaScript:void(0)"><input type="number"
                                                        id="renderPageNumber"
                                                        maxlength="5"
                                                        style="height: 20px;width: 50px;"
                                                        oninput="value = value.replace(/[^\d]/g,'')">
                    </a>
                </li>
                <li class="next">
                    <a href="javaScript:void(0)" onclick="renderPage()">
                        跳转
                    </a>
                </li>
            </ul>

        </div>
    </div>
<#else >
    <div style="text-align: center">
        <h3><i class="icon icon-coffee"></i></h3>
        <h3>暂无数据</h3>
    </div>
</#if>

<div class="modal fade" id="userUpdateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/xyadmin/updateUser" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                                class="sr-only">关闭</span></button>
                    <h4 class="modal-title">修改</h4>
                </div>
                <div class="modal-body">

                    <input type="hidden" name="userIdUpdate" id="userIdUpdate">
                    <div class="form-group">
                        <label for="userNameUpdate">用户名：</label>
                        <input type="text" class="form-control" disabled="disabled" id="userNameUpdate"
                               placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <label for="userPasswordUpdate">密码：</label>
                        <input type="password" class="form-control" id="userPasswordUpdate" placeholder="密码">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputMoney1">是否被冻结</label>
                        <div class="form-group">
                            <label class="radio-inline">
                                <input type="radio" name="userFrozenUpdate" value="0" checked="checked"> 正常
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="userFrozenUpdate" value="1"> 冻结
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputMoney2">是否允许发布文章</label>
                        <div class="form-group">
                            <label class="radio-inline">
                                <input type="radio" name="userWriteUpdate" value="0" checked="checked"> 允许
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="userWriteUpdate" value="1"> 不允许
                            </label>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="updateUserForm()">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function updateUserForm() {
        let userId = $('#userIdUpdate').val()
        let userPassword = $('#userPasswordUpdate').val();
        let userFrozen = $("input[name='userFrozenUpdate']:checked").val();
        let userWrite = $("input[name='userWriteUpdate']:checked").val();

        if (!checkNotNull(userId)) {
            warningZuiMsg("程序出错，请刷新页面重试！")
            return;
        }
        if (!checkNotNull(userFrozen)) {
            warningZuiMsg("请选择是否要冻结用户")
        }
        $.post("/xyadmin/updateUser", {
                userId: userId,
                userPassword: userPassword,
                userFrozen: userFrozen,
                userWrite: userWrite
            },
            function (data) {

                if (data.code == 200) {
                    alert("修改成功！")
                    location.reload();
                    return;
                }
                warningZuiMsg(data.message);
            });

    }

    function updateUser(userId, userName, userFrozen, userWrite) {
        $('#userUpdateModal').modal('toggle', 'center');

        $('#userIdUpdate').val(userId)
        $('#userNameUpdate').val(userName)
        $(":radio[name='userFrozenUpdate'][value='" + userFrozen + "']").prop("checked", "checked");
        $(":radio[name='userWriteUpdate'][value='" + userWrite + "']").prop("checked", "checked");
    }

    function delUser(userId) {
        if (confirm("是否删除该用户？")) {

            if (!checkNotNull(userId)) {
                new $.zui.Messager('程序出错，请重新刷新页面重试！', {
                    type: 'warning' // 定义颜色主题
                }).show();
                return;
            }

            $.post("/xyadmin/delUser", {userId: userId},
                function (data) {

                    if (data.code == 200) {
                        alert("删除成功！")
                        location.reload();
                        return;
                    }
                    new $.zui.Messager(data.message, {
                        type: 'warning',
                        placement: 'center'
                    }).show();
                });
        }

    }

    function getNewData(pageNumber) {
        if (!checkNotNull(pageNumber)) {
            pageNumber = 1;
        }
        window.location.href = "/xyadmin/userList?pageNum=" + pageNumber + "<#if (userName??&&userName?length>0)>&userName=${userName!}</#if>";

    }

    function renderPage() {
        let renderPageNumber = $("#renderPageNumber").val();
        if (!checkNotNull(renderPageNumber)) {
            new $.zui.Messager("请输入页码", {
                type: 'warning',
                placement: 'center'
            }).show();
            return;
        }
        let totalPage = '${userList.getTotalPages()}';
        if (parseInt(renderPageNumber) > parseInt(totalPage)) {
            renderPageNumber = totalPage;
        }
        getNewData(renderPageNumber);
    }


</script>
<style>
    .table th, .table td {
        text-align: center;
        vertical-align: middle !important;
    }
</style>

<#include "../import/bottom.ftl">