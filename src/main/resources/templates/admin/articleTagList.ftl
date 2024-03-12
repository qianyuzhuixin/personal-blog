<#include "../import/adminTop.ftl">

<div class="panel">
    <div class="panel-body">
        <div class="form-inline">
            <div class="form-group">
                <label for="articleTagName">标签名称：</label>
                <input class="form-control" type="text" id="articleTagName">
            </div>
            <button type="button" onclick="addArticleTag()" class="btn btn-success">添加</button>
        </div>
        <hr/>
        <#if articleTagList?? && articleTagList?size gt 0>
            <#list articleTagList as articleTag>
                <div class="col-sm-2" style="padding: 2px;margin-bottom: 5px;">
                    <div
                            class="img-thumbnail">
                        <div class="pull-right">
                            <i class="icon icon-cog" data-toggle="tooltip" data-placement="bottom" title="修改"
                               onclick="updateArticleTag('${(articleTag.getArticleTagId())!}')"></i>
                            <i class="icon icon-remove" data-toggle="tooltip" data-placement="bottom" title="删除"
                               onclick="delArticleTag('${(articleTag.getArticleTagId())!}')"></i>
                        </div>
                        <input class="form-control" type="text" value="${(articleTag.getArticleTagName())!}"
                               id="${(articleTag.getArticleTagId())!}"/>
                    </div>
                </div>

            </#list>
        <#else >
            <div class="panel">
                <div class="panel-body">
                    <div style="text-align: center">
                        <h3><i class="icon icon-coffee"></i></h3>
                        <h3>暂无数据</h3>
                    </div>
                </div>
            </div>

        </#if>
    </div>
</div>


<script>
    function addArticleTag() {
        let articleTagName = $("#articleTagName").val();
        if (!checkNotNull(articleTagName)) {
            warningZuiMsg("请输入文章标签名称");
            return;
        }
        $.post("/xyadmin/addOrUpdateArticleTag", {
            articleTagName: articleTagName,
        }, function (data) {
            if (data.code == 200) {
                alert(data.message)
                location.reload();
                return;
            }
            warningZuiMsg(data.message);
        })
    }

    function delArticleTag(articleTagId) {
        if (confirm("确认要删除吗？")) {
            $.post("/xyadmin/delArticleTag", {
                articleTagId: articleTagId
            }, function (data) {
                if (data.code == 200) {
                    alert(data.message)
                    location.reload();
                    return;
                }
                warningZuiMsg(data.message);
            })
        }
    }


    function updateArticleTag(articleTagId) {
        let articleTagName = $("#" + articleTagId).val();
        if (confirm("确认要修改吗？")) {
            if (!checkNotNull(articleTagId) || !checkNotNull(articleTagName)) {
                warningZuiMsg("修改的参数内容有误！")
                return;
            }
            $.post("/xyadmin/addOrUpdateArticleTag", {
                articleTagName: articleTagName,
                articleTagId: articleTagId
            }, function (data) {
                if (data.code == 200) {
                    alert(data.message)
                    location.reload();
                    return;
                }
                warningZuiMsg(data.message);
            })
        }
    }

    $(
        '[data-toggle="tooltip"]'
    ).tooltip({
        placement: 'bottom'
    });
</script>


<#include "../import/bottom.ftl">