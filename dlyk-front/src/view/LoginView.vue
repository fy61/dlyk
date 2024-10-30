<template>
    <el-container>
        <el-aside width="200px">
            <img src="../assets/loginBox.svg" />
            <p class="imageTitle">欢迎进入动力云客系统</p>
        </el-aside>
        <el-main>
            <div class="loginTitle">欢迎登录</div>

            <el-form ref="loginRefFrom" :model="user" label-width="auto" style="max-width: 600px" :rules="loginRules">
                <el-form-item label="账号" prop="loginAct"> <el-input v-model="user.loginAct" /><!--此处form.name需要定义属性--> </el-form-item>

                <el-form-item label="密码" prop="loginPwd"> <el-input type="password" v-model="user.loginPwd" /><!--此处form.name需要定义属性--> </el-form-item>

                <el-form-item>
                    <el-button style="width: 100%; margin-left: 50px" type="primary" @click="login">登 录</el-button>
                </el-form-item>

                <el-form-item>
                    <el-checkbox label="记住我" v-model="user.rememberMe" />
                </el-form-item>
            </el-form>
        </el-main>
    </el-container>
</template>

<script>
import { defineComponent } from 'vue';
import { doGet, doPost } from '../http/httpRequest';
import { getTokenName, messageTip, removeToken } from '../util/util';
import { el } from 'element-plus/es/locales.mjs';
export default {
    //组件名字
    name: 'LoginView',

    // 定义页面时需要使用到的变量,初始值都是空
    data() {
        return {
            // 对象变量定义
            user: {},
            //字符串变量定义
            name: '',
            //数字变量定义
            age: 0,
            //数组变量定义
            arr: [],
            //list集合对象定义
            userList: [{}],
            //定义登录表单的验证规则
            loginRules: {
                // 定义账号的验证规则
                loginAct: [{ required: true, message: '请输入登录账号:', trigger: 'blur' }],
                // 定义密码的验证规则
                loginPwd: [
                    { required: true, message: '请输入登录密码:', trigger: 'blur' },
                    { min: 6, max: 16, message: '登录密码长度为6-16位', trigger: 'blur' }
                ]
            }
        };
    },

    //页面渲染时会触发此函数钩子(生命周期)
    mounted() {
        this.freeLogin();
    },

    //页面上需要使用的js函数,都在method属性中定义
    methods: {
        // 登录函数
        login() {
            this.$refs.loginRefFrom.validate((isValid) => {
                if (isValid) {
                    //isValid是true表示验证通过了--表单中的输入是否符合指定的验证规则
                    let formData = new FormData(); //用于存储表单数据的键值对
                    formData.append('loginAct', this.user.loginAct);
                    formData.append('loginPwd', this.user.loginPwd);
                    formData.append('rememberMe', this.user.rememberMe);

                    doPost('/api/login', formData).then((resq) => {
                        if (resq.data.code === 200) {
                            //登录成功
                            messageTip('登录成功', 'success');

                            //删除一下历史中localStorage和sessionStorage中存储的token
                            removeToken();

                            //前端保存jwt
                            if (this.user.rememberMe === true) {
                                window.localStorage.setItem(getTokenName(), resq.data.data);
                            } else {
                                window.sessionStorage.setItem(getTokenName(), resq.data.data);
                            }
                            //跳转到系统总页面
                            window.location.href = '/dashboard';
                        } else {
                            //登录失败
                            messageTip('登录失败', 'error');
                        }
                    });
                }
            });
        },

        //免登录(自动登录)
        freeLogin() {
            let token = window.localStorage.getItem(getTokenName());
            if (token) {
                //token有值,不是空
                doGet('/api/login/free', {}).then((resp) => {
                    if (resp.data.code === 200) {
                        //token验证通过,免登录
                        window.location.href = '/dashboard';
                    }
                });
            }
        }
    }
};
</script>

<style>
.el-aside {
    background-color: #1a1a1a;
    width: 40%;
    text-align: center;
}

.el-main {
    height: calc(100vh);
    /* 屏幕高度的百分之百 */
}

img {
    height: 420px;
}

.imageTitle {
    color: aliceblue;
    font-size: 25px;
}

.el-form {
    width: 60%;
    margin: auto;
}

.loginTitle {
    text-align: center;
    margin-top: 100px;
    margin-bottom: 30px;
    font-weight: bold;
}

.el-form-item {
    text-align: center;
    width: 80%;
    margin-left: 65px;
}

.register {
    margin-left: 50px;
}
</style>
