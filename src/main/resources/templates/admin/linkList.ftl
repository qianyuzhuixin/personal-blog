<#include "../import/adminTop.ftl">


<#if linkList?? && linkList?size gt 0 >
    <div class="panel">
        <div class="panel-body">
            <h4>当前友链数：${(linkList?size!0)}个</h4>
            <button type="button" class="btn btn-success" onclick="addLink()"><i class="icon icon-plus"></i>添加
            </button>
            <hr/>
            <table class="table">
                <thead>
                <tr>
                    <th>友情链接排序</th>
                    <th>Logo</th>
                    <th>添加时间</th>
                    <th>友情链接标题</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list  linkList as link>
                    <tr>
                        <td>${(link.getLinkSort())!}</td>
                        <td><img class="thumbnail img-thumbnail" src="${(link.getLinkLogoUrl()!'/imgs/null_logo.png')}"
                                 alt="友链logo"/></td>
                        <td>
                            <#if (link.getLinkAddTime())??>
                                ${(link.getLinkAddTime())?string("yyyy-MM-dd HH:mm:ss")}
                            <#else >
                                无
                            </#if>
                        </td>
                        <td>${(link.getLinkTitle())!}</td>

                        <td>
                            <a href="${(link.getLinkUrl()!)}" target="_blank" class=" btn btn-mini"><i
                                        class="icon icon-eye-open">查看</i></a>
                            <button onclick="updateLink('${(link.getLinkId())!}',
                                    '${(link.getLinkTitle())!}',
                                    '${(link.getLinkUrl())!}',
                                    '${(link.getLinkLogoUrl())!}',
                                    '${(link.getLinkSort())!}'
                                    )"
                                    type="button"
                                    class="btn btn-mini"><i
                                        class="icon icon-cog"></i> 修改
                            </button>
                            <button onclick="delLink('${link.getLinkId()!}')" type="button"
                                    class="btn btn-mini">
                                <i
                                        class="icon icon-remove"></i>删除
                            </button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
<#else >
    <div style="text-align: center">
        <h3><i class="icon icon-coffee"></i></h3>
        <h3>暂无数据</h3>
    </div>
</#if>

<div class="modal fade" id="linkUpdateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/xyadmin/addOrUpdateLink" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                                class="sr-only">关闭</span></button>
                    <h4 class="modal-title" id="linkTittle">修改友联</h4>
                </div>
                <div class="modal-body">

                    <input type="hidden" name="linkIdUpdate" id="linkIdUpdate">
                    <div class="form-group">
                        <label for="linkTitleUpdate">友联名称：</label>
                        <input type="text" class="form-control" id="linkTitleUpdate"
                               placeholder="友联名称">
                    </div>
                    <div class="form-group">
                        <label for="linkUrlUpdate">友情链接：</label>
                        <input type="text" class="form-control" id="linkUrlUpdate" placeholder="友情链接">
                    </div>
                    <div class="form-group">
                        <label for="linkLogoUrlUpdate">友联logo：</label>
                        <input type="text" class="form-control" id="linkLogoUrlUpdate" placeholder="友联logo">
                    </div>
                    <div class="form-group">
                        <label for="linkSortUpdate">友联排序：</label>
                        <input type="text" class="form-control" id="linkSortUpdate"
                               placeholder="友联排序">
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="addOrUpdateLinkForm()">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function addLink() {
        $('#linkUpdateModal').modal('toggle', 'center');
        $("#linkTittle").text("添加友联")
        $('#linkIdUpdate').val("")
        $('#linkTitleUpdate').val("")
        $('#linkUrlUpdate').val("")
        $('#linkLogoUrlUpdate').val("")
        $('#linkSortUpdate').val("")

    }


    function updateLink(linkId, linkTitle, linkUrl, linkLogoUrl, linkSort) {
        $('#linkUpdateModal').modal('toggle', 'center');
        $("#linkTittle").text("修改友联")
        $('#linkIdUpdate').val(linkId)
        $('#linkTitleUpdate').val(linkTitle)
        $('#linkUrlUpdate').val(linkUrl)
        $('#linkLogoUrlUpdate').val(linkLogoUrl)
        $('#linkSortUpdate').val(linkSort)
    }

    function addOrUpdateLinkForm() {
        let linkId = $('#linkIdUpdate').val()
        let linkTitle = $('#linkTitleUpdate').val()
        let linkUrl = $('#linkUrlUpdate').val();
        let linkLogoUrl = $('#linkLogoUrlUpdate').val();
        let linkSort = $('#linkSortUpdate').val();

        if (!checkNotNull(linkTitle) || !checkNotNull(linkUrl) || !checkNotNull(linkLogoUrl) || !checkNotNull(linkSort)) {
            warningZuiMsg('请填写完整友联信息');
            return;
        }
        $.post("/xyadmin/addOrUpdateLink", {
                linkId: linkId,
                linkTitle: linkTitle,
                linkUrl: linkUrl,
                linkLogoUrl: linkLogoUrl,
                linkSort: linkSort
            },
            function (data) {
                if (data.code == 200) {
                    alert("添加成功！")
                    location.reload();
                    return;
                }
                warningZuiMsg(data.message);
            });
    }


    function delLink(linkId) {
        if (confirm("是否删除该友联？")) {

            if (!checkNotNull(linkId)) {
                new $.zui.Messager('程序出错，请重新刷新页面重试！', {
                    type: 'warning' // 定义颜色主题
                }).show();
                return;
            }
            $.post("/xyadmin/delLink", {linkId: linkId},
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

<#include "../import/bottom.ftl">