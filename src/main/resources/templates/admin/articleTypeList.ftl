<#include "../import/adminTop.ftl">
<div class="panel">
    <div class="panel-body">
        <div class="panel col-sm-6">
            <div class="panel-body" style="float: left ; width: 100%">
                <h3>一级分类：</h3>
                <div class="panel">
                    <div class="panel-body ">
                        <button type="button" class="btn btn-success" onclick="addOrUpdateArticleType()">添加一级类型
                        </button>
                        <button type="button" class="btn btn-warning">
                            <a href="/xyadmin/articleTypeList"
                               style="color:white;text-decoration:none;">查看所有二级类型</a>
                        </button>
                        <hr/>
                        <#if articleTypeVoList?? && articleTypeVoList?size gt 0 >
                        <table class="table">
                            <thead>
                            <tr>
                                <th>排序</th>
                                <th>添加时间</th>
                                <th>文章类型名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list  articleTypeVoList as articleType>
                                <tr>
                                    <td>${(articleType.getArticleTypeSort())!}</td>
                                    <td>
                                        <#if (articleType.getArticleTypeAddTime())??>
                                            ${(articleType.getArticleTypeAddTime())?string("yyyy-MM-dd HH:mm:ss")}
                                        <#else >
                                            无
                                        </#if>
                                    </td>
                                    <td>
                                        <a href="/xyadmin/articleTypeList?articleTypeParentId=${(articleType.getArticleTypeId()!)}"
                                           style="color:#f37272;">${(articleType.getArticleTypeName())!}</a>
                                    </td>

                                    <td>
                                        <button onclick="addOrUpdateArticleType('${(articleType.getArticleTypeId())!}','${(articleType.getArticleTypeName())!}','${(articleType.getArticleTypeSort())!}')"
                                                type="button"
                                                class="btn btn-mini"><i
                                                    class="icon icon-cog"></i> 修改
                                        </button>
                                        <button onclick="delArticleType('${articleType.getArticleTypeId()}')"
                                                type="button"
                                                class="btn btn-mini">
                                            <i
                                                    class="icon icon-remove"></i>删除
                                        </button>
                                    </td>
                                </tr>
                            </#list>
                            <#else >
                                <div style="text-align: center">
                                    <h3><i class="icon icon-coffee"></i></h3>
                                    <h3>暂无数据</h3>
                                </div>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel col-sm-6">
            <div class="panel-body" style="float: right ; width: 100%">
                <h3>二级分类：</h3>
                <div class="panel">
                    <div class="panel-body ">
                        <button type="button" class="btn btn-success" onclick="addOrUpdateSonArticleType()">
                            添加二级类型
                        </button>
                        <hr/>
                        <#if sonArticleTypeVoList?? && sonArticleTypeVoList?size gt 0 >
                        <table class="table">
                            <thead>
                            <tr>
                                <th>排序</th>
                                <th>添加时间</th>
                                <th>文章类型名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list  sonArticleTypeVoList as articleType>
                                <tr>
                                    <td>${(articleType.getArticleTypeSort())!}</td>
                                    <td>
                                        <#if (articleType.getArticleTypeAddTime())??>
                                            ${(articleType.getArticleTypeAddTime())?string("yyyy-MM-dd HH:mm:ss")}
                                        <#else >
                                            无
                                        </#if>
                                    </td>
                                    <td>
                                        ${(articleType.getArticleTypeName())!}
                                    </td>

                                    <td>
                                        <button onclick="addOrUpdateSonArticleType('${(articleType.getArticleTypeParentId())!}','${(articleType.getArticleTypeId())!}','${(articleType.getArticleTypeName())!}','${(articleType.getArticleTypeSort())!}')"
                                                type="button"
                                                class="btn btn-mini"><i
                                                    class="icon icon-cog"></i> 修改
                                        </button>
                                        <button onclick="delArticleType('${articleType.getArticleTypeId()}')"
                                                type="button"
                                                class="btn btn-mini">
                                            <i
                                                    class="icon icon-remove"></i>删除
                                        </button>
                                    </td>
                                </tr>
                            </#list>
                            <#else >
                                <div style="text-align: center">
                                    <h3><i class="icon icon-coffee"></i></h3>
                                    <h3>暂无数据</h3>
                                </div>
                            </#if>
                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="articleTypeUpdateModal">
    <div class="modal-dialog" style="width: 400px">
        <div class="modal-content">
            <form action="/xyadmin/updateArticle" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                                class="sr-only">关闭</span></button>
                    <h4 class="modal-title" id="articleTypeTittle">修改文章类型</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="articleTypeIdUpdate" id="articleTypeIdUpdate">
                    <div class="form-group" name="selectParentType" id="selectParentType">
                        <label for="parentArticleTypeId">选择一级类型：</label>
                        <select name="parentArticleTypeId" id="parentArticleTypeId"
                                class="form-control">
                            <#if articleTypeVoList?? && articleTypeVoList?size gt 0 >
                                <option value="" selected>--无--</option>
                                <#list  articleTypeVoList as articleType>
                                    <option value="${(articleType.getArticleTypeId())!}"
                                            data-keys="${(articleType.getArticleTypeId())!}">${(articleType.getArticleTypeName())!}
                                    </option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="articleTypeNameUpdate">类型名称：</label>
                        <input type="text" class="form-control" id="articleTypeNameUpdate"
                               placeholder="类型名称">
                    </div>
                    <div class="form-group">
                        <label for="articleTypeSortUpdate">类型排序：</label>
                        <input type="number" class="form-control" id="articleTypeSortUpdate"
                               placeholder="类型排序">
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="addOrUpdateArticleTypeForm()">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    let but = 1;

    function addOrUpdateSonArticleType(articleTypeParentId, articleTypeId, articleTypeName, articleTypeSort) {
        $('#articleTypeUpdateModal').modal('toggle', 'center');
        $('#articleTypeIdUpdate').val(articleTypeId)
        $('#articleTypeNameUpdate').val(articleTypeName)
        $('#articleTypeSortUpdate').val(articleTypeSort)
        $('#parentArticleTypeId').val(articleTypeParentId)
        document.getElementById("selectParentType").style.display = "block";

        if (!checkNotNull(articleTypeId)) {
            but = 2
            $("#articleTypeTittle").text("添加二级文章类型")
        } else {
            $("#articleTypeTittle").text("修改二级文章类型")
        }
    }


    function addOrUpdateArticleTypeForm() {
        let articleTypeId = $('#articleTypeIdUpdate').val()
        let articleTypeName = $('#articleTypeNameUpdate').val()
        let articleTypeSort = $('#articleTypeSortUpdate').val()
        let articleTypeParentId = $('#parentArticleTypeId').val()

        if (!checkNotNull(articleTypeId)) {
            $.post("/xyadmin/addArticleType", {
                    articleTypeName: articleTypeName,
                    articleTypeSort: articleTypeSort,
                    articleTypeParentId: articleTypeParentId,
                    articleTypeLevel: but
                },
                function (data) {
                    if (data.code == 200) {
                        alert("添加成功！")
                        location.reload();
                        but = 1;
                        return;
                    }
                    warningZuiMsg(data.message);
                });
            return;

        }
        $.post("/xyadmin/updateArticleType", {
                articleTypeId: articleTypeId,
                articleTypeName: articleTypeName,
                articleTypeSort: articleTypeSort,
                articleTypeParentId: articleTypeParentId
            },
            function (data) {
                if (data.code == 200) {
                    alert("修改成功！")
                    location.reload();
                    return;
                }
                warningZuiMsg(data.message);
            });

    }

    function addOrUpdateArticleType(articleTypeId, articleTypeName, articleTypeSort, articleTypeParentId) {
        $('#articleTypeUpdateModal').modal('toggle', 'center');
        $('#articleTypeIdUpdate').val(articleTypeId)
        $('#articleTypeNameUpdate').val(articleTypeName)
        $('#articleTypeSortUpdate').val(articleTypeSort)
        $('#parentArticleTypeId').val(articleTypeParentId)
        document.getElementById("selectParentType").style.display = "none";// 不占位

        if (!checkNotNull(articleTypeId)) {
            $("#articleTypeTittle").text("添加一级文章类型")
        } else {
            $("#articleTypeTittle").text("修改一级文章类型")
        }

    }

    function delArticleType(articleTypeId) {
        if (confirm("是否删除该类型？")) {

            if (!checkNotNull(articleTypeId)) {
                new $.zui.Messager('程序出错，请重新刷新页面重试！', {
                    type: 'warning' // 定义颜色主题
                }).show();
                return;
            }
            $.post("/xyadmin/delArticleType", {articleTypeId: articleTypeId},
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
</script>
<style>
    .table th, .table td {
        text-align: center;
        vertical-align: middle !important;
    }
</style>

<#include "../import/bottom.ftl">