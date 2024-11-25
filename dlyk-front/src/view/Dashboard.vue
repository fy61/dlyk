<template>
    <el-container>
        <!-- 左侧 -->
        <el-aside :style="{ width: leftPage }">
            <div class="menuTitle">@动力云客管理系统</div>
            <el-menu
                active-text-color="#ffd04b"
                background-color="#334157"
                style="border-right: 0px"
                unique-opened="true"
                :collapse="isCollapse"
                class="el-menu-vertical-demo"
                :default-active="currentRouterPath"
                text-color="#fff"
                @open="handleOpen"
                @close="handleClose"
                :collapse-transition="false"
                :router="true"
            >
                <!-- element-plus开启路由模式后,子菜单可以通过index作为路由路径进行跳转 -->
                <!-- 市场活动菜单 -->
                <el-sub-menu
                    :index="index"
                    v-for="(menuPermission, index) in user.menuPermissionList"
                    :key="menuPermission.id"
                >
                    <template #title>
                        <el-icon>
                            <component :is="menuPermission.icon"></component>
                        </el-icon>
                        <span>{{ menuPermission.name }}</span>
                    </template>

                    <el-menu-item
                        v-for="subPermission in menuPermission.subPermissionList"
                        :key="subPermission.id"
                        :index="subPermission.url"
                    >
                        <el-icon>
                            <component :is="subPermission.icon"></component>
                        </el-icon>
                        >{{ subPermission.name }}</el-menu-item
                    >
                </el-sub-menu>
            </el-menu>
        </el-aside>

        <!-- 右侧 -->
        <el-container class="rightContent">
            <!-- 右侧-上 -->
            <el-header>
                <el-icon class="show" @click="showMenu">
                    <Fold />
                </el-icon>

                <el-dropdown :hide-on-click="false">
                    <span class="el-dropdown-link">
                        {{ user.name
                        }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>我的资料</el-dropdown-item>
                            <el-dropdown-item>修改密码</el-dropdown-item>
                            <el-dropdown-item divided @click="logout()"
                                >退出登录</el-dropdown-item
                            >
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 右侧-中 -->
            <el-main>
                <router-view v-if="isRouterAlive" />
            </el-main>
            <!-- 右侧-下 -->
            <el-footer
                >@版权所有 2009-2099 动力节点
                北京市通州区马驹桥镇景盛中街17号顺景总部公元B4栋</el-footer
            >
        </el-container>
    </el-container>
</template>
<script>
import { isComment } from 'element-plus/es/utils/index.mjs';
import { defineComponent } from 'vue';
import { doGet } from '../http/httpRequest';
import { messageConfirm, messageTip, removeToken } from '../util/util';
export default defineComponent({
    // 定义组件名
    data() {
        return {
            //控制左侧菜单左右展开和折叠,true-折叠 flase-展开
            isCollapse: false,
            leftPage: '200px',
            //登录用户对象，初始值是空
            user: {},
            isRouterAlive: true, //控制仪表盘页面右侧内容是否显示
            //当前访问的路由路径
            currentRouterPath: ''
        };
    },

    //vue的生命周期中的一个函数钩子,此钩子是在页面渲染后执行
    mounted() {
        //加载当前用户
        this.loadLoginUser();
        this.loadCurrentRouterPath();
    },
    //提供者（生产者）
    provide() {
        return {
            //提供一个函数（要求是箭头函数）
            reload: () => {
                this.isRouterAlive = false; //右侧内容隐藏
                this.$nextTick(() => {
                    //$nextTick(), 当数据更新了，在dom中渲染后，自动执行该函数，
                    this.isRouterAlive = true;
                });
            }

            //提供一个字符串
            // content: "是对负荷计算东方红郡凯撒的合法户籍卡",

            //提供一个数字
            // age: 28,

            //提供一个对象
            // user: { id: 1098, name: "张三", age: 18 },

            //提供一个数组
            // arr: [12, 56, 109, 356, 8901]

            //......
        };
    },
    methods: {
        //左侧菜单的左右展开和折叠
        showMenu() {
            this.isCollapse = !this.isCollapse;
            this.leftPage = this.isCollapse ? '64px' : '200px';
        },
        //加载当前登录用户
        loadLoginUser() {
            doGet('/api/login/info', {}).then((resp) => {
                //用function就不能this引用了,需要用箭头函数
                console.log(resp);
                this.user = resp.data.data;
            });
        },
        //退出登录
        logout() {
            doGet('/api/logout', {}).then((resp) => {
                console.log(resp);
                if (resp.data.code === 200) {
                    removeToken();
                    messageTip('退出成功', 'success');
                    //跳到登录页
                    window.location.href = '/';
                } else {
                    messageConfirm('退出异常,是否要强制退出?')
                        .then(() => {
                            //用户点击确定按钮
                            removeToken(); //后端token验证未通过,则前端token有问题,没必要保留
                            window.location.href = '/';
                        })
                        .catch(() => {
                            //用户点击取消按钮
                            messageTip('取消强制退出', 'warning');
                        });
                }
            });
        },

        //加载当前路由路径
        loadCurrentRouterPath() {
            let path = this.$route.path; //   /dashboard/activity/add
            let arr = path.split('/'); //   [  ,dashboard, activity, add]
            if (arr.length > 3) {
                this.currentRouterPath = '/' + arr[1] + '/' + arr[2];
            } else {
                this.currentRouterPath = path;
            }
        }
    }
});
</script>
<style>
.el-header {
    background-color: rgb(229, 255, 255);
    height: 35px;
    line-height: 35px;
    /* rgb(201, 249, 249) */
}

.el-main {
    background-color: aliceblue;
}

.el-footer {
    background-color: azure;
    height: 35px;
    line-height: 35px;
    /*上下居中(和高度一样)*/
    text-align: center;
    /*左右居中*/
}

.rightContent {
    height: calc(100vh);
}

.menuTitle {
    height: 35px;
    line-height: 35px;
    color: azure;
    text-align: center;
}

.show {
    cursor: pointer;
}

.el-dropdown {
    float: right;
    line-height: 35px;
}
</style>
