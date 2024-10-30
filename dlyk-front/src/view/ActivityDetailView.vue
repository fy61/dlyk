<template>
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
</template>
<script>
import { defineComponent } from 'vue';
import { doDelete, doGet, doPost, doPut } from '../http/httpRequest.js';

export default defineComponent({
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
            activityRemarkRules: [{}]
        };
    },

    mounted() {
        this.loadActivityDetail();
    },

    methods: {
        //加载市场活动详情
        loadActivityDetail() {
            let id = this.$route.params.id;
            doGet('/api/activity/' + id, {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.activityDetail = resp.data.data;
                    console.log(resp.data.data);
                    // this.activityDetail = JSON.parse(JSON.stringify(resp.data.data));
                    console.log(this.activityDetail);
                    if (!this.activityDetail.ownerDo) {
                        console.log('名字和id没有');
                        this.activityDetail.ownerDo = {};
                        // this.$set(this.activityDetail, 'ownerDO', {});
                    }
                    if (!this.activityDetail.createByDO) {
                        this.activityDetail.createByDO = {};
                        // this.$set(this.activityDetail, 'createByDO', {});
                    }
                    if (!this.activityDetail.editByDO) {
                        this.activityDetail.editByDO = {};
                        // this.$set(this.activityDetail, 'editByDO', {});
                    }
                }
            });
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
