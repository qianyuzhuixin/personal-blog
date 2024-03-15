<#include "./import/userTop.ftl">
<div class="container">
    <div class="col-xs-12 col-sm-9">
        <div class="img-thumbnail slider-container">
            <div id="myNiceCarousel" class="carousel slide carousel-map" data-ride="carousel">
                <!-- 圆点指示器 -->
                <ol class="carousel-indicators">
                    <#if adHomeList?? && adHomeList?size gt 0>
                        <#list adHomeList as ad>
                            <#if ad_index==0>
                                <li data-target="#myNiceCarousel" data-slide-to="${ad_index!}" class="active"></li>
                            <#else >
                                <li data-target="#myNiceCarousel" data-slide-to="${ad_index!}"></li>
                            </#if>
                        </#list>
                    </#if>
                </ol>

                <!-- 轮播项目 -->
                <div class="carousel-inner" style="border-radius: 10px;">
                    <#if adHomeList?? && adHomeList?size gt 0>
                        <#list adHomeList as ad>
                            <#if ad_index==0>
                                <div class="item active">
                                    <a target="_blank" href="${ad.adLinkUrl}">
                                        <img alt="${(ad.adTitle)!}" src="${(ad.adImgUrl)!}">
                                        <div class="carousel-caption">
                                            <h3>${(ad.adTitle)!}</h3>
                                        </div>
                                    </a>
                                </div>
                            <#else >
                                <div class="item">
                                    <a target="_blank" href="${ad.adLinkUrl}">
                                        <img alt="${(ad.adTitle)!}" src="${(ad.adImgUrl)!}">
                                        <div class="carousel-caption">
                                            <h3>${(ad.adTitle)!}</h3>
                                        </div>
                                    </a>

                                </div>
                            </#if>
                        </#list>
                    </#if>
                </div>

                <!-- 项目切换按钮 -->
                <a class="left carousel-control" style="border-radius: 10px" href="#myNiceCarousel" data-slide="prev">
                    <span class="icon icon-chevron-left"></span>
                </a>
                <a class="right carousel-control" style="border-radius: 10px" href="#myNiceCarousel" data-slide="next">
                    <span class="icon icon-chevron-right"></span>
                </a>
            </div>
        </div>
        <div class="articles">
            <div class="new-tittle">
                <img src="../imgs/new_article.svg" title="最新文章">
                <span>最新文章</span>
            </div>
            <#if articleIndexList?? && articleIndexList?size gt 0>
                <#list articleIndexList as article>
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
            <#else >
                <div style="text-align: center">
                    <h3><i class="icon icon-coffee"></i></h3>
                    <h3>暂无数据</h3>
                </div>
            </#if>
        </div>
    </div>

    <div class="col-xs-12 col-sm-3 hot">
        <div class="hot-articles">
            <img src="../imgs/hot_article.svg" title="热门文章">
            <span>热门文章</span>
        </div>
        <div>
            <ul class="list-group">
                <#if articleHotVoList?? && articleHotVoList?size gt 0>
                    <#list articleHotVoList as article>
                        <li class="list-group-item">
                            <a href="/user/showArticle?articleId=${(article.articleId)!}"
                               style="text-decoration: none;">
                                ${article.articleTitle!}
                            </a>
                        </li>
                    </#list>
                <#else >
                    <div style="text-align: center">
                        <h3><i class="icon icon-coffee"></i></h3>
                        <h3>暂无数据</h3>
                    </div>
                </#if>
            </ul>
        </div>
    </div>

    <div class="col-xs-12 col-sm-3 hot">
        <div class="hot-tags">
            <img src="../imgs/hot_tag.svg" title="热门标签">
            <span>热门标签</span>
        </div>
        <div class="panel ">
            <div class="panel-body">
                <div class="tags " id="articleTagListBox">
                    <#if articleTagList?? && articleTagList?size gt 0>
                        <#list articleTagList as tag>
                            <span class="label label-badge palm"
                                  onclick="articleListByTagId('${tag.articleTagId!}')">${tag.articleTagName!}</span>
                        </#list>
                    <#else >
                        <div style="text-align: center;margin-right: 40%">
                            <h3><i class="icon icon-coffee"></i></h3>
                            <h3>暂无数据</h3>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>

<style>

    .slider-container {
        display: flex;
        justify-content: center;
    }

    .list-group-item {
        text-align: center;
    }

    .item {
        border-radius: 10px;
        height: 300px;
    }

    .carousel-map, .article {
        padding-right: 0;
        padding-left: 0;
        border-radius: 10px;
        transition: box-shadow 0.3s ease; /* 添加过渡效果，使阴影变化平滑 */
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); /* 默认阴影 */
    }

    .carousel-map:hover, .article:hover {
        box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5); /* 悬停时的阴影 */
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
        width: 206px;
        border-radius: 10px;
        border: 1px solid #bbc6ce;
        box-shadow: 3px 5px 5px -4px rgba(148, 147, 147, 0.8);
        margin: 20px 0;
        padding: 0;
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


    .new-tittle {
        height: 40px;
        width: 100%;
        color: #037cd7;
        border-radius: 5px 5px 0 0;
        font-size: larger;
        display: flex;
        align-items: center;
        margin-left: 4%;

    }

    .hot {
        box-shadow: 5px 5px 5px -4px rgba(148, 147, 147, 0.8);
        padding: 0 0 0 20px;
    }

    .hot-articles, .hot-tags {
        height: 40px;
        width: 100%;
        background-color: #67b1e8;
        border: 1px solid #bbc6ce;
        border-radius: 5px 5px 0 0;
        font-size: larger;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .hot-articles img, .hot-tags img, .new-tittle img {
        width: 15px;
    }

    .hot-articles span, .hot-tags span, new-tittle span {
        margin-left: 5px
    }

    .hot-tags {
        margin-top: 20px;
    }

    .tags {
        display: flex;
        flex-wrap: wrap;
    }

    .tags span {
        margin: 10px;
        height: 20px;
        justify-content: center;
        align-items: center;
        border-radius: 10px;
    }

    .palm:hover {
        cursor: pointer;
        background-color: #037cd7;
    }
</style>

<script>
    function articleListByTagId(articleTagId) {
        if (!checkNotNull(articleTagId)) {
            warningZuiMsg("请刷新重试！")
            return
        }
        window.location.href = "/articleListByTagId?articleTagId=" + articleTagId;
    }

    $(function () {
        let labelClassList = ["label-primary", "label-success", "label-info", "label-warning", "label-danger"];
        let articleTags = $("#articleTagListBox span");
        for (let i = 0; i < articleTags.length; i++) {
            $(articleTags[i]).addClass(labelClassList[Math.floor(Math.random() * labelClassList.length)]);
        }
    })

</script>

<#include "./import/userBottom.ftl">