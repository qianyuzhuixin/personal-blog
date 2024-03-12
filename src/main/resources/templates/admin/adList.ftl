<#include "../import/adminTop.ftl">
<#-- 日期时间选择器-->
<link href="https://cdn.bootcdn.net/ajax/libs/smalot-bootstrap-datetimepicker/2.4.4/css/bootstrap-datetimepicker.min.css"
      rel="stylesheet">
<script src="https://cdn.bootcdn.net/ajax/libs/smalot-bootstrap-datetimepicker/2.4.4/js/bootstrap-datetimepicker.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/smalot-bootstrap-datetimepicker/2.4.4/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<div class="panel panel-default">
    <div class="panel col-sm-3">
        <div class="panel-body">
            <form class="form-horizontal" action="#">
                <input type="hidden" name="adTypeId" id="adTypeId">
                <div class="form-group">
                    <label for="adTypeName" class="col-sm-5">类型名称：</label>
                    <div class="col-md-6 col-sm-10">
                        <input type="text" class="form-control" name="adTypeName" id="adTypeName"
                               placeholder="类型名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="adTypeTag" class="col-sm-5">类型标识：</label>
                    <div class="col-md-6 col-sm-10">
                        <input type="text" class="form-control" name="adTypeTag" id="adTypeTag" placeholder="类型标识">
                    </div>
                </div>
                <div class="form-group">
                    <label for="adTypeSort" class="col-sm-5">类型排序：</label>
                    <div class="col-md-6 col-sm-10">
                        <input type="text" class="form-control" name="adTypeSort" id="adTypeSort"
                               placeholder="类型排序">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary pull-right" onclick="submitUpdateAdType()">提交</button>
            </form>
            <br/>
            <hr/>
            <hr/>
            <ul class="list-group">
                <#if adTypeList?? && adTypeList?size gt 0>
                    <#list adTypeList as adType>
                        <li class="list-group-item" style="width: 100%"><a
                                    href="/xyadmin/adList?adTypeId=${adType.getAdTypeId()!}">
                                ${adType.getAdTypeName()!}
                            </a>
                            <button type="button" class="btn btn-mini pull-right"
                                    onclick="updateAdType(
                                            '${adType.getAdTypeId()!}',
                                            '${adType.getAdTypeName()!}',
                                            '${adType.getAdTypeTag()!}',
                                            '${adType.getAdTypeSort()!}'
                                            )"><i class="icon icon-cog"></i>
                            </button>
                        </li>

                    </#list>
                <#else>
                    <div style="text-align: center">
                        <h3><i class="icon icon-coffee"></i></h3>
                        <h3>暂无数据</h3>
                    </div>
                </#if>
            </ul>
        </div>
    </div>
    <div class="panel col-sm-9">
        <div class="panel-body">
            <form class="form-horizontal">
                <input name="adId" type="hidden" id="adId">
                <div class="form-group">
                    <label for="ad-adTypeId" class="col-sm-2">广告类型：</label>
                    <div class="col-sm-2">
                        <select class="form-control" id="ad-adTypeId" name="ad-adTypeId">
                            <#if adTypeList?? && adTypeList?size gt 0>
                                <#list adTypeList as adType>
                                    <option value="${adType.getAdTypeId()!}">${adType.getAdTypeName()!}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <label for="adTitle" class="col-sm-2">广告标题：</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" id="adTitle" name="adTitle"
                               placeholder="广告标题">
                    </div>
                    <label for="adSort" class="col-sm-2">广告排序:</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" id="adSort" name="adSort"
                               placeholder="广告排序">
                    </div>
                </div>

                <div class="form-group">
                    <label for="adImgUrl" class="col-sm-2">广告图片链接：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="adImgUrl" name="adImgUrl"
                               placeholder="广告图片链接">
                    </div>
                    <label for="adLinkUrl" class="col-sm-2">广告链接：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="adLinkUrl" name="adLinkUrl"
                               placeholder="广告链接">
                    </div>
                </div>

                <div class="form-group">
                    <label for="adBeginTime" class="col-sm-2">广告开始时间：</label>
                    <div class="col-sm-4">
                        <input type="text" id="adBeginTime" name="adBeginTime"
                               placeholder="开始时间" class="form-control form-datetime" readonly="readonly">
                    </div>
                    <label for="adEndTime" class="col-sm-2">广告结束时间：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control form-datetime" id="adEndTime" name="adEndTime"
                               placeholder="结束时间" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2"></label>
                    <div class="col-sm-4">
                        <button type="button" onclick="clearAd()" class="btn btn-warning ">清空</button>
                        <button type="button" onclick="addAd()" class="btn btn-primary pull-right">添加</button>
                    </div>
                    <label class="col-sm-2"></label>
                    <div class="col-sm-4">
                        <button type="button" onclick="submitUpdateAd()" class="btn btn-success">确认修改
                        </button>
                    </div>
                </div>
            </form>
            <hr/>
            <hr/>
            <table class="table table-striped table-bordered  table-hover my-table table-auto">
                <thead>
                <tr>
                    <th>广告图片</th>
                    <th style="width: 15%">广告类型</th>
                    <th style="width: 10%">广告标题</th>
                    <th>广告开始时间</th>
                    <th>广告结束时间</th>
                    <th>广告添加时间</th>
                    <th style="width: 23%">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list  adVoList as adVo>
                    <tr>
                        <td><img class="thumbnail img-thumbnail" src="${(adVo.getAdImgUrl()!'/imgs/null_logo.png')}"
                                 alt="广告图片"></td>
                        <td>${(adVo.getAdTypeName())!}</td>
                        <td>${(adVo.getAdTitle())!}</td>
                        <td>
                            <#if (adVo.getAdBeginTime())??>
                                ${(adVo.getAdBeginTime())!?string("yyyy-MM-dd HH:mm:ss")}
                            <#else >
                                无
                            </#if>
                        </td>
                        <td>
                            <#if (adVo.getAdEndTime())??>
                                ${(adVo.getAdEndTime())!?string("yyyy-MM-dd HH:mm:ss")}
                            <#else >
                                无
                            </#if>
                        </td>
                        <td>
                            <#if (adVo.getAdAddTime())??>
                                ${(adVo.getAdAddTime())!?string("yyyy-MM-dd HH:mm:ss")}
                            <#else >
                                无
                            </#if>
                        </td>
                        <td>
                            <button onclick="updateAd(
                                    '${adVo.getAdId()!}',
                                    '${adVo.getAdTypeId()!}',
                                    '${adVo.getAdTitle()!}',
                                    '${adVo.getAdImgUrl()!}',
                                    '${adVo.getAdLinkUrl()!}',
                                    '${adVo.getAdSort()!}',
                                    '${(adVo.getAdBeginTime())!?string("yyyy-MM-dd HH:mm:ss")}',
                                    '${(adVo.getAdEndTime())!?string("yyyy-MM-dd HH:mm:ss")}',
                                    )" type="button" class="btn btn-mini"><i class="icon icon-cog"></i>修改
                            </button>
                            <button onclick="delAd('${adVo.getAdId()!}')" type="button"
                                    class="btn btn-mini">
                                <i
                                        class="icon icon-remove"></i>删除
                            </button>
                            <a target="_blank" href="${adVo.getAdLinkUrl()!}" class="btn btn-mini"><i
                                        class="icon icon-eye-open"></i>查看</a>
                        </td>
                    </tr>
                </#list>
                </tbody>

            </table>
        </div>
    </div>
