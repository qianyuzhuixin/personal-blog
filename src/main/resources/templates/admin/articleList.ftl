<#include "../import/adminTop.ftl">

<#if articlePageVoList?? && articlePageVoList.getData()?size gt 0 >
    <form class="form-horizontal" action="/xyadmin/articleList" method="get">
        <div class="form-group">
            <label for="articleTitle" class="col-sm-1">文章标题</label>
            <div class="col-sm-3">
                <input type="text" value="${articleTitle!}" class="form-control" id="articleTitle" name="articleTitle"
                       placeholder="文章标题">
            </div>
            <div class="col-sm-1 ">
                <button class="btn btn-success" type="submit"><i class="icon icon-search"></i> 搜索</button>
            </div>
            <div class="col-sm-1 ">
                <a href="/xyadmin/articleList" class="btn btn-success" type="submit"><i class="icon icon-search"></i> 全部</a>
            </div>

        </div>
    </form>
    <div class="panel">
        <div class="panel-body">
            <h4>当前：${(articlePageVoList.getTotal())!0}条文章</h4>
            <hr/>
            <table class="table table-striped table-bordered  table-hover">
                <thead>
                <tr>
                    <th>发布时间</th>
                    <th>文章类型</th>
                    <th>发布者</th>
                    <th>文章标题</th>
                    <th>游览数</th>
                    <th>点赞数</th>
                    <th>收藏数</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list  articlePageVoList.getData() as articleVo>
                    <tr>
                        <td>
                            <#if (articleVo.getArticleAddTime())??>
                                ${(articleVo.getArticleAddTime())?string("yyyy-MM-dd HH:mm:ss")}
                            <#else >
                                无
                            </#if>
                        </td>
                        <td>${(articleVo.getArticleTypeName())!}</td>
                        <td>${(articleVo.getUserName())!}</td>
                        <td>${(articleVo.getArticleTitle())!}</td>
                        <td>${(articleVo.getArticleLookNums())!}</td>
                        <td>${(articleVo.getArticleGoodNums())!}</td>
                        <td>${(articleVo.getArticleCollectionNums())!}</td>
                        <td>
                            <button onclick="delArticle('${articleVo.getArticleId()}')" type="button"
                                    class="btn btn-mini">
                                <i
                                        class="icon icon-remove"></i>删除
                            </button>
                            <a target="_blank" href="/view/article/${(articleVo.getArticleId()!)}" class="btn btn-mini"><i
                                        class="icon icon-eye-open"></i>查看</a>
                        </td>
                    </tr>
                </#list>
                </tbody>

            </table>
        </div>
    </div>

    <div class="panel">
        <div class="panel-body" style="text-align: center">
            <ul class="pager pager-pills" style="">
                <li class="previous" onclick="getNewData(1)">
                    <a href="javaScript:void(0)"><i class="icon icon-step-backward"></i></a>
                </li>
                <#if articlePageVoList.getCurrentPage() lte 1>
                    <li class="previous disabled">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                    </li>
                <#else >
                    <li class="previous" onclick="getNewData('${articlePageVoList.getCurrentPage()-1}')">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-left"></i></a>
                    </li>
                </#if>
                <li>
                    <a href="javaScript:void(0)" class="btn">第${articlePageVoList.getCurrentPage()}
                        页/共${articlePageVoList.getTotalPages()}
                        页</a>
                </li>
                <#if articlePageVoList.getCurrentPage() gte articlePageVoList.getTotalPages()>
                    <li class="next disabled">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                    </li>
                <#else>
                    <li class="next" onclick="getNewData('${articlePageVoList.getCurrentPage()+1}')">
                        <a href="javaScript:void(0)"><i class="icon icon-chevron-right"></i></a>
                    </li>
                </#if>
                <li class="previous" onclick="getNewData('${articlePageVoList.getTotalPages()}')">
                    <a href="javaScript:void(0)"><i class="icon icon-step-forward"></i></a>
                </li>
                <li class="next">
                    <a href="javaScript:void(0)"><input type="number"
                                                        id="renderPageNumber"
                                                        maxlength="5"
                                                        style="height: 20px;width: 50px;"
                                                        oninput="value = value.replace(/[^\d]/g,'')"/>
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

<script type="text/javascript">
    function delArticle(articleId) {
        if (confirm("是否删除该文章？")) {
            if (!checkNotNull(articleId)) {
                new $.zui.Messager('程序出错，请重新刷新页面重试！', {
                    type: 'warning' // 定义颜色主题
                }).show();
                return;
            }
            $.post("/xyadmin/delArticle", {articleId: articleId},
                function (data) {
                    if (data.code == 200) {
                        alert("删除成功！")
                        location.reload();
                        return;
                    }
                    new $.zui.Messager(data.message, {
                        type: 'warning',
                        placement: 'center'
                    }).show();
                });
        }
    }

    function getNewData(pageNumber) {
        if (!checkNotNull(pageNumber)) {
            pageNumber = 1;
        }
        window.location.href = "/xyadmin/articleList?pageNum=" + pageNumber + "<#if (articleTitle??&&articleTitle?length>0)>&articleTitle=${articleTitle!}</#if>";
    }

    function renderPage() {
        let renderPageNumber = $("#renderPageNumber").val();
        if (!checkNotNull(renderPageNumber)) {
            new $.zui.Messager("请输入页码", {
                type: 'warning',
                placement: 'center'
            }).show();
            return;
        }
        let totalPage = '${articlePageVoList.getTotalPages()}';
        if (parseInt(renderPageNumber) > parseInt(totalPage)) {
            renderPageNumber = totalPage;
        }
        getNewData(renderPageNumber);
    }
</script>

<#include "../import/bottom.ftl">