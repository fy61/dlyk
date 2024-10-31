<template>
    <!-- 市场活动详情页面 -->
    <el-form
        ref="activityRemarkRefForm"
        :model="activityRemarkQuery"
        label-width="120px"
        :rules="activityRemarkRules"
    >
        <el-form-item label="ID">
            <div class="desc">{{ activityDetail.id }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="负责人">
            <div class="desc">{{ activityDetail.ownerDo.name }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="活动名称">
            <div class="desc">{{ activityDetail.name }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="开始时间">
            <div class="desc">{{ activityDetail.startDate }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="结束时间">
            <div class="desc">{{ activityDetail.endDate }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="活动预算">
            <div class="desc">{{ activityDetail.cost }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="活动描述">
            <div class="desc">{{ activityDetail.description }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="创建时间">
            <div class="desc">{{ activityDetail.createTime }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="创建人">
            <div class="desc">{{ activityDetail.createByDO.name }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="编辑时间">
            <div class="desc">{{ activityDetail.editTime }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="编辑人">
            <div class="desc">{{ activityDetail.editByDO.name }}&nbsp;</div>
        </el-form-item>

        <el-form-item label="填写备注" prop="noteContent">
            <el-input
                v-model="activityRemarkQuery.noteContent"
                :rows="8"
                type="textarea"
                placeholder="请输入活动备注"
            />
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="activityRemarkSubmit">提 交</el-button>
            <el-button @click="goBack">返 回</el-button>
        </el-form-item>
    </el-form>

    <el-table :data="activityRemarkList" style="width: 100%">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column prop="noteContent" label="备注内容" />
        <el-table-column property="createTime" label="备注时间" />
        <el-table-column property="createByDO.name" label="备注人" />
        <el-table-column property="editTime" label="编辑时间" />
        <el-table-column property="editByDO.name" label="编辑人" />
        <el-table-column label="操作" show-overflow-tooltip>
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
import { doDelete, doGet, doPost, doPut } from '../http/httpRequest.js';
import { goBack, messageTip } from '../util/util.js';

export default defineComponent({
    //注入
    inject: ['reload'],
    data() {
        return {
            //活动详情对象
            activityDetail: {
                ownerDo: {},
                createByDO: {},
                editByDO: {}
            },

            //市场活动备注对象，初始值是空
            activityRemarkQuery: {},

            //提交活动备注的验证规则
            activityRemarkRules: {
                noteContent: [
                    { Required: true, message: '请输入活动备注', trigger: 'blur' },
                    {
                        min: 5,
                        max: 255,
                        message: '活动备注长度为5-255个字符',
                        trigger: 'blur'
                    }
                ]
            },
            //活动备注的列表对象,初始值为空
            activityRemarkList: [
                {
                    createByDO: {},
                    editByDO: {}
                }
            ],
            //分页时每页显示多少条数据
            pageSize: 0,
            //分页总共查询出多少条数据
            total: 0
        };
    },

    mounted() {
        this.loadActivityDetail();
        this.getData(1);
    },

    methods: {
        //声明一下函数才可以使用
        goBack,

        //加载市场活动详情
        loadActivityDetail() {
            let id = this.$route.params.id;
            doGet('/api/activity/' + id, {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.activityDetail = resp.data.data;

                    console.log(this.activityDetail);
                    if (!this.activityDetail.ownerDo) {
                        this.activityDetail.ownerDo = {};
                    }
                    if (!this.activityDetail.createByDO) {
                        this.activityDetail.createByDO = {};
                    }
                    if (!this.activityDetail.editByDO) {
                        this.activityDetail.editByDO = {};
                    }
                }
            });
        },

        //提交活动备注
        activityRemarkSubmit() {
            this.$refs.activityRemarkRefForm.validate((isValid) => {
                if (isValid) {
                    doPost('/api/activity/remark', {
                        activityId: this.activityDetail.id,
                        noteContent: this.activityRemarkQuery.noteContent
                    }).then((resp) => {
                        if (resp.data.code === 200) {
                            messageTip('提交成功', 'success');
                            //刷新
                            this.reload();
                        } else {
                            messageTip('提交失败', 'error');
                        }
                    });
                }
            });
        },

        //查询用户列表数据
        getData(current) {
            // this.$forceUpdate();
            doGet('/api/activity/remark', {
                current: current //当前查询第几页
            }).then((resp) => {
                console.log(resp.data.data.list); //id-1为当前数组元素
                if (resp.data.code === 200) {
                    this.activityRemarkList = resp.data.data.list;
                    this.pageSize = resp.data.data.pageSize;
                    this.total = resp.data.data.total;
                }
            });
        },

        //分页函数(current是ele plus传过来的参数)
        toPage(current) {
            this.getData(current);
        }
    }
});
</script>
<style scoped>
.desc {
    background-color: #f0ffff;
    width: 100%;
    padding-left: 15px;
}
.el-form {
    margin: 0;
    width: 100%;
}
.el-form-item {
    text-align: left;
}
</style>
