<#include "../import/userTop.ftl">
<div class="container donate-box">
    <div class="tittle-box">
        <span>
            赞助我们：
        </span>
    </div>
    <div class="content-box">
        <h3>喝瓶可乐(>_<)</h3>
        <div class="img-box">
            <div class="wx-box">
                <span><i class="icon icon-wechat" style="font-size: 18px;"></i>微信</span>
                <img src="/imgs/PixPin_wx.png" alt="微信">
            </div>
            <div class="zfb-box">
                <span><i class="icon icon-zhifubao" style="font-size: 18px;"></i>支付宝</span>
                <img src="/imgs/PixPin_zfb.png" alt="支付宝">
            </div>
        </div>

    </div>
</div>
<style>
    .donate-box {
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
        height: 400px;
        margin-bottom: 20px;
        border: 1px solid #67b1e8;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center; /* 或者 space-around */
        align-items: center;
    }

    .content-box h3 {
        font-size: 25px;
        font-weight: bolder;
    }

    .img-box {
        display: flex;
        justify-content: space-around;
        width: 60%;
    }

    .zfb-box {
        margin-left: auto;
    }

    .wx-box span, .zfb-box span {
        font-size: 18px;
        font-weight: bold;
        text-align: center;
        display: block;
    }

    .wx-box img, .zfb-box img {
        width: 200px;
    }


</style>

<#include "../import/userBottom.ftl">