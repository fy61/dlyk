<template>
    <el-form :inline="true" :model="activityQuery" :rules="activityRules">
        <el-form-item label="负责人">
            <el-select
                v-model="activityQuery.ownerId"
                placeholder="请选择负责人"
                clearable
                @click="loadOwner"
            >
                <!-- key提高渲染效率,传一个唯一值 -->
                <el-option
                    v-for="item in ownerOPtions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                />
            </el-select>
        </el-form-item>

        <el-form-item label="活动名称">
            <el-input
                v-model="activityQuery.name"
                placeholder="请输入活动名称"
                clearable
            />
        </el-form-item>

        <el-form-item label="活动时间" style="width: auto">
            <el-date-picker
                v-model="activityQuery.activityTime"
                type="datetimerange"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                value-format="YYYY-MM-DD HH:mm:ss"
            />
        </el-form-item>

        <el-form-item label="活动预算" prop="cost">
            <el-input
                v-model="activityQuery.cost"
                placeholder="请输入活动名称"
                clearable
            />
        </el-form-item>

        <el-form-item label="创建时间">
            <el-date-picker
                v-model="activityQuery.createTime"
                type="datetime"
                placeholder="请选择创建时间"
                value-format="YYYY-MM-DD HH:mm:ss"
            />
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="onSearch">搜索</el-button>
            <el-button type="primary" plain @click="onReset">重 置</el-button>
        </el-form-item>
    </el-form>

    <el-button type="primary" @click="add">录入市场活动</el-button>
    <el-button type="danger" @click="batchDel">批量删除</el-button>

    <el-table :data="activetyList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55px" />
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column property="ownerDo.name" label="负责人" />
        <el-table-column property="name" label="活动名称" show-overflow-tooltip />
        <el-table-column property="startTime" label="开始时间" show-overflow-tooltip />
        <el-table-column property="endTime" label="结束时间" show-overflow-tooltip />
        <el-table-column property="cost" label="活动预算" show-overflow-tooltip />
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
</template>

<script>
import { defineComponent } from 'vue';
import { doDelete, doGet } from '../http/httpRequest';
import { messageConfirm, messageTip } from '../util/util';

export default defineComponent({
    inject: ['reload'],
    data() {
        return {
            //市场活动搜索表单对象,初始值为空
            activityQuery: {},
            //市场活动列表对象,初始值为空
            activetyList: [
                {
                    ownerDo: {}
                }
            ],
            //分页时,每页显示多少条数据
            pageSize: 0,
            //分页时,总共查询多少条数据
            total: 0,
            //负责人的下拉列表数据
            ownerOPtions: [{}],
            //定义市场活动搜索表单验证规则
            activityRules: {
                cost: [
                    {
                        //正则表达式，
                        pattern: /^[0-9]+(\.[0-9]{2})?$/,
                        message: '活动预算必须是整数或者两位小数:',
                        trigger: 'blur'
                    }
                ]
            },
            //录入市场Id
            activityIdArray: []
        };
    },

    mounted() {
        this.getData(1);
    },

    methods: {
        getData(current) {
            let startTime = ''; //开始时间
            let endTime = ''; //结束时间
            for (let key in this.activityQuery.activityTime) {
                if (key === '0') {
                    startTime = this.activityQuery.activityTime[key];
                }
                if (key === '1') {
                    endTime = this.activityQuery.activityTime[key];
                }
            }

            this.$forceUpdate();

            doGet('/api/activitys', {
                current: current, //当前查询第几页
                //6个搜索条件参数
                ownerId: this.activityQuery.ownerId,
                name: this.activityQuery.name,
                // activityTime: this.activityQuery.activityTime,
                startTime: startTime,
                endTime: endTime,
                cost: this.activityQuery.cost,
                createTime: this.activityQuery.createTime
            }).then((resp) => {
                console.log(resp.data.data.list); //id-1为当前数组元素
                if (resp.data.code === 200) {
                    this.activetyList = resp.data.data.list;
                    this.pageSize = resp.data.data.pageSize;
                    this.total = resp.data.data.total;
                }
            });
        },

        //分页函数(current这个参数是ele-plus组件传过来，就是传的当前页)
        toPage(current) {
            this.getData(current);
        },

        //加载负责人
        loadOwner() {
            doGet('/api/owner', {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.ownerOPtions = resp.data.data;
                }
            });
        },
        //搜索
        onSearch() {
            this.getData(1);
        },

        //搜索条件重置（清空）
        onReset() {
            this.activityQuery = {};
        },
        add() {
            this.$router.push('/dashboard/activity/add');
        },

        //编辑市场活动
        edit(id) {
            this.$router.push('/dashboard/activity/edit/' + id);
        },
        //查看详情
        view(id) {
            this.$router.push('/dashboard/activity/' + id);
        },
        //勾选或者取消勾选时,触发该函数
        handleSelectionChange(selectionDataArray) {
            this.activityIdArray = [];
            selectionDataArray.forEach((data) => {
                this.activityIdArray.push(data.id);
            });
            console.log(this.activityIdArray);
        },
        //批量删除市场活动
        batchDel() {
            if (this.activityIdArray.length <= 0) {
                messageTip('请选择要删除的数据', 'warning');
                return;
            } else {
                messageConfirm('您确定要删除该数据吗?').then(() => {
                    //原来是数组：[1,3,5,6,7,11,15]  --> ids = "1,3,5,6,7,11,15";
                    let ids = this.activityIdArray.join(',');
                    doDelete('/api/activity', { ids: ids })
                        .then((resp) => {
                            if (resp.data.code === 200) {
                                messageTip('删除成功', 'success');
                                //页面刷新
                                this.reload();
                            } else {
                                messageTip('删除失败，原因：' + resp.data.msg, 'error');
                            }
                        })
                        .catch(() => {
                            //用户点击“取消”按钮就会触发catch函数
                            messageTip('取消删除', 'warning');
                        });
                });
            }
        }
    }
});
</script>

<style scoped>
.el-form {
    width: auto;
    margin: 0%;
}
.el-form-item {
    margin-left: 0%;
    width: 255px;
}
.el-table {
    margin-top: 12px;
    width: 100%;
}
.el-pagination {
    margin-top: 12px;
}
</style>
