<div class="container" style="font-size: larger;margin: 0 auto;display: block">
    <div class="panel col-xs-5" style="float: left;box-shadow: 2px 2px 5px #6a6a6b">
        <div class="panel-body">
            <span>分享生活-感受生活</span>
        </div>
    </div>

    <div class="panel col-xs-7" style="float: left;box-shadow: 2px 2px 5px #7d7d80">
        <div class="panel-body ">
            <#if linkList?? && linkList?size gt 0>
            <span>友情链接：
                <#list linkList as link>
                    <a href="${link.linkUrl!}">${link.linkTitle!}</a>|
                </#list>
                </#if>
        </div>
    </div>


    <style>
        a {
            font-size: 15px
        }

        a:link {
            color: blue;
            text-decoration: none;
        }

        a:active {
            color: red;
        }

        a:visited {
            color: purple;
            text-decoration: none;
        }

        a:hover {
            color: red;
            text-decoration: none;
        }
    </style>
<#include "./bottom.ftl">