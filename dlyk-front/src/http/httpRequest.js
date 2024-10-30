import axios from "axios";
import { getTokenName, messageConfirm, messageTip, removeToken } from "../util/util";
import { ElMessage, ElMessageBox } from "element-plus";

axios.defaults.baseURL = "http://localhost:8089";
//查询
export function doGet(url, params) {
    // :void
    return axios({
        method: "get",
        url: url,
        params: params,//数据{,,,}
        dataType: "json"
    })
}

//新增
export function doPost(url, data) {
    //:void
    return axios({
        method: "post",
        url: url,
        data: data,//数据{,,,}
        dataType: "json"
    })
}

//修改
export function doPut(url, data) {
    //:void
    return axios({
        method: "put",
        url: url,
        data: data,//数据{,,,}
        dataType: "json"
    })
}

//删除
export function doDelete(url, params) {
    // :void
    return axios({
        method: "delete",
        url: url,
        params: params,//数据{,,,}
        dataType: "json"
    })
}

// 添加请求拦截器
axios.interceptors.request.use((config) => {
    // 在发送请求之前做些什么，在请求头中放一个token（jwt），传给后端接口
    let token = window.sessionStorage.getItem(getTokenName());
    if (!token) {//前面加了一个！，表示token不存在，token是空的，token没有值，这个意思
        token = window.localStorage.getItem(getTokenName())
        if(token){
            config.headers['rememberMe']=true
        }
    }
    if (token) {//表示token存在，token不是空的，token有值，这个意思
        config.headers['Authorization'] = token
    }

    return config;
}, (error) => {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use((response) => {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么，拦截token验证的结果，进行相应的提示和页面跳转
    if (response.data.code > 900) {//token验证未通过,并跳转页面

        messageConfirm(response.data.msg + ",是否重新去登录?").then(() => {//用户点击确定按钮
            removeToken()//后端token验证未通过,则前端token有问题,没必要保留
            window.location.href = "/"
        })
            .catch(() => {//用户点击取消按钮,触发catch函数
                messageTip("取消去登录", "warning")
            })
        return;
    }
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});