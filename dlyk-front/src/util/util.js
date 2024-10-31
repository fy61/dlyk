import { ElMessage, ElMessageBox } from 'element-plus';
/**
 * 显示消息提示
 * @param 消息文字 msg
 * @param 消息类型 type
 */
export function messageTip(msg, type) {
    ElMessage({
        showClose: true, //是否显示关闭按钮
        center: true, //文字是否居中
        duration: 3000, //显示时间，单位为毫秒
        message: msg, //消息文字
        type: type //消息类型 'success' | 'warning' | 'info' | 'error'
    });
}

/**
 *
 * @returns 获取token(jwt)名字
 */
export function getTokenName() {
    return 'dlyk_token';
}

/**
 * 删除token
 */
export function removeToken() {
    window.localStorage.removeItem(getTokenName());
    window.sessionStorage.removeItem(getTokenName());
}

/**
 * 消息确认
 * @param {消息内容} msg
 * @returns
 */
export function messageConfirm(msg) {
    return ElMessageBox.confirm(
        msg, //提示语
        '系统提醒', //提示的标题
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消 ',
            type: 'warning'
        }
    );
}

/**
 * 封装返回函数
 */
export function goBack() {
    this.$router.go(-1);
}
