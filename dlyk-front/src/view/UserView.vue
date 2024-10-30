<template>
    <!-- <div class="hello">
        你好
        //透传传递数据方法
        <HelloWorld :cxk="userId" :info="pageSize" />
        <el-button @click="() => { this.test = 10 }">man</el-button>
    </div> -->
    <el-button type="primary" @click="add">添加用户</el-button>
    <el-button type="danger" @click="batchDel">批量删除</el-button>
    <el-table :data="userList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55px" />
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column property="loginAct" label="账号" />
        <el-table-column property="name" label="姓名" show-overflow-tooltip />
        <el-table-column property="phone" label="手机" show-overflow-tooltip />
        <el-table-column property="email" label="邮箱" show-overflow-tooltip />
        <el-table-column property="createTime" label="创建时间" show-overflow-tooltip />
        <el-table-column label="操作" width="240" show-overflow-tooltip>
            <template #default="scope">
                <el-button type="primary" @click="view(scope.row.id)">详情</el-button>
                <el-button type="success" @click="edit(scope.row.id)">编辑</el-button>
                <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="total"
        @prev-click="toPage"
        @next-click="toPage"
        @current-change="toPage"
    />

    <!-- 新增用户的对话框 -->
    <el-dialog
        v-model="useDialogVisible"
        :title="userQuery.id > 0 ? '编辑用户' : '新增用户'"
        width="55%"
        center
        draggable
    >
        <el-form
            ref="userRefFrom"
            :model="userQuery"
            label-width="110px"
            :rules="userRules"
        >
            <el-form-item label="账号" prop="loginAct">
                <el-input v-model="userQuery.loginAct" />
            </el-form-item>

            <el-form-item label="密码" v-if="userQuery.id > 0">
                <el-input type="password" v-model="userQuery.loginPwd" />
            </el-form-item>

            <el-form-item label="密码" prop="loginPwd" v-else>
                <el-input type="password" v-model="userQuery.loginPwd" />
            </el-form-item>

            <el-form-item label="姓名" prop="name">
                <el-input v-model="userQuery.name" />
            </el-form-item>

            <el-form-item label="手机" prop="phone">
                <el-input v-model="userQuery.phone" />
            </el-form-item>

            <el-form-item label="邮箱" prop="email">
                <el-input v-model="userQuery.email" />
            </el-form-item>

            <el-form-item label="账号未过期" prop="accountNoExpired">
                <el-select v-model="userQuery.accountNoExpired" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    /><!--userQuery-accountExpired中的值会和options里的值进行匹配,选中options中的"是"或"否"-->
                </el-select>
            </el-form-item>

            <el-form-item label="密码未过期" prop="credentialsNoExpired">
                <el-select v-model="userQuery.credentialsNoExpired" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="账号未锁定" prop="accountNoLocked">
                <el-select v-model="userQuery.accountNoLocked" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="账号是否启用" prop="accountEnabled">
                <el-select v-model="userQuery.accountEnabled" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
        </el-form>

        <template #footer>
            <div class="dialog-footer">
                <el-button @click="useDialogVisible = false"> 关 闭 </el-button>
                <el-button type="primary" @click="userSubmit"> 提 交 </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script>
import { defineComponent } from 'vue';

import HelloWorld from '../components/HelloWorld.vue';
import { doDelete, doGet, doPost, doPut } from '../http/httpRequest';
import { messageConfirm, messageTip } from '../util/util';
import { id } from 'element-plus/es/locales.mjs';

