<#include "../import/userTop.ftl">
<#include "../import/individualCenterLeft.ftl">
<div class="col-xs-9 individual-content">
    <div class="panel tag">
        <div class="panel-body">
            <i class="icon icon-folder-open"></i>
            <span>我的资料</span>
        </div>
    </div>

    <div class="panel ">
        <div class="panel-body">
            <#if user??>
                <div class="tags">
                    <span class="label"><i class="icon icon-time"></i>注册时间：</span>
                    <p class="label label-success">${(user.getUserRegisterTime())?string("yyyy-MM-dd HH:mm:ss")}</p>
                    <br/>
                    <span class="label"><i class="icon icon-user"></i>用户名&emsp;：</span>
                    <p class="label label-success">${(user.getUserName())!}</p>
                    <br/>
                    <span class="label"><i class="icon icon-leaf"></i>状态&emsp;&emsp;：</span>
                    <#if (user.getUserFrozen())?? && (user.getUserFrozen()) == 1>
                        <p class="label label-danger">冻结</p>
                    <#else>
                        <p class="label label-success">正常</p>
                    </#if>
                </div>
            </#if>
        </div>
    </div>
</div>
</div>


<style>
    .list-group {
        text-align: center;
        list-style: none;
    }

    .individual-content {

    }

    .tag {
        border: 1px solid #a2a0a0;
        background-color: #f5f1f5;
        border-radius: 5px 5px 0 0;
    }

    .tag span {
        font-size: 15px;
        color: #807d7d
    }

    .tags span {
        font-size: 15px;
        display: inline-block;
        margin-bottom: 5px;
        width: 100px;
    }

    .tags p {
        font-size: 15px;
        display: inline-block;
        width: fit-content;
        margin-bottom: 5px;
    }

    .articles {
        border: 1px solid #bbc6ce;
        margin-top: 20px;
        margin-bottom: 20px;
        box-shadow: 5px 5px 5px -4px rgba(148, 147, 147, 0.8);
        border-radius: 10px;
        display: flex;
        flex-wrap: wrap;
    }

    .article {
        width: 206px;
        border-radius: 10px;
        border: 1px solid #bbc6ce;
        box-shadow: 3px 5px 5px -4px rgba(148, 147, 147, 0.8);
        margin-bottom: 20px;
        margin-left: 5%;
        padding: 0;
    }

    .article img {
        width: 200px;
        height: 150px;
        margin-left: 3px;
        border-radius: 10px;
    }

    #review {
        width: 20px;
        height: 20px;
        margin-right: 1px;
        float: right;
    }

    #review-num {
        float: right;
        height: 20px;
        margin: 0 10px 10px 0;
        color: #037cd7;
    }

    .article p, .article span {
        margin-left: 15px;
        margin-top: 15px;
        margin-right: 5px;
    }

    .article span {
        margin-bottom: 15px;
    }

    #article-tittle {
        color: #0780da;
        font-size: larger;
    }


</style>

<#include "../import/userBottom.ftl">