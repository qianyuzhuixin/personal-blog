<#include "../import/userTop.ftl">
<div class="container">
    <div class="col-xs-12 individual-content">
        <div class="panel tag">
            <div class="panel-body">
                <i class="icon icon-folder-open"></i>
                <span>发布文章</span>
            </div>
        </div>
        <div class="panel ">
            <div class="panel-body">
                <div class="col-xs-12 col-sm-3" style="padding: 2px;display: flex; border: 1px solid #9f9d9d;">
                    <img src="${(article.articleCoverUrl)!}" style="height: 150px;margin: auto;">
                </div>
                <div class="col-xs-12 col-sm-9" style="padding: 2px;">
                    <form class="form-horizontal">
                        <input type="hidden" id="articleId" name="articleId" value="${(article.articleId)!}">

                        <div class="form-group">
                            <label for="articleTitle" class="col-xs-2 col-sm-1">标题</label>
                            <div class="col-xs-10 col-sm-10">
                                <input type="text" id="articleTitle" name="articleTitle" class="form-control"
                                       value="${(article.articleTitle)!}"
                                       placeholder="标题">
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="hidden" id="articleCoverUrl" name="articleCoverUrl"
                                   value="${(article.articleCoverUrl)!}">
                            <label for="articleCoverFile" class="col-xs-2 col-sm-1">封面</label>
                            <div class="col-xs-10 col-sm-4">
                                <input type="file" id="articleCoverFile" accept="image/*" name="articleCoverFile"
                                       class="form-control"
                                       placeholder="请选择封面">
                            </div>
                            <#if articleType1List?? &&articleType1List?size gt 0>
                                <label for="articleType0" class="col-xs-2 col-sm-1">分类</label>
                                <div class="col-xs-5 col-sm-2">
                                    <select id="articleType0" class="form-control"
                                            onchange="getArticleTypeChild()">
                                        <option value="">-请选择-</option>
                                        <#list articleType1List as articleType1>
                                            <#if articleParentType??>
                                                <#if articleParentType.articleTypeId == articleType1.articleTypeId>
                                                    <option value="${(articleType1.getArticleTypeId())}"
                                                            selected="selected">${(articleType1.getArticleTypeName())}</option>
                                                <#else >
                                                    <option value="${(articleType1.getArticleTypeId())}">${(articleType1.getArticleTypeName())}</option>
                                                </#if>
                                            <#else >
                                                <option value="${(articleType1.getArticleTypeId())}">${(articleType1.getArticleTypeName())}</option>
                                            </#if>

                                        </#list>
                                    </select>
                                </div>
                                <div class="col-xs-5 col-sm-3">
                                    <select class="form-control"
                                            id="articleTypeChild" name="articleTypeChild">
                                        <option value="">-请选择-</option>
                                        <#if articleTypes?? && articleTypes?size gt 0>
                                            <#list articleTypes as articletType>
                                                <#if articletType.articleTypeId==article.articleTypeId>
                                                    <option value="${(articletType.getArticleTypeId())}"
                                                            selected="selected">${(articletType.getArticleTypeName())}</option>
                                                <#else >
                                                    <option value="${(articletType.getArticleTypeId())}">${(articletType.getArticleTypeName())}</option>
                                                </#if>

                                            </#list>
                                        </#if>
                                    </select>
                                </div>

                            </#if>
                        </div>
                        <#if articleTagList?? && articleTagList?size gt 0>
                            <div class="form-group">
                                <label class="col-xs-2 col-sm-1" style="padding-top: 0">标签</label>
                                <div class="col-xs-10 col-sm-10">
                                    <#list articleTagList as articleTag>
                                        <#if articleTagIds??&&articleTagIds?size gt 0>
                                            <#if articleTagIds?seq_contains(articleTag.articleTagId)>
                                                <span class="label label-badge label-success"
                                                      onclick="selOrDelArticleTag('${(articleTag.getArticleTagId())}')"
                                                      id="${(articleTag.getArticleTagId())}">${(articleTag.getArticleTagName())}</span>
                                            <#else>
                                                <span class="label label-badge"
                                                      onclick="selOrDelArticleTag('${(articleTag.getArticleTagId())}')"
                                                      id="${(articleTag.getArticleTagId())}">${(articleTag.getArticleTagName())}</span>
                                            </#if>
                                        <#else >
                                            <span class="label label-badge"
                                                  onclick="selOrDelArticleTag('${(articleTag.getArticleTagId())}')"
                                                  id="${(articleTag.getArticleTagId())}">${(articleTag.getArticleTagName())}</span>
                                        </#if>

                                    </#list>
                                </div>
                            </div>
                        </#if>

                    </form>
                </div>
                <div class="col-xs-12" style="margin-top: 20px">
                <textarea id="articleContent" name="articleContent" maxlength="14999" minlength="5" rows="5"
                          style="display: none;">${(article.articleContent)!}</textarea>
                </div>

                <div class="form-group">
                    <button type="button" onclick="publishArticle()" class="btn btn-success"
                            style="margin-top: 15px;margin-right:15px;float: right"><i
                                class="icon icon-edit"></i>发布
                    </button>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="//cdn.jsdelivr.net/gh/xwLrbh/HandyEditor@1.8.0/HandyEditor.min.js"></script>
<script>

    // 选中标签列表
    let articleTagIds = [];

    // 富文本编辑器
    var he = HE.getEditor('articleContent', {
        width: '100%',
        height: '200px',
        autoHeight: true,
        autoFloat: false,
        topOffset: 0,
        uploadPhoto: true,
        uploadPhotoHandler: '/user/uploadArticle',
        uploadPhotoSize: 1024,
        uploadPhotoType: 'gif,png,jpg,jpeg',
        uploadPhotoSizeError: '不能上传大于1024KB的图片',
        uploadPhotoTypeError: '只能上传gif,png,jpg,jpeg格式的图片',
        uploadParam: {},
        lang: 'zh-jian',
        skin: 'HandyEditor',
        externalSkin: '',
        item: ['bold', 'italic', 'strike', 'underline', 'fontSize', 'fontName', 'paragraph', 'color', 'backColor', '|', 'center', 'left', 'right', 'full', 'indent', 'outdent', '|', 'link', 'unlink', 'textBlock', 'code', 'selectAll', 'removeFormat', 'trash', '|', 'image', 'expression', 'subscript', 'superscript', 'horizontal', 'orderedList', 'unorderedList', '|', 'undo', 'redo', '|', 'html', '|', 'about']

    });

    // 发布文章
    function publishArticle() {
        let articleTitle = $("#articleTitle").val()
        let articleCoverFile = $("#articleCoverFile").val()
        let articleCoverUrl = $("#articleCoverUrl").val()
        let articleTypeChild = $("#articleTypeChild").val()
        let articleId = $("#articleId").val()
        let articleContent = he.getHtml()

        if (!checkNotNull(articleTitle)) {
            warningZuiMsg("请填写标题")
            return
        }
        if (!checkNotNull(articleCoverUrl) && !checkNotNull(articleCoverFile)) {
            warningZuiMsg("请选择封面")
            return
        }
        if (!checkNotNull(articleTypeChild)) {
            warningZuiMsg("请选择分类")
            return
        }
        if (!checkNotNull(articleContent)) {
            warningZuiMsg("请输入内容")
            return
        }

        let formData = new FormData();
        formData.append("articleTitle", articleTitle);
        formData.append("articleId", articleId);
        formData.append("articleCoverFile", $("#articleCoverFile")[0].files[0]);
        formData.append("articleTypeId", articleTypeChild);
        formData.append("articleCoverUrl", articleCoverUrl)
        formData.append("articleContent", articleContent);
        formData.append("articleTagIds", articleTagIds);


        $.ajax({
                url: "/user/publishArticleAction",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                beforeSend: function () {
                    warningZuiMsg("正在发布，请稍等...")
                },
                success: function (data) {
                    if (data.code == 200) {
                        successZuiMsg("发布成功")
                        window.location.href = '/user/myArticles'
                        return
                    }
                    warningZuiMsg(data.message)
                },
                error: function (responseStr) {
                    warningZuiMsg("发布失败")
                }
            }
        )
    }

    // 选中或取消标签
    function selOrDelArticleTag(articleTagId) {
        let index = articleTagIds.indexOf(articleTagId);
        if (index > -1) {
            articleTagIds.splice(index, 1);
            $("#" + articleTagId).removeClass("label-success");
        } else {
            articleTagIds.push(articleTagId);
            $("#" + articleTagId).addClass("label-success");
        }
        console.log(articleTagIds)
    }

    // 获取二级分类
    function getArticleTypeChild() {
        $("#articleTypeChild").html("")
        $.post("/user/getArticleTypeChild", {
                articleTypeId: $("#articleType0").val(),
            },
            function (data) {
                if (data.code == 200) {
                    let articleTypeChildList = data.data;
                    for (let i = 0; i < articleTypeChildList.length; i++) {
                        $("#articleTypeChild").append("<option value='" + articleTypeChildList[i].articleTypeId + "'>" + articleTypeChildList[i].articleTypeName + "</option>");
                    }
                    return;
                }
                warningZuiMsg(data.message);
            });
    }

    // 获取所有选中标签id
    $(function () {
        let selectedTags = $("span.label-success");
        for (let i = 0; i < selectedTags.length; i++) {
            articleTagIds[i] = selectedTags[i].id;
        }
    })


</script>


<style>
    .list-group {
        text-align: center;
        list-style: none;
    }

    select {
        display: flex;
        align-items: center;
        justify-content: center;
    }
</style>

<#include "../import/userBottom.ftl">