</div>
<script>
    // 删除广告
    function delAd(adId) {
        if (confirm("确认删除？")) {
            $.post("/xyadmin/delAd", {
                    adId: adId,
                },
                function (data) {
                    if (data.code == 200) {
                        successZuiMsg("删除成功！")
                        location.reload()
                        return;
                    }
                    warningZuiMsg(data.message)
                });
        }

    }


    // 添加广告
    function addAd() {
        let adTypeId = $("#ad-adTypeId").val()
        let adTitle = $("#adTitle").val()
        let adImgUrl = $("#adImgUrl").val()
        let adLinkUrl = $("#adLinkUrl").val()
        let adSort = $("#adSort").val()
        let adBeginTime = $("#adBeginTime").val()
        let adEndTime = $("#adEndTime").val()


        if (!checkNotNull(adTypeId) || !checkNotNull(adTitle) ||
            !checkNotNull(adImgUrl) || !checkNotNull(adLinkUrl) || !checkNotNull(adSort) ||
            !checkNotNull(adBeginTime) || !checkNotNull(adEndTime)) {
            warningZuiMsg("参数不完整")
            return
        }

        $.post("/xyadmin/addOrUpdateAd", {
                adTypeId: adTypeId,
                adTitle: adTitle,
                adImgUrl: adImgUrl,
                adLinkUrl: adLinkUrl,
                adSort: adSort,
                adBeginTime: adBeginTime,
                adEndTime: adEndTime,
            },
            function (data) {
                if (data.code == 200) {
                    successZuiMsg("添加成功！")
                    location.reload()
                    return;
                }
                warningZuiMsg(data.message)
            });

    }


    // 清空信息
    function clearAd() {
        $("#adId").val("")
        $("#ad-adTypeId").val("")
        $("#adTitle").val("")
        $("#adImgUrl").val("")
        $("#adLinkUrl").val("")
        $("#adSort").val("")
        $("#adBeginTime").val("")
        $("#adEndTime").val("")
    }

    // 提交修改的广告信息
    function submitUpdateAd() {
        let adId = $("#adId").val()
        let adTypeId = $("#ad-adTypeId").val()
        let adTitle = $("#adTitle").val()
        let adImgUrl = $("#adImgUrl").val()
        let adLinkUrl = $("#adLinkUrl").val()
        let adSort = $("#adSort").val()
        let adBeginTime = $("#adBeginTime").val()
        let adEndTime = $("#adEndTime").val()

        if (!checkNotNull(adTypeId) || !checkNotNull(adTitle) ||
            !checkNotNull(adImgUrl) || !checkNotNull(adLinkUrl) || !checkNotNull(adSort) ||
            !checkNotNull(adBeginTime) || !checkNotNull(adEndTime)) {
            warningZuiMsg("参数不完整")
            return
        }

        $.post("/xyadmin/addOrUpdateAd", {
                adId: adId,
                adTypeId: adTypeId,
                adTitle: adTitle,
                adImgUrl: adImgUrl,
                adLinkUrl: adLinkUrl,
                adSort: adSort,
                adBeginTime: adBeginTime,
                adEndTime: adEndTime,
            },
            function (data) {
                if (data.code == 200) {
                    successZuiMsg("修改成功！")
                    location.reload()
                    return;
                }
                warningZuiMsg(data.message)
            });


    }

    // 写入需要修改的广告信息
    function updateAd(adId, adTypeId, adTitle, adImgUrl, adLinkUrl, adSort, adBeginTime, adEndTime) {
        $("#adId").val(adId)
        $("#ad-adTypeId").val(adTypeId)
        $("#adTitle").val(adTitle)
        $("#adImgUrl").val(adImgUrl)
        $("#adLinkUrl").val(adLinkUrl)
        $("#adSort").val(adSort)
        console.log(adSort)
        $("#adBeginTime").val(adBeginTime)
        $("#adEndTime").val(adEndTime)
    }

    // 提交修改广告类型
    function submitUpdateAdType() {
        let adTypeId = $("#adTypeId").val();
        let adTypeName = $("#adTypeName").val();
        let adTypeTag = $("#adTypeTag").val();
        let adTypeSort = $("#adTypeSort").val();
        if (!checkNotNull(adTypeId) || !checkNotNull(adTypeName) || !checkNotNull(adTypeTag) || !checkNotNull(adTypeSort)) {
            warningZuiMsg("参数不完整");
            return;
        }
        $.post("/xyadmin/addOrUpdateAdType", {
                adTypeId: adTypeId,
                adTypeName: adTypeName,
                adTypeTag: adTypeTag,
                adTypeSort: adTypeSort,
            },
            function (data) {
                if (data.code == 200) {
                    successZuiMsg("修改成功！")
                    location.reload()
                    return;
                }
                warningZuiMsg(data.message)
            });
    }

    // 需修改广告类型写入input框
    function updateAdType(adTypeId, adTypeName, adTypeTag, adTypeSort) {
        $("#adTypeId").val(adTypeId)
        $("#adTypeName").val(adTypeName)
        $("#adTypeTag").val(adTypeTag)
        $("#adTypeSort").val(adTypeSort)
    }


    // 选择时间和日期
    $(".form-datetime").datetimepicker(
        {
            language: "zh-CN",
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            format: "yyyy-mm-dd hh:ii:ss"
        });

</script>


<#include "../import/bottom.ftl">