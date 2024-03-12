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
                <#if articleTypeHomeTreeVoList?? && articleTypeHomeTreeVoList?size gt 0>
                    <#list articleTypeHomeTreeVoList as articleTypeHomeTreeVo>
                        <li class="dropdown">
                            <a href="javascript:void(0)" class="dropdown-toggle"
                               data-toggle="dropdown">${articleTypeHomeTreeVo.articleTypeName!} <b
                                        class="caret"></b></a>
                            <ul class="dropdown-menu" role="menu">
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
                        </li>
                    </#list>
                </#if>
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
<script>

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


