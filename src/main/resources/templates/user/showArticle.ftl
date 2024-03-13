<#include "../import/userTop.ftl">
<div class="container">
    <ol class="breadcrumb">
        <li><a href="/"><i class="icon icon-home"></i>首页</a></li>
        <li><a href="/articleList?articleTypeId=${article.articleTypeId!}">${article.articleTypeName!}</a></li>
        <li class="active">${article.articleTitle!}</li>
    </ol>
    <#if article??>
        <div class="panel">
            <div class="panel-body show-article">
                <h1>${article.articleTitle!}
                    <br/>
                    <small style="font-size: small">
                        发布时间：${article.getArticleAddTime()?string("yyyy-MM-dd HH:mm:ss")}
                    </small>
                </h1>
                <div class="sign">
                    <span style="margin-left: 20px"><i class="icon icon-user"></i>作者：${article.userName!}</span>
                    <span class="label label-primary" style="margin-left: auto"><i
                                class="icon icon-eye-open "></i>${article.articleLookNums!}</span>
                    <span class="label label-success palm" onclick="articleAddGood('${article.articleId!}')"><i
                                class="icon icon-thumbs-o-up "></i>${article.articleGoodNums!}</span>
                    <span class="label label-danger palm" onclick="articleAddCollection('${article.articleId!}')"><i
                                class="icon icon-star-empty "></i>${article.articleCollectionNums!}</span>
                </div>
                <div class="article-context">
                    ${article.articleContent!}
                </div>
                <div class="comments">
                    <header>
                        <div class="pull-right"><a href="#commentReplyForm2" class="btn btn-primary"
                                                   style="color: white"><i
                                        class="icon-comment-alt"></i> 发表评论</a></div>
                        <h3 style="font-size: small">所有评论</h3>
                    </header>
                    <section class="comments-list" id="commentsList">
                    </section>
                    <footer>
                        <span id="moreComment" class="palm moreComment pull-right"
                              onclick="showComment('${article.articleId!}')">更多评论<i
                                    class="icon icon-double-angle-down"></i></span>
                        <br/>
                        <label for="comment-text" id="replyPeopleParent">评论：
                        </label>
                        <div class="reply-form" id="commentReplyForm2" style="display: flex">
                            <textarea class="form-control new-comment-text" rows="3" id="commentContext"
                                      name="commentContext"
                                      style="display: inline"
                                              <#if Session["user"]?exists>
                                                  placeholder="撰写评论..."
                                                  <#else >
                                                      placeholder="请登录..."
                                                      disabled="disabled"
                                    </#if>></textarea>
                            <button type="button" class="btn btn-primary" onclick="saveComment('${article.articleId!}')"
                                    style="margin:auto 10px;width: 80px"
                                    <#if Session["user"]?exists>
                            <#else >
                                disabled="disabled"
                                    </#if>><i
                                        class="icon-ok"></i></button>
                        </div>
                    </footer>
                </div>
            </div>
        </div>
    </#if>
</div>
<style>
    .palm:hover {
        cursor: pointer;
    }

    .background-deep:hover {
        background-color: #d6f3f3;
    }

    .moreComment:hover {
        color: #67b1e8;
    }

    .goodClick {
        background-color: #FCEBEB;
    }

    h1 {
        text-align: center;
        font-size: 36px;
    }


    .show-article {
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
    }

    .sign {
        width: 100%;
        height: 30px;
        background-color: #e0e0e0;
        display: flex;
        justify-items: center;
    }

    .sign span {
        font-size: 13px;
        margin: auto 10px auto 0;
    }

    .article-context {
        margin: 20px 5px;
    }

    .article-context p {
        /*font-size: 15px;*/
    }

    .recoverPeople {
        background-color: #d3d1d3;
        border-radius: 2px;
        padding: 2px 5px;
        /*添加阴影*/
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    }

    .recoverPeople strong {
        /*字体为蓝色*/
        color: #1e90ff;
        margin: 0 5px;
    }

    .recoverPeople span {
        /*字体调大*/
        font-size: 20px;
        /*添加背景色*/
        background-color: #e1dede;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        /*呈正方形*/
        display: inline-block;
        width: 20px;
        height: 20px;
        line-height: 20px;
        text-align: center;
        border-radius: 2px;

    }

    /*鼠标移入背景改变*/
    .recoverPeople span:hover {
        background-color: #c7c6c6;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    }
</style>
<script>
    let beRepliedCommentId = ''
    let topLevelCommentId = ''

    //删除回复人
    function deleteCommentPeople() {
        beRepliedCommentId = '';
        topLevelCommentId = '';
        $("#recoverPeople").empty().remove()
    }

    //评论回复
    function recover(commentId, type) {
        deleteCommentPeople()
        if (type == 1) {
            beRepliedCommentId = commentId;
            topLevelCommentId = commentId;
        }

        let userName = ''

        <#if Session["user"]?exists>
        $.post("/user/getReplyPeople", {
            commentId: commentId
        }, function (data) {
            if (data.code == 200) {
                userName = data.data.userName
                let context = '<p class="recoverPeople" id="recoverPeople">回复<strong>@' + userName + '</strong><span class="palm" onclick = "deleteCommentPeople()" >×</span></p>'
                $("#replyPeopleParent").append(context)
                $("#commentContext").focus();
                return
            } else {
                warningZuiMsg(data.message)
            }
        })
        <#else >
        warningZuiMsg('请先登录')
        </#if>
    }

    // 发表回复评论
    function saveReplyComment() {
        let commentContext = $("#commentContext").val();
        if (!checkNotNull(commentContext) || commentContext.length < 1) {
            warningZuiMsg("请填写评论！")
            return
        }
        $.post("/user/saveReplyComment", {
            topLevelCommentId: topLevelCommentId,
            beRepliedCommentId: beRepliedCommentId,
            commentContext: commentContext,
        }, function (data) {
            if (data.code == 200) {
                successZuiMsg("评论成功！")
                $("#commentContext").val("")
                $('#1' + beRepliedCommentId)
                    .prepend(addRecoverHtml(
                        data.data.commentTime,
                        data.data.replyUserName,
                        data.data.beRepliedUserName,
                        data.data.commentContext,
                        data.data.replyCommentId,
                        data.data.commentGoodNums,
                        data.data.isGood))
                deleteCommentPeople()
                return
            } else {
                warningZuiMsg(data.message)
            }
        })
    }

    // 添加回复html
    function addRecoverHtml(commentTime, replyUserName,
                            beRepliedUserName, commentContext,
                            replyCommentId, commentGoodNums,
                            isGood, commentStatus) {
        let comment =
            '<div class="comment">' +
            '<div class="content">' +
            '<div class="pull-right text-muted">' + commentTime + '</div>' +
            '<div><i class="icon icon-user"></i><strong>' + replyUserName +
            '</strong>'
        if (commentStatus == 2) {
            comment += '<span class="text-muted">回复</span>' + beRepliedUserName
        }
        comment += '</div><div class="text">' + '&emsp;&emsp;' + commentContext + '</div>' +
            '<div class="actions">' +
            '<span onclick="recover(\'' + replyCommentId + '\')"  class="palm img-thumbnail background-deep">回复</span>';
        if (isGood == 1) {
            comment += '  <span onclick="goodComment(this,\'' + replyCommentId + '\')"  class="palm img-thumbnail background-deep goodClick"><i class="icon icon-thumbs-o-up"></i>点赞' + '&emsp;' + '<i class="icon icon-thumbs-o-up"></i>' + commentGoodNums + '</span>' +
                '</div>' +
                '</div>' + '</div>'
        } else {
            comment += ' <span onclick="goodComment(this,\'' + replyCommentId + '\')"  class="palm img-thumbnail background-deep"><i class="icon icon-thumbs-o-up"></i>点赞' + '&emsp;' + '<i class="icon icon-thumbs-o-up"></i>' + commentGoodNums + '</span>' +
                '</div>' +
                '</div>' + '</div>'
        }

        return comment;
    }

    let replyTotalPage = 0
    let replyPageNumber = 1

    // 展示回复评论
    function showReplyComment(commentId) {
        $.post("/user/showRecoverComment",
            {
                commentId: commentId,
                pageNumber: ++replyPageNumber
            }, function (data) {
                if (data.code == 200) {
                    let replyCommentVos = data.data.data;
                    replyTotalPage = data.data.totalPages;
                    replyPageNumber = data.data.currentPage;
                    if (replyTotalPage > replyPageNumber) {
                        $('#10' + commentId).show();
                    } else {
                        $('#10' + commentId).hide();
                    }
                    for (let j = 0; j < replyCommentVos.length; j++) {
                        $('#1' + commentId)
                            .append(addRecoverHtml(
                                replyCommentVos[j].commentTime,
                                replyCommentVos[j].replyUserName,
                                replyCommentVos[j].beRepliedUserName,
                                replyCommentVos[j].commentContext,
                                replyCommentVos[j].replyCommentId,
                                replyCommentVos[j].commentGoodNums,
                                replyCommentVos[j].isGood,
                                replyCommentVos[j].commentStatus))
                    }
                    return
                } else {
                    warningZuiMsg("页面出错！")
                }
            })
    }


    // 评论点赞
    function goodComment(obj, commentId) {
        commentId = commentId + '';
        $.post("/goodComment",
            {
                commentId: commentId,
            }, function (data) {
                if (data.code == 200) {
                    $(obj).addClass('goodClick')
                    let goodTxt = $(obj).text();
                    goodTxt = goodTxt.replace('点赞', '');
                    goodTxt = goodTxt.trim()
                    goodTxt = parseInt(goodTxt) + 1;
                    $(obj).html('<i class="icon icon-thumbs-o-up"></i>点赞&emsp;<i class="icon icon-thumbs-o-up"></i>' + goodTxt);
                    successZuiMsg(data.message)
                    return
                } else {
                    warningZuiMsg(data.message)
                }
            })
    }

    let articleId = '${article.articleId!}'
    let totalPage = 0
    let pageNumber = 0


    // 展示评论
    function showComment(articleId) {
        $.post("/user/showComment",
            {
                articleId: articleId,
                pageNumber: ++pageNumber
            }, function (data) {
                if (data.code == 200) {
                    let commentList = data.data.data;
                    totalPage = data.data.totalPages;
                    pageNumber = data.data.currentPage;
                    if (totalPage > pageNumber) {
                        $('#moreComment').show();
                    } else {
                        $('#moreComment').hide();
                    }
                    for (let i = 0; i < commentList.length; i++) {
                        $('#commentsList').append(addCommentHtml(commentList[i].commentTime, commentList[i].userName, commentList[i].commentContext, commentList[i].commentId, commentList[i].commentGoodNums, commentList[i].idGood))
                        if (commentList[i].replyCommentVos.length < 5) {
                            $('#10' + commentList[i].commentId).hide()
                        }
                    }
                    return
                } else {
                    warningZuiMsg("页面出错！")
                }
            })
    }

    // 页面初始化后,加载评论
    $(function () {
        $('#moreComment').hide();
        $.post("/user/showComment",
            {
                articleId: articleId,
                pageNumber: ++pageNumber
            }, function (data) {
                if (data.code == 200) {
                    let commentList = data.data.data;
                    totalPage = data.data.totalPages;
                    pageNumber = data.data.currentPage;
                    if (totalPage > pageNumber) {
                        $('#moreComment').show();
                    }
                    for (let i = 0; i < commentList.length; i++) {
                        $('#commentsList')
                            .append(addCommentHtml(
                                commentList[i].commentTime,
                                commentList[i].userName,
                                commentList[i].commentContext,
                                commentList[i].commentId,
                                commentList[i].commentGoodNums,
                                commentList[i].isGood))
                        let replyCommentVos = commentList[i].replyCommentVos;
                        if (replyCommentVos.length < 5) {
                            $('#10' + commentList[i].commentId).hide()
                        }
                        for (let j = 0; j < replyCommentVos.length; j++) {
                            $('#1' + replyCommentVos[j].topLevelCommentId)
                                .append(addRecoverHtml(
                                    replyCommentVos[j].commentTime,
                                    replyCommentVos[j].replyUserName,
                                    replyCommentVos[j].beRepliedUserName,
                                    replyCommentVos[j].commentContext,
                                    replyCommentVos[j].replyCommentId,
                                    replyCommentVos[j].commentGoodNums,
                                    replyCommentVos[j].isGood,
                                    replyCommentVos[j].commentStatus))
                        }
                    }
                    return
                } else {
                    warningZuiMsg("页面出错！")
                }
            })

    })

    // 发表评论
    function saveComment(articleId) {
        if (!checkNotNull(beRepliedCommentId)) {
            let commentContext = $("#commentContext").val();
            if (!checkNotNull(commentContext) || commentContext.length < 1) {
                warningZuiMsg("请填写评论！")
                return
            }
            $.post("/user/saveComment", {
                commentContext: commentContext,
                articleId: articleId
            }, function (data) {
                if (data.code == 200) {
                    successZuiMsg("评论成功！")
                    $("#commentContext").val("")
                    $('#commentsList').prepend(addCommentHtml(data.data.commentTime, data.data.userName, data.data.commentContext, data.data.commentId, data.data.commentGoodNums, data.data.isGood))
                    return
                } else {
                    warningZuiMsg(data.message)
                }
            })
        } else {
            saveReplyComment()
        }

    }


    // 添加评论html
    function addCommentHtml(commentTime, userName, commentContext, commentId, commentGoodNums, isGood) {
        let comment = '<div class="comment" >' +
            '<div class="content">' +
            '<div class="pull-right text-muted">' + commentTime + '</div>' +
            '<div><i class="icon icon-user"></i><strong>' + userName +
            '</strong></div>' +
            '<div class="text">' + '&emsp;&emsp;' + commentContext + '</div>' +
            '<div class="actions">' +
            '<span onclick="recover(\'' + commentId + '\',1)"  class="palm img-thumbnail background-deep">回复</span>';

        if (isGood == 1) {
            comment += ' <span onclick="goodComment(this,\'' + commentId + '\')"  class="palm img-thumbnail background-deep goodClick"><i class="icon icon-thumbs-o-up"></i>点赞' + '&emsp;' + '<i class="icon icon-thumbs-o-up"></i>' + commentGoodNums + '</span>'
        } else {
            comment += ' <span onclick="goodComment(this,\'' + commentId + '\')"  class="palm img-thumbnail background-deep"><i class="icon icon-thumbs-o-up"></i>点赞' + '&emsp;' + '<i class="icon icon-thumbs-o-up"></i>' + commentGoodNums + '</span>'
        }
        comment += '</div>' +
            '</div>' +
            '<div class="comments-list" id=1' + commentId + '></div>' +
            '<span id="10' + commentId + '" class="palm moreComment pull-right " ' +
            'onclick="showReplyComment(\'' + commentId + '\')">更多回复' +
            '<i class="icon icon-double-angle-down"></i>' +
            '</span>' +
            '</div>'

        return comment;
    }

    // 点击文章收藏
    function articleAddCollection(articleId) {
        $.post("/articleAddCollection", {
                articleId: articleId
            },
            function (data) {
                if (data.code == 200) {
                    successZuiMsg(data.message)
                    location.reload();
                    return;
                }
                warningZuiMsg(data.message);
            });

    }

    // 点击文章点赞
    function articleAddGood(articleId) {
        $.post("/articleAddGood", {
                articleId: articleId
            },
            function (data) {
                if (data.code == 200) {
                    successZuiMsg(data.message)
                    location.reload();
                    return;
                }
                warningZuiMsg(data.message);
            });

    }


</script>
<#include "../import/userBottom.ftl">