<#include "../import/userTop.ftl">
<#include "../import/individualCenterLeft.ftl">
<div class="col-xs-9 individual-content">
    <div class="panel tag">
        <div class="panel-body">
            <i class="icon icon-edit-sign"></i>
            <span>我的文章</span>
        </div>
    </div>
    <#if articlePageVoIPage.getData()??&&articlePageVoIPage.getData()?size gt 0>
        <div class="panel ">
            <div class="panel-body">
                <div class="articles">
                    <#list articlePageVoIPage.getData() as article>
                        <div class="article">
                            <a href="/user/showArticle?articleId=${(article.articleId)!}">
                                <img class="articleCoverUrl" src="${article.articleCoverUrl!}"
                                     title="${article.articleTitle!}">
                                <span class="article-tittle">${article.articleTitle!}</span>
                                <p>
                                    <i class="icon icon-time"></i>${article.articleAddTime?string("yyyy-MM-dd HH:mm:ss")}
                                </p>
                            </a>
                            <div class="article-bottom">
                                <span class="label label-warning type-name" style="margin: 0 10px;"
                                >${article.articleTypeName!}</span>
                                <span style="margin: 0"><i
                                            class="icon icon-eye-open"></i>${article.articleLookNums!}</span>
                                <span class="label label-info updateArticle"
                                      style="margin: 0 0 0 auto ;height: 20px"
                                      onclick="updateArticle('${(article.articleId)!}')">
                                    修改</span>
                                <span class="label label-danger deleteArticle" style="margin: 0 10px;height: 20px"
                                      onclick="delArticle('${(article.articleId)!}')"
                                >删除</span>
                            </div>

                        </div>
                    </#list>
                </div>

            </div>
        </div>
        <div class="panel">
            <div class="panel-body" style="text-align: center">
                <ul class="pager pager-pills" style="">
                    <li class="previous" onclick="getNewData(1)">
                        <a href="javaScript:void(0)"><i class="icon icon-step-backward"></i></a>
                    </li>
                    <#if articlePageVoIPage.currentPage lte 1>
                        <li class="previous disabled">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                        </li>
                    <#else >
                        <li class="previous" onclick="getNewData('${articlePageVoIPage.currentPage-1}')">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                        </li>
                    </#if>
                    <li>
                        <a href="javaScript:void(0)" class="btn">第${articlePageVoIPage.currentPage}
                            页/共${articlePageVoIPage.getTotalPages()}
                            页</a>
                    </li>
                    <#if articlePageVoIPage.currentPage gte articlePageVoIPage.getTotalPages()>
                        <li class="next disabled">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                        </li>
                    <#else>
                        <li class="next" onclick="getNewData('${articlePageVoIPage.currentPage+1}')">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                        </li>
                    </#if>
                    <li class="previous" onclick="getNewData('${articlePageVoIPage.getTotalPages()}')">
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
        border-radius: 5px 5px 0 0;
        border: 1px solid #a2a0a0;
        background-color: #f5f1f5;
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
        justify-content: space-around;
    }

    .article {
        width: 250px;
        border-radius: 10px;
        border: 1px solid #bbc6ce;
        margin: 20px 0;
        padding: 0;
        transition: box-shadow 0.3s ease; /* 添加过渡效果，使阴影变化平滑 */
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); /* 默认阴影 */
    }

    .article:hover {
        box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5); /* 悬停时的阴影 */
    }

    .articleCoverUrl {
        margin-right: auto;
        margin-left: auto;
        margin-top: 5px;
        box-shadow: 3px 5px 5px -4px rgba(148, 147, 147, 0.8);
        display: block;
        height: 150px;
        border-radius: 10px;
    }

    .article p, .article span {
        margin-left: 15px;
        margin-top: 15px;
        margin-right: 5px;
    }

    .article span {
        margin-bottom: 15px;
    }

    .article-tittle {
        margin-top: 15px;
        color: #0780da;
        font-size: larger;
    }

    .article-bottom {
        margin-bottom: 15px;
        display: flex;
        flex-wrap: wrap;
    }

    .updateArticle, .deleteArticle, .type-name {
        line-height: 16px;
    }

    .updateArticle:hover {
        background-color: #0780da;
        cursor: pointer;
    }

    .deleteArticle:hover {
        background-color: red;
        cursor: pointer;
    }


</style>

<script>

    // // 停止事件冒泡
    // function stopPropagation(e) {
    //     e = e || window.event;
    //     if (e.stopPropagation) {
    //         e.stopPropagation(); //W3C阻止冒泡方法
    //     } else {
    //         e.cancelBubble = true; //IE阻止冒泡方法
    //     }
    // }

    // 删除文章
    function delArticle(articleId) {
        if (confirm("删除文章将删除对应的所有评论，请确认是否需要删除！")) {
            $.post("/user/delArticle", {
                    articleId: articleId,
                },
                function (data) {
                    if (data.code == 200) {
                        successZuiMsg("删除成功！")
                        location.reload();
                        return;
                    }
                    warningZuiMsg(data.message);
                });

        }
    }


    // 修改文章
    function updateArticle(articleId) {
        window.location.href = "/user/publishArticle?articleId=" + articleId;
        return
    }

    // // jQ
    // $("#updateArticle").click(function () {
    //
    //     return false;
    // });

    function getNewData(pageNumber) {
        if (!checkNotNull(pageNumber)) {
            pageNumber = 1;
        }
        window.location.href = "/user/myArticles?pageNumber=" + pageNumber;
    }

    function renderPage() {
        let renderPageNumber = $("#renderPageNumber").val();
        if (!checkNotNull(renderPageNumber)) {
            warningZuiMsg("请输入页码")
            return;
        }
        let totalPage = '${articlePageVoIPage.getTotalPages()}';
        if (parseInt(renderPageNumber) > parseInt(totalPage)) {
            renderPageNumber = totalPage;
        }
        getNewData(renderPageNumber);
    }

</script>

<#include "../import/userBottom.ftl">