<#include "../import/adminTop.ftl">

<div class="panel col-xs-12">
    <div class="panel-body">
        <div class="col-xs-6">
            <div class="panel">
                <div class="panel-body">
                    <h2>系统信息：</h2>
                    <h3><i class="icon icon-desktop"> </i>系统名称：${osName!}</h3>
                    <h3><i class="icon icon-home"> </i>当前主机IP：${hostIP!}</h3>
                </div>
            </div>
        </div>

        <div class="col-xs-6">
            <div class="panel">
                <div class="panel-body">
                    <h2>网站信息：</h2>
                    <h3><i class="icon icon-user"> </i>用户数量：${userNums!}</h3>
                    <h3><i class="icon icon-stack"> </i>文章数量：${articleNums!}</h3>
                    <h3><i class="icon icon-list"> </i>文章类型数量：${articleTypeNums!}</h3>
                    <h3><i class="icon icon-tag"> </i>文章标签数量：${articleTagNums!}</h3>

                </div>
            </div>
        </div>
    </div>
</div>

<#include "../import/bottom.ftl">