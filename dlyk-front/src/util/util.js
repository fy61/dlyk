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

/**
 * 获取token
 *
 * @returns {string}
 */
export function getToken() {
    let token = window.sessionStorage.getItem(getTokenName());
    if (!token) {
        //前面加了一个！，表示token不存在，token是空的，token没有值，这个意思
        token = window.localStorage.getItem(getTokenName());
    }
    if (token) {
        //表示token存在，token不是空的，token有值，这个意思
        return token;
    } else {
        messageConfirm('请求token为空，是否重新去登录？')
            .then(() => {
                //用户点击“确定”按钮就会触发then函数
                //既然后端验证token未通过，那么前端的token肯定是有问题的，那没必要存储在浏览器中，直接删除一下
                removeToken();
                //跳到登录页
                window.location.href = '/';
            })
            .catch(() => {
                //用户点击“取消”按钮就会触发catch函数
                messageTip('取消去登录', 'warning');
            });
    }
}
