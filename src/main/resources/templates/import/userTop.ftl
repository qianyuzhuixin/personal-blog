<#include "./top.ftl">
<!--大框架-->
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid" style="font-size: larger">
        <!-- 导航头部 -->
        <div class="navbar-header" style="margin-left: 12%">
            <!-- 移动设备上的导航切换按钮 -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-example">
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
                <!-- 导航中的下拉菜单 -->
                <li class="my-dropdown">
                    <div class="my-drop-btn">分类</div>
                    <div class="my-dropdown-content">
                        <#if articleTypeHomeTreeVoList?? && articleTypeHomeTreeVoList?size gt 0>
                            <#list articleTypeHomeTreeVoList as articleTypeHomeTreeVo>
                                <a href="javascript:void(0)" class="dropdown-toggle father-type"
                                   id="fa${articleTypeHomeTreeVo.articleTypeId!}"
                                   onmouseover="mouseOpenChildType('${articleTypeHomeTreeVo.articleTypeId!}')"
                                   onclick="clickOpenChildType('${articleTypeHomeTreeVo.articleTypeId!}')"
                                   data-toggle="dropdown">${articleTypeHomeTreeVo.articleTypeName!} <b
                                            class="caret"></b></a>
                                <ul class="child-type" id="ch${articleTypeHomeTreeVo.articleTypeId!}"
                                    scrollTop="0">
                                    <#if articleTypeHomeTreeVo.articleChildList?? && articleTypeHomeTreeVo.articleChildList?size gt 0>
                                        <#list articleTypeHomeTreeVo.articleChildList as articleTypeHomeTreeVoChild>
                                            <li>
                                                <a href="/articleList?articleTypeId=${articleTypeHomeTreeVoChild.articleTypeId!}"
                                                >${articleTypeHomeTreeVoChild.articleTypeName!}</a>
                                            </li>
                                        </#list>
                                    <#else >
                                        <li><a href="#">暂无该分类</a></li>
                                    </#if>
                                </ul>

                            </#list>
                        </#if>
                    </div>
                </li>
                <li><a href="/contact"><i class="icon icon-comments"></i>联系</a></li>
                <li><a href="/donate">￥请站长喝可乐</a></li>
                <#if Session["user"]?exists>
                    <li>
                        <input id="myInput" type="text" style="border-radius: 5px;margin-top: 5px;"
                               autocomplete="on" name="myInput" placeholder="搜索">
                        <button type="button" style="border-radius: 5px;" onclick="searchBtn()"><i
                                    class="icon icon-search"></i> 搜索
                        </button>
                    </li>
                </#if>
            </ul>
            <#if Session["user"]?exists>
                <!-- 已登录右侧的导航项目 -->
                <ul class="nav navbar-nav navbar-right" style="margin-right: 15%">
                    <li><a href="/user/myInformation"><i class="icon icon-user"></i>个人中心</a></li>
                    <#if Session["user"].getUserWrite() == 0>
                        <li><a href="/user/publishArticle"><i class="icon icon-pencil"></i>发布文章 </a></li>
                    </#if>

                    <li><a href="/user/logOut"><i class="icon icon-signout"></i>退出登录</a></li>
                </ul>
            <#else >
                <!-- 未登录右侧的导航项目 -->
                <ul class="nav navbar-nav navbar-right" style="margin-right: 15%">
                    <li><a href="/user/logInOrSignIn">登录/注册</a></li>
                </ul>
            </#if>

        </div><!-- END .navbar-collapse -->
    </div>
</nav>
<style>
    /* 下拉按钮 */
    .my-drop-btn {
        color: #ecebeb;
        padding: 9px 15px;
        font-size: 15px;
        border: none;
    }

    /* 容器 <div> - 需要定位下拉内容 */
    .my-dropdown {
        position: relative;
        display: inline-block;
    }

    /* 下拉内容（默认隐藏）*/
    .my-dropdown-content {
        display: none;
        position: absolute;
        background-color: #f1f1f1;
        min-width: 160px;
        border-radius: 5px;
        box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
        z-index: 1;
    }

    /* 下拉列表中的链接 */
    .my-dropdown-content a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
    }

    /* 更改悬停时下拉链接的颜色 */
    .my-dropdown-content a:hover {
        background-color: #ddd;
    }

    /* 悬停时显示下拉菜单 */
    .my-dropdown:hover .my-dropdown-content {
        display: block;
    }

    /* 显示下拉内容时更改下拉按钮的背景颜色 */
    .my-dropdown:hover .my-drop-btn {
        background-color: #1868E8;
    }

    .child-type {
        position: absolute;
        background-color: #d3d1d3;
        margin-left: 160px;
        margin-top: -47px;
        width: 200px;
        height: 188px;
        border-radius: 5px;
        box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
        display: none;
        overflow-y: scroll;
    }


</style>
<script>

    // 悬停分类中下拉菜单显示对应内容
    function mouseOpenChildType(articleTypeId) {
        let childArticleTypeId = 'ch' + articleTypeId;
        let childType = document.getElementsByClassName("child-type");
        // 遍历
        for (let i = 0; i < childType.length; i++) {
            childType[i].style.display = "none";
        }
        document.getElementById(childArticleTypeId).style.display = "block";
    }

    // 点击分类中下拉菜单显示对应内容
    function clickOpenChildType(articleTypeId) {
        let fatherArticleTypeId = 'fa' + articleTypeId;
        let fatherType = document.getElementsByClassName("father-type");
        // 遍历
        for (let i = 0; i < fatherType.length; i++) {
            fatherType[i].style.background = "#f1f1f1";
        }
        document.getElementById(fatherArticleTypeId).style.background = "#5493f8";
        mouseOpenChildType(articleTypeId)
    }

    //初始化函数
    $(function () {
        let childType = document.getElementsByClassName("child-type");
        // 遍历
        for (var i = 0; i < childType.length; i++) {
            var marginTop = -47 - i * 47;
            childType[i].style.marginTop = marginTop + "px";
        }
    });

    // 搜索按钮
    function searchBtn() {
        let keyword = document.getElementById("myInput").value;
        if (!checkNotNull(keyword)) {
            return;
        }
        window.location.href = "/searchArticleByTypeOrTitle?keyword=" + keyword;
    }


</script>
<style>

</style>