export default defineComponent({
    //注入父级页面提供的所有属性、函数、对象等等
    inject: ['reload'],

    setup() {},
    // setup: () => {
    // }
    components: {
        HelloWorld //主要透传条件
    },
    data() {
        return {
            /**
             * 用户列表数据
             */
            userList: [{}],
            // userId: 0,
            pageSize: 0,
            total: 0,
            //用户的弹窗,true弹出来 false不弹
            useDialogVisible: false,
            // 定义用户表单对象,初始值为空
            userQuery: {},
            // 定义用户验证的规则
            userRules: {
                loginAct: [
                    { required: true, message: '请输入登录账号:', trigger: 'blur' }
                ],
                loginPwd: [
                    { required: true, message: '请输入登录密码:', trigger: 'blur' },
                    { min: 6, max: 16, message: '登录密码长度为6-16位', trigger: 'blur' }
                ],
                name: [
                    { required: true, message: '请输入姓名:', trigger: 'blur' },
                    {
                        pattern: /^[\u4E00-\u9FA5]{1,5}$/,
                        message: '姓名必须是中文:',
                        trigger: 'blur'
                    }
                ],
                phone: [
                    { required: true, message: '请输入手机号码:', trigger: 'blur' },
                    {
                        pattern: /^1[3-9]\d{9}$/,
                        message: '手机号码格式有误',
                        trigger: 'blur'
                    }
                ],
                email: [
                    { required: true, message: '请输入邮箱:', trigger: 'blur' },
                    {
                        pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                        message: '邮箱格式有误',
                        trigger: 'blur'
                    }
                ],
                accountNoExpired: [
                    { required: true, message: '请选择账号是否未过期:', trigger: 'blur' }
                ],
                credentialsNoExpired: [
                    { required: true, message: '请选择密码是否未过期:', trigger: 'blur' }
                ],
                accountNoLocked: [
                    {
                        required: true,
                        message: '请选择账号是否未未锁定:',
                        trigger: 'blur'
                    }
                ],
                accountEnabled: [
                    { required: true, message: '请选择账号是否可用:', trigger: 'blur' }
                ]
            },
            //用户下拉列表数组
            options: [
                { label: '是', value: 1 },
                { label: '否', value: 0 }
            ],
            //用户Id数组
            userIdArray: []
        };
    },

    mounted() {
        this.getData(1);
    },

    methods: {
        //勾选或者取消勾选时,触发该函数
        handleSelectionChange(selectionDataArray) {
            this.userIdArray = [];
            selectionDataArray.forEach((data) => {
                this.userIdArray.push(data.id);
            });
            console.log(this.userIdArray);
        },
        //查询用户列表数据
        getData(current) {
            this.$forceUpdate();
            doGet('/api/users', {
                current: current //当前查询第几页
            }).then((resp) => {
                console.log(resp.data.data.list); //id-1为当前数组元素
                if (resp.data.code === 200) {
                    this.userList = resp.data.data.list;
                    this.pageSize = resp.data.data.pageSize;
                    this.total = resp.data.data.total;
                }
            });
        },

        //分页函数(current是ele plus传过来的参数)
        toPage(current) {
            this.getData(current);
        },

        //详情用户
        view(id) {
            console.log(id);

            //跳转到/dashboard/user/1路由上
            this.$router.push('/dashboard/user/' + id);
        },

        //新增用户
        add() {
            this.userQuery = {};
            this.useDialogVisible = true;
        },
        //编辑用户
        edit(id) {
            this.useDialogVisible = true;
            this.loadUser(id);
        },

        //加载用户信息
        loadUser(id) {
            doGet('/api/user/' + id, {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.userQuery = resp.data.data;
                    this.userQuery.loginPwd = '';
                }
            });
        },

        //新增用户
        userSubmit() {
            let formdata = new FormData();
            for (let field in this.userQuery) {
                formdata.append(field, this.userQuery[field]);
            }

            this.$refs.userRefFrom.validate((isValid) => {
                if (isValid) {
                    if (this.userQuery.id > 0) {
                        //编辑
                        doPut('/api/user', formdata).then((resp) => {
                            if (resp.data.code === 200) {
                                // console.log("编辑成功")
                                messageTip('编辑成功', 'success');
                                //页面刷新
                                this.reload();
                            } else {
                                // console.log("编辑失败")
                                messageTip('编辑失败', 'error');
                            }
                        });
                    } else {
                        // console.log("验证成功")
                        doPost('/api/user', formdata).then((resp) => {
                            if (resp.data.code === 200) {
                                // console.log("提交成功")
                                messageTip('提交成功', 'success');
                                //页面刷新
                                this.reload();
                            } else {
                                // console.log("提交失败")
                                messageTip('提交失败', 'error');
                            }
                        });
                    }
                } else {
                    // console.log("验证失败")
                    messageTip('验证表单失败', 'error');
                }
            });
        },

        //删除用户
        del(id) {
            messageConfirm('您确定要删除该数据吗?')
                .then(() => {
                    doDelete('/api/user/' + id, {}).then((resp) => {
                        if (resp.data.code === 200) {
                            messageTip('删除成功', 'success');
                            //页面刷新
                            this.reload();
                        } else {
                            messageTip('删除失败，原因：' + resp.data.msg, 'error');
                        }
                    });
                })
                .catch(() => {
                    //用户点击“取消”按钮就会触发catch函数
                    messageTip('取消删除', 'warning');
                });
        },

        //批量删除用户
        batchDel() {
            if (this.userIdArray.length <= 0) {
                messageTip('请选择要删除的数据', 'warning');
                return;
            } else {
                messageConfirm('您确定要删除该数据吗?')
                    .then(() => {
                        //原来是数组：[1,3,5,6,7,11,15]  --> ids = "1,3,5,6,7,11,15";
                        let ids = this.userIdArray.join(',');
                        doDelete('/api/user', { ids: ids }).then((resp) => {
                            if (resp.data.code === 200) {
                                messageTip('删除成功', 'success');
                                //页面刷新
                                this.reload();
                            } else {
                                messageTip('删除失败，原因：' + resp.data.msg, 'error');
                            }
                        });
                    })
                    .catch(() => {
                        //用户点击“取消”按钮就会触发catch函数
                        messageTip('取消删除', 'warning');
                    });
            }
        }
    }
});
</script>

<style scoped>
.el-pagination {
    margin-top: 12px;
}

.el-table {
    margin-top: 12px;
    width: 100%;
}

.el-form {
    width: 95%;
    margin: 0px;
}

.el-form-item {
    width: 100%;
    margin-left: 0px;
}

.el-select {
    width: 100%;
}

.el-table-column {
    width: 500px;
}
</style>
