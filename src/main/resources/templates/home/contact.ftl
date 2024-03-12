<#include "../import/userTop.ftl">
<div class="container contact-box">
    <div class="tittle-box">
        <span>
            联系我们：
        </span>
    </div>
    <div class="content-box">
        <span><i class="icon icon-wechat"></i>微信：18820441194</span>
        <span><i class="icon icon-envelope"></i>邮箱：2816930838@qq.com</span>
    </div>
</div>
<style>
    .contact-box {
        border-radius: 5px
    }

    .tittle-box {
        width: 100%;
        height: 40px;
        background-color: #67b1e8;
        border-radius: 5px 5px 0 0;
        display: flex;
        align-items: center; /* 垂直居中 */
    }

    .tittle-box span {
        font-size: 15px;
        margin-left: 15px;
    }

    .content-box {
        width: 100%;
        height: 100px;
        margin-bottom: 20px;
        border: 1px solid #67b1e8;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center; /* 或者 space-around */
        align-items: center;
    }

    .content-box span {
        font-size: 15px;
        font-weight: bold;
    }

</style>

<#include "../import/userBottom.ftl">