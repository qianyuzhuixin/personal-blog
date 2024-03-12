<#include "../import/userTop.ftl">
<div class="container">
    <ol class="breadcrumb">
        <li><a href="/"><i class="icon icon-home"></i>首页</a></li>
        <li class="active">${articleTypeName!}</li>
    </ol>
    <#if articleIPageList?? && articleIPageList.getData()?size gt 0>
        <div class="panel">
            <div class="panel-body">
                <div class="articles">
                    <#list articleIPageList.getData() as article>
                        <div class="article">
                            <a href="/user/showArticle?articleId=${(article.articleId)!}">
                                <img class="articleCoverUrl" src="${article.getArticleCoverUrl()!}"
                                     title="${article.getArticleTitle()!}">
                                <span class="article-tittle">${article.getArticleTitle()!}</span>
                                <p>
                                    <i class="icon icon-time"></i>${article.getArticleAddTime()?string("yyyy-MM-dd HH:mm:ss")}
                                </p>
                            </a>
                            <div class="article-bottom">
                            <span class="label label-primary"><i
                                        class="icon icon-eye-open"></i>${article.articleLookNums!}</span>
                                <span class="label label-success"><i
                                            class="icon icon-thumbs-o-up"></i>${article.articleGoodNums!}</span>
                                <span class="label label-danger"><i
                                            class="icon icon-star-empty"></i>${article.articleCollectionNums!}</span>
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
                    <#if articleIPageList.currentPage lte 1>
                        <li class="previous disabled">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                        </li>
                    <#else >
                        <li class="previous" onclick="getNewData('${articleIPageList.currentPage-1}')">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                        </li>
                    </#if>
                    <li>
                        <a href="javaScript:void(0)" class="btn">第${articleIPageList.currentPage}
                            页/共${articleIPageList.getTotalPages()}
                            页</a>
                    </li>
                    <#if articleIPageList.currentPage gte articleIPageList.getTotalPages()>
                        <li class="next disabled">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                        </li>
                    <#else>
                        <li class="next" onclick="getNewData('${articleIPageList.currentPage+1}')">
                            <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                        </li>
                    </#if>
                    <li class="previous" onclick="getNewData('${articleIPageList.getTotalPages()}')">
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
<style>


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
        width: 206px;
        border-radius: 10px;
        border: 1px solid #bbc6ce;
        margin: 20px 0;
        padding: 0;
        transition: box-shadow 0.3s ease; /* 添加过渡效果，使阴影变化平滑 */
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); /* 默认阴影 */
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
        justify-content: space-evenly;
    }

    .article:hover {
        box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5); /* 悬停时的阴影 */
    }


</style>

<script>
    function getNewData(pageNumber) {
        if (!checkNotNull(pageNumber)) {
            pageNumber = 1;
        }
        if (checkNotNull('${keyword!}')) {
            window.location.href = "/searchArticleByTypeOrTitle?pageNumber=" + pageNumber + "&keyword=${keyword!}";
            return
        }
        if (checkNotNull('${articleTypeId!}')) {
            window.location.href = "/articleList?pageNumber=" + pageNumber + "&articleTypeId=${articleTypeId!}";
            return;
        }

        if (checkNotNull('${articleTagId!}')) {
            window.location.href = "/articleListByTagId?pageNumber=" + pageNumber + "&articleTagId=${articleTagId!}";
            return;
        }
    }

    function renderPage() {
        let renderPageNumber = $("#renderPageNumber").val();
        if (!checkNotNull(renderPageNumber)) {
            warningZuiMsg("请输入页码")
            return;
        }
        let totalPage = '${articleIPageList.getTotalPages()}';
        if (parseInt(renderPageNumber) > parseInt(totalPage)) {
            renderPageNumber = totalPage;
        }
        getNewData(renderPageNumber);
    }
</script>
<#include "../import/userBottom.ftl">