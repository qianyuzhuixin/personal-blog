// 判断字符串是否为空
function checkNotNull(str) {
    if (str == null || str == "" || str.length < 1 || str == undefined) {
        return false;
    }
    return true;
}

// 休眠函数
function sleep(d) {
    for (let t = Date.now(); Date.now() - t <= d;) ;
}

// 警告弹出信息
function warningZuiMsg(msg) {
    new $.zui.Messager(msg, {
        type: 'warning', // 定义颜色主题
    }).show();
    return;
}

// 成功弹出信息
function successZuiMsg(msg) {
    new $.zui.Messager(msg, {
        type: 'success', // 定义颜色主题
    }).show();
    return;
}